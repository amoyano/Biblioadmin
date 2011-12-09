/*
 * BeanUtils.java
 *
 * Created on 28 Июнь 2007 г.
 *
 */

package datechooser.beans;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;

/**
 * JavaBeans utilities.<br>
 * Сервисный класс для JavaBean компонент.
 * @author Androsov Vadim
 * @since 1.1
 */
public class BeanUtils {
    private static AbstractDateChooserBeanInfo[] info =
            new AbstractDateChooserBeanInfo[] {null, null, null};
    private static final byte PANEL = 0;
    private static final byte COMBO = 1;
    private static final byte DIALOG = 2;
    
    private static AbstractDateChooserBeanInfo getDateChooserBeanInfo(DateChooserBean bean) {
        if (bean == null) return null;
        if (bean instanceof DateChooserPanel) {
            if (info[PANEL] == null) info[PANEL] = new DateChooserPanelBeanInfo();
            return info[PANEL];
        }
        if (bean instanceof DateChooserCombo) {
            if (info[COMBO] == null) info[COMBO] = new DateChooserComboBeanInfo();
            return info[COMBO];
        }
        if (bean instanceof DateChooserDialog) {
            if (info[DIALOG] == null) info[DIALOG] = new DateChooserDialogBeanInfo();
            return info[DIALOG];
        }
        throw new IllegalArgumentException("BeanInfo not found for " + bean.getClass().getName());
    }
    
    /**
     * Sets target datechooser bean properties using source bean.<br>
     * Устанавливает свойства целевого компонента свойствами исходного.
     * @param srcBean Source bean.<br>
     * Исходный компонент.
     * @param trgBean Target bean.<br>
     * Компонент, свойства которого нужно установить.
     * @since 1.1
     */
    public static void assignBean(DateChooserBean srcBean, DateChooserBean trgBean) {
        assignBean(srcBean, trgBean, getDateChooserBeanInfo(srcBean));
    }
    
    /**
     * Clones datechooser javabean using default constructor.<br>
     * Клонирует компонент используя конструктор по умолчанию.
     * @param bean Bean to clone.<br>
     * Компонент для клонирования.
     * @return Cloned bean.<br>
     * Клонированный компонент.
     * @since 1.1
     */
    public static DateChooserBean cloneBean(DateChooserBean bean) {
        return (DateChooserBean) cloneBean(bean, getDateChooserBeanInfo(bean));
    }
    
    /**
     * Clones javabean using default constructor and BeanInfo class.<br>
     * Клонирует компонент используя конструктор по умолчанию и класс BeanInfo.
     * @param bean Bean to clone.<br>
     * Компонент для клонирования.
     * @param beanInfo BeanInfo class.<br>
     * Информационный класс компонента.
     * @return Cloned bean.<br>
     * Клонированный компонент.
     * @since 1.1
     */
    public static Object cloneBean(Object bean, BeanInfo beanInfo) {
        if (!beanInfo.getBeanDescriptor().getBeanClass().equals(bean.getClass())) {
            throw new IllegalArgumentException("Bad beaninfo for bean");
        }
        Object newBean = null;
        try {
            newBean = bean.getClass().newInstance();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cant create bean", ex);
        }
        assignBean(bean, newBean, beanInfo);
        return newBean;
    }
    
    /**
     * Sets target bean properties using source bean.<br>
     * Устанавливает свойства целевого компонента свойствами исходного.
     * @param srcBean Source bean.<br>
     * Исходный компонент.
     * @param trgBean Target bean.<br>
     * Компонент, свойства которого нужно установить.
     * @param beanInfo BeanInfo class.<br>
     * Информационный класс компонента.
     * @since 1.1
     */
    public static void assignBean(Object srcBean, Object trgBean, BeanInfo beanInfo) {
        if (!srcBean.getClass().equals(trgBean.getClass())) {
            throw new IllegalArgumentException("Cant assign beans of different classes");
        }
        if (!beanInfo.getBeanDescriptor().getBeanClass().equals(srcBean.getClass())) {
            throw new IllegalArgumentException("Bad beaninfo for bean");
        }
        PropertyDescriptor[] descr = beanInfo.getPropertyDescriptors();
        PropertyDescriptor currDescr = null;
        for (int i = 0; i < descr.length; i++) {
            currDescr = descr[i];
            Method writeM = currDescr.getWriteMethod();
            Method readM = currDescr.getReadMethod();
            if (writeM == null) continue;
            try {
                writeM.invoke(trgBean, readM.invoke(srcBean));
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cant set property", ex);
            }
        }
    }
}
