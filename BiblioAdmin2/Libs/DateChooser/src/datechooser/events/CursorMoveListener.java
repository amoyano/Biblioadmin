/*
 * SelectionChangedListener.java
 *
 * Created on 24 ������ 2006 �., 18:02
 *
 */

package datechooser.events;

import java.util.EventListener;

/**
 * Date selection cursor move listener.<br>
 * ��������� ����������� �������.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.events.CursorMoveEvent
 */
public interface CursorMoveListener extends EventListener {
    /**
     * Cursor moved.
     * To get date under cursor use getCurrent bean method.<br>
     * ����� ���������� ��� ����������� �������. 
     * ����, �� ������� ��������� ������ ����� ������, ������ ����� getCurrent ����������.
     */
    void onCursorMove(CursorMoveEvent evt);
}
