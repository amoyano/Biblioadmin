/*
 * PeriodSet.java
 *
 * Created on 8 ������ 2006 �., 16:23
 *
 */

package datechooser.model.multiple;

import datechooser.model.DateUtils;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.util.*;

/**
 * Periods set.<br>
 * ��������� ��������.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.model.multiple.Period
 */
public class PeriodSet extends DateOutputStyle implements Serializable, Cloneable {
    
    private LinkedList<Period> data;
    private Period periodCash = null;
    private Period lastAdded = null;
    
    /**
     * Constructs empty periods set.<br>
     * �����������, ��������� ������ ������.
     * @since 1.0
     */
    public PeriodSet() {
        data = new LinkedList<Period>();
    }
    
    /**
     * Copying constructor.<br>
     * ���������� �����������.
     * @since 1.0
     */
    public PeriodSet(PeriodSet src) {
        this();
        data.addAll(src.data);
    }
    
    /**
     * Constructs set, using variable argument list. Used for automatic code
     * generation.<br>
     * �����������, ��������� ��������� �� ������ ���������� ���������� ����������.
     * ������������ ��� �������������� ��������� ����.
     * @since 1.0
     */
    public PeriodSet(Period... periods) {
        this();
        for (Period elem : periods) {
            add((Period) elem.clone());
        }
    }
    
    /**
     * Adds period to set. If intersecting periods presented - unite them.<br>
     * ��������� ������ � ���������. ���� ��� ���� �������, � �������� �� ������������
     * ��� �������� - ����������.
     * @since 1.0
     */
    public void add(Period newPeriod) {
        if (newPeriod == null) return;
        lastAdded = newPeriod;
        for (Period elem : data) {
            if (elem.isIntersects(newPeriod) || elem.isNear(newPeriod)) {
                elem.unite(newPeriod);
                return;
            }
        }
        int insPos = -1;
        for (Iterator it = data.iterator(); it.hasNext();) {
            Period elem = (Period) it.next();
            insPos++;
            if (DateUtils.before(newPeriod.getEndDate(), elem.getStartDate())) {
                data.add(insPos, newPeriod);
                return;
            }
        }
        data.addLast(newPeriod);
    }
    
    /**
     * Adds periods set.<br>
     * ��������� ��������� ��������.
     * @since 1.0
     */
    public void add(PeriodSet periods) {
        if (periods == null) return;
        data.addAll(periods.data);
//        firePropertyChange();
    }
    
    /**
     * Initialize set from another.<br>
     * �������������� ��������� ������.
     * @since 1.0
     */
    public void set(PeriodSet periods) {
        clear();
        add(periods);
    }
    
    /**
     * Adds date to set.<br>
     * ��������� ���� � ���������.
     * @since 1.0
     */
    public void add(Calendar date) {
        add(new Period(date));
    }
    
    /**
     * Adds periods collection to set.<br>
     * ��������� ��� �������� � ���������.
     * @since 1.0
     */
    public void add(Iterable<Period> newData) {
        for (Period elem : newData) {
            add(elem);
        }
    }
    
    /**
     * Initializes set by periods collection.<br>
     * �������������� ��������� ���������� ���������� ��������.
     * @since 1.0
     */
    public void set(Iterable<Period> newData) {
        clear();
        add(newData);
    }
    
    /**
     * Get all dates from periods set.
     * Creates date object for <b>each</b> day in periods set.<br>
     * ���������� ��� ���� �� ��������� ��������.
     * ��������! �� ����� ���������� ����� �����. ������ ������ � ���������
     * �������� ����������� ��������� ������� ������.
     * @see PeriodSet#getPeriods()
     * @since 1.0
     */
    public Iterable<Calendar> getDates() {
        ArrayList<Calendar> result = new ArrayList<Calendar>();
        for (Period period : data) {
            result.addAll(period.listDates());
        }
        return result;
    }
    
    /**
     * Get periods from set.<br>
     * ���������� ������� �� ���������.
     * @since 1.0
     */
    public Iterable<Period> getPeriods() {
        ArrayList<Period> result = new ArrayList<Period>();
        result.addAll(data);
        return result;
    }
    
    /**
     * Does given period intersect any period from set.<br>
     * ���������, ���������� �� �������� ������ �����-������ �� ���������.
     * @since 1.0
     */
    public boolean intersects(Period period) {
        if (period == null) {
            return false;
        }
        for (Period elem : data) {
            if (elem.isIntersects(period)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Does given periods set intersect any period from current set.<br>
     * ���������, ���������� �� ���������� ��������� �������� �������.
     * @since 1.0
     */
    public boolean intersects(PeriodSet periods) {
        for (Period srcPeriod : data) {
            for (Period trgPeriod : periods.data) {
                if (srcPeriod.isIntersects(trgPeriod)) return true;
            }
        }
        return false;
    }
    
    /**
     * Is given period near to any period from set.<br>
     * ��������� ����� �� �������� ������ ����� � ����� �� �������� � ���������.
     * @since 1.0
     */
    public boolean near(Period period) {
        for (Period srcPeriod : data) {
            if (srcPeriod.isNear(period)) return true;
        }
        return false;
    }
    
    /**
     * Does set contain given date.<br>
     * �������� �� ��������� �������� ����.
     * @since 1.0
     */
    public boolean contains(Calendar date) {
        if (date == null) {
            return false;
        }
        if (periodCash == null) {
            periodCash = new Period(date);
        } else {
            periodCash.set(date, date);
        }
        return intersects(periodCash);
    }
    
    /**
     * Clears set.<br>
     * ������� ���������.
     * @since 1.0
     */
    public void clear() {
        data.clear();
    }
    
    /**
     * Is set empty.<br>
     * ����� �� ���������.
     * @since 1.0
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }
    
    /**
     * Last added period.<br>
     * ���������� ��������� ����������� � ��������� ������.
     * @since 1.0
     */
    public Period getLastAddedPeriod() {
        return lastAdded;
    }
    
    /**
     * Minimal date from set.<br>
     * ���������� ������ (����� ������) ���� ���������.
     * @since 1.0
     */
    public Calendar getFirstDate() {
        if (data.isEmpty()) return null;
        return data.get(0).getStartDate();
    }
    
    /**
     * First period from set (before others).<br>
     * ���������� ������ (����� ������) ������ ���������.
     * @since 1.0
     */
    public Period getFirstPeriod() {
        if (data.isEmpty()) return null;
        return data.get(0);
    }
    
    /**
     * Cast to String.<br>
     * �������������� � ������.
     * @since 1.0
     */
    public String toString() {
        if (isEmpty()) return "";
        Calendar first = getFirstDate();
        if (first == null) {
            return null;
        }
        StringBuffer ans = new StringBuffer();
        boolean firstDate = true;
        for (Period elem : data) {
            if (!firstDate) {
                ans.append("; ");
            } else {
                firstDate = false;
            }
            
            ans.append(elem.toString());
        }
        return ans.toString();
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
     * @param format Date format.<br>
     * ����� ���.
     * @since 1.0
     */
    public String toString(DateFormat format) {
        Calendar first = getFirstDate();
        if (first == null) {
            return null;
        }
        StringBuffer ans = new StringBuffer();
        boolean firstDate = true;
        for (Period elem : data) {
            if (!firstDate) {
                ans.append("; ");
            } else {
                firstDate = false;
            }
            
            ans.append(elem.toString(format));
        }
        return ans.toString();
    }
    
    /**
     * Periods count in set.<br>
     * ���������� ���������� �������� �� ���������.
     * @since 1.0
     */
    public int getCount() {
        return  data.size();
    }
    
    /**
     * <b>Not deep</b> clone.<br>
     * ������������. �������� �������������. ������� �������� �� �����������.
     * @since 1.0
     */
    public Object clone(){
        return new PeriodSet(this);
    }
    
    /**
     * Does periods set contains only one date.<br>
     * ���������, ������� �� ��������� �������� �� ����� ����.
     * @since 1.0
     */
    public boolean isSingleDate() {
        if (data.size() != 1) {
            return false;
        }
        return data.get(0).isOneDate();
    }
}
