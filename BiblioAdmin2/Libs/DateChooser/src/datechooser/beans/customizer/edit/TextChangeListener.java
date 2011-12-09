/*
 * TextChangeListener.java
 *
 * Created on 8 ������ 2006 �., 8:11
 *
 */

package datechooser.beans.customizer.edit;

import javax.swing.event.*;

/**
 * Listens any text changes without their differentiation.<br>
 * ������ ��� ��������. ��������� ������� �� ����������� ������ ��� ���������
 * ��������� ���� ���������.
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class TextChangeListener implements DocumentListener {
    
    public void insertUpdate(DocumentEvent e) {
        textChanged(e);
    }

    public void removeUpdate(DocumentEvent e) {
        textChanged(e);
    }

    public void changedUpdate(DocumentEvent e) {
    }
    
    /**
     * Use this method to listen changes.<br>
     * ����������� ���� ����� ��� �������� �� ����������� ������.
     * @since 1.0
     */
    public abstract void textChanged(DocumentEvent e);
}
