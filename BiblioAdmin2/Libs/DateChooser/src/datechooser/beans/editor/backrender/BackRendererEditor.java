/*
 * BackRendererEditor.java
 *
 * Created on 29 ������ 2006 �., 23:32
 *
 */

package datechooser.beans.editor.backrender;

import datechooser.beans.editor.VisualEditorCashed;
import javax.swing.JComponent;

/**
 * Background picture editor.<br>
 * ��������, ����������� ���������� ������� �������.
 * @see datechooser.view.appearance.AppearancesList
 * @see datechooser.view.appearance.ViewAppearance
 * @author Androsov Vadim
 * @since 1.0
 */
public class BackRendererEditor extends VisualEditorCashed {
    
    protected JComponent createEditor() {
        return new BackRendererEditorPane(this);
    }
    
}
