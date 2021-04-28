/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MiTabla;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class IconCellRenderer extends DefaultTableCellRenderer 
{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {
        table.setRowHeight(28);
        setHorizontalAlignment(SwingConstants.CENTER);
        
        if (row%2!=0)
        {
            setBackground(new Color(255,102,0));
            setForeground(new Color(255,255,255));
        }
        else
        {
            setBackground(new Color(255,255,255));
            setForeground(new Color(0,0,0));
        }
        if (value instanceof JLabel) 
        {
            JLabel label = (JLabel) value;
            label.setOpaque(true);
            fillColor(table, label, row);
            return label;
        } 
        else 
        {
            
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
    public void fillColor(JTable t, JLabel l, int row) 
    {
        
            if (row%2!=0)
                l.setBackground(new Color(255,102,0));
           else
                l.setBackground(new Color(255,255,255));
            l.setForeground(t.getForeground());
        
    }
 }
