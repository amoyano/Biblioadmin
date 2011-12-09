/*
 * DateChooserComboCustomizer.java
 *
 * Created on 8 ������ 2006 �., 7:55
 *
 */

package datechooser.beans;

import datechooser.beans.customizer.DateChooserCustomizer;
import java.beans.IntrospectionException;

/**
 * Customizer for DateChooserCombo.<br>
 * ���������� ��������������� ��������� ���.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.beans.customizer.DateChooserCustomizer
 */
public class DateChooserComboCustomizer extends DateChooserCustomizer {
    
    public DateChooserComboCustomizer() throws IntrospectionException {
        super(new DateChooserComboBeanInfo());
    }
    
}
