package com.fangxuele.tool.push.ui.form.msg;

import com.fangxuele.tool.push.dao.TMsgSmsMapper;
import com.fangxuele.tool.push.domain.TMsgSms;
import com.fangxuele.tool.push.logic.MessageTypeEnum;
import com.fangxuele.tool.push.ui.form.MainWindow;
import com.fangxuele.tool.push.util.MybatisUtil;
import com.fangxuele.tool.push.util.SqliteUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

/**
 * <pre>
 * YunpianMsgForm
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">Zhou Bo</a>
 * @since 2019/6/3.
 */
@Getter
public class YunpianMsgForm implements IMsgForm {
    private JTextArea msgYunpianMsgContentTextField;
    private JPanel yunpianMsgPanel;

    private static YunpianMsgForm yunpianMsgForm;

    private static TMsgSmsMapper msgSmsMapper = MybatisUtil.getSqlSession().getMapper(TMsgSmsMapper.class);

    @Override
    public void init(String msgName) {
        clearAllField();
        List<TMsgSms> tMsgSmsList = msgSmsMapper.selectByMsgTypeAndMsgName(MessageTypeEnum.YUN_PIAN_CODE, msgName);
        if (tMsgSmsList.size() > 0) {
            TMsgSms tMsgSms = tMsgSmsList.get(0);
            getInstance().getMsgYunpianMsgContentTextField().setText(tMsgSms.getContent());
        }
    }

    @Override
    public void save(String msgName) {
        boolean existSameMsg = false;

        List<TMsgSms> tMsgSmsList = msgSmsMapper.selectByMsgTypeAndMsgName(MessageTypeEnum.YUN_PIAN_CODE, msgName);
        if (tMsgSmsList.size() > 0) {
            existSameMsg = true;
        }

        int isCover = JOptionPane.NO_OPTION;
        if (existSameMsg) {
            // 如果存在，是否覆盖
            isCover = JOptionPane.showConfirmDialog(MainWindow.mainWindow.getMessagePanel(), "已经存在同名的历史消息，\n是否覆盖？", "确认",
                    JOptionPane.YES_NO_OPTION);
        }
        if (!existSameMsg || isCover == JOptionPane.YES_OPTION) {
            String yunpianMsgContent = getInstance().getMsgYunpianMsgContentTextField().getText();

            String now = SqliteUtil.nowDateForSqlite();

            TMsgSms tMsgSms = new TMsgSms();
            tMsgSms.setMsgType(MessageTypeEnum.YUN_PIAN_CODE);
            tMsgSms.setMsgName(msgName);
            tMsgSms.setContent(yunpianMsgContent);
            tMsgSms.setCreateTime(now);
            tMsgSms.setModifiedTime(now);

            if (existSameMsg) {
                msgSmsMapper.updateByMsgTypeAndMsgName(tMsgSms);
            } else {
                msgSmsMapper.insertSelective(tMsgSms);
            }

            JOptionPane.showMessageDialog(MainWindow.mainWindow.getMessagePanel(), "保存成功！", "成功",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static YunpianMsgForm getInstance() {
        if (yunpianMsgForm == null) {
            yunpianMsgForm = new YunpianMsgForm();
        }
        return yunpianMsgForm;
    }

    /**
     * 清空所有界面字段
     */
    public static void clearAllField() {
        getInstance().getMsgYunpianMsgContentTextField().setText("");
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        yunpianMsgPanel = new JPanel();
        yunpianMsgPanel.setLayout(new GridLayoutManager(1, 1, new Insets(10, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(10, 0, 0, 0), -1, -1));
        yunpianMsgPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "短信内容编辑", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, -1, panel1.getFont())));
        msgYunpianMsgContentTextField = new JTextArea();
        msgYunpianMsgContentTextField.setAutoscrolls(false);
        msgYunpianMsgContentTextField.setPreferredSize(new Dimension(0, 200));
        panel1.add(msgYunpianMsgContentTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return yunpianMsgPanel;
    }

}
