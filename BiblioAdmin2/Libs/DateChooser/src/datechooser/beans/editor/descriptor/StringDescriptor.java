/*
 * StringDescriptor.java
 *
 * Created on 5 ������ 2006 �., 7:31
 *
 */

package datechooser.beans.editor.descriptor;

import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see java.lang.String
 * @author Androsov Vadim
 * @since 1.0
 */
public class StringDescriptor extends ClassDescriptor {
    
    public Class getDescriptedClass() {
        return String.class;
    }

    public String getJavaDescription(Object value) {
        return '"' + value.toString() + '"';
    }

    public String getDescription(Object value) {
        if (value == null) return getEditorLocaleString("null");
        return (String) value;
    }
    
}
