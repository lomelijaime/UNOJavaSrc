/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author roafi
 */
public class Cristal extends JLabel
{
    private Timer tmrAnimacion;
    private Image img;
    private ImageIcon img2;
    private int frame;
    
    public Cristal(boolean animacion)
    {
        super();
        frame=1;
        setSize(24,24);
        img= new ImageIcon(getClass().getResource("imagenes/cristal/1.png")).getImage();
        img2=new ImageIcon(img.getScaledInstance(24, 24, Image.SCALE_FAST));
        setIcon(img2);
        setHorizontalAlignment(SwingConstants.CENTER);
        if (animacion)
        {
            tmrAnimacion=new Timer(125,new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    img= new ImageIcon(getClass().getResource("imagenes/cristal/"+frame+".png")).getImage();
                    img2=new ImageIcon(img.getScaledInstance(24, 24, Image.SCALE_FAST));
                    setIcon(img2);
                    frame++;
                    if (frame>8)
                        frame=1;
                }
                
            });
            tmrAnimacion.start();
        }
        else
            tmrAnimacion=null;
        
    }
    
    
}
