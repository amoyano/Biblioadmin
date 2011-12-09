package datechooser.beans;

import datechooser.events.CommitEvent;
import datechooser.events.CommitListener;
import datechooser.model.multiple.MultyModelBehavior;
import datechooser.model.multiple.PeriodSet;
import java.awt.*;
import java.util.Calendar;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Table cell editor based on datechooser bean.
 * Редактор ячеек на основе компонент выбора даты.
 * @author Androsov Vadim
 * @since 1.1
 */
class BeanTableCellEditor extends AbstractCellEditor implements TableCellEditor, BeanTableCell {
    
    private DateChooserVisual bean;
    private boolean commitLocked;
    private BeanTableCellRenderer renderer;
    
    public BeanTableCellEditor(DateChooserVisual bean) {
        setCommitLocked(false);
        renderer = new BeanTableCellRenderer(bean);
        this.bean = bean;
        bean.addCommitListener(new CommitListener() {
            public void onCommit(CommitEvent evt) {
                if (!isCommitLocked()) stopCellEditing();
            }
        });
    }
    
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return renderer.getTableCellRendererComponent(table, value, isSelected, true, row, column);
    }
    
    /**
     * Returns bean editor value: Calendar or PeriodSet Object.
     * Возвращает значение компонента-редактора: объект типа Calendar или PeriodSet.
     * @see datechooser.beans.TableCellEditorAndRenderer#isUseCalendarForSingleDate
     */
    public Object getCellEditorValue() {
        if ((bean.getBehavior()  == MultyModelBehavior.SELECT_SINGLE) && isUseCalendarForSingleDate()) {
            return bean.getSelectedDate();
        }
        return bean.getSelectedPeriodSet();
    }
    
    public boolean isCommitLocked() {
        return commitLocked;
    }
    
    public void setCommitLocked(boolean commitLocked) {
        this.commitLocked = commitLocked;
    }
    
    public boolean isUseCalendarForSingleDate() {
        return renderer.isUseCalendarForSingleDate();
    }
    
    public void setUseCalendarForSingleDate(boolean useCalendarForSingleDate) {
        renderer.setUseCalendarForSingleDate(useCalendarForSingleDate);
    }
    
    
}