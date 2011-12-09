/*
 * ConfigBean.java
 *
 * Created on 10 ������ 2006 �., 6:46
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
 * �����, ������������ ���� ����������/����������,
 * ������������ ����������/�������� �������� � �����
 * ���������� � ������������
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class ConfigBean extends JPanel implements PropertyChangeListener {
    
    /**
     * This string is returned by methods in case of successfull execution.  <br>
     * ������, ������������ ��� ������� ���������� �������.
     * @since 1.0
     */
    public static final String OK = PropertyDescriptorsHolder.OK;
    
    /**
     * ������������� ���������.
     */
    private DateChooserBean bean;
    /**
     * ������ ������-�����������.
     */
    private DateChooserCustomizer customizer;
    /**
     * ����, � ������� ��������� ��������� ����������.
     */
    private File file;
    /**
     * ������� ����, ��� ��� ��������� ���������.
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
     * ���������� � ���������� ����
     * @return Component's description.
     * �������� ����������
     * @since 1.0
     */
    public String toString() {
        return getBeanInfo().getBeanDescriptor().getDisplayName();
    }
    
    /**
     * Handles event "property cahnged" in customizer.
     * Sets "saved" flag = false and repaints component (if it is visual). <br>
     * ���������� ������� "��������� �������� � �����������".
     * ������� ���� ���������� � ��������������, ���� ��������� ����������.
     * @param evt Event description.
     * �������� �������
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
     * ������ ���������.
     * @param bean ���������
     */
    private void setBean(DateChooserBean bean) {
        this.bean = bean;
    }
    
    protected DateChooserCustomizer getCustomizer() {
        return customizer;
    }
    
    /**
     * ������������� ����������.
     * @param customizer ����������
     */
    private void setCustomizer(DateChooserCustomizer customizer) {
        this.customizer = customizer;
    }
    
    protected BeanInfo getBeanInfo() {
        return getCustomizer().getBeanInfo();
    }
    
    /**
     * Short component's description. <br>
     * �������� ������� �������� ����������.
     * @return Component's description.
     * �������� ����������
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
     * ��������� ��������� ������� � ����.
     * @param file File to write in.
     * ���� ��� ������
     * @return OK is operation successfull, error description otherwise.
     * OK � ������ ����� � �������� ������ � ��������� ������.
     * @since 1.0
     */
    public String writeToFile(File file) {
        setFile(file);
        setSaved(true);
        return getCustomizer().getHolder().writeToFile(file);
    }
    
    /**
     * Reads properies form file. <br>
     * ������ ��������� �� �����.
     * @param file File with properies.
     * ���� � �����������
     * @return OK if operation successfull.
     * OK � ������ ����� � �������� ������ � ��������� ������.
     * @since 1.0
     */
    public String readFromFile(File file) {
        setFile(file);
        setSaved(true);
        return getCustomizer().getHolder().readFromFile(file);
    }
    
    /**
     * File extension for concrete component properties. <br>
     * ���������� ���������� �����, ����������� ��� ����������� ����������.
     * @return Extension.
     * ����������
     * @since 1.0
     */
    public abstract String getFileExt();
    
    /**
     * File in wich properties was saved. <br>
     * ���������� ���� � ������� ��������� ��������.
     * @return Properties file, null if thre was no save operations for the component.
     * ���� �� ����������, null ���� ���������� ��� �� �������������
     * @since 1.0
     */
    public File getFile() {
        return file;
    }
    
    /**
     * Sets file when saving. <br>
     * ������������� ���� ��� ����������.
     * @param file Properties file.
     * ����, � ������� ���� ��������� ��������� ����������
     * @since 1.0
     */
    public void setFile(File file) {
        this.file = file;
    }
    
    /**
     * Was changes saved?. <br>
     * ���������� ������� ����, ���� �� ��������� ��������� �������.
     * @return true if there is no unsaved properties.
     * true, ���� ��� ������������� ���������
     * @since 1.0
     */
    public boolean isSaved() {
        return saved;
    }
    
    /**
     * Sets "saved" flag. <br>
     * ������������� ���� ���������.
     * @param saved "saved" flag.
     * ������� ���������
     * @since 1.0
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
