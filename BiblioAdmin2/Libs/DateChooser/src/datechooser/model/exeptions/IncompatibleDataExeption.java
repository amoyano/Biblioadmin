/*
 * UncompatibleDataExeption.java
 *
 * Created on 8 ������� 2006 �., 7:47
 *
 */

package datechooser.model.exeptions;

/**
 * Throwed at attempt to set dates not compatible with current model state.<br>
 * ����������, ����������� ��� ������� ���������� ����, �������������� ��������
 * ��������� ������.
 * @author Androsov Vadim
 * @since 1.0
 */
public class IncompatibleDataExeption extends CalendarModelExeption {
    
    public IncompatibleDataExeption() {
    }
    
    public IncompatibleDataExeption(String mess) {
        super(mess);
    }
    
}
