/*
 * SpinPane.java
 *
 * Created on 31 ���� 2006 �., 21:57
 *
 */

package datechooser.beans.editor.utils;

import java.awt.*;
import javax.swing.*;

/**
 * Panel, contains spinner and its caption.
 * ������ � ��������� �����������: �������� ������� � ��� ���������.
 * @author Androsov Vadim
 * @since 1.0
 */
public class SpinPane extends JPanel {
    public SpinPane(SpinnerModel model, String caption) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(new JLabel(caption));
        add(new JSpinner(model));
    }
}
