package com.fangxuele.tool.push.ui.form;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fangxuele.tool.push.App;
import com.fangxuele.tool.push.bean.UserCase;
import com.fangxuele.tool.push.ui.UiConsts;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * <pre>
 * UserCaseForm
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2019/5/6.
 */
@Getter
@Slf4j
public class UserCaseForm {
    private JPanel userCasePanel;
    private JScrollPane userCaseScrollPane;
    private JPanel userCaseListPanel;

    private static UserCaseForm userCaseForm;

    public static UserCaseForm getInstance() {
        if (userCaseForm == null) {
            userCaseForm = new UserCaseForm();
        }
        return userCaseForm;
    }

    /**
     * 初始化他们都在用tab
     */
    public static void init() {
        // 从github获取用户案例相关信息
        String userCaseInfoContent = HttpUtil.get(UiConsts.USER_CASE_URL);
        if (StringUtils.isNotEmpty(userCaseInfoContent)) {
            List<UserCase> userCaseInfoList = JSONUtil.toList(JSONUtil.parseArray(userCaseInfoContent), UserCase.class);

            JPanel userCaseListPanel = userCaseForm.getUserCaseListPanel();
            int listSize = userCaseInfoList.size();
            userCaseListPanel.setLayout(new GridLayoutManager((int) Math.ceil(listSize / 2.0) + 1, 3, new Insets(0, 0, 0, 0), -1, -1));
            for (int i = 0; i < listSize; i++) {
                UserCase userCase = userCaseInfoList.get(i);
                JPanel userCasePanel = new JPanel();
                userCasePanel.setLayout(new GridLayoutManager(2, 2, new Insets(10, 10, 0, 0), -1, -1));

                JLabel qrCodeLabel = new JLabel();
                try {
                    URL url = new URL(userCase.getQrCodeUrl());
                    BufferedImage image = ImageIO.read(url);
                    qrCodeLabel.setIcon(new ImageIcon(image));
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("从github获取用户案例相关信息失败", e);
                }
                JLabel titleLabel = new JLabel();
                titleLabel.setText(userCase.getTitle());
                Font fnt = new Font(App.config.getFont(), Font.BOLD, 20);
                titleLabel.setFont(fnt);
                JLabel descLabel = new JLabel();
                descLabel.setText(userCase.getDesc());

                userCasePanel.add(qrCodeLabel, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                userCasePanel.add(titleLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                userCasePanel.add(descLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                userCaseListPanel.add(userCasePanel, new GridConstraints(i / 2, i % 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            }

            final Spacer spacer1 = new Spacer();
            userCaseListPanel.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
            final Spacer spacer2 = new Spacer();
            userCaseListPanel.add(spacer2, new GridConstraints((int) Math.ceil(listSize / 2.0), 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));

            userCaseListPanel.updateUI();
        }
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
        userCasePanel = new JPanel();
        userCasePanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        userCaseScrollPane = new JScrollPane();
        userCasePanel.add(userCaseScrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        userCaseListPanel = new JPanel();
        userCaseListPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        userCaseScrollPane.setViewportView(userCaseListPanel);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 1, new Insets(10, 10, 0, 0), -1, -1));
        userCasePanel.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("产品信息将会在这里展示，帮助宣传您的产品");
        panel1.add(label1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, Font.BOLD, -1, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("WePush不会收集用户的任何信息");
        panel1.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("也正因如此，我无法得知哪些优秀的企业或个人在使用WePush");
        panel1.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("如果WePush能给您带来一些便利");
        panel1.add(label4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("欢迎将您的产品邮件告诉我：rememberber@163.com");
        panel1.add(label5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return userCasePanel;
    }
}
