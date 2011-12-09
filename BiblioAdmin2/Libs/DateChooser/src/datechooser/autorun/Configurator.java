/*
 * Test.java
 *
 * Created on 29 Октябрь 2006 г., 20:44
 *
 */

package datechooser.autorun;

import datechooser.beans.locale.LocaleUtils;
import java.beans.*;
import javax.swing.JOptionPane;

/**
 * Main library class. Shows logo and then starts visual components customization.<br>
 * Класс, отвечающий за запуск приложения. 
 * Показывает заставку, а потом открывает редакторы.
 * @author Androsov Vadim
 * @since 1.0
 */
public class Configurator implements Runnable {
    
    private ConfigWindow configWindow = null;
    
    /**
     * Main method.<br>
     * Главная процедура.
     * @since 1.0
     * @param args Not used.<br>
     * Не используется.
     */
    public static void main(String[] args) {
        Configurator instance = new Configurator();
        instance.start(true);

    }
    
    /**
     * Starts visual customizator after logo. <br>
     * Запускает визуальный настройщик после того, как покажет логотип.
     * @param showLogo Is logo required.<br>
     * Требуется ли показывать логотип.
     * @since 1.0
     */
    public void start(boolean showLogo) {
        LocaleUtils.reset();
        Logo logo = null;
        if (showLogo) {
            logo = new Logo();
            logo.setVisible(true);
        }
        Thread starter = new Thread(this);
        starter.start();
        while (starter.isAlive()) {
            try {Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        configWindow.setVisible(true);
        if (showLogo) {
            logo.setVisible(false);
            logo.dispose();
        }
    }
    
    /**
     * Allows construct components and their customizers in background while logo is 
     * showing. <br>
     * Позволяет создавать компоненты и их настройщики в фоновом режиме, пока
     * отображается заставка.
     * @since 1.0
     */
    public void run() {
        try {
            configWindow = new ConfigWindow();
        } catch (IntrospectionException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

