/*
 * MultyModelBehavior.java
 *
 * Created on 6 ������ 2006 �., 14:07
 *
 */

package datechooser.model.multiple;

/**
 * Model selection behavior.<br>
 * ������������, ����������� ����������� ������ ��� ������.
 * @author Androsov Vadim
 * @since 1.0
 */
public enum MultyModelBehavior {
    /**
     * Single date selection mode.<br>
     * �������� ����� ������ ����� ����.
     * @since 1.0
     */
    SELECT_SINGLE,
    /**
     * Single period selection mode.<br>
     * �������� ����� ������ ������ �������.
     * @since 1.0
     */
    SELECT_PERIOD,
    /**
     * Free selection mode (multy periods and dates selection).<br>
     * �������� ����� ���������� �������� (��� �����������)
     * @since 1.0
     */
    SELECT_ALL
}
