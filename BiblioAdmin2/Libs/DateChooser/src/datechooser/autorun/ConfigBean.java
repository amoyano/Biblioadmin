/*
 * ConfigBean.java
 *
 * Created on 10 Ноябрь 2006 г., 6:46
 *
 */

package datechooser.autorun;

import datechooser.beans.DateChooserBean;
import datechooser.beans.AbstractDateChooserBeanInfo;
import datechooser.beans.customizer.DateChooserCustomizer;
import datechooser.beans.customizer.PropertyDescriptorsHolder;

import java.beans.*;
import java.io.*;
import javax.swing.*;

/**
 * The class displays customizer/component pair.
 * It provides save/load properties functions and links component with its customizer.
 * <br>
 * Класс, отображающий пару настройщик/компоненты,
 * обеспечивает сохранение/загрузку настроек и связь
 * компонента с настройщиком
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class ConfigBean extends JPanel implements PropertyChangeListener {
    
    /**
     * This string is returned by methods in case of successfull execution.  <br>
     * Строка, возвращаемая при удачном выполнении методов.
     * @since 1.0
     */
    public static final String OK = PropertyDescriptorsHolder.OK;
    
    /**
     * Настраиваемый компонент.
     */
    private DateChooserBean bean;
    /**
     * Объект класса-настройщика.
     */
    private DateChooserCustomizer customizer;
    /**
     * Файл, в котором сохранены настройки компонента.
     */
    private File file;
    /**
     * Признак того, что все изменения сохранены.
     */
    private boolean saved;
    
    protected ConfigBean(DateChooserBean bean, DateChooserCustomizer customizer) {
        setFile(null);
        setBean(bean);
        setCustomizer(customizer);
        getCustomizer().setObject(getBean());
        getCustomizer().addPropertyChangeListener(this);
        setSaved(true);
        
        setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
    }
    
    /**
     * Describes component. <br>
     * Приведение к строковому типу
     * @return Component's description.
     * Описание компонента
     * @since 1.0
     */
    public String toString() {
        return getBeanInfo().getBeanDescriptor().getDisplayName();
    }
    
    /**
     * Handles event "property cahnged" in customizer.
     * Sets "saved" flag = false and repaints component (if it is visual). <br>
     * Обработчик события "Изменение свойства в настройщике".
     * Снимает флаг сохранения и перерисовывает, если компонент визуальный.
     * @param evt Event description.
     * Описание события
     * @since 1.0
     */
    public void propertyChange(PropertyChangeEvent evt) {
        setSaved(false);
        if (getBean() instanceof  JComponent) {
            ((JComponent)getBean()).repaint();
        }
    }
    
    protected DateChooserBean getBean() {
        return bean;
    }
    
    /**
     * Задает компонент.
     * @param bean Компонент
     */
    private void setBean(DateChooserBean bean) {
        this.bean = bean;
    }
    
    protected DateChooserCustomizer getCustomizer() {
        return customizer;
    }
    
    /**
     * Устанавливает настройщик.
     * @param customizer Настройщик
     */
    private void setCustomizer(DateChooserCustomizer customizer) {
        this.customizer = customizer;
    }
    
    protected BeanInfo getBeanInfo() {
        return getCustomizer().getBeanInfo();
    }
    
    /**
     * Short component's description. <br>
     * Получает краткое описание компонента.
     * @return Component's description.
     * Описание компонента
     * @since 1.0
     */
    public String getBeanDisplayName() {
        try {
            return getBeanInfo().getBeanDescriptor().getDisplayName();
        } catch (NullPointerException ex) {
            return "?";
        }
    }
    
    /**
     * Saves customized properties to file. <br>
     * Сохраняет настройки свойств в файл.
     * @param file File to write in.
     * Файл для записи
     * @return OK is operation successfull, error description otherwise.
     * OK в случае удачи и описание ошибки в противном случае.
     * @since 1.0
     */
    public String writeToFile(File file) {
        setFile(file);
        setSaved(true);
        return getCustomizer().getHolder().writeToFile(file);
    }
    
    /**
     * Reads properies form file. <br>
     * Читает настройки из файла.
     * @param file File with properies.
     * Файл с настройками
     * @return OK if operation successfull.
     * OK в случае удачи и описание ошибки в противном случае.
     * @since 1.0
     */
    public String readFromFile(File file) {
        setFile(file);
        setSaved(true);
        return getCustomizer().getHolder().readFromFile(file);
    }
    
    /**
     * File extension for concrete component properties. <br>
     * Возвращает расширение файла, характерной для конкретного компонента.
     * @return Extension.
     * Расширение
     * @since 1.0
     */
    public abstract String getFileExt();
    
    /**
     * File in wich properties was saved. <br>
     * Возвращает файл в который сохранены свойтсва.
     * @return Properties file, null if thre was no save operations for the component.
     * Файл со свойствами, null если сохранения еще не производилось
     * @since 1.0
     */
    public File getFile() {
        return file;
    }
    
    /**
     * Sets file when saving. <br>
     * Устанавливает файл при сохранении.
     * @param file Properties file.
     * Файл, в который были сохранены настройки компонента
     * @since 1.0
     */
    public void setFile(File file) {
        this.file = file;
    }
    
    /**
     * Was changes saved?. <br>
     * Возвращает признак того, были ли сохранены изменения свойств.
     * @return true if there is no unsaved properties.
     * true, если нет несохраненных изменений
     * @since 1.0
     */
    public boolean isSaved() {
        return saved;
    }
    
    /**
     * Sets "saved" flag. <br>
     * Устанавливает флаг изменения.
     * @param saved "saved" flag.
     * Признак изменения
     * @since 1.0
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
