/*
 * ClassDescriptor.java
 *
 * Created on 3 Август 2006 г., 15:58
 *
 */

package datechooser.beans.editor.descriptor;

import java.util.Locale;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * Abstract object descriptor class.
 * You must extend it to register own class in DescriptionManager.<br>
 * Абстрактный класс - описатель объектов.
 * Все классы, зарегистрированные в DescriptionManager должны
 * быть унаследованы от него.
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
     * Класс описываемого объекта.
     */
    public abstract Class getDescriptedClass();
    /**
     * Java initialization code.<br>
     * Возвращает код инициализации на Java.
     */
    public abstract String getJavaDescription(Object value);
    /**
     * Describes object for output.<br>
     * Возвращает описание объекта для вывода на экран.
     * @see ClassDescriptor#getDescription(Object, Locale)
     */
    public abstract String getDescription(Object value);
    
    /**
     * Describes object for output.<br>
     * Возвращает описание объекта для вывода на экран.
     * @param value Descripting object.<br>
     * Описываемый объект.
     * @param locale Localization.<br>
     * Локализация
     * @return Object description.<br>
     * Описание объекта
     */
    public String getDescription(Object value, Locale locale) {
        return getDescription(value) + " (" + getEditorLocaleString("default_locale_used") + ")";
    }
    /**
     * Возвращает полное имя класса.
     */
    protected String getClassName() {
        return getDescriptedClass().getName();
    }
    /**
     * Возвращает разделитель параметров.
     * Или запятая, или запятая с переходом на новую строку.
     */
    protected String getSeparator() {
        return isNewLineParameters() ? NEW_LINE_SEPARATOR : ONE_LINE_SEPARATOR;
    }
    /**
     * Must parameters list use one line.
     * Used when java code generated - if code will be too long, it is better to
     * write each parameter in new line.<br>
     * Делать ли список параметров на одной строке.
     * Используется при генарации java - кода, когда он очень длинный,
     * лучше каждый параметр делать с новой строки для удобочитаемости.
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
     * Берет ли класс на себя обработку пустых значений.
     */
    public boolean canProcessNull() {
        return false;
    }
}
