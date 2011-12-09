/*
 * Test.java
 *
 * Created on 29 ������� 2006 �., 20:44
 *
 */

package datechooser.autorun;

import datechooser.beans.locale.LocaleUtils;
import java.beans.*;
import javax.swing.JOptionPane;

/**
 * Main library class. Shows logo and then starts visual components customization.<br>
 * �����, ���������� �� ������ ����������. 
 * ���������� ��������, � ����� ��������� ���������.
 * @author Androsov Vadim
 * @since 1.0
 */
public class Configurator implements Runnable {
    
    private ConfigWindow configWindow = null;
    
    /**
     * Main method.<br>
     * ������� ���������.
     * @since 1.0
     * @param args Not used.<br>
     * �� ������������.
     */
    public static void main(String[] args) {
        Configurator instance = new Configurator();
        instance.start(true);

    }
    
    /**
     * Starts visual customizator after logo. <br>
     * ��������� ���������� ���������� ����� ����, ��� ������� �������.
     * @param showLogo Is logo required.<br>
     * ��������� �� ���������� �������.
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
     * ��������� ��������� ���������� � �� ����������� � ������� ������, ����
     * ������������ ��������.
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

