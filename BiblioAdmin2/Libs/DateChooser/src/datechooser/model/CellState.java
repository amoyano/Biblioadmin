/*
 * CellStates.java
 *
 * Created on 20 ��� 2006 �., 18:48
 *
 */

package datechooser.model;

/**
 * Set of cell states.<br>
 * ������������, �������� ��������� ��������� ������ - ����.
 * @author Androsov Vadim
 * @since 1.0
 */
public enum CellState {
    
    /**
     * Current date.<br>
     * ������� ����
     * @since 1.0
     */
    NOW,
    /**
     * Selected cell.<br>
     * �������
     * @since 1.0
     */
    SELECTED,
    /**
     * Unaccessible for selection cell.<br>
     * ����������� ��� ������
     * @since 1.0
     */
    UNACCESSIBLE, 
    /**
     * Usual cell.<br>
     * ������� ������
     * @since 1.0
     */
    NORMAL, 
    /**
     * If select this cell month will be scrolled.<br>
     * ������� ������, ��� �� ������ ���������� ��������� �� ������ �����.
     * @since 1.0
     */
    NORMAL_SCROLL 
}
