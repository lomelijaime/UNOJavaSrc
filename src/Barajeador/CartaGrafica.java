
package Barajeador;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import unodistribuido.Cliente;

public class CartaGrafica extends JLabel
{
    private final Carta carta;
    private final Cliente tablero;
    private final int index;
    
    private Timer tmrAnimacion;
    private Point posicion;
    public CartaGrafica(Carta carta,Cliente tablero,int index)
    {
        super();
        this.tablero=tablero;
        this.carta=carta;
        this.index=index;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setSize(90,135);
        Image img= new ImageIcon(getClass().getResource("imagenes/baraja/"+carta.getCarta()+".png")).getImage();
        ImageIcon img2=new ImageIcon(img.getScaledInstance(90, 135, Image.SCALE_FAST));
        setIcon(img2);
        
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e)
            {
                setLocation(getLocation().x,getLocation().y-80);
                setSize(getSize().width,getSize().height+75);
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                setLocation(getLocation().x,getLocation().y+80);
                setSize(getSize().width,getSize().height-75);
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                //tablero.quitaCarta(getMe());
            }
        });

    }
    
    public void activaTimers(int mili)
    {
        
        
        tmrAnimacion=new Timer(mili,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
              
                if (getLocation().x==posicion.x){
                   tmrAnimacion.stop();
                   
                   
               }
               else
               {
                    int i=0;
                    while(i<10&&getLocation().x!=posicion.x)
                    {
                        setLocation(getLocation().x-1,getLocation().y);
                        i++;
                    }
               }
            }          
        });
        
       
        tmrAnimacion.start();
    }
    public void setPoint(Point posicion)
    {
        this.posicion=posicion;
    }
    public CartaGrafica getMe()
    {
        return this;
    }
  
}
