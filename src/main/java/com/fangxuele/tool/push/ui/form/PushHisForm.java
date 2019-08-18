package com.fangxuele.tool.push.ui.form;

import com.fangxuele.tool.push.App;
import com.fangxuele.tool.push.dao.TPushHistoryMapper;
import com.fangxuele.tool.push.domain.TPushHistory;
import com.fangxuele.tool.push.util.JTableUtil;
import com.fangxuele.tool.push.util.MybatisUtil;
import com.fangxuele.tool.push.util.UndoUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * <pre>
 * PushHisForm
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2019/5/6.
 */
@Getter
public class PushHisForm {
    private JPanel pushHisPanel;
    private JButton pushHisLeftDeleteButton;
    private JButton pushHisExportButton;
    private JTable pushHisLeftTable;
    private JLabel pushHisCountLabel;
    private JButton pushHisCopyButton;
    private JTextArea pushHisTextArea;
    private JButton resendFromHisButton;
    private JSplitPane splitPane;

    private static PushHisForm pushHisForm;

    private static TPushHistoryMapper pushHistoryMapper = MybatisUtil.getSqlSession().getMapper(TPushHistoryMapper.class);

    private PushHisForm() {
        UndoUtil.register(this);
    }

    public static PushHisForm getInstance() {
        if (pushHisForm == null) {
            pushHisForm = new PushHisForm();
        }
        return pushHisForm;
    }

    /**
     * 初始化推送历史tab
     */
    public static void init() {
        pushHisForm = getInstance();

        if ("Darcula(推荐)".equals(App.config.getTheme())) {
            Color bgColor = new Color(43, 43, 43);
            pushHisForm.getPushHisTextArea().setBackground(bgColor);
            Color foreColor = new Color(187, 187, 187);
            pushHisForm.getPushHisTextArea().setForeground(foreColor);
        }

        pushHisForm.getSplitPane().setDividerLocation((int) (App.mainFrame.getWidth() * 0.38));
        initHisLeftTable();

    }

    private static void initHisLeftTable() {
        // 导入历史管理
        String[] headerNames = {"消息名称", "状态", "时间", "id"};
        DefaultTableModel model = new DefaultTableModel(null, headerNames);
        pushHisForm.getPushHisLeftTable().setModel(model);

        DefaultTableCellRenderer hr = (DefaultTableCellRenderer) pushHisForm.getPushHisLeftTable().getTableHeader()
                .getDefaultRenderer();
        // 表头列名居左
        hr.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);

        List<TPushHistory> pushHistoryList = pushHistoryMapper.selectByMsgType(App.config.getMsgType());
        Object[] data;
        for (TPushHistory tPushHistory : pushHistoryList) {
            data = new Object[4];
            data[0] = tPushHistory.getMsgName();
            data[1] = tPushHistory.getResult();
            data[2] = tPushHistory.getCreateTime();
            data[3] = tPushHistory.getId();
            model.addRow(data);
        }

        // 隐藏id列
        JTableUtil.hideColumn(pushHisForm.getPushHisLeftTable(), 3);
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        pushHisPanel = new JPanel();
        pushHisPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(pushHisPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setDividerLocation(620);
        splitPane.setDividerSize(4);
        splitPane.setDoubleBuffered(true);
        splitPane.setInheritsPopupMenu(false);
        splitPane.setLastDividerLocation(620);
        splitPane.setResizeWeight(0.0);
        pushHisPanel.add(splitPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setMinimumSize(new Dimension(100, 24));
        panel2.setPreferredSize(new Dimension(740, 24));
        splitPane.setLeftComponent(panel2);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 4, new Insets(5, 5, 5, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel3.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        pushHisLeftDeleteButton = new JButton();
        pushHisLeftDeleteButton.setIcon(new ImageIcon(getClass().getResource("/icon/remove.png")));
        pushHisLeftDeleteButton.setText("删除");
        panel3.add(pushHisLeftDeleteButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pushHisExportButton = new JButton();
        pushHisExportButton.setIcon(new ImageIcon(getClass().getResource("/icon/export_dark.png")));
        pushHisExportButton.setText("导出");
        panel3.add(pushHisExportButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resendFromHisButton = new JButton();
        resendFromHisButton.setIcon(new ImageIcon(getClass().getResource("/icon/refresh.png")));
        resendFromHisButton.setText("重发");
        panel3.add(resendFromHisButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        pushHisLeftTable = new JTable();
        pushHisLeftTable.setAlignmentX(0.5f);
        pushHisLeftTable.setAutoResizeMode(2);
        pushHisLeftTable.setDoubleBuffered(true);
        pushHisLeftTable.setGridColor(new Color(-12236470));
        pushHisLeftTable.setIntercellSpacing(new Dimension(1, 1));
        pushHisLeftTable.setRowHeight(36);
        pushHisLeftTable.setShowVerticalLines(false);
        scrollPane1.setViewportView(pushHisLeftTable);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane.setRightComponent(panel4);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 4, new Insets(5, 10, 5, 5), -1, -1));
        panel4.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel5.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        pushHisCountLabel = new JLabel();
        pushHisCountLabel.setText("");
        panel5.add(pushHisCountLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pushHisCopyButton = new JButton();
        pushHisCopyButton.setText("复制");
        panel5.add(pushHisCopyButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel4.add(scrollPane2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        pushHisTextArea = new JTextArea();
        pushHisTextArea.setEditable(false);
        scrollPane2.setViewportView(pushHisTextArea);
    }
}
