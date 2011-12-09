/*
 * DateChooserPanelCustomizer.java
 *
 * Created on 8 ������ 2006 �., 7:44
 *
 */

package datechooser.beans;

import datechooser.beans.customizer.DateChooserCustomizer;
import java.beans.*;

/**
 * Customizer for DateChooserPanel.<br>
 * ���������� ��������������� ��������� ���.
 * @author Androsov Vadim
 * @since 1.0
 */
public class DateChooserPanelCustomizer extends DateChooserCustomizer {
    
    public DateChooserPanelCustomizer() throws IntrospectionException {
        super(new DateChooserPanelBeanInfo());
    }
    
}
