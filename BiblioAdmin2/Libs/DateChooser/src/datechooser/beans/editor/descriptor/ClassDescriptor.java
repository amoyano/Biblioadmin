/*
 * ClassDescriptor.java
 *
 * Created on 3 ������ 2006 �., 15:58
 *
 */

package datechooser.beans.editor.descriptor;

import java.util.Locale;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * Abstract object descriptor class.
 * You must extend it to register own class in DescriptionManager.<br>
 * ����������� ����� - ��������� ��������.
 * ��� ������, ������������������ � DescriptionManager ������
 * ���� ������������ �� ����.
 * @author Androsov Vadim
 * @see DescriptionManager
 * @since 1.0
 */
public abstract class ClassDescriptor {
    
    protected static final String ONE_LINE_SEPARATOR = ", ";
    protected static final String NEW_LINE_SEPARATOR = ",\n";
    
    private static boolean newLineParameters = true;
    
    /**
     * Class of descripting object.<br> 
     * ����� ������������ �������.
     */
    public abstract Class getDescriptedClass();
    /**
     * Java initialization code.<br>
     * ���������� ��� ������������� �� Java.
     */
    public abstract String getJavaDescription(Object value);
    /**
     * Describes object for output.<br>
     * ���������� �������� ������� ��� ������ �� �����.
     * @see ClassDescriptor#getDescription(Object, Locale)
     */
    public abstract String getDescription(Object value);
    
    /**
     * Describes object for output.<br>
     * ���������� �������� ������� ��� ������ �� �����.
     * @param value Descripting object.<br>
     * ����������� ������.
     * @param locale Localization.<br>
     * �����������
     * @return Object description.<br>
     * �������� �������
     */
    public String getDescription(Object value, Locale locale) {
        return getDescription(value) + " (" + getEditorLocaleString("default_locale_used") + ")";
    }
    /**
     * ���������� ������ ��� ������.
     */
    protected String getClassName() {
        return getDescriptedClass().getName();
    }
    /**
     * ���������� ����������� ����������.
     * ��� �������, ��� ������� � ��������� �� ����� ������.
     */
    protected String getSeparator() {
        return isNewLineParameters() ? NEW_LINE_SEPARATOR : ONE_LINE_SEPARATOR;
    }
    /**
     * Must parameters list use one line.
     * Used when java code generated - if code will be too long, it is better to
     * write each parameter in new line.<br>
     * ������ �� ������ ���������� �� ����� ������.
     * ������������ ��� ��������� java - ����, ����� �� ����� �������,
     * ����� ������ �������� ������ � ����� ������ ��� ���������������.
     */
    public static boolean isNewLineParameters() {
        return newLineParameters;
    }
    /**
     * @see ClassDescriptor#isNewLineParameters()
     */
    public static void setNewLineParameters(boolean aNewLineParameters) {
        newLineParameters = aNewLineParameters;
    }
    /**
     * Does class plan to process <b>null</b> values.
     * ����� �� ����� �� ���� ��������� ������ ��������.
     */
    public boolean canProcessNull() {
        return false;
    }
}
