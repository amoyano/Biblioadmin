/*
 * PeriodChooseModel.java
 *
 * Created on 20 ��� 2006 �., 19:29
 */

package datechooser.model.multiple;

import datechooser.model.*;
import java.util.Calendar;

/**
 * Interface for multy selection model.<br>
 * ��������� ��� ������, ����������� ������������� �����.
 * @author Androsov Vadim
 * @see datechooser.model.DateChoose
 * @since 1.0
 */
public interface MultyDateChoose extends DateChoose {
    /**
     * Resets selection.<br>
     * ����� , ����� �������� �������.
     * @since 1.0
     */
    void reset();
    /**
     * Sets selection mode.
     * ��������� ������ ������.
     * @see datechooser.model.multiple.MultySelectModes
     * @since 1.0
     */
    void setMode(MultySelectModes mode, boolean add);
    /**
     * Get all selected dates.<br>
     * ���������� ��������� ����.
     * @since 1.0
     */
    Iterable<Calendar> getSelectedDates();
    /**
     * Get selected periods.<br>
     * ���������� ��������� �������.
     * @since 1.0
     * @see datechooser.model.multiple.Period
     */ 
    Iterable<Period> getSelectedPeriods();
    /**
     * Sets period or date addition mode.<br>
     * ������������� ����� ���������� ���� ��� �������.
     * @since 1.0
     */
    void setAdd(boolean add);
    /**
     * User started period selection.<br>
     * ��������� ��� ���� ������� ������ �������.
     * @since 1.0
     */
    void setPeriodSelectionStarted(boolean periodSelectionStarted);
    /**
     * Get multiple choise mode.<br>
     * ���������� ��������� ������ (�������� �������������� ������).
     * @since 1.0
     * @see datechooser.model.multiple.MultyModelBehavior
     */ 
    MultyModelBehavior getBehavior();
    /**
     * Sets multiple choise mode.<br>
     * ������������� ��������� ������ (�������� �������������� ������).
     * @since 1.0
     * @see datechooser.model.multiple.MultyModelBehavior
     */
    void setBehavior(MultyModelBehavior behavior);
    /**
     * Get selected periods.<br>
     * ���������� ��������� �������.
     * @since 1.0
     * @see datechooser.model.multiple.PeriodSet
     */ 
    PeriodSet getSelectedPeriodSet();
}
