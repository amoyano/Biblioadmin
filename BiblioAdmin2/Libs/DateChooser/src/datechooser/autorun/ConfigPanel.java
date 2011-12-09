/*
 * ConfigPanel.java
 *
 * Created on 10 Ноябрь 2006 г., 6:48
 *
 */

package datechooser.autorun;

import datechooser.beans.DateChooserPanel;
import datechooser.beans.DateChooserPanelBeanInfo;
import datechooser.beans.DateChooserPanelCustomizer;
import datechooser.beans.customizer.DateChooserCustomizer;

import java.awt.*;
import java.beans.*;
import javax.swing.*;

/**
 * Outputs DateChooserPanel and its customizer.<br>
 * Класс для вывода диалогового окна и его настройщика.
 * @author Androsov Vadim
 * @see datechooser.beans.DateChooserPanel
 * @see datechooser.beans.DateChooserPanelCustomizer
 * @since 1.0
 */
public class ConfigPanel extends ConfigBean {
    
    /**
     * Sets component and customizer classes using parent (super) constructor.<br>
     * Устанавливает классы, вызывая конструктор родителя.
     * @throws java.beans.IntrospectionException If component and customizer are incompatible.<br>
     * Если компонент и настройщик не соответствуют друг другу
     * @since 1.0
     */
    public ConfigPanel() throws IntrospectionException {
        super(new DateChooserPanel(), new DateChooserPanelCustomizer());
        initializeInterface();
    }

    /**
     * Генерация пользовательского интерфейса.
     */
     private void initializeInterface() {
        setLayout(new GridLayout(1, 2, 2, 2));
        add((JComponent) getBean());
        add(getCustomizer());
    }

    /**
     * Returns properies file extension for panel component.<br>
     * Возвращает расширения файла.
     * @return <B>.dchp</B>
     * @since 1.0
     */
    public String getFileExt() {
        return "dchp";
    }
    
}
