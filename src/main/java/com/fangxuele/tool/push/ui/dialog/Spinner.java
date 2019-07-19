package com.fangxuele.tool.push.ui.dialog;

import com.fangxuele.tool.push.util.ComponentUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * Spinner
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2019/7/19.
 */
@Getter
public class Spinner extends JDialog {
    private static final long serialVersionUID = -2540975077707505185L;
    private JPanel contentPane;
    private JProgressBar progressBar;

    private static final AtomicInteger PROGRESS_VALUE = new AtomicInteger();

    private static Spinner spinner;

    public Spinner() {
        setContentPane(contentPane);
        setModal(true);
        setUndecorated(true);
        ComponentUtil.setPrefersizeAndLocateToCenter(this, 420, 50);
    }

    public static Spinner getInstance() {
        if (spinner == null) {
            spinner = new Spinner();
        }
        return spinner;
    }

    public static void showSpinner() {
        Spinner spinner = getInstance();
        spinner.pack();
        spinner.getProgressBar().setIndeterminate(true);
        spinner.setVisible(true);
    }

    public static void hideSpinner() {
        spinner.getProgressBar().setIndeterminate(false);
        getInstance().setVisible(false);
    }

    public static void showSpinner(int total) {
        Spinner spinner = getInstance();
        spinner.getProgressBar().setMaximum(total);
        spinner.pack();
        spinner.setVisible(true);
    }

    public static void increase(int increaseNum) {
        PROGRESS_VALUE.addAndGet(increaseNum);
        getInstance().getProgressBar().setValue(PROGRESS_VALUE.get());
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
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-12828863));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        progressBar = new JProgressBar();
        panel1.add(progressBar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
