
package Barajeador;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.TOP;
import unodistribuido.Cliente;

public class CartaGrafica extends JLabel
{
    private Carta carta;
    private Cliente tablero;
    private int index;
    public CartaGrafica(Carta carta,Cliente tablero,int index)
    {
        super();
        this.tablero=tablero;
        this.carta=carta;
        this.index=index;
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
    public CartaGrafica getMe()
    {
        return this;
    }
}
