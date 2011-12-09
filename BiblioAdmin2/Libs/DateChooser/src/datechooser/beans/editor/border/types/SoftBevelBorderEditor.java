/*
 * SoftBevelBorder.java
 *
 * Created on 31 ���� 2006 �., 18:06
 *
 */

package datechooser.beans.editor.border.types;

import javax.swing.border.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * Editor for soft bevel borders.<br>  
 * �������� ������ ���� Soft bevel
 * @author Androsov Vadim
 * @since 1.0
 */
public class SoftBevelBorderEditor 
        extends AbstractBevelBorderEditor {
    
    public SoftBevelBorderEditor() {
        setCaption(getEditorLocaleString("Soft_bevel"));
    }
    
    protected SoftBevelBorder getDefaultValue() {
        return new SoftBevelBorder(BevelBorder.RAISED);
    }

    protected SoftBevelBorder getBorderByParams() {
        return new SoftBevelBorder (bevelType,
                highlightOuter.getColor(), highlightInner.getColor(),
                shadowOuter.getColor(), shadowInner.getColor());
    }
}
