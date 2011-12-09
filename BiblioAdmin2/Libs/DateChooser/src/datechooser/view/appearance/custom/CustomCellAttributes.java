/*
 * CustomCellAttributes.java
 *
 * Created on 2 ������ 2006 �., 8:56
 *
 */

package datechooser.view.appearance.custom;

import datechooser.view.appearance.*;
import java.awt.Color;
import javax.swing.border.Border;

/**
 * Customized appearance's attributes.<br>
 * ������������� �������� ������.
 * @author Androsov Vadim
 * @since 1.0
 */
public interface CustomCellAttributes extends CellAttributes {
    Color getBackgroundColor();

    Border getCellBorder();

    void setBackgroundColor(Color backgroundColor);

    void setCellBorder(Border cellBorder);
    
}
