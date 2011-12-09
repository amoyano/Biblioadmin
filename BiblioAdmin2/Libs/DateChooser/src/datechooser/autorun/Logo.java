/*
 * Logo.java
 *
 * Created on 2 Декабрь 2006 г., 18:08
 *
 */

package datechooser.autorun;

import static datechooser.beans.locale.LocaleUtils.getConfigLocaleString;
import datechooser.beans.pic.Pictures;
import java.awt.*;
import javax.swing.*;

/**
 * Logo window.<br>
 * Окно-заставка.
 * @author Androsov Vadim
 * @since 1.0
 */
public class Logo extends JWindow {
    
    private static final int WIDTH = 300;
    private static final int HEIGHT = 150;
    private static final int FONT_SIZE = 16;
    
    /**
     * Constructs logo.<br>
     * Создает окно-заставку.
     * @since 1.0
     */
    public Logo() {
        super();
        generateInterface();
        setCentered();
    }
    
    /**
     * createInfoLabel(true).
     * @see datechooser.autorun.Logo#createInfoLabel(boolean)
     */
    public static JLabel createInfoLabel() {
        return createInfoLabel(true);
    }
    
    /**
     * Creates info JLabel (contaings logotype and about information).
     * Used when program starts and in then "about" dialog<br>
     * Создает объект типа JLabel, содержащий логотип и информацию о программе.
     * Используется при старте программы и в меню "О программе".
     * @since 1.0
     * @param text Is about text information required.<br>
     * Требуется ли включать текстовую информацию "О программе".
     * @return JLabel with required info.
     * JLabel с необходимой информацией.
     */
    public static JLabel createInfoLabel(boolean text) {
        JLabel info = new JLabel();
        info.setIcon(new ImageIcon(Pictures.getResource("logo_small.gif")));
        if (text) {
            info.setText("<html>" +
                    getConfigLocaleString("ProjectName") +
                    "<br> (c)" + getConfigLocaleString("Author"));
        } else {
            info.setHorizontalAlignment(JLabel.CENTER);
        }
        info.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        return info;
    }
    
    private void generateInterface() {
        JPanel mainPane = new JPanel(new BorderLayout(5, 5));
        mainPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JLabel info = createInfoLabel();
        
        JProgressBar progres = new JProgressBar();
        progres.setIndeterminate(true);
        progres.setStringPainted(false);
        
        mainPane.add(info, BorderLayout.CENTER);
        mainPane.add(progres, BorderLayout.SOUTH);
        
        getContentPane().add(mainPane);
    }
    
    private void setCentered() {
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        if ((WIDTH > screenSize.width) || (HEIGHT > screenSize.height)) {
            setLocation(0, 0);
        } else {
            setLocation(
                    (screenSize.width - WIDTH) / 2,
                    (screenSize.height - HEIGHT) / 2);
        }
    }
    
}
