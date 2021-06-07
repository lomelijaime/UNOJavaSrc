/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MiTabla;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author roafi
 */
public class MiModeloColumna extends DefaultTableColumnModel
{
    public MiModeloColumna()
    {
        super();
    }
    @Override
    public TableColumn getColumn(int columnIndex)
    {
        
        try {
            TableColumn tmp = super.getColumn(columnIndex);
            return tmp;
        } catch (Exception e) {
            return new TableColumn();
        }
    }
}
