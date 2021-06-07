
package Barajeador;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import unodistribuido.Cliente;

public class CartaGrafica extends JLabel
{
    private final Carta carta;
    private final Cliente tablero;
    private final int index;
    
    private Timer tmrAnimacion,tmrAgitar;
    private Point posicion;
    private boolean activo,banAgitar,banBajar;
    private final int grados;
    private int cont;
    private ImageIcon img2;
    private Image img;
    
    public CartaGrafica(Carta carta,Cliente tablero,int index,boolean turno)
    {
        super();
        grados=0;
        cont=1;
        banAgitar=banBajar=false;
        this.tablero=tablero;
        this.carta=carta;
        this.index=index;
        activo=false;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setSize(90,135);
        if (turno)
            img= new ImageIcon(getClass().getResource("imagenes/baraja/"+carta.getCarta()+".png")).getImage();
        else
            img= new ImageIcon(getClass().getResource("imagenes/barajaInactiva/"+carta.getCarta()+".png")).getImage();
        img2=new ImageIcon(img.getScaledInstance(90, 135, Image.SCALE_FAST));
        setIcon(img2);
        tmrAgitar=new Timer(50,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Graphics g=getMe().getGraphics();
                double r;
                if (cont>5)
                {
                    cont=1;
                    r = Math.toRadians(0); //se convierte a radianes lo grados
                    if (banBajar)
                    {
                        setLocation(getLocation().x,getLocation().y+80);
                        setSize(getSize().width,getSize().height-75);
                        banBajar=false;
                    }
                    banAgitar=false;
                    tmrAgitar.stop();
                }
                else
                {
                    switch(cont)
                    {
                        case 1:  r = Math.toRadians(45); break;
                        case 2:  r = Math.toRadians(27); break;
                        case 3:  r = Math.toRadians(0); break;
                        case 4:  r = Math.toRadians(-27); break;
                        case 5:  r = Math.toRadians(-45); break;
                        default:  r = Math.toRadians(-45); break;
                    }  
                    cont++;
                }
                AffineTransform at = new AffineTransform();
                at.rotate(r, getMe().getSize().width/2,getMe().getSize().height/2); //se asigna el angulo y centro de rotacion
                ((Graphics2D) g).setTransform(at);
                tablero.getPanel().paintImmediately(getMe().getLocation().x, getMe().getLocation().y, getMe().getLocation().x+getMe().getSize().width,getMe().getLocation().y+getMe().getSize().height+75);   
                getMe().paint(g);
            }          
        });
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
                if (!banAgitar)
                {
                    setLocation(getLocation().x,getLocation().y+80);
                    setSize(getSize().width,getSize().height-75);
                }
                else
                    banBajar=true;
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
               if (tablero.getTurno()||activo)
                {
                    tablero.setCODOP(19);
                    tablero.setCartaTirada(getMe());
                    tablero.notificar();
                    if(carta.getColor()==0)
                        tablero.quitaCarta(getMe());
                }
            }
        });

    }
   
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
    }
   
    public void agita()
    {
        banAgitar=true;
        tmrAgitar.start();
    }
    
    public void setBN(boolean activar)
    {
        if(activar)
        {
            Image imgt= new ImageIcon(getClass().getResource("imagenes/barajabn/"+carta.getCarta()+".png")).getImage();
            ImageIcon imgt2=new ImageIcon(imgt.getScaledInstance(90, 135, Image.SCALE_FAST));
            setIcon(imgt2);
        }
        else
            setIcon(img2);
    }
    public void setActivo(boolean activo)
    {
        this.activo=activo;
        setIcon(null);
        if (activo)
        {
            img= new ImageIcon(getClass().getResource("imagenes/baraja/"+carta.getCarta()+".png")).getImage();
            img2=new ImageIcon(img.getScaledInstance(90, 135, Image.SCALE_FAST));
            setIcon(img2);
        }
        else
        {
            img= new ImageIcon(getClass().getResource("imagenes/barajaInactiva/"+carta.getCarta()+".png")).getImage();
            img2=new ImageIcon(img.getScaledInstance(90, 135, Image.SCALE_FAST));
            setIcon(img2);
        }    
        
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
    public Carta getCarta()
    {
        return carta;
    }
}
