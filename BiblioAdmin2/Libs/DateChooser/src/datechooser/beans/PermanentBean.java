/*
 * PermanentBean.java
 *
 * Created on 26 ������ 2006 �., 16:37
 *
 */

package datechooser.beans;

import datechooser.beans.customizer.DateChooserCustomizer;
import java.io.*;



/**
 * Service class. It is user for loading any components properies from file.
 * Files are created by customizer (run library file to access customizer).<br>
 * ��������� �����. ����������� ��� ������������� �������� �������� ����������
 * (������ �� �������������� � ����������) �� �����. ����� ��������� �����������,
 * ������ � �������� ����� ��������, �������� ����������.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.beans.customizer.DateChooserCustomizer
 */
public class PermanentBean {
    
    private static final DateChooserCustomizer customizers[] =
            new DateChooserCustomizer[] {null, null, null};
    
    private static final int PANEL = 0;
    private static final int DIALOG = 0;
    private static final int COMBO = 0;
    
    
    
    private static boolean saveBeanContext = false;
    /**
     * Clear cached customizer classes.
     * Use only if you turned on property "save component's context"
     * (saveBeanContext). <br>
     * ������� ������ �� ������������ ������ ������������.
     * �������� ����� ����� ����� ������ ���� �� �������� ��������
     * "��������� �������� �����������" (saveBeanContext).
     * @see PermanentBean#isSaveBeanContext()
     * @since 1.0
     */
    public static void dispose() {
        for (int i = 0; i < customizers.length; i++) {
            customizers[i] = null;
        }
    }
    /**
     * Lets store component's context.
     * For properties saving needs to create customizer class instance and initialize
     * it by bean's properties. This is a long operation. You can turn on customizer's
     * caching (setting parameter "true"). But if you do not plan load/save component's
     * properties frequently turn it off to save memory. You need to call dispose
     * method if turned this ptoperty on after all loading/saving operations finished.<br>
     *
     * ��������� ��������� �������� ����������.
     * ���� � ��� ��� ��� ���������� ��������� ������� ��������� ������-�����������
     * � ������������������� ��� ���������� ����������. ��� �������� ������ ��������,
     * ��������� ��������� ����� ����� ����������� ����� �������� ���������� ���������
     * ������������ (��������� �������� �������� true). ���� �� �������� ��������� ������
     * �������, �������� ����� ���������, ����� �������� ���������� � ������ ��������
     * ��������. ���� �� �������� �������� - ����� �������� ������� ������ ��
     * �����������, �������� ����� dispose.
     * @see PermanentBean#dispose()
     * @since 1.0
     */
    public static boolean isSaveBeanContext() {
        return saveBeanContext;
    }
    /**
     *
     * @see PermanentBean#isSaveBeanContext()
     * @since 1.0
     */
    public static void setSaveBeanContext(boolean aSaveBeanContext) {
        saveBeanContext = aSaveBeanContext;
    }
    
    private static String loadBeanParameters(int beanID, Class customizerClass , Object bean, InputStream from) {
        DateChooserCustomizer customizer = customizers[beanID];
        if (customizer == null) {
            try {
                customizer = (DateChooserCustomizer) customizerClass.newInstance();
            } catch (Exception ex) {
                return ex.getClass().getName();
            }
        }
        customizer.setObject(bean);
        String result = customizer.getHolder().readFromStream(from);
        if (isSaveBeanContext()) {
            customizers[beanID] = customizer;
        } else {
            customizers[beanID] = null;
        }
        return result;
    }
    /**
     * Loads bean's parameters from input stream.
     * ��������� ��������� ������ �� ������ �����.
     * @param panel Components for properties assignment.<br>
     * ���������, �������� ����� ��������� ��������.
     * @since 1.0
     */
    public static String loadBeanParameters(DateChooserPanel panel, InputStream from) {
        return loadBeanParameters(PANEL, DateChooserPanelCustomizer.class, panel, from);
    }
    /**
     * Loads bean's parameters from input stream.
     * ��������� ��������� ������ �� ������ �����.
     * @param dialog Components for properties assignment.<br>
     * ���������, �������� ����� ��������� ��������.
     * @since 1.0
     */
    public static String loadBeanParameters(DateChooserDialog dialog, InputStream from) {
        return loadBeanParameters(DIALOG, DateChooserDialogCustomizer.class, dialog, from);
    }
    /**
     * Loads bean's parameters from input stream.
     * ��������� ��������� ������ �� ������ �����.
     * @param combo Components for properties assignment.<br>
     * ���������, �������� ����� ��������� ��������.
     * @since 1.0
     */
    public static String loadBeanParameters(DateChooserCombo combo, InputStream from) {
        return loadBeanParameters(COMBO, DateChooserComboCustomizer.class, combo, from);
    }
    /**
     * Loads bean's parameters from input stream.
     * ��������� ��������� ������ �� ������ �����.
     * @param panel Components to store properties.<br>
     * ���������, �������� �������� ������ ���� ���������.
     * @since 1.0
     * @throws java.io.FileNotFoundException If properies file not found.<br>
     * ���������, ���� ���� �� ������.
     */
    public static String loadBeanParameters(DateChooserPanel panel, File from) throws FileNotFoundException {
        return loadBeanParameters(panel, new FileInputStream(from));
    }
    /**
     * Loads bean's parameters from input stream.
     * ��������� ��������� ������ �� ������ �����.
     * @param dialog Components for properties assignment.<br>
     * ���������, �������� ����� ��������� ��������.
     * @since 1.0
     * @throws java.io.FileNotFoundException If properies file not found.<br>
     * ���������, ���� ���� �� ������.
     */
    public static String loadBeanParameters(DateChooserDialog dialog, File from) throws FileNotFoundException {
        return loadBeanParameters(dialog, new FileInputStream(from));
    }
    /**
     * Loads bean's parameters from input stream.
     * ��������� ��������� ������ �� ������ �����.
     * @param combo Components for properties assignment.<br>
     * ���������, �������� ����� ��������� ��������.
     * @since 1.0
     * @throws java.io.FileNotFoundException If properies file not found.<br>
     * ���������, ���� ���� �� ������.
     */
    public static String loadBeanParameters(DateChooserCombo combo, File from) throws FileNotFoundException {
        return loadBeanParameters(combo, new FileInputStream(from));
    }
}
