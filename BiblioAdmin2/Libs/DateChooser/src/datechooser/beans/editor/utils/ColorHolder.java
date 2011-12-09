/*
 * ColorHolder.java
 *
 * Created on 1 ������ 2006 �., 15:50
 *
 */

package datechooser.beans.editor.utils;

import java.awt.Color;

/**
 * Holdes color class.<br>
 * ������� ��� ������ �����.
 * @author Androsov Vadim
 * @since 1.0
 */
public class ColorHolder {
    
    private Color color;
    
    public ColorHolder(Color color) {
        setColor(color);
    }

    public ColorHolder() {
        setColor(Color.WHITE);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
}
