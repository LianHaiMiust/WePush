package com.fangxuele.tool.push.ui.listener;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.fangxuele.tool.push.bean.UserCase;
import com.fangxuele.tool.push.bean.VersionSummary;
import com.fangxuele.tool.push.ui.ConstantsUI;
import com.fangxuele.tool.push.ui.MainWindow;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by rememberber(https://github.com/rememberber) on 2017/6/23.
 */
public class AboutListener {
    private static final Log logger = LogFactory.get();

    public static void addListeners() {
        MainWindow.mainWindow.getCompanyLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("https://gitee.com/zhoubochina/WePush"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                MainWindow.mainWindow.getCompanyLabel().setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

        });

        // 检查更新
        MainWindow.mainWindow.getCheckUpdateLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ThreadUtil.execute(() -> checkUpdate(false));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                MainWindow.mainWindow.getCheckUpdateLabel().setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
    }

    public static void checkUpdate(boolean initCheck) {
        Desktop desktop = Desktop.getDesktop();
        try {
            // 当前版本
            String currentVersion = ConstantsUI.APP_VERSION;

            // 从github获取最新版本相关信息
            String content = HttpUtil.get(ConstantsUI.CHECK_VERSION_URL);
            if (StringUtils.isEmpty(content) && !initCheck) {
                JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "检查超时，请关注GitHub Release！", "网络错误",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            content = content.replace("\n", "");

            VersionSummary versionSummary = JSON.parseObject(content, VersionSummary.class);
            // 最新版本
            String newVersion = versionSummary.getCurrentVersion();
            String versionIndex = versionSummary.getVersionIndex();
            // 版本索引
            Map<String, String> versionIndexMap = JSON.parseObject(versionIndex, Map.class);
            // 版本明细列表
            List<VersionSummary.Version> versionDetailList = versionSummary.getVersionDetailList();

            if (newVersion.compareTo(currentVersion) > 0) {
                // 当前版本索引
                int currentVersionIndex = Integer.parseInt(versionIndexMap.get(currentVersion));
                // 版本更新日志：
                StringBuilder versionLogBuilder = new StringBuilder("惊现新版本！立即下载？\n\n");
                VersionSummary.Version version;
                for (int i = currentVersionIndex + 1; i < versionDetailList.size(); i++) {
                    version = versionDetailList.get(i);
                    versionLogBuilder.append(version.getVersion()).append("\n");
                    versionLogBuilder.append(version.getTitle()).append("\n");
                    versionLogBuilder.append(version.getLog()).append("\n");
                }
                String versionLog = versionLogBuilder.toString();

                int isPush = JOptionPane.showConfirmDialog(MainWindow.mainWindow.getPushPanel(),
                        versionLog, "惊现新版本！立即下载？",
                        JOptionPane.YES_NO_OPTION);

                if (isPush == JOptionPane.YES_OPTION) {
                    desktop.browse(new URI("https://github.com/rememberber/WePush/releases"));
                }
            } else {
                if (!initCheck) {
                    JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "当前已经是最新版本！", "恭喜",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 初始化二维码
     */
    public static void initQrCode() {
        String qrCodeContent = HttpUtil.get(ConstantsUI.QR_CODE_URL);
        if (StringUtils.isNotEmpty(qrCodeContent)) {
            Map<String, String> urlMap = JSONUtil.toBean(qrCodeContent, Map.class);
            JLabel qrCodeLabel = MainWindow.mainWindow.getQrCodeLabel();

            try {
                URL url = new URL(urlMap.get("url"));
                BufferedImage image = ImageIO.read(url);
                qrCodeLabel.setIcon(new ImageIcon(image));
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e);
            }

            MainWindow.mainWindow.getAboutPanel().updateUI();
        }
    }
}
