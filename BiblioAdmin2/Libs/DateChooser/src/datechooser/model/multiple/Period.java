/*
 * Period.java
 *
 * Created on 3 ���� 2006 �., 12:09
 *
 */

package datechooser.model.multiple;

import datechooser.model.DateUtils;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.*;

/**
 * Period (interval between start and end dates).<br>
 * �����, ����������� ������.
 * @author Androsov Vadim
 * @since 1.0
 */
public class Period extends DateOutputStyle implements Comparable, Serializable, Cloneable {
    
    /**
     * ������ �������
     * @since 1.0
     */
    private Calendar startDate;
    /**
     * ����� �������.
     * @since 1.0
     */
    private Calendar endDate;
    
    /**
     * Sets minimal date as startn (even if end date parameter before start).<br>
     * ��� ����������� �� ������� ���������� ������� ������� ��������������� ������� ����.
     * @param start Period start.<br>
     * ������ �������
     * @param end Period end.<br>
     * ����� �������.
     * @since 1.0
     */
    public Period(Calendar start, Calendar end) {
        set(start, end);
    }
    
    /**
     * Creates period contained one date (start=end).<br>
     * ������� ������, ��������� �� ����� ����.
     * @param aDate Date.<br>
     * ����
     * @since 1.0
     */
    public Period(Calendar aDate) {
        this(aDate, aDate);
    }
    
    /**
     * Sets minimal date as startn (even if end date parameter before start).<br>
     * ��� ����������� �� ������� ���������� ������� ������� ��������������� ������� ����.
     * @param start Period start.<br>
     * ������ �������
     * @param end Period end.<br>
     * ����� �������.
     * @since 1.0
     */
    public void set(Calendar start, Calendar end) {
        if ((start == null) || (end == null)) {
            setStartDate(start);
            setEndDate(end);
        }
        if (DateUtils.before(start, end)) {
            setStartDate(start);
            setEndDate(end);
        } else {
            setStartDate(end);
            setEndDate(start);
        }
    }
    
    /**
     * Is given date in period.<br>
     * ���������, ����� �� �������� ���� ������ �������.
     * @since 1.0
     */
    public boolean isIn(Calendar aDate) {
        return (DateUtils.after(aDate, getStartDate()) && DateUtils.before(aDate, getEndDate()) ||
                DateUtils.equals(aDate, getStartDate()) || DateUtils.equals(aDate, getEndDate()));
    }
    
    /**
     * Get start period date.<br>
     * ���������� ��������� ����.
     * @since 1.0
     */
    public Calendar getStartDate() {
        return startDate;
    }
    
    /**
     * Sets period start.<br> 
     * ������������� ���� ������ �������. ����� �������� null, �������� ������
     * � ���������������� ���������.
     * @since 1.0
     * @param startDate Start date. <b>null</b> make period invalid.<br>
     * ��������� ����. <b>null</b> ��������� ������ � ���������������� ����������.
     */
    public void setStartDate(Calendar startDate) {
        if (this.startDate == null) {
            if (startDate == null) return;
            this.startDate = (Calendar) startDate.clone();
        } else {
            if (startDate == null) {
                this.startDate = null;
            } else {
                this.startDate.setTime(startDate.getTime());
            }
        }
    }
    
    /**
     * Get end date.<br>
     * ���������� �������� ����.
     * @since 1.0
     */
    public Calendar getEndDate() {
        return endDate;
    }
    
    /**
     * Sets period end.<br> 
     * ������������� ���� ����� �������. ����� �������� null, �������� ������
     * � ���������������� ���������.
     * @since 1.0
     * @param endDate End date. <b>null</b> make period invalid.<br>
     * ���� ����� �������. <b>null</b> ��������� ������ � ���������������� ����������.
     */
    public void setEndDate(Calendar endDate) {
        if (this.endDate == null) {
            if (endDate == null) return;
            this.endDate = (Calendar) endDate.clone();
        } else {
            if (endDate == null) {
                this.endDate = null;
            } else {
                this.endDate.setTime(endDate.getTime());
            }
        }
    }
    
    /**
     * Is period equals one date.<br>
     * ������� �� ������ �� ����� ����.
     * @since 1.0
     */
    public boolean isOneDate() {
        return (getStartDate() != null) && (getEndDate() != null) &&
                (DateUtils.equals(getStartDate(), getEndDate()));
    }
    
    /**
     * Is period valid. Invalid if start or end equals null.<br> 
     * ��������� �� ������.
     * ����������� - ���� ������ ��� ����� null.
     * @since 1.0
     */
    public boolean isValid() {
        return (getStartDate() != null) && (getEndDate() != null);
    }
    
    /**
     * Compares periods. If they intersect, return 0.<br>
     * ���������� �������. ���� ��� ������������, ���������� 0.
     * @since 1.0
     */
    public int compareTo(Object o) {
        Period trg = (Period) o;
        if (isIntersects(trg)) return 0;
        return getStartDate().compareTo(trg.getStartDate());
    }
    
    /**
     * ���������� ������ � ������ ��� (�� ������ ����, ������� � �������).
     * ����� ��������� (������������ � ����� �� ���� ���),
     * ����� ��������� ������� � ������ �������.
     * @since 1.0
     */
    Collection<Calendar> listDates() {
        if (!isValid()) return null;
        ArrayList<Calendar> result = new ArrayList<Calendar>();
        Calendar buffer = (Calendar) getStartDate().clone();
        while (buffer.before(getEndDate()) || buffer.equals(getEndDate())) {
            result.add(buffer);
            buffer = (Calendar) buffer.clone();
            buffer.add(Calendar.DAY_OF_MONTH, 1);
        }
        return result;
    }
    
    /**
     * Does current period intersect another.<br>
     * ���������� �� ���������� ������ �������.
     * @since 1.0
     */
    public boolean isIntersects(Period anotherPeriod) {
        return isIn(anotherPeriod.getStartDate()) ||
                isIn(anotherPeriod.getEndDate()) ||
                anotherPeriod.isIn(getStartDate()) ||
                anotherPeriod.isIn(getEndDate());
    }
    
    /**
     * Is current period near to another.<br>
     * �������� �� ���������� ������ � �������.
     * @since 1.0
     */
    public boolean isNear(Period anotherPeriod) {
        return DateUtils.isNear(anotherPeriod.getEndDate(), getStartDate()) ||
                DateUtils.isNear(getEndDate(), anotherPeriod.getStartDate());
    }
    
    /**
     * Is current period near to date.<br>
     * �������� �� ���������� ���� � ������� ��������.
     * @since 1.0
     */
    public boolean isNear(Calendar date) {
        return DateUtils.isNear(date, getStartDate()) ||
                DateUtils.isNear(getEndDate(), date);
    }
    
    /**
     * Unites two periods. If they don't intersect, ne period contains dates
     * between source periods.<br>
     * ���������� �������. ���� ��� �� ������������, � �������������� ������
     * ���������� � ���� ����� ���������.
     * @since 1.0
     */
    public void unite(Period anotherPeriod) {
        if (getStartDate().after(anotherPeriod.getStartDate())) {
            setStartDate(anotherPeriod.getStartDate());
        }
        if (getEndDate().before(anotherPeriod.getEndDate())) {
            setEndDate(anotherPeriod.getEndDate());
        }
    }
    
    /**
     * Are periods equals. Intersecting periods are considered as equals.<br> 
     * ��������� ������� �� ���������. �������������� ������� ���������
     * (� ����� ������ ���������� ������) �������.
     * @since 1.0
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return compareTo(obj) == 0;
    }
    
    /**
     * <b>Deep</b> period clone.<br>
     * �������� ������������ �������.
     * @since 1.0
     */
    public Object clone() {
        return new Period(
                (Calendar) getStartDate().clone(),
                (Calendar) getEndDate().clone());
    }
    
    /**
     * Cast to String.<br>
     * �������������� � ������.
     * @since 1.0
     */
    public String toString() {
        return toString(getDateFormat());
    }
    
    /**
     * Cast to String.<br>
     * �������������� � ������.
     * @param fmtStyle Date format style.<br>
     * ����� ����������� ���.
     * @param locale Locale.<br>
     * �����������.
     * @since 1.0
     */
    public String toString(int fmtStyle, Locale locale) {
        return toString(DateFormat.getDateInstance(fmtStyle, locale));
    }
    
    /**
     * Cast to String.<br>
     * �������������� � ������.
     * @param dateFormat Date format.<br>
     * ����� ���.
     * @since 1.0
     */
    public String toString(DateFormat dateFormat) {
        if (!isValid()) return "invalid";
        StringBuffer ans = new StringBuffer();
        ans.append(dateFormat.format(getStartDate().getTime()));
        if (!isOneDate()) {
            ans.append(" - ");
            ans.append(dateFormat.format(getEndDate().getTime()));
        }
        return ans.toString();
    }
}
