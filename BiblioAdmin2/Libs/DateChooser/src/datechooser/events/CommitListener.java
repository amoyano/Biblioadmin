/*
 * SelectionChangedListener.java
 *
 * Created on 24 ������ 2006 �., 18:02
 *
 */

package datechooser.events;

import java.util.EventListener;

/**
 * Commit event listenet.<br>
 * ��������� ������������� ������.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.events.CommitEvent
 */
public interface CommitListener extends EventListener { 
    /**
     * User finished input.<br>
     * ����� ���������� ��� ��������� ����� �������������.
     */
    void onCommit(CommitEvent evt);
}
