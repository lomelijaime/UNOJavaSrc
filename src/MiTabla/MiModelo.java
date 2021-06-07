/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MiTabla;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author roafi
 */
public class MiModelo extends DefaultTableModel
{
    public MiModelo()
    {
        super();
        
    }
    @Override
    public Object getValueAt(int row, int column)
    {
        if (this.getDataVector().size()<=row)
            return new Object();
        return super.getValueAt(row, column);
    }
    
}
