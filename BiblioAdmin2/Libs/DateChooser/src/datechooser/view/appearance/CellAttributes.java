/*
 * SwingAttributes.java
 *
 * Created on 2 ������ 2006 �., 0:26
 *
 */

package datechooser.view.appearance;

import java.awt.*;

/**
 * Basic date cell attributes.<br>
 * ������� �������� ������ � �����.
 * @author Androsov Vadim
 * @since 1.0
 */
public interface CellAttributes {
    Font getFont();
    
    Color getTextColor();
    
    void setFont(Font font);
    
    void setTextColor(Color textColor);
    
    /**
     * Assign one cell properties for another. Not used (reserved).<br>
     * ��������������� ���� ��������� ������� ������ ��������� ��� ���� �����
     * @since 1.0
     */
    void assign(CellAppearance newAppearance);
}
