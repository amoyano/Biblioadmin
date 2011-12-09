/*
 * SelectionChangedEvent.java
 *
 * Created on 24 ������ 2006 �., 17:58
 *
 */

package datechooser.events;

import java.util.EventObject;

/**
 * Event: "Selection changed".<br>
 * ������� "��������� ������".
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.events.SelectionChangedListener
 */
public class SelectionChangedEvent extends EventObject {
    
    public SelectionChangedEvent(Object source) {
        super(source);
    }
}
