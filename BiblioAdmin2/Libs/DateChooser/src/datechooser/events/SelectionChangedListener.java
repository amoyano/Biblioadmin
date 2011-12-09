/*
 * SelectionChangedListener.java
 *
 * Created on 24 ������ 2006 �., 18:02
 *
 */

package datechooser.events;

import java.util.EventListener;

/**
 * Selection changed listener.<br>
 * ��������� ��������� ������.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.events.SelectionChangedEvent
 */
public interface SelectionChangedListener extends EventListener { 
    /**
     * User change selection.<br>
     * ����� ���������� ��� ��������� ������ ������������.
     */
    void onSelectionChange(SelectionChangedEvent evt);
}
