package com.fangxuele.tool.push.ui.form;

import com.fangxuele.tool.push.ui.Init;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * <pre>
 * MemberForm
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2019/5/6.
 */
@Getter
public class MemberForm {
    private JPanel memberPanelRight;
    private JPanel memberTabUpPanel;
    private JLabel memberTabCountLabel;
    private JProgressBar memberTabImportProgressBar;
    private JButton clearImportButton;
    private JPanel memberTabDownPanel;
    private JLabel importFromFileLabel;
    private JTextField memberFilePathField;
    private JButton importFromFileButton;
    private JButton memberImportExploreButton;
    private JPanel memberTabCenterPanel;
    private JTextArea importFromSqlTextArea;
    private JButton importFromSqlButton;
    private JButton memberImportTagButton;
    private JComboBox memberImportTagComboBox;
    private JButton memberImportTagFreshButton;
    private JButton memberImportTagRetainButton;
    private JButton memberImportAllButton;
    private JTable memberListTable;
    private JPanel memberPanel;
    private JButton 全选Button;
    private JButton 选择导入Button;
    private JButton 删除Button;
    private JTextField textField1;
    private JButton 搜索Button;
    private JButton 导出Button;

    public static MemberForm memberForm = new MemberForm();

    /**
     * 初始化导入用户tab
     */
    public static void init() {
        memberForm.getImportFromSqlTextArea().setText(Init.config.getMemberSql());
        memberForm.getMemberFilePathField().setText(Init.config.getMemberFilePath());
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
        memberPanel = new JPanel();
        memberPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        memberPanel.setMinimumSize(new Dimension(1200, 738));
        memberPanel.setOpaque(true);
        memberPanel.setPreferredSize(new Dimension(1200, 738));
        final JSplitPane splitPane1 = new JSplitPane();
        splitPane1.setContinuousLayout(true);
        splitPane1.setDividerLocation(720);
        splitPane1.setDividerSize(4);
        splitPane1.setDoubleBuffered(true);
        splitPane1.setLastDividerLocation(700);
        memberPanel.add(splitPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setLeftComponent(panel1);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        memberListTable = new JTable();
        memberListTable.setRowHeight(40);
        scrollPane1.setViewportView(memberListTable);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        全选Button = new JButton();
        全选Button.setIcon(new ImageIcon(getClass().getResource("/icon/selectall_dark.png")));
        全选Button.setText("全选");
        panel2.add(全选Button, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        选择导入Button = new JButton();
        选择导入Button.setIcon(new ImageIcon(getClass().getResource("/icon/import_dark.png")));
        选择导入Button.setText("选择导入");
        panel2.add(选择导入Button, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        panel2.add(textField1, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        搜索Button = new JButton();
        搜索Button.setIcon(new ImageIcon(getClass().getResource("/icon/search.png")));
        搜索Button.setText("搜索");
        panel2.add(搜索Button, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        导出Button = new JButton();
        导出Button.setIcon(new ImageIcon(getClass().getResource("/icon/export_dark.png")));
        导出Button.setText("导出");
        panel2.add(导出Button, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        删除Button = new JButton();
        删除Button.setIcon(new ImageIcon(getClass().getResource("/icon/remove.png")));
        删除Button.setText("删除");
        panel2.add(删除Button, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setRightComponent(panel3);
        memberPanelRight = new JPanel();
        memberPanelRight.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(memberPanelRight, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        memberTabUpPanel = new JPanel();
        memberTabUpPanel.setLayout(new GridLayoutManager(6, 6, new Insets(0, 0, 0, 0), -1, -1));
        memberPanelRight.add(memberTabUpPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        memberTabCountLabel = new JLabel();
        Font memberTabCountLabelFont = this.$$$getFont$$$("Microsoft YaHei UI Light", -1, 72, memberTabCountLabel.getFont());
        if (memberTabCountLabelFont != null) memberTabCountLabel.setFont(memberTabCountLabelFont);
        memberTabCountLabel.setForeground(new Color(-276358));
        memberTabCountLabel.setText("0");
        memberTabUpPanel.add(memberTabCountLabel, new GridConstraints(0, 0, 4, 3, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        memberTabImportProgressBar = new JProgressBar();
        memberTabUpPanel.add(memberTabImportProgressBar, new GridConstraints(4, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        memberTabUpPanel.add(spacer1, new GridConstraints(0, 5, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("已导入");
        memberTabUpPanel.add(label1, new GridConstraints(0, 3, 4, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        memberTabUpPanel.add(separator1, new GridConstraints(5, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        clearImportButton = new JButton();
        clearImportButton.setText("清除");
        memberTabUpPanel.add(clearImportButton, new GridConstraints(0, 4, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        memberTabDownPanel = new JPanel();
        memberTabDownPanel.setLayout(new GridLayoutManager(2, 3, new Insets(10, 5, 10, 0), -1, -1));
        memberPanelRight.add(memberTabDownPanel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        memberTabDownPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "通过文件导入", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, -1, memberTabDownPanel.getFont())));
        importFromFileLabel = new JLabel();
        importFromFileLabel.setHorizontalAlignment(11);
        importFromFileLabel.setHorizontalTextPosition(4);
        importFromFileLabel.setText("文件路径（*.txt,*.csv,*.xlsx）");
        memberTabDownPanel.add(importFromFileLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        memberFilePathField = new JTextField();
        memberTabDownPanel.add(memberFilePathField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        memberImportExploreButton = new JButton();
        memberImportExploreButton.setText("浏览");
        memberTabDownPanel.add(memberImportExploreButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        importFromFileButton = new JButton();
        importFromFileButton.setIcon(new ImageIcon(getClass().getResource("/icon/fromVCS.png")));
        importFromFileButton.setText("导入");
        memberTabDownPanel.add(importFromFileButton, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        memberTabCenterPanel = new JPanel();
        memberTabCenterPanel.setLayout(new GridLayoutManager(3, 1, new Insets(10, 5, 0, 0), -1, -1));
        memberPanelRight.add(memberTabCenterPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        memberTabCenterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "通过SQL导入", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, -1, memberTabCenterPanel.getFont())));
        importFromSqlTextArea = new JTextArea();
        importFromSqlTextArea.setText("");
        importFromSqlTextArea.setToolTipText("只要查询出的结果集只有一列openid（短信时对应的是手机号）就可以，列名不重要；例如：SELECT openid FROM t_user");
        memberTabCenterPanel.add(importFromSqlTextArea, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 150), null, 0, false));
        importFromSqlButton = new JButton();
        importFromSqlButton.setIcon(new ImageIcon(getClass().getResource("/icon/fromVCS.png")));
        importFromSqlButton.setText("导入");
        memberTabCenterPanel.add(importFromSqlButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        memberPanelRight.add(spacer2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        memberPanelRight.add(spacer3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(4, 6, new Insets(0, 5, 0, 0), -1, -1));
        memberPanelRight.add(panel4, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "通过微信公众平台导入", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, -1, panel4.getFont())));
        memberImportTagComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        memberImportTagComboBox.setModel(defaultComboBoxModel1);
        panel4.add(memberImportTagComboBox, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        memberImportAllButton = new JButton();
        Font memberImportAllButtonFont = this.$$$getFont$$$(null, Font.PLAIN, -1, memberImportAllButton.getFont());
        if (memberImportAllButtonFont != null) memberImportAllButton.setFont(memberImportAllButtonFont);
        memberImportAllButton.setIcon(new ImageIcon(getClass().getResource("/icon/fromVCS.png")));
        memberImportAllButton.setText("导入所有关注公众号的用户");
        panel4.add(memberImportAllButton, new GridConstraints(3, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        memberImportTagFreshButton = new JButton();
        Font memberImportTagFreshButtonFont = this.$$$getFont$$$(null, Font.PLAIN, -1, memberImportTagFreshButton.getFont());
        if (memberImportTagFreshButtonFont != null) memberImportTagFreshButton.setFont(memberImportTagFreshButtonFont);
        memberImportTagFreshButton.setIcon(new ImageIcon(getClass().getResource("/icon/refresh.png")));
        memberImportTagFreshButton.setText("刷新可选的标签分组");
        panel4.add(memberImportTagFreshButton, new GridConstraints(0, 3, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        memberImportTagButton = new JButton();
        Font memberImportTagButtonFont = this.$$$getFont$$$(null, Font.PLAIN, -1, memberImportTagButton.getFont());
        if (memberImportTagButtonFont != null) memberImportTagButton.setFont(memberImportTagButtonFont);
        memberImportTagButton.setIcon(new ImageIcon(getClass().getResource("/icon/fromVCS.png")));
        memberImportTagButton.setText("导入选择的标签分组-取并集");
        panel4.add(memberImportTagButton, new GridConstraints(1, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        memberImportTagRetainButton = new JButton();
        Font memberImportTagRetainButtonFont = this.$$$getFont$$$(null, Font.PLAIN, -1, memberImportTagRetainButton.getFont());
        if (memberImportTagRetainButtonFont != null)
            memberImportTagRetainButton.setFont(memberImportTagRetainButtonFont);
        memberImportTagRetainButton.setIcon(new ImageIcon(getClass().getResource("/icon/fromVCS.png")));
        memberImportTagRetainButton.setText("导入选择的标签分组-取交集");
        panel4.add(memberImportTagRetainButton, new GridConstraints(2, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return memberPanel;
    }

}
