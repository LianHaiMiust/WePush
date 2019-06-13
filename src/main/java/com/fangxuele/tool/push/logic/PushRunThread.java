package com.fangxuele.tool.push.logic;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fangxuele.tool.push.App;
import com.fangxuele.tool.push.logic.msgthread.AliDayuTemplateSmsMsgThread;
import com.fangxuele.tool.push.logic.msgthread.AliYunSmsMsgThread;
import com.fangxuele.tool.push.logic.msgthread.BaseMsgThread;
import com.fangxuele.tool.push.logic.msgthread.KeFuMsgThread;
import com.fangxuele.tool.push.logic.msgthread.KeFuPriorMsgThread;
import com.fangxuele.tool.push.logic.msgthread.MaTemplateMsgThread;
import com.fangxuele.tool.push.logic.msgthread.MpTemplateMsgThread;
import com.fangxuele.tool.push.logic.msgthread.TxYunSmsMsgThread;
import com.fangxuele.tool.push.logic.msgthread.YunpianSmsMsgThread;
import com.fangxuele.tool.push.ui.component.TableInCellProgressBarRenderer;
import com.fangxuele.tool.push.ui.form.PushForm;
import com.fangxuele.tool.push.ui.form.SettingForm;
import com.fangxuele.tool.push.util.ConsoleUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <pre>
 * 推送执行控制线程
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2017/6/28.
 */
public class PushRunThread extends Thread {

    private static final Log logger = LogFactory.get();

    @Override
    public void run() {
        PushForm.pushForm.getPushTotalProgressBar().setIndeterminate(true);

        PushForm.pushForm.getPushStopButton().setText("停止");
        // 初始化
        PushForm.pushForm.getPushSuccessCount().setText("0");
        PushForm.pushForm.getPushFailCount().setText("0");

        // 重置推送数据
        PushData.reset();

        // 拷贝准备的目标用户
        PushData.toSendList.addAll(PushData.allUser);
        // 总记录数
        PushData.totalRecords = PushData.toSendList.size();

        PushForm.pushForm.getPushTotalCountLabel().setText("消息总数：" + PushData.totalRecords);
        PushForm.pushForm.getPushTotalProgressBar().setMaximum((int) PushData.totalRecords);
        ConsoleUtil.consoleWithLog("消息总数：" + PushData.totalRecords);
        // 可用处理器核心数量
        PushForm.pushForm.getAvailableProcessorLabel().setText("可用处理器核心：" + Runtime.getRuntime().availableProcessors());
        ConsoleUtil.consoleWithLog("可用处理器核心：" + Runtime.getRuntime().availableProcessors());

        // 线程数
        App.config.setThreadCount(Integer.parseInt(PushForm.pushForm.getThreadCountTextField().getText()));
        App.config.save();
        ConsoleUtil.consoleWithLog("线程数：" + PushForm.pushForm.getThreadCountTextField().getText());

        // 线程池大小
        App.config.setMaxThreadPool(Integer.parseInt(PushForm.pushForm.getMaxThreadPoolTextField().getText()));
        App.config.save();
        ConsoleUtil.consoleWithLog("线程池大小：" + PushForm.pushForm.getMaxThreadPoolTextField().getText());

        // 准备消息构造器
        PushControl.prepareMsgMaker();

        // 线程数
        PushData.threadCount = Integer.parseInt(PushForm.pushForm.getThreadCountTextField().getText());

        // 初始化线程table
        String[] headerNames = {"线程", "分片区间", "成功", "失败", "总数", "当前进度"};
        DefaultTableModel tableModel = new DefaultTableModel(null, headerNames);
        PushForm.pushForm.getPushThreadTable().setModel(tableModel);
        PushForm.pushForm.getPushThreadTable().getColumn("当前进度").setCellRenderer(new TableInCellProgressBarRenderer());

        DefaultTableCellRenderer hr = (DefaultTableCellRenderer) PushForm.pushForm.getPushThreadTable().getTableHeader()
                .getDefaultRenderer();
        // 表头列名居左
        hr.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
        PushForm.pushForm.getPushThreadTable().updateUI();

        if (pushRunCheck()) {
            ConsoleUtil.consoleWithLog("推送开始……");
            // 消息数据分片以及线程纷发
            shardingAndMsgThread();
            // 时间监控
            timeKeeper();
        }

        PushForm.pushForm.getPushTotalProgressBar().setIndeterminate(false);
        ConsoleUtil.consoleWithLog("所有线程宝宝启动完毕……");
    }

    private static boolean pushRunCheck() {
        int msgType = App.config.getMsgType();
        switch (msgType) {
            case MessageTypeEnum.MP_TEMPLATE_CODE: {
                WxMpService wxMpService = PushControl.getWxMpService();
                if (wxMpService == null || wxMpService.getWxMpConfigStorage() == null) {
                    return false;
                }
                break;
            }
            case MessageTypeEnum.MA_TEMPLATE_CODE:

                WxMaService wxMaService = PushControl.getWxMaService();
                if (wxMaService == null || wxMaService.getWxMaConfig() == null) {
                    return false;
                }
                break;
            case MessageTypeEnum.KEFU_CODE: {

                WxMpService wxMpService = PushControl.getWxMpService();
                if (wxMpService.getWxMpConfigStorage() == null) {
                    return false;
                }
                break;
            }
            case MessageTypeEnum.KEFU_PRIORITY_CODE: {

                WxMpService wxMpService = PushControl.getWxMpService();
                if (wxMpService.getWxMpConfigStorage() == null) {
                    return false;
                }
                break;
            }
            case MessageTypeEnum.ALI_TEMPLATE_CODE:
                String aliServerUrl = App.config.getAliServerUrl();
                String aliAppKey = App.config.getAliAppKey();
                String aliAppSecret = App.config.getAliAppSecret();

                if (StringUtils.isEmpty(aliServerUrl) || StringUtils.isEmpty(aliAppKey)
                        || StringUtils.isEmpty(aliAppSecret)) {
                    JOptionPane.showMessageDialog(SettingForm.settingForm.getSettingPanel(),
                            "请先在设置中填写并保存阿里大于相关配置！", "提示",
                            JOptionPane.INFORMATION_MESSAGE);
                    PushForm.pushForm.getScheduleRunButton().setEnabled(true);
                    PushForm.pushForm.getPushStartButton().setEnabled(true);
                    PushForm.pushForm.getPushStopButton().setEnabled(false);
                    PushForm.pushForm.getPushTotalProgressBar().setIndeterminate(false);
                    return false;
                }
                break;
            case MessageTypeEnum.ALI_YUN_CODE:
                String aliyunAccessKeyId = App.config.getAliyunAccessKeyId();
                String aliyunAccessKeySecret = App.config.getAliyunAccessKeySecret();

                if (StringUtils.isEmpty(aliyunAccessKeyId) || StringUtils.isEmpty(aliyunAccessKeySecret)) {
                    JOptionPane.showMessageDialog(SettingForm.settingForm.getSettingPanel(),
                            "请先在设置中填写并保存阿里云短信相关配置！", "提示",
                            JOptionPane.INFORMATION_MESSAGE);
                    PushForm.pushForm.getScheduleRunButton().setEnabled(true);
                    PushForm.pushForm.getPushStartButton().setEnabled(true);
                    PushForm.pushForm.getPushStopButton().setEnabled(false);
                    PushForm.pushForm.getPushTotalProgressBar().setIndeterminate(false);
                    return false;
                }
                break;
            case MessageTypeEnum.TX_YUN_CODE:
                String txyunAppId = App.config.getTxyunAppId();
                String txyunAppKey = App.config.getTxyunAppKey();

                if (StringUtils.isEmpty(txyunAppId) || StringUtils.isEmpty(txyunAppKey)) {
                    JOptionPane.showMessageDialog(SettingForm.settingForm.getSettingPanel(),
                            "请先在设置中填写并保存腾讯云短信相关配置！", "提示",
                            JOptionPane.INFORMATION_MESSAGE);
                    PushForm.pushForm.getScheduleRunButton().setEnabled(true);
                    PushForm.pushForm.getPushStartButton().setEnabled(true);
                    PushForm.pushForm.getPushStopButton().setEnabled(false);
                    PushForm.pushForm.getPushTotalProgressBar().setIndeterminate(false);
                    return false;
                }
                break;
            case MessageTypeEnum.YUN_PIAN_CODE:
                String yunpianApiKey = App.config.getYunpianApiKey();
                if (StringUtils.isEmpty(yunpianApiKey)) {
                    JOptionPane.showMessageDialog(SettingForm.settingForm.getSettingPanel(),
                            "请先在设置中填写并保存云片网短信相关配置！", "提示",
                            JOptionPane.INFORMATION_MESSAGE);
                    PushForm.pushForm.getScheduleRunButton().setEnabled(true);
                    PushForm.pushForm.getPushStartButton().setEnabled(true);
                    PushForm.pushForm.getPushStopButton().setEnabled(false);
                    PushForm.pushForm.getPushTotalProgressBar().setIndeterminate(false);
                    return false;
                }

                break;
            default:
        }
        return true;
    }

    /**
     * 消息数据分片以及线程纷发
     */
    private static void shardingAndMsgThread() {
        Object[] data;
        int msgType = App.config.getMsgType();

        int maxThreadPoolSize = App.config.getMaxThreadPool();
        ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(maxThreadPoolSize, maxThreadPoolSize);
        BaseMsgThread thread = null;
        // 每个线程分配
        int perThread = (int) (PushData.totalRecords / PushData.threadCount) + 1;
        DefaultTableModel tableModel = (DefaultTableModel) PushForm.pushForm.getPushThreadTable().getModel();
        for (int i = 0; i < PushData.threadCount; i++) {
            int startIndex = i * perThread;
            if (startIndex > PushData.totalRecords - 1) {
                PushData.threadCount = i;
                break;
            }
            int endIndex = i * perThread + perThread;
            if (endIndex > PushData.totalRecords - 1) {
                endIndex = (int) (PushData.totalRecords);
            }
            switch (msgType) {
                case MessageTypeEnum.MP_TEMPLATE_CODE: {
                    thread = new MpTemplateMsgThread(startIndex, endIndex);
                    WxMpService wxMpService = PushControl.getWxMpService();
                    thread.setWxMpService(wxMpService);
                    break;
                }
                case MessageTypeEnum.MA_TEMPLATE_CODE:
                    thread = new MaTemplateMsgThread(startIndex, endIndex);
                    WxMaService wxMaService = PushControl.getWxMaService();
                    ((MaTemplateMsgThread) thread).setWxMaService(wxMaService);
                    break;
                case MessageTypeEnum.KEFU_CODE: {
                    thread = new KeFuMsgThread(startIndex, endIndex);
                    WxMpService wxMpService = PushControl.getWxMpService();
                    thread.setWxMpService(wxMpService);
                    break;
                }
                case MessageTypeEnum.KEFU_PRIORITY_CODE: {
                    thread = new KeFuPriorMsgThread(startIndex, endIndex);
                    WxMpService wxMpService = PushControl.getWxMpService();
                    thread.setWxMpService(wxMpService);
                    break;
                }
                case MessageTypeEnum.ALI_TEMPLATE_CODE:
                    thread = new AliDayuTemplateSmsMsgThread(startIndex, endIndex);
                    break;
                case MessageTypeEnum.ALI_YUN_CODE:
                    thread = new AliYunSmsMsgThread(startIndex, endIndex);
                    break;
                case MessageTypeEnum.TX_YUN_CODE:
                    thread = new TxYunSmsMsgThread(startIndex, endIndex);
                    break;
                case MessageTypeEnum.YUN_PIAN_CODE:
                    thread = new YunpianSmsMsgThread(startIndex, endIndex);
                    break;
                default:
            }

            thread.setTableRow(i);
            thread.setName("T-" + i);

            data = new Object[6];
            data[0] = thread.getName();
            data[1] = startIndex + "-" + endIndex;
            data[5] = 0;
            tableModel.addRow(data);

            threadPoolExecutor.execute(thread);
        }
    }

    /**
     * 时间监控
     */
    private void timeKeeper() {
        long startTimeMillis = System.currentTimeMillis();
        // 计时
        while (true) {
            if (PushData.stopedThreadCount.intValue() == PushData.threadCount) {
                if (!PushData.fixRateScheduling) {
                    PushForm.pushForm.getPushStopButton().setEnabled(false);
                    PushForm.pushForm.getPushStopButton().updateUI();
                }

                String finishTip = "发送完毕！\n";
                if (!PushData.fixRateScheduling) {
                    JOptionPane.showMessageDialog(PushForm.pushForm.getPushPanel(), finishTip, "提示",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                // 保存停止前的数据
                try {
                    ConsoleUtil.consoleWithLog("正在保存结果数据……");
                    PushForm.pushForm.getPushTotalProgressBar().setIndeterminate(true);
                    // 空跑控制
                    if (!PushForm.pushForm.getDryRunCheckBox().isSelected()) {
                        PushControl.savePushData();
                    }
                    ConsoleUtil.consoleWithLog("结果数据保存完毕！");
                } catch (IOException e) {
                    logger.error(e);
                } finally {
                    PushForm.pushForm.getPushTotalProgressBar().setIndeterminate(false);
                }

                if (!PushData.fixRateScheduling) {
                    PushForm.pushForm.getPushStartButton().setEnabled(true);
                    PushForm.pushForm.getScheduleRunButton().setEnabled(true);
                    PushForm.pushForm.getPushStartButton().updateUI();
                    PushForm.pushForm.getScheduleRunButton().updateUI();

                    PushForm.pushForm.getScheduleDetailLabel().setText("");
                } else {
                    PushForm.pushForm.getPushStopButton().setText("停止计划任务");
                }

                break;
            }

            long currentTimeMillis = System.currentTimeMillis();
            long lastTimeMillis = currentTimeMillis - startTimeMillis;
            long leftTimeMillis = (long) ((double) lastTimeMillis / (PushData.sendSuccessList.size() + PushData.sendFailList.size()) * (PushData.allUser.size() - PushData.sendSuccessList.size() - PushData.sendFailList.size()));

            String formatBetweenLast = DateUtil.formatBetween(lastTimeMillis, BetweenFormater.Level.SECOND);
            PushForm.pushForm.getPushLastTimeLabel().setText("".equals(formatBetweenLast) ? "0s" : formatBetweenLast);

            String formatBetweenLeft = DateUtil.formatBetween(leftTimeMillis, BetweenFormater.Level.SECOND);
            PushForm.pushForm.getPushLeftTimeLabel().setText("".equals(formatBetweenLeft) ? "0s" : formatBetweenLeft);

            PushForm.pushForm.getJvmMemoryLabel().setText("JVM内存占用：" + FileUtil.readableFileSize(Runtime.getRuntime().totalMemory()) + "/" + FileUtil.readableFileSize(Runtime.getRuntime().maxMemory()));

            ThreadUtil.safeSleep(100);
        }
    }

}