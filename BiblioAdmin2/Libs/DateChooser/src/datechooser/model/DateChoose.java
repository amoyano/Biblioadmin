/*
 * DateChooseModel.java
 *
 * Created on 20 ��� 2006 �., 18:05
 */

package datechooser.model;

import datechooser.events.CommitListener;
import datechooser.events.CursorMoveListener;
import datechooser.events.SelectionChangedEvent;
import datechooser.events.SelectionChangedListener;
import datechooser.model.exeptions.IncompatibleDataExeption;
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.*;

/**
 * Date selection model interface.<br>
 * ������, ����������� ��� ��������� ���� ������ ����.
 * @author Androsov Vadim
 * @since 1.0
 */
public interface DateChoose extends Serializable {
    /**
     * Rows count in day selection grid.<br>
     * ���������� ����� � ����� ������ ����.
     * @since 1.0
     */
    int getRowsCount();
    /**
     * Columns count in day selection grid.<br>
     * ���������� �������� � ����� ������ ����.
     * @since 1.0
     */
    int getColsCount();
    /**
     * State of the specified cell.<br>
     * ��������� ��������� ������.
     * @since 1.0
     */
    CellState getCellState(int row, int column);
    /**
     * Caption of the specified cell.<br>
     * ��������� ��������� ������
     * @since 1.0
     */
    String getCellCaption(int row, int column);
    /**
     * Date corresponding to the specified cell.<br>
     * ����, ��������������� ��������� ������.
     * @since 1.0
     */
    Calendar getCellDate(int row, int column);
    /**
     * Date selection constraints.<br>
     * ����������� �� ����� ����.
     * @since 1.0
     */
    void setConstraints(Calendar min, Calendar max);
    /**
     * Model needs full reoutput.<br>
     * ��������� �� ������ ����������� ����.
     * @since 1.0
     */
    boolean needsFullValidation();
    /**
     * Selects specified cell.<br>
     * ����� ������.
     * @return ��������� �� �����.
     * @since 1.0
     */
    boolean select(int row, int column);
    /**
     * Is specified date selected.<br>
     * ������� �� ������
     * @since 1.0
     */
    boolean isSelected(Calendar aDate);
    /**
     * Get selected date.<br>
     * ����, ��������������� ��������� ������.
     * @since 1.0
     */
    Calendar getSelectedDate();
    /**
     * Sets cursor on specified date.<br>
     * ������������� ������ �� �������� ����.
     * @since 1.0
     */
    void setSelectedDate(Calendar aDate);
    /**
     * Shows specified month and year.<br>
     * �������� ������� �� �������� ����� � ���.
     * @since 1.0
     */
    void showMonthYear(int month, int year);
    /**
     * Visible date.<br>
     * ������� ����.
     * @since 1.0
     */
    Calendar getVisibleDate();
    /**
     * Default date.<br>
     * ���� �� ���������.
     * @since 1.0
     */
    Calendar getDefaultDate();
    /**
     * Sets default date.<br>
     * ������������� ���� �� ���������.
     * @since 1.0
     */
    void setDefaultDate(Calendar aDate) throws IncompatibleDataExeption;
    /**
     * Shifts cursor on specified steps count vertically and hirizontally.<br>
     * ����� ��������� ������.
     * @since 1.0
     */
    void shift(int rowShift, int columnShift);
    /**
     * Trying select date under cursor, must be defined in child classes.<br>
     * �������� ���� ��� �������� � ������ ��������� ����������. ������ ���� ������������� � ��������.
     * @since 1.0
     */
    void tryApplySelection();
    /**
     * Selects null.<br>
     * ����� null
     * @since 1.0
     */
    void selectNothing();
    /**
     * Is cursor in specified position.<br>
     * ���������, ��������� �� ������ � �������� �������.
     * @since 1.0
     */
    boolean isCursor(int row, int column);
    /**
     * Jumps on specified months count.<br>
     * ������������ ������ �� �������� ���������� �������.
     * @param shift �� ������ ������� �������������.
     * ������������� �������� ���������� ������ �����.
     * @since 1.0
     */
    void monthShift(int shift);
    /**
     * Jumps on specified years count.<br>
     * ������������ ������ �� �������� ���������� ���.
     * @param shift �� ������ ��� �������������.
     * ������������� �������� ���������� ������ �����.
     * @since 1.0
     */
    void yearShift(int shift);
    /**
     * Are neighbour months visible.<br>
     * �������� �� �������� �����.
     * @since 1.0
     */
    boolean isShowNeighbourMonth();
    /**
     * Sets neighbour months visibility.<br>
     * ����������� ����� ���� �� ��������� ������.
     * @since 1.0
     */
    void setShowNeighbourMonth(boolean showNeighbourMonth);
    /**
     * Is model enabled.<br>
     * �������� ��� ���������.
     * @since 1.0
     */
    boolean isEnabled();
    /**
     * Sets model enabled.<br>
     * �������� ��� ���������.
     * @since 1.0
     */
    void setEnabled(boolean enabled);
    /**
     * Forbidden date for selection.<br>
     * ���������� ����������� ��� ������ ����.
     * @since 1.0
     */
    Iterable<Period> getForbidden();
    /**
     * Sets forbiddend for selection dates.<br>
     * ������������� ����������� ��� ������ ����.
     * @since 1.0
     */
    void setForbidden(Iterable<Period> forbiddenPeriods);
    /**
     * Get maximal enabled date.<br>
     * ���������� ������������ ����.
     * @since 1.0
     */
    Calendar getMaxConstraint();
    /**
     * Get minimal enabled date.<br>
     * ���������� ����������� ����.
     * @since 1.0
     */
    Calendar getMinConstraint();
    /**
     * Sets maximal date.<br>
     * ������������� ������������ ����.
     * @since 1.0
     */
    void setMaxConstraint(Calendar maxConstraint);
    /**
     * Sets minimal date.<br>
     * ������������� ����������� ����.
     * @since 1.0
     */
    void setMinConstraint(Calendar minConstraint);    
    /**
     * Locale.<br>
     * ���������� �����������.
     * @since 1.0
     */
    Locale getLocale();
    /**
     * Sets locale.<br>
     * ������������� �����������.
     * @since 1.0
     */
    void setLocale(Locale locale);
    /**
     * Fires "Selection changed" event.<br>
     * ��������� ������� "��������� ������".
     * @since 1.0
     */
    void fireSelectionChange();
    /**
     * Commits selection.<br>
     * ���������� �����
     * @since 1.0
     */
    void commit();
    /**
     * Is auto month scroll enabled.<br>
     * ���������, �������� �� �������������� ���������.
     * @since 1.0
     */
    boolean isAutoScroll();
    /**
     * Sets auto month scroll enabled.<br>
     * ������������� ����� �������������� ���������.
     * @since 1.0
     */
    void setAutoScroll(boolean autoScroll);
    /**
     * Is model locked (cursor is moving but no selection available).<br>
     * ��������� �� ������������ �� ���������.
     * @since 1.0
     */
    boolean isLocked();
    /**
     * Sets lock.<br>
     * �������� ��������� ���������� ����������.
     * @since 1.0
     */
    void setLocked(boolean locked);
    /**
     * True if no selected dates (null selection).<br>
     * ���������� ������, ���� �� ������� �� ����� ����.
     * @since 1.0
     */
    boolean isNothingSelected();
    /**
     * Selects nothing (null).<br>
     * ������� ����� �� ���� ���.
     * @since 1.0
     */
    void setNothingSelected(boolean nothingSelected);
    /**
     * Get date under cursor.<br>
     * ���������� ����, �� ������� ��������� ������.
     * @since 1.0
     */
    Calendar getCurrent();
    /**
     * Selects specified date.<br>
     * �������� �������� ����.
     * @since 1.0
     */
    boolean select(Calendar aDate);
    /**
     * Allows null selection.<br>
     * ��������� ��������� ��� ��������� �� �������� �� ����� ����.
     * @since 1.0
     */
    void setNothingAllowed(boolean allow);
    /**
     * Is null selection allowed.<br>
     * �������� �� ������ �����.
     * @since 1.0
     */
    boolean isNothingAllowed();
    /**
     * Adds property change listener.<br>
     * ��������� ��������� ������� "��������� ��������".
     * @since 1.0
     */
    void addPropertyChangeListener(PropertyChangeListener listener);
    /**
     * Removes property change listener.<br>
     * ������� ��������� ������� "��������� ��������".
     * @since 1.0
     */
    void removePropertyChangeListener(PropertyChangeListener listener);
    /**
     * Adds cursor move listener.<br>
     * ��������� ��������� ������� "����������� �������".
     * @since 1.0
     */
    void addCursorMoveListener(CursorMoveListener listener);
    /**
     * Removes cursor move listener.<br>
     * ������� ��������� ������� "����������� �������".
     * @since 1.0
     */
    void removeCursorMoveListener(CursorMoveListener listener);
    /**
     * Adds selection change listener.<br>
     * ��������� ��������� ������� "��������� ������".
     * @since 1.0
     */
    void addSelectionChangedListener(SelectionChangedListener listener);
    /**
     * Removes selection change listener.<br>
     * ������� ��������� ������� "��������� ������".
     * @since 1.0
     */
    void removeSelectionChangedListener(SelectionChangedListener listener);
    /**
     * Adds commit selection event listener.<br>
     * ��������� ��������� ������� "������������� ������".
     * @since 1.0
     */
    void addCommitListener(CommitListener listener);
    /**
     * Removes commit selection event listener.<br>
     * ������� ��������� ������� "������������� ������".
     * @since 1.0
     */
    void removeCommitListener(CommitListener listener);
    
}
