/*
 * DateChooseController.java
 *
 * Created on 4 ���� 2006 �., 19:15
 *
 */

package datechooser.controller;

import datechooser.view.*;

/**
 * Controller interface for daychooser panel.<br>
 * ����� ��������� ����������� ������ ������ ����.
 * @author Androsov Vadim
 * @since 1.0
 */
public interface DateChooseController {
    /**
     * When panel size changes.<br>
     * ������������ � �������, ����� ������ ������ ��������.
     */
    void reBound();
    /**
     * Attaches to day selection panel.<br>
     * ������������� ������ ���������, ��������� � ��� ���� (����������).
     */
    void setView(GridPane palette);
}
