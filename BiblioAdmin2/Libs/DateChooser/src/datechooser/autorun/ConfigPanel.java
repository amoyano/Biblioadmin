/*
 * ConfigPanel.java
 *
 * Created on 10 ������ 2006 �., 6:48
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
 * ����� ��� ������ ����������� ���� � ��� �����������.
 * @author Androsov Vadim
 * @see datechooser.beans.DateChooserPanel
 * @see datechooser.beans.DateChooserPanelCustomizer
 * @since 1.0
 */
public class ConfigPanel extends ConfigBean {
    
    /**
     * Sets component and customizer classes using parent (super) constructor.<br>
     * ������������� ������, ������� ����������� ��������.
     * @throws java.beans.IntrospectionException If component and customizer are incompatible.<br>
     * ���� ��������� � ���������� �� ������������� ���� �����
     * @since 1.0
     */
    public ConfigPanel() throws IntrospectionException {
        super(new DateChooserPanel(), new DateChooserPanelCustomizer());
        initializeInterface();
    }

    /**
     * ��������� ����������������� ����������.
     */
     private void initializeInterface() {
        setLayout(new GridLayout(1, 2, 2, 2));
        add((JComponent) getBean());
        add(getCustomizer());
    }

    /**
     * Returns properies file extension for panel component.<br>
     * ���������� ���������� �����.
     * @return <B>.dchp</B>
     * @since 1.0
     */
    public String getFileExt() {
        return "dchp";
    }
    
}
