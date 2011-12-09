/*
 * BeanTableCell.java
 *
 * Created on 28 Июнь 2007 г., 19:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datechooser.beans;

/**
 * Table cells editor and renderer common options.
 * @author Vadim
 * @since 1.1
 */
public interface BeanTableCell {
    /**
     * getCellEditorValue returns Calendar object if true, PeriodSet otherwise.
     * Ignored if bean behavior is not MultyModelBehavior.SELECT_SINGLE.<br>
     * getCellEditorValue возвращает объект типа Calendar если истина,
     * PeriodSet в противном случае. Игнорируется если свойство
     * "поведение" (behavior) компонента не MultyModelBehavior.SELECT_SINGLE.
     * @see datechooser.model.multiple.PeriodSet
     * @see datechooser.model.multiple.MultyModelBehavior
     * @since 1.1
     */
    public boolean isUseCalendarForSingleDate();
    
    /**
     *
     * @see datechooser.beans.BeanTableCell#isUseCalendarForSingleDate
     */
    public void setUseCalendarForSingleDate(boolean useCalendarForSingleDate);
}
