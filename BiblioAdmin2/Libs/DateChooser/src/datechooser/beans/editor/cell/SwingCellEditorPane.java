/*
 * SwingCellEditorPane.java
 *
 * Created on 29 ������� 2006 �., 13:38
 *
 */

package datechooser.beans.editor.cell;

import java.awt.*;
import javax.swing.*;

/**
 * Panel for swing appearance.<br>
 * ������ ��� �������������� swing - ������������� (��� ��������� ������������
 * ���������� ���������� ���������� swing)
 * @see datechooser.view.appearance.swing.SwingCellAppearance
 * @author Androsov Vadim
 * @since 1.0
 */
class SwingCellEditorPane extends CellEditorPane {
    
    public SwingCellEditorPane(MainCellEditorPane parentEditor) {
        super(parentEditor);
    }
    
    protected void generateInterface() {
        setLayout(new GridLayout(1, 3, 5, 5));
        add(createFontChooseButton());
        add(createTextColorChooseButton());
        add(createCursorColorChooseButton());
       setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
     }

    protected void updateEditorState() {
    }
}
