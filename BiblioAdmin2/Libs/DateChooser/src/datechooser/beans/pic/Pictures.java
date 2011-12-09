/*
 * Pictures.java
 *
 * Created on 9 ������ 2006 �., 8:52
 *
 */

package datechooser.beans.pic;

import java.net.URL;

/**
 * Simplifies resources use.<br>
 * �����, �������������� ��� �������� ��������� � ��������.
 * �.�. ��� ����������� ����� ������, �������� ��� �� ���� ��������� 
 * ����� ��������� ��� ������� ��������� ������ ������������ �����������.
 * @author Androsov Vadim
 * @since 1.0
 */
public class Pictures {
    
    public static URL getResource(String name) {
        return Pictures.class.getResource(name);
    }
    
    public static URL getDefaultPicture() {
        return getResource("java_logo.png");
    }
}
