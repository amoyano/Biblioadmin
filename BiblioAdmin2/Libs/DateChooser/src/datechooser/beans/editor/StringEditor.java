/*
 * StringEditor.java
 *
 * Created on 5 ������ 2006 �., 7:28
 *
 */

package datechooser.beans.editor;

import datechooser.beans.editor.descriptor.DescriptionManager;
import java.beans.PropertyEditorSupport;

/**
 * Editor for string properties.
 * Needs for uniform properties store.<br>
 * ��������-�������� ��� ������� ���������� ����.
 * ������������ ��� �������������� ���������� �������.
 * @author Androsov Vadim
 * @since 1.0
 */
public class StringEditor extends PropertyEditorSupport {
    
    public String getAsText() {
        return (String) getValue();
    }

    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        setValue(text);
        firePropertyChange();
    }

    public String getJavaInitializationString() {
        return DescriptionManager.describeJava(getValue(), String.class);
    }
}
