/*
 * LocaleUtils.java
 *
 * Created on 11 ������ 2006 �., 12:29
 *
 */

package datechooser.beans.locale;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.UIManager;

/**
 * Used for simple localization.
 * Properties are cached, so you have to call reset method after locale change.<br>
 * ����� ��� ������� ������ � �������������.
 * �������� ��������, ������� ��� ����� ����������� ���������� ������� reset.
 * @author Androsov Vadim
 * @since 1.0
 */
public class LocaleUtils {
    
    private static final int EDITOR_BUNDLE = 0;
    private static final int CALENDAR_BUNDLE = 1;
    private static final int ERROR_BUNDLE = 2;
    private static final int CONFIG_BUNDLE = 3;
    private static final int GLOBAL_BUNDLE = 4;
    private static final int BUNDLES_COUNT = 5;
    
    private static ResourceBundle[] bundles = null;

    /**
     * Localization of standart dialogs.<br>
     * ������������ ���������� ����������� ����������� ���������� ����.
     */
    public static void prepareStandartDialogButtonText() {
        UIManager.put("OptionPane.cancelButtonText", getConfigLocaleString("Cancel"));
        UIManager.put("OptionPane.yesButtonText", getConfigLocaleString("Yes"));
        UIManager.put("OptionPane.noButtonText", getConfigLocaleString("No"));
        UIManager.put("FileChooser.cancelButtonText", getConfigLocaleString("Cancel"));
        UIManager.put("FileChooser.directoryOpenButtonText", getConfigLocaleString("Open"));
        UIManager.put("FileChooser.updateButtonText", getConfigLocaleString("Update"));
    }
    /**
     * Resets localization parameters cache. Call it after locale change.<br> 
     * ���������� ��� �������������� �����. ����� �������� ���� ����� ��� ����� �����������.
     */
    public static void reset() {
        bundles = null;
    }
   /**
     * Localized editor string.<br>
     * ���������� �������������� ������ ��� ���������.
     */
   public static String getEditorLocaleString(String key) {
         return getLocaleString(EDITOR_BUNDLE, key);
    }
   /**
     * Localized calendar string.<br>
     * ���������� �������������� ������ ��� ���������.
     */
    public static String getCalendarLocaleString(String key) {
         return getLocaleString(CALENDAR_BUNDLE, key);
    }
   /**
     * Localized error or exception string.<br>
     * ���������� �������������� ������ � ��������� ������ ��� ������������� ��������.
     */
    public static String getErrorsLocaleString(String key) {
         return getLocaleString(ERROR_BUNDLE, key);
    }
   /**
     * Localized configurator string.<br>
     * ���������� �������������� ������ ��� �����������.
     */
    public static String getConfigLocaleString(String key) {
         return getLocaleString(CONFIG_BUNDLE, key);
    }
    
    private LocaleUtils() {
    }
    /**
     * ���� ����������� � ��������� ���������.
     * ���� ����� ���������� ����� ����������, ���� �� ���-�� ������, 
     * ���������� ����� �� ������������.
     */
    private static String tryGetGlobal(String key) {
        String result = null;
        try {
            result = bundles[GLOBAL_BUNDLE].getString(key);
        } finally {
            return result;
        }
    }
    
    private static String getLocaleString(int bundle, String key) {
        if (bundles == null) {
            initBundles();
        }
        String result = tryGetGlobal(key);
        if (result == null) {
            result = bundles[bundle].getString(key);
        }
        return result;
     }
    
     private static void initBundles() {
        bundles = new ResourceBundle[BUNDLES_COUNT];
        bundles[EDITOR_BUNDLE] = ResourceBundle.getBundle("datechooser/beans/locale/editors");
        bundles[CALENDAR_BUNDLE] = ResourceBundle.getBundle("datechooser/beans/locale/calendar");
        bundles[ERROR_BUNDLE] = ResourceBundle.getBundle("datechooser/beans/locale/errors");
        bundles[CONFIG_BUNDLE] = ResourceBundle.getBundle("datechooser/beans/locale/config");
        bundles[GLOBAL_BUNDLE] = ResourceBundle.getBundle("datechooser/beans/locale/global");
    }
}
