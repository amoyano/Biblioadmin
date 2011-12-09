/*
 * CellRenderer.java
 *
 * Created on 25 ������� 2006 �., 8:39
 *
 */

package datechooser.view.appearance;

import java.awt.*;
import java.io.Serializable;

/**
 * Abstract class renders cell.<br>
 * ������� ����� ��� ������������� �����.
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class CellRenderer implements Serializable {
    /**
     * ���� �� ������� ������, ��������� ��� ������.
     * @since 1.0
     */
    private static final int CURSOR_PART = 5;
    /**
     * ������ ������� �� ���� ������ � ��������.
     * @since 1.0
     */
    private static final int CURSOR_SHIFT = 2;
    
    /**
     * ������� � ������ ������� ������ ����� ���������� ����� ������.
     * @since 1.0
     */
    private static final int CURSOR_BOLD = 3;
    
    private static final BasicStroke boldStroke = new BasicStroke(2f);
    private static final BasicStroke usualStroke = new BasicStroke(1f);
    private static Stroke savedStroke;
    
    /**
     * Draw cell method.<br>
     * ����� ��������� ������.
     * @param g Graphics.<br>
     * ����������� ��������.
     * @param c Palette component. <br>
     * ���������, �� ������� �������������� ���������.
     * @param text Cell text.<br>
     * ����� ������.
     * @param width Cell width.<br>
     * ������ ������.
     * @param height Cell height.<br>
     * ������ ������.
     * @param isCursor Draw cursor.<br>
     * �������� ������.
     * @since 1.0
     */
    public abstract void render(Graphics2D g, Component c, String text, int width, int height, boolean isCursor);
    /**
     * @see CellRenderer#render(Graphics2D, Component, String, int, int, boolean)
     * @since 1.0
     */
    public void render(Graphics2D g, Component c, String text, int width, int height) {
        render(g, c, text, width, height, false);
    }
    /**
     * ��������� �������.
     * @since 1.0
     */
    protected void paintCursor(Graphics2D g2d, Rectangle rec, Color color) {
        g2d.setColor(color);
        int minSize = rec.height < rec.width ? rec.height : rec.width;
        int cursorSize = minSize / CURSOR_PART;
        
        savedStroke = g2d.getStroke();
        
        if (cursorSize > CURSOR_BOLD) {
            g2d.setStroke(boldStroke);
        } else {
            g2d.setStroke(usualStroke);
        }
        int cursorShift = CURSOR_SHIFT;//minSize / CURSOR_SHIFT;
        
        int x = rec.x + cursorShift;
        int y = rec.y + cursorShift;
        g2d.drawLine(x, y, x + cursorSize, y);
        g2d.drawLine(x, y, x, y + cursorSize);
        
        x = rec.x + rec.width - cursorShift;
        y = rec.y + cursorShift;
        g2d.drawLine(x, y, x - cursorSize, y);
        g2d.drawLine(x, y, x, y + cursorSize);
        
        x = rec.x + rec.width - cursorShift;
        y = rec.y + rec.height - cursorShift;
        g2d.drawLine(x, y, x - cursorSize, y);
        g2d.drawLine(x, y, x, y - cursorSize);
        
        x = rec.x + cursorShift;
        y = rec.y + rec.height - cursorShift;
        g2d.drawLine(x, y, x + cursorSize, y);
        g2d.drawLine(x, y, x, y - cursorSize);
        
        g2d.setStroke(savedStroke);
    }
}
