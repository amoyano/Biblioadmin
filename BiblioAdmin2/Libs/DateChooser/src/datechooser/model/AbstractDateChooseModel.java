/*
 * DefaultDateChooseModel.java
 *
 * Created on 20 ��� 2006 �., 20:02
 *
 */

package datechooser.model;

import datechooser.events.*;
import datechooser.model.exeptions.IncompatibleDataExeption;
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;
import static datechooser.beans.locale.LocaleUtils.getErrorsLocaleString;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;

import java.util.*;
import javax.swing.event.EventListenerList;


/**
 * Dafault date selection model.
 * Uses one fully visible month and partially two neighbour months.
 * Provides basic functionality for month scrolling.
 * Only selection functions are abstract.<br>
 * ������ ������ ���� �� ���������.
 * ������������ ������ ����� � �������� ���������� � ���������.
 * ������������ ��� ������� ���������������� �� ��������� �������.
 * ���������� ������ ������� ����������������� ������.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.model.DateChoose
 */
public abstract class AbstractDateChooseModel implements DateChoose {
    
    private int rowsCount;
    private int colsCount;
    private int selRow;
    private int selCol;
    
    private boolean isValid;
    
    private boolean enabled;
    private boolean autoScroll;
    private boolean showNeighbourMonth;
    private Locale locale;
    private int firstWeekDay;
    
    private PropertyChangeSupport changeSupport;
    protected boolean changeEventsOn;
    
    private EventListenerList listenerList = new EventListenerList();
    
    /**
     * ������ ������� ���� �� ��������� ��� ������.
     */
    private Calendar first;
    /**
     * ����������� ���������� ��� ������ ����.
     * ==null - ������� ����, ��� ����������� �����������.
     */
    private Calendar minConstraint;
    /**
     * ������������ ���������� ��� ������ ����.
     * ==null - ������� ����, ��� ����������� �����������.
     */
    private Calendar maxConstraint;
    /**
     * ������� ����.
     */
    private Calendar current;
    private Calendar cursor;
    private Calendar defaultDate;
    private Calendar tempDate;
    private PeriodSet forbidden = null;
    private boolean locked;
    
    private boolean nothingAllowed;
    
    protected abstract void selectColumn(int column);
    
    
    /**
     * Constructor for abstract model.<br> 
     * ����������� ����������� ������ ������.
     * @param current Current date.<br>
     * ������� ����.
     * @param rowsCount Rows count in day selection grid.<br>
     * ���������� ����� � ����� ������ ����.
     * @param colsCount Columns count in day selection grid.<br>
     * ���������� ������� � ����� ������ ����.
     */
    public AbstractDateChooseModel(Calendar current, int rowsCount, int colsCount) {
        
        changeSupport = new PropertyChangeSupport(this);
        setChangeEventsOn(false);
        
        this.current = (Calendar)current.clone();
        
        tempDate = (Calendar)current.clone();
        first = (Calendar)current.clone();
        defaultDate = (Calendar)current.clone();
        cursor = (Calendar)current.clone();
        
        forbidden = new PeriodSet();
        
        setAutoScroll(true);
        setShowNeighbourMonth(true);
        setEnabled(true);
        setLocked(false);
        setNothingAllowed(true);
        
        defaultDate = (Calendar) current.clone();
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
        
        setLocale(Locale.getDefault());
        
        initFirst();
        invalidate();
        
        setMinConstraint(null);
        setMaxConstraint(null);
        
        setChangeEventsOn(true);
    }
    
    public int getRowsCount() {
        return rowsCount;
    }
    
    public int getColsCount() {
        return colsCount;
    }
    
    public boolean isCursor(int row, int column) {
        tempDate = getCellDate(row, column);
        return DateUtils.equals(tempDate, getCurrent());
    }
    
    public CellState getCellState(int row, int column) {
        tempDate = getCellDate(row, column);
        if (tempDate.get(Calendar.MONTH) !=
                getNextMonth(first.get(Calendar.MONTH)))  {
            return CellState.NORMAL_SCROLL;
        }
        if (!isEnabled()) {
            return CellState.UNACCESSIBLE;
        }
        if (!checkConstraints(tempDate) || isDateForbidden(tempDate)) {
            return CellState.UNACCESSIBLE;
        }
        if (isSelected(tempDate)) {
            return CellState.SELECTED;
        }
        if ((getDefaultDate() != null) && (DateUtils.equals(tempDate, getDefaultDate()))) {
            return CellState.NOW;
        }
        return CellState.NORMAL;
    }
    
    public String getCellCaption(int row, int column) {
        return getCellDate(row, column).get(Calendar.DAY_OF_MONTH) + "";
    }
    
    public Calendar getCellDate(int row, int column) {
        //����� ����� ������� �����������!
        tempDate.setTime(first.getTime());
        tempDate.add(Calendar.DAY_OF_MONTH, (row ) * 7 + column);
        return tempDate;
    }
    
    public void setConstraints(Calendar min, Calendar max) {
        setMinConstraint(min);
        setMaxConstraint(max);
    }
    
    /**
     * Note: returns validation flag and <b>resets</b> it.<br>
     * ��������! ��������� ������ ������������� ����������, ���������� ��� -
     * ���� ������������� �� ����, �� ������ �� ��������.
     */
    public boolean needsFullValidation() {
        if (isValid) {
            return false;
        } else {
            isValid = false;
            return true;
        }
    }
    
    public boolean select(int row, int column) {
        if ((row < 0) && (column >= 0)) {
            selectColumn(column);
        }
        if (!isEnabled()) return false;
        tempDate = getCellDate(row, column);
        return select(tempDate);
    }
    
    private boolean isInVisibleMonth(Calendar aDate) {
        int visibleMonth = getNextMonth(first.get(Calendar.MONTH));
        return (aDate.get(Calendar.MONTH) == visibleMonth) &&
                (aDate.get(Calendar.YEAR) ==
                first.get(Calendar.YEAR) + ((visibleMonth == 0) ? 1 : 0));
    }
    
    public boolean select(Calendar aDate) {
        if (aDate == null) {
            return true; //���� ��� �� ���� �������
        }
        if (!isEnabled()) return false;
        tempDate.setTime(aDate.getTime());
        if ((!isInVisibleMonth(tempDate)) && (!isAutoScroll())) {
            return false;
        }
        if (DateUtils.equals(tempDate, getCurrent())) {
            return false;
        }
        if (!checkConstraints(tempDate)) {
            return false;
        }
        getCurrent().setTime(tempDate.getTime());
        
        selRow = getSelectedRow();
        selCol = getSelectedColumn();
        
        if (!isInVisibleMonth(tempDate)) {
            initFirst();
            invalidate();
        }
        firePropertyChange("selected", null, aDate);
        fireCursorMove();
        return true;
    }
    
    /**
     * ���������� ���������� ���� �� ������� �������� �� ��������.
     */
    private int getDaysPassed() {
        return first.getActualMaximum(Calendar.DAY_OF_MONTH) -
                first.get(Calendar.DAY_OF_MONTH) + 1 +
                getCurrent().get(Calendar.DAY_OF_MONTH);
    }
    
    private int getSelectedRow() {
        return getDaysPassed() / getColsCount();
    }
    
    private int getSelectedColumn() {
        return getDaysPassed() % getColsCount();
    }
    
    protected void invalidate() {
        isValid = false;
    }
    
    /**
     * ��������� ����������� �� ���������� ���� ���������� ���������.
     * @param check ����������� ����
     * @return ���� ���� ��������� ���������� ������.
     */
    private boolean checkConstraints(Calendar check) {
        if (getMinConstraint() != null) {
            if (getMinConstraint().after(check)) {
                return false;
            }
        }
        if (getMaxConstraint() != null) {
            if (getMaxConstraint().before(check)) {
                return false;
            }
        }
//        if (forbidden != null) {
//            if (forbidden.contains(check)) {
//                return false;
//            }
//        }
        return true;
    }
    
    
    /**
     * ���������� ������� �������� ��� ������ � ��������� � ������ ������.
     */
    private int getDayOfWeek(int calendarConstant) {
        return calendarConstant >= getFirstWeekDay() ?
            (calendarConstant - getFirstWeekDay() + 1) :
            ((7 - getFirstWeekDay() + 1) + calendarConstant);
    }
    
    /**
     * �������� ��������� �����.
     * ��������!!! ������ ���������� � ����.
     */
    private  int getNextMonth(int month) {
        if (month == 11) {
            return 0;
        }
        return month + 1;
    }
    
    /**
     * ���������� ����, ������� ��������� � ����� ������� ���� ���� ������.
     * (���������� ������� ����)
     */
    private void initFirst() {
        initFirst(getCurrent());
    }
    
    /**
     * ���������� ����, ������� ��������� � ����� ������� ���� ���� ������.
     * (�������� - ����, ������� ������ ���� �����)
     */
    private void initFirst(Calendar aDate) {
        first.set(aDate.get(Calendar.YEAR), aDate.get(Calendar.MONTH), 1);
        int cellsPassed = 0;
        
        // �������� ���� ������ ��� ������� �����.
        int firstDayOfWeek = getDayOfWeek(first.get(Calendar.DAY_OF_WEEK));
        if (firstDayOfWeek == 1) {
            /*
             * ���� ������ ����� ������ �� �����������, ������ �������
             * ������� �� ����������� ������.
             * ��� ����� �������� ������.
             */
            cellsPassed = 7;
        } else {
            /*
             * ����� ������� � ������������ ���������� ����� �������� ������.
             * ��� ����� �������� ������� ���� �� ���������� ������������.
             */
            cellsPassed = firstDayOfWeek - 1;
        }
        first.add(Calendar.DAY_OF_MONTH, -cellsPassed);
        /*
         * ������ ���������� ��� �������� ������.
         */
        cellsPassed += aDate.get(Calendar.DAY_OF_MONTH);
        /*
         * ����� �������� ������� ������� ����� ����� ������ �� ����������
         * �������� (�������� ������� ������ ���� ��������).
         */
        selRow = cellsPassed / getColsCount();
        selCol = cellsPassed - getColsCount() * selRow - 1;
    }
    
    private static boolean equalsYearMonth(Calendar dat1, Calendar dat2) {
        return (dat1.get(Calendar.YEAR) == dat2.get(Calendar.YEAR)) &&
                (dat1.get(Calendar.MONTH) == dat2.get(Calendar.MONTH));
    }
    
    public Calendar getCurrent() {
        return current;
    }
    
    public void showMonthYear(int month, int year) {
        tempDate.set(year, month, 1);
        initFirst(tempDate);
        invalidate();
        firePropertyChange("year_month", null, null);
    }
    
    public Calendar getVisibleDate() {
        //����� �����, ��������� ��� ������� ����� ������ (first)
        tempDate.setTime(first.getTime());
        tempDate.set(Calendar.DAY_OF_MONTH, 1);
        tempDate.add(Calendar.MONTH, 1);
        return tempDate;
    }
    
    public Calendar getDefaultDate() {
        return defaultDate;
    }
    
    public void setDefaultDate(Calendar aDate) throws IncompatibleDataExeption {
        if (isDateForbidden(aDate)) {
            throw new IncompatibleDataExeption(getErrorsLocaleString("Date_forbidden"));
        }
        defaultDate.setTime(aDate.getTime());
    }
    
    /**
     * ����� �� �������� ���������� ��������� ���� (����, �������, ���).
     */
    private void dateShift(int field, int shift) {
        tempDate.setTime(getCurrent().getTime());
        tempDate.add(field, shift);
        select(tempDate);
    }
    
    public void shift(int rowShift, int columnShift) {
        if ((rowShift == 0) && (columnShift == 0)) return;
        dateShift(Calendar.DAY_OF_MONTH, rowShift * getColsCount() + columnShift);
    }
    
    public void monthShift(int shift) {
        if (shift == 0) return;
        dateShift(Calendar.MONTH, shift);
    }
    
    public void yearShift(int shift) {
        if (shift == 0) return;
        dateShift(Calendar.YEAR, shift);
    }
    
    public boolean isAutoScroll() {
        return autoScroll;
    }
    
    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }
    
    public boolean isShowNeighbourMonth() {
        return showNeighbourMonth;
    }
    
    public void setShowNeighbourMonth(boolean showNeighbourMonth) {
        this.showNeighbourMonth = showNeighbourMonth;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        boolean oldEnabled = isEnabled();
        this.enabled = enabled;
        firePropertyChange("enabled", oldEnabled, enabled);
    }
    
    public PeriodSet getForbiddenSet() {
        return forbidden;
    }
    
    protected boolean isForbiddenDefault(PeriodSet forbiddenPeriods) {
        return (forbiddenPeriods != null) && (forbiddenPeriods.contains(getDefaultDate()));
    }
    
    public void setForbiddenSet(PeriodSet forbiddenPeriods) throws IncompatibleDataExeption {
        if (isForbiddenDefault(forbiddenPeriods)) {
            throw new IncompatibleDataExeption(getErrorsLocaleString("Forbidden_default"));
        }
        PeriodSet oldForbid = getForbiddenSet();
        forbidden.set(forbiddenPeriods);
        firePropertyChange("forbidDates", null, null);
    }
    
    public Iterable<Period> getForbidden() {
        return forbidden.getPeriods();
    }
    
    public void setForbidden(Iterable<Period> forbiddenPeriods) {
        forbidden.set(forbiddenPeriods);
        firePropertyChange("forbidDates", null, null);
    }
    
    protected boolean isPeriodForbidden(Period period) {
        return (forbidden != null) && forbidden.intersects(period);
    }
    
    protected boolean isDateForbidden(Calendar date) {
        return (forbidden != null) && forbidden.contains(date);
    }
    
    public Calendar getMinConstraint() {
        return minConstraint;
    }
    
    public void setMinConstraint(Calendar minConstraint) {
        this.minConstraint = minConstraint;
    }
    
    public Calendar getMaxConstraint() {
        return maxConstraint;
    }
    
    public void setMaxConstraint(Calendar maxConstraint) {
        this.maxConstraint = maxConstraint;
    }
    
    public void setSelectedDate(Calendar aDate) {
        select(aDate);
        tryApplySelection();
    }
    
    public Locale getLocale() {
        return locale;
    }
    
    public void setLocale(Locale locale) {
        if (locale == null) return;
        Locale oldLocale = getLocale();
        this.locale = locale;
        setFirstWeekDay(Calendar.getInstance(locale).getFirstDayOfWeek());
        initFirst();
        firePropertyChange("locale", oldLocale, locale);
    }
    
    private int getFirstWeekDay() {
        return firstWeekDay;
    }
    
    private void setFirstWeekDay(int firstWeekDay) {
        this.firstWeekDay = firstWeekDay;
    }
    
    public void commit() {
        fireCommit();
    }
    
    public final void tryApplySelection() {
        if (isLocked()) return;
        applySelection();
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    public void setNothingAllowed(boolean allow) {
        if (isNothingSelected() && (!allow)) return;
        boolean wasAllowed = isNothingAllowed();
        nothingAllowed = allow;
        firePropertyChange("nothingAllowed", wasAllowed, isNothingAllowed());
    }
    
    public boolean isNothingAllowed() {
        return nothingAllowed;
    }
    
    public boolean isChangeEventsOn() {
        return changeEventsOn;
    }
    
    public void setChangeEventsOn(boolean changeEventsOn) {
        this.changeEventsOn = changeEventsOn;
    }
    
    public void selectNothing() {
        if (!isNothingAllowed()) return;
        applySelectNothing();
    }
    
    
    
    public void addSelectionChangedListener(SelectionChangedListener listener) {
        listenerList.add(SelectionChangedListener.class, listener);
    }
    
    public void removeSelectionChangedListener(SelectionChangedListener listener) {
        listenerList.remove(SelectionChangedListener.class, listener);
    }
    
    public void fireSelectionChange() {
        SelectionChangedEvent evt = new SelectionChangedEvent(this);
        SelectionChangedListener[] listeners =
                listenerList.getListeners(SelectionChangedListener.class);
        for (SelectionChangedListener listener : listeners) {
            listener.onSelectionChange(evt);
        }
    }
    
    public void addCommitListener(CommitListener listener) {
        listenerList.add(CommitListener.class, listener);
    }
    
    public void removeCommitListener(CommitListener listener) {
        listenerList.remove(CommitListener.class, listener);
    }
    
    private void fireCommit() {
        CommitEvent evt = new CommitEvent(this);
        CommitListener[] listeners =
                listenerList.getListeners(CommitListener.class);
        for (CommitListener listener : listeners) {
            listener.onCommit(evt);
        }
    }
    
    public void addCursorMoveListener(CursorMoveListener listener) {
        listenerList.add(CursorMoveListener.class, listener);
    }
    
    public void removeCursorMoveListener(CursorMoveListener listener) {
        listenerList.remove(CursorMoveListener.class, listener);
    }
    
    protected void fireCursorMove() {
        CursorMoveEvent evt = new CursorMoveEvent(this);
        CursorMoveListener[] listeners =
                listenerList.getListeners(CursorMoveListener.class);
        for (CursorMoveListener listener : listeners) {
            listener.onCursorMove(evt);
        }
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
    public void firePropertyChange(String name, Object oldValue, Object newValue) {
        if (!isChangeEventsOn()) return;
        changeSupport.firePropertyChange(name, oldValue, newValue);
    }
    
    /**
     * ������ ������� ������� ������ ��� ��������. ��� ������ ������ ������� ��
     * ���������� ��������� ������.
     */
    protected abstract void applySelection();
    
    /**
     * ������� �� ������� �� ����� ����.
     */
    protected abstract void applySelectNothing();
}
