/*
 * NamedInt.java
 *
 * Created on 2 ������ 2006 �., 16:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datechooser.beans.editor.utils;

/**
 * Pair: Integer and name.<br>
 * ������ ����: �����, ������.
 * @author Androsov Vadim
 * @since 1.0
 */
public class NamedInt {
    
    private String name;
    private int value;
    
    public NamedInt(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        return name;
    }
    
    public int getValue() {
        return value;
    }
    
}
