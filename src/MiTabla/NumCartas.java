/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MiTabla;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author roafi
 */
public class NumCartas extends JPanel 
{
    private JLabel lblCarta;
    private JLabel lblnCarta;
    public NumCartas(int ncarta)
    {
        super();
        lblnCarta=new JLabel();
        lblCarta=new JLabel(new ImageIcon(getClass().getResource("imagenes/otros/carta.png")));
        setSize(48,24);
        setLayout(null);
        lblCarta.setSize(16,24);
        lblnCarta.setSize(32,24);
        lblCarta.setLocation(8,0);
        lblnCarta.setLocation(32,0);
        lblnCarta.setText("x "+String.valueOf(ncarta));
        add(lblCarta);
        add(lblnCarta);
        
    }
}
