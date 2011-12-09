/*
 * DaysGrid.java
 *
 * Created on 3 ���� 2006 �., 11:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datechooser.model;

/**
 * Describes day selection grid size.
 * Currently hardcoded (6 weeks for 7 days).<br>
 * ����� ���������� �� ����������� ����� ������ ���. 
 * �������� ������ ������ (���������� 6 ������ �� 7 ���� � ������).
 * @author Androsov Vadim
 * @since 1.0
 */
public class DaysGrid {

    private static final int ROWS_COUNT = 6;
    private static final int COLS_COUNT = 7;

    public static int getRowsCount() {
        return ROWS_COUNT;
    } 
    
    public static int getColsCount() {
        return COLS_COUNT;
    } 
    
    private DaysGrid() {
    }
    
}
