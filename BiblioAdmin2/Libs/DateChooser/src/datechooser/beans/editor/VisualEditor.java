/*
 * VisualEditorCashed.java
 *
 * Created on 24 ������ 2006 �., 9:19
 *
 */

package datechooser.beans.editor;

import java.beans.PropertyEditorSupport;
import javax.swing.JComponent;

/**
 * Superclass for all visual properties editors in this library.<br>
 * ������ ��� ���� ����������, ������� ���������� ���������������� ��������.
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class VisualEditor extends PropertyEditorSupport {
    
     public boolean supportsCustomEditor() {
        return true;
    }

    public JComponent getCustomEditor() {
        return createEditor();
    }

    protected abstract JComponent createEditor();
}
