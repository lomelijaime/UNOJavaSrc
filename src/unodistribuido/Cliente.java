/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import Barajeador.Carta;
import Barajeador.CartaGrafica;
import Barajeador.Mano;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import MiTabla.IconCellRenderer;
import MiTabla.MiModelo;
import MiTabla.MiModeloColumna;
import MiTabla.NumCartas;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import utilidades.Pila;
import utilidades.ProgressCircleUI;
/**
 *
 * @author Luis
 * 
 */


/**
 * NUMERACION DE LOS CODOP
 * 
 * 1: Solicitud de conexion al servidor
 * 2: Peticion de salida por cerrar el cliente
 * 3: Aceptacion de conexion al servidor
 * 4: Negacion de conecion al servidor
 * 5: Peticion de salida por desconexion del cliente
 * 6: Envio de voto de inicio de partida
 * 7: Envio del listado de jugadores para el cliente
 * 8: Aceptacion de salida de la sala de juegos
 * 9: Informe de que la sala de juego se ha cerrado
 * 10: Salida de sala por no votar en el inicio de partida
 * 11: Informe de partida iniciada (incluye la mano inicial)
 * 12: Informe de cancelacion de juego por menos de 2 participantes en sala
 * 13: Peticion del cliente para solicitar grito de UNO!
 * 14: Orden de gritar UNO! en cliente
 * 15: Cancelacion del voto de inicio de partida
 * 16: Actualizacion tabla de turno y ncartas en cliente
 * 17: Informe de reasignacion de siguiente jugador a dar el turno, por reinicoo de anillo
 * 18: Token de turno
 * 19: Tira carta
 * 20: Aceptacion de carta
 * 21: informe de tiro de carta
 * 22: Actualizacion de tabla jugadores en numero de cartas
 * 23: Pedir carta del mazo
 * 24: Recepcion de cartas de mazo
 * 25: Actualizacion por tomar carta en cliente
 * 26: Peticion de actualizacion por cambio de turno
 * 27: Informe de reto come 4 al servidor
 * 28: Informe del reto come 4 al cliente
 * 29: Reto por no gritar UNO
 * 30: Castigo del reto por no gritar UNO
 * 
*/

/**
 * Regla 1: Acumulacion
 * Regla 2: Robar hasta jugar
 * Regla 3: Forzar tiro
 * Regla 4: Sin faroles
 * 
 */

/**
 * RESGUARDO DEL CLIENTE
 * 
 * cadena conectar(in entero,in cadena)
 * booleano cerrar(in entero,in cadena,in booleano)
 * booleano desconectar(in entero,in cadena,in booleano)
 * vacio votarInicio(vacio)
 * vacia ejecutaGrito(vacio)
 * vacia cancelaVoto(vacio)
 * booleano tiraCarta(cadena)
 * booleano pideCarta(vacio)
 * vacio mandaRetoUno(in cadena, in cadena)
 * 
 */

public final class Cliente extends javax.swing.JFrame implements Runnable {

    private int CODOP;
    private boolean banGrito;
    private boolean banIniciado;
    private boolean banTurno,banAcumulado,banCambioSentido,banNoCerrar,banAcumuladoC4;
    private boolean activo,banCastigo,banReto;
    private boolean sentidoAnillo;
    private final Thread hiloCliente;
    private final HiloEscuchador hiloEscuchador;
    private final String[] nombres={"JOEL","JENNIE","OSVALDO","YOLANDA","DIANA","MARIO"};
    private final String[] nombresCol= {"Voto","Avatar", "Nombre", "# Cartas"};
    private final String[] nombresCol2= {"Turno","Avatar", "Nombre", "# Cartas"};
    private final ArrayList<CartaGrafica> cartas;
    private final Mano cartasMano;
    private final Micronucleo micronucleo;
    private final int idProceso;
    private final int foto;
    private final Timer tmrMano,tmrGiro,tmrCancelar,tmrCome,tmrContador,tmrFinalizar;
    private boolean[] reglas;
    private int frameMano,frameGiro,frameCancelar,frameCome,frameReto;
    private Clip sonido;
    private Point initialClick;
    private MiModelo modelo;
    private String idSiguiente,ipSiguiente,colorAnillo;
    private Carta cartaPila,cartaAnterior;
    private CartaGrafica cartaUsada;
    private int come,contador;
    private Reto reto;
    
    
    
    public Cliente(Micronucleo micronucleo,Avatar avatar,int idProceso) 
    {
        initComponents();
        cartaAnterior=null;
        contador=0;
        banGrito=false;
        banIniciado=false;
        banCambioSentido=false;
        banCastigo=false;
        banTurno=false;
        banAcumulado=false;
        banAcumuladoC4=false;
        banNoCerrar=false;
        banReto=false;
        this.micronucleo=micronucleo;
        this.idProceso=idProceso;
        activo=true;
        CODOP=0;
        come=2;
        frameMano=1;
        frameCancelar=1;
        frameReto=1;
        cartas=new ArrayList<>();
        cartasMano=new Mano();
        setSize(1080, 608);
        Toolkit tk=Toolkit.getDefaultToolkit();
        Dimension d=tk.getScreenSize();
        setLocation((d.width-getSize().width)/2,(d.height-getSize().height)/2);
        txtIDCliente.setText(String.valueOf(idProceso));
        lblNick.setText(nombres[avatar.getAvatar()-1]);
        Image img= new ImageIcon(getClass().getResource("imagenes/avatar/"+avatar.getAvatar()+".png")).getImage();
        ImageIcon img2=new ImageIcon(img.getScaledInstance(91, 91, Image.SCALE_FAST));
        lblFoto.setIcon(img2);
        lblMano.setVisible(false);
        lblGanaste.setVisible(false);
        lblPerdiste.setVisible(false);
        foto=avatar.getAvatar();
        avatar.dispose();
        cambiaImagenSonido();
        colorAnillo="1";
        ipSiguiente="";
        idSiguiente="";
        lblCancelado.setVisible(false);
        lblCome.setVisible(false);
        
        tmrFinalizar=new Timer(8000,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                semiReiniciar();
                tmrFinalizar.stop();
                
            }
            
        });
        
        tmrCancelar=new Timer(500,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                
                if(frameCancelar==1)
                {
                    banNoCerrar=true;
                    btnCerrar.setEnabled(false);
                    btnDesconectar.setEnabled(false);
                    
                    AudioFilePlayer player;
                    player = new AudioFilePlayer(getClass().getResource("sonidos/cancelado.wav"),micronucleo,0);
                    for (int i = 0; i < cartas.size(); i++) {
                            CartaGrafica c=cartas.get(i);
                            c.setBN(true);
                        }
                }
                else if (frameCancelar==2)
                    lblCancelado.setVisible(true);
                else if (frameCancelar==4)
                {
                    lblCancelado.setVisible(false);
                    
                }
                else if (frameCancelar==5)
                {
                    for (int i = 0; i < cartas.size(); i++) {
                            CartaGrafica c=cartas.get(i);
                            c.setBN(false);
                        }
                    cambiaTurno();
                     pasaTurno();
                    banNoCerrar=false;
                    btnCerrar.setEnabled(true);
                    btnDesconectar.setEnabled(true);
                    tmrCancelar.stop();
                }
              frameCancelar++;
            }
            
        });
        
        
        
        
        tmrCome=new Timer(4,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                banNoCerrar=true;
                btnCerrar.setEnabled(false);
                btnDesconectar.setEnabled(false);
                    
                lblCome.setFont(new Font(lblCome.getFont().getName(),lblCome.getFont().getStyle(),frameCome));
                lblCome.setVisible(true);
                if (frameCome==1)
                {
                    
                    for (int i=0;i<cartas.size();i++)
                    {
                        CartaGrafica c=cartas.get(i);
                        c.setActivo(false);

                    }
                }
                else if (frameCome==200)
                {
                    lblCome.setVisible(false);
                    if(!banReto){
                        cambiaTurno();
                        pasaTurno();
                        banReto=false;
                    }
                    tmrCome.stop();
                    banNoCerrar=false;
                    btnCerrar.setEnabled(true);
                    btnDesconectar.setEnabled(true);
                    banNoCerrar=false;
                    btnCerrar.setEnabled(true);
                    btnDesconectar.setEnabled(true);
                    
                }
               
              frameCome++;
            }
            
        });
        

        
        tmrMano=new Timer(400,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                frameMano++;
                if(frameMano>2)
                    frameMano=1;
                lblMano.setIcon(new ImageIcon(getClass().getResource("imagenes/mano/"+frameMano+".png")));
            }
            
        });
                   
      
         tmrGiro=new Timer(250,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) 
            {   
                if(frameGiro==1)
                {
                    
                    tblJugadores.setVisible(false);
                    lblGiro.setVisible(true);
                    AudioFilePlayer player;
                    player = new AudioFilePlayer(getClass().getResource("sonidos/cambioGiro.wav"),micronucleo,0);
                }
                frameGiro++;
                if(frameGiro>4)
                {
                    tblJugadores.setVisible(true);
                    tmrGiro.stop();
                    lblGiro.setVisible(false);
                }
                else
                {   
                    if(sentidoAnillo)
                    {
                          lblGiro.setIcon(new ImageIcon(getClass().getResource("imagenes/gira/izquierda/" + colorAnillo + "/" + frameGiro + ".png")));
                            
                    }
                    else
                    {
                          lblGiro.setIcon(new ImageIcon(getClass().getResource("imagenes/gira/derecha/"+colorAnillo+"/"+frameGiro+".png")));
                            
                    }
                        
                }

            }
            
        });
        lblGiro.setVisible(false);
        modelo=new MiModelo();
        tblJugadores.setModel(modelo);
        tblJugadores.setDefaultRenderer(Object.class,new IconCellRenderer());
        tblJugadores.setColumnModel(new MiModeloColumna());
        modelo.setColumnIdentifiers(nombresCol);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) 
            {
                int thisX = getMe().getLocation().x;
                int thisY = getMe().getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                getMe().setLocation(X, Y);
            }
        });
        
       
        
       hiloCliente=new Thread(this);
        hiloCliente.start();
        
        hiloEscuchador=new HiloEscuchador(this,micronucleo);
        hiloEscuchador.start();
        
        JProgressBar progress = new JProgressBar();
        progress.setUI(new ProgressCircleUI());
        progress.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        progress.setStringPainted(true);
        progress.setFont(progress.getFont().deriveFont(24f));
        progress.setForeground(Color.GREEN);
        progress.setOpaque(false);
        
        progress.setSize(60,60);
        progress.setLocation(((int)(pnlCarga.getSize().width-60))/2,((int)(pnlCarga.getSize().height-55))/2);
        
        tmrContador=new Timer(200,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) 
            {
               
                if (contador==1)
                {
                    banNoCerrar=true;
                    btnCerrar.setEnabled(false);
                    btnDesconectar.setEnabled(false);
                    
                    AudioFilePlayer player;
                    player = new AudioFilePlayer(getClass().getResource("sonidos/acumular.wav"),micronucleo,0);
                    banAcumuladoC4=true;
                    banAcumulado=true;
                }
                    
                if (contador%5==0)
                {
                    String cad="00:";
                    if(contador/5>9)
                        cad+=((int)(contador/5));
                    else
                        cad+="0"+((int)(contador/5));
                    lblAcumulador.setText(cad);
                }
                if(contador==50)
                    progress.setForeground(Color.YELLOW);
                else if(contador==75)
                   progress.setForeground(Color.ORANGE);
                else if(contador==100)
                {
                    progress.setForeground(Color.RED);
                    lblAcumulador.setVisible(false);
                    pnlCarga.setVisible(false);
                    btnRobar.setVisible(false);
                    btnGrito.setVisible(true);
                    btnReto.setVisible(true);
                    btnDesconectar.setVisible(true);
                    btnCerrar.setVisible(true);
                    frameCome=1;
                    AudioFilePlayer player;
                    player = new AudioFilePlayer(getClass().getResource("sonidos/come.wav"),micronucleo,0);
                    pideCartas(come);
                    tmrCome.start();
                    banNoCerrar=false;
                    btnCerrar.setEnabled(true);
                    btnDesconectar.setEnabled(true);
                    
                    tmrContador.stop();
                }
                
                progress.setValue(contador++);
            }
        }); 
        
        pnlCarga.add(progress);
        lblAcumulador.setVisible(false);
        pnlCarga.setVisible(false);
        btnRobar.setVisible(false);
        reto=new Reto(this);
        reto.setLocation(530,250);
        reto.setSize(325, 238);
        reto.setVisible(false);
        add(reto,1);
        setVisible(true);
    }
    public void finaliza(boolean resultado)
    {
        if (resultado)
        {
            AudioFilePlayer player;
            player = new AudioFilePlayer(getClass().getResource("sonidos/ganaste.wav"),micronucleo,0);
            lblGanaste.setVisible(true);
        }
        else
        {
            AudioFilePlayer player;
            player = new AudioFilePlayer(getClass().getResource("sonidos/perdiste.wav"),micronucleo,0);
            lblPerdiste.setVisible(true);
        }    
        tmrFinalizar.start();
    }
    public void setSiguienteTurno(String ip,String id,String sentido,String color)
    {
       if (!ipSiguiente.equals(""))
       {
            idSiguiente=id;
            ipSiguiente=ip;
            if(sentido.trim().equals("2"))
                sentidoAnillo=true;
            else
            {
                colorAnillo=color.trim();
                sentidoAnillo=sentido.trim().equals("1");
                frameGiro=1;
                tmrGiro.start();
            }
            if (banCambioSentido)
            {
                String mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|1";
                if (cartas.size()==1)
                {
                    if (!banGrito)
                    {
                        String mensaje2= txtID.getText()+"|"+idProceso+"|"+"31|";
                        byte[] msg2=mensaje2.getBytes();
                        micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg2);
                    }
                }
                byte[] msg=mensaje.getBytes();
                micronucleo.send(Integer.parseInt(this.idSiguiente)*-1, ipSiguiente,msg);
                banCambioSentido=false;
                setTurno(false);
            }
        }
        else
        {
            idSiguiente=id;
            ipSiguiente=ip;
            if(sentido.trim().equals("2"))
                sentidoAnillo=true;
            else
                sentidoAnillo=sentido.trim().equals("1");
             
        }
        
        
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPerdiste = new javax.swing.JLabel();
        lblGanaste = new javax.swing.JLabel();
        lblCome = new javax.swing.JLabel();
        lblCancelado = new javax.swing.JLabel();
        pnlJugadores = new javax.swing.JPanel();
        lblGiro = new javax.swing.JLabel();
        scrJugadores = new javax.swing.JScrollPane();
        tblJugadores = new javax.swing.JTable();
        pnlJugador = new javax.swing.JPanel();
        lblNick = new javax.swing.JLabel();
        txtIDCliente = new javax.swing.JTextField();
        lblFoto = new javax.swing.JLabel();
        lblIDCliente1 = new javax.swing.JLabel();
        lblFoto1 = new javax.swing.JLabel();
        pnlCartas = new javax.swing.JPanel();
        pnlServidor = new javax.swing.JPanel();
        lblIP = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        lblID = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        btnConectar = new javax.swing.JButton();
        lblCarta = new javax.swing.JLabel();
        btnDesconectar = new javax.swing.JButton();
        btnReto = new javax.swing.JButton();
        lblMazo = new javax.swing.JLabel();
        btnGrito = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        lblSonido = new javax.swing.JLabel();
        lblMano = new javax.swing.JLabel();
        lblAcumulador = new javax.swing.JLabel();
        pnlCarga = new javax.swing.JPanel();
        btnRobar = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();
        lblReto3 = new javax.swing.JLabel();
        lblReto4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("imagenes/fondos/logo.png")).getImage());
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        lblPerdiste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/lose.png"))); // NOI18N
        getContentPane().add(lblPerdiste);
        lblPerdiste.setBounds(410, 295, 596, 171);

        lblGanaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/win.png"))); // NOI18N
        getContentPane().add(lblGanaste);
        lblGanaste.setBounds(410, 295, 596, 171);

        lblCome.setFont(new java.awt.Font("Comic Sans MS", 1, 168)); // NOI18N
        lblCome.setForeground(new java.awt.Color(255, 255, 255));
        lblCome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCome.setText("+2");
        getContentPane().add(lblCome);
        lblCome.setBounds(470, 170, 400, 400);

        lblCancelado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/fondos/cancelado.png"))); // NOI18N
        getContentPane().add(lblCancelado);
        lblCancelado.setBounds(470, 180, 400, 400);

        pnlJugadores.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3), "Jugadores en la Sala", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Britannic Bold", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlJugadores.setEnabled(false);
        pnlJugadores.setOpaque(false);
        pnlJugadores.setLayout(null);

        lblGiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/gira/derecha/1/1.png"))); // NOI18N
        pnlJugadores.add(lblGiro);
        lblGiro.setBounds(60, 20, 195, 195);

        tblJugadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Imagen", "Nick", "# Cartas"
            }
        ));
        tblJugadores.setEnabled(false);
        tblJugadores.setOpaque(false);
        tblJugadores.getTableHeader().setReorderingAllowed(false);
        scrJugadores.setViewportView(tblJugadores);

        pnlJugadores.add(scrJugadores);
        scrJugadores.setBounds(10, 20, 290, 190);

        getContentPane().add(pnlJugadores);
        pnlJugadores.setBounds(730, 10, 310, 220);

        pnlJugador.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3), "Datos del jugador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Britannic Bold", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlJugador.setOpaque(false);
        pnlJugador.setLayout(null);

        lblNick.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        lblNick.setForeground(new java.awt.Color(255, 255, 255));
        lblNick.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNick.setText("JUGADOR 1");
        lblNick.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255))));
        pnlJugador.add(lblNick);
        lblNick.setBounds(140, 80, 110, 60);

        txtIDCliente.setEditable(false);
        txtIDCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtIDCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pnlJugador.add(txtIDCliente);
        txtIDCliente.setBounds(120, 30, 140, 21);

        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlJugador.add(lblFoto);
        lblFoto.setBounds(25, 65, 91, 91);

        lblIDCliente1.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        lblIDCliente1.setForeground(new java.awt.Color(255, 255, 255));
        lblIDCliente1.setText("ID JUGADOR");
        pnlJugador.add(lblIDCliente1);
        lblIDCliente1.setBounds(20, 30, 90, 20);

        lblFoto1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/brillo.png"))); // NOI18N
        pnlJugador.add(lblFoto1);
        lblFoto1.setBounds(20, 60, 101, 101);

        getContentPane().add(pnlJugador);
        pnlJugador.setBounds(50, 240, 270, 170);

        pnlCartas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        pnlCartas.setEnabled(false);
        pnlCartas.setMaximumSize(new java.awt.Dimension(705, 233));
        pnlCartas.setMinimumSize(new java.awt.Dimension(705, 233));
        pnlCartas.setOpaque(false);
        pnlCartas.setPreferredSize(new java.awt.Dimension(470, 233));
        pnlCartas.setLayout(null);
        getContentPane().add(pnlCartas);
        pnlCartas.setBounds(350, 260, 705, 233);

        pnlServidor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3), "Datos de la Sala de Juego", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Britannic Bold", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlServidor.setOpaque(false);
        pnlServidor.setLayout(null);

        lblIP.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        lblIP.setForeground(new java.awt.Color(255, 255, 255));
        lblIP.setText("IP SERVIDOR");
        pnlServidor.add(lblIP);
        lblIP.setBounds(20, 30, 90, 20);
        pnlServidor.add(txtIP);
        txtIP.setBounds(130, 30, 110, 22);

        lblID.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        lblID.setForeground(new java.awt.Color(255, 255, 255));
        lblID.setText("ID SERVIDOR");
        pnlServidor.add(lblID);
        lblID.setBounds(20, 70, 90, 20);
        pnlServidor.add(txtID);
        txtID.setBounds(130, 70, 110, 22);

        btnConectar.setBackground(new java.awt.Color(230, 7, 12));
        btnConectar.setFont(new java.awt.Font("Britannic Bold", 1, 12)); // NOI18N
        btnConectar.setForeground(new java.awt.Color(255, 255, 255));
        btnConectar.setText("Conectar");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });
        pnlServidor.add(btnConectar);
        btnConectar.setBounds(50, 110, 160, 18);

        getContentPane().add(pnlServidor);
        pnlServidor.setBounds(50, 420, 270, 150);

        lblCarta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCarta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/baraja/fondo2.png"))); // NOI18N
        lblCarta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        lblCarta.setEnabled(false);
        getContentPane().add(lblCarta);
        lblCarta.setBounds(550, 50, 110, 160);

        btnDesconectar.setBackground(new java.awt.Color(255, 102, 0));
        btnDesconectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/desconectar.png"))); // NOI18N
        btnDesconectar.setEnabled(false);
        btnDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesconectarActionPerformed(evt);
            }
        });
        getContentPane().add(btnDesconectar);
        btnDesconectar.setBounds(800, 520, 60, 60);

        btnReto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/reto.png"))); // NOI18N
        btnReto.setEnabled(false);
        btnReto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetoActionPerformed(evt);
            }
        });
        getContentPane().add(btnReto);
        btnReto.setBounds(640, 520, 60, 60);

        lblMazo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/baraja/fondo2.png"))); // NOI18N
        lblMazo.setText("jLabel2");
        lblMazo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        lblMazo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMazo.setEnabled(false);
        lblMazo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMazoMouseClicked(evt);
            }
        });
        getContentPane().add(lblMazo);
        lblMazo.setBounds(410, 70, 90, 135);

        btnGrito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/uno.png"))); // NOI18N
        btnGrito.setEnabled(false);
        btnGrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGritoActionPerformed(evt);
            }
        });
        getContentPane().add(btnGrito);
        btnGrito.setBounds(480, 520, 60, 60);

        btnCerrar.setBackground(new java.awt.Color(255, 102, 0));
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/cerrar.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCerrar);
        btnCerrar.setBounds(960, 520, 60, 60);

        lblSonido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSonido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/sonido.png"))); // NOI18N
        lblSonido.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSonido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSonidoMouseClicked(evt);
            }
        });
        getContentPane().add(lblSonido);
        lblSonido.setBounds(350, 525, 50, 50);

        lblMano.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMano.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/mano/1.png"))); // NOI18N
        getContentPane().add(lblMano);
        lblMano.setBounds(435, 20, 40, 40);

        lblAcumulador.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblAcumulador.setForeground(new java.awt.Color(51, 51, 51));
        lblAcumulador.setText("00:00");
        getContentPane().add(lblAcumulador);
        lblAcumulador.setBounds(570, 540, 51, 22);

        pnlCarga.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3), "Tiempo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlCarga.setOpaque(false);
        pnlCarga.setLayout(null);
        getContentPane().add(pnlCarga);
        pnlCarga.setBounds(550, 500, 90, 100);

        btnRobar.setBackground(new java.awt.Color(255, 102, 0));
        btnRobar.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        btnRobar.setForeground(new java.awt.Color(255, 255, 255));
        btnRobar.setText("Robar");
        btnRobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRobarActionPerformed(evt);
            }
        });
        getContentPane().add(btnRobar);
        btnRobar.setBounds(700, 520, 100, 60);

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/fondos/fondogrande.png"))); // NOI18N
        getContentPane().add(lblFondo);
        lblFondo.setBounds(0, 0, 1080, 608);

        lblReto3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/retoG.png"))); // NOI18N
        getContentPane().add(lblReto3);
        lblReto3.setBounds(520, 255, 325, 238);

        lblReto4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/retoG.png"))); // NOI18N
        getContentPane().add(lblReto4);
        lblReto4.setBounds(520, 255, 325, 238);

        pack();
    }// </editor-fold>//GEN-END:initComponents
  
    private void btnDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesconectarActionPerformed
        CODOP=5;
        notificar();
    }//GEN-LAST:event_btnDesconectarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       if(!banNoCerrar)
       {
            if (!txtIP.getText().equals(""))
            {
                CODOP=2;
                notificar();

            }
            else
            {
                activo=false;
                hiloEscuchador.notificar();
                notificar();
                micronucleo.eliminaCliente(this);
                dispose();
            }
       }
    }//GEN-LAST:event_formWindowClosing

    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
        if (btnConectar.getText().equals("Conectar"))
        {
            if (txtIP.getText().equals("")||txtID.getText().equals(""))
            {
                new Aviso(this,"Debes intruducir los datos de la Sala!",0);
            }
            else
            {
                CODOP=1;
                notificar();
            }
        }
        else if (!btnConectar.getText().equals("Cancelar voto!"))
        {
            CODOP=6;
            btnConectar.setText("Cancelar voto!");
            notificar();
        }
        else
        {
            CODOP=15;
            notificar();
            btnConectar.setEnabled(true);
            btnConectar.setText("Votar para iniciar!!");
           
        }
      
    }//GEN-LAST:event_btnConectarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        if (!txtIP.getText().equals("")&&!btnConectar.getText().equals("Conectar"))
        {
            CODOP=2;
            notificar();
        }
        else
        {
            activo=false;
            notificar();
            hiloEscuchador.notificar();
            micronucleo.eliminaCliente(this);
            dispose();
        }
        
        

    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGritoActionPerformed
        btnGrito.setEnabled(false);
        banGrito=true;
        CODOP=13;
        notificar();       
    }//GEN-LAST:event_btnGritoActionPerformed

    private void lblSonidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSonidoMouseClicked
        micronucleo.cambiaImagenSonido();
    }//GEN-LAST:event_lblSonidoMouseClicked

    private void lblMazoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMazoMouseClicked
        if (banTurno&&!banNoCerrar)
        {
            CODOP=23;
            notificar();
        }
        banGrito=false;

    }//GEN-LAST:event_lblMazoMouseClicked

    private void btnRobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRobarActionPerformed
                    lblAcumulador.setVisible(false);
                    pnlCarga.setVisible(false);
                    btnRobar.setVisible(false);
                    btnGrito.setVisible(true);
                    btnReto.setVisible(true);
                    btnDesconectar.setVisible(true);
                    btnCerrar.setVisible(true);
                    frameCome=1;
                    AudioFilePlayer player;
                    player = new AudioFilePlayer(getClass().getResource("sonidos/come.wav"),micronucleo,0);
                    pideCartas(come);
                    banNoCerrar=false;
                    btnCerrar.setEnabled(true);
                    btnDesconectar.setEnabled(true);
                    tmrCome.start();
                    tmrContador.stop();
    }//GEN-LAST:event_btnRobarActionPerformed

    private void btnRetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetoActionPerformed
        CODOP=29;
        notificar();
    }//GEN-LAST:event_btnRetoActionPerformed
    public void retoUno()
    {
        btnReto.setEnabled(!btnReto.isEnabled());
    }
    public void cerrar()
    {
        if (!txtIP.getText().equals(""))
        {
            CODOP=2;
            notificar();
            
        }
        else
        {
            if (banTurno)
                pasaTurno();
            activo=false;
            hiloEscuchador.notificar();
            notificar();
            micronucleo.eliminaCliente(this);
            dispose();
        }
    }
    public void retar(int ncartas)
    {
         CODOP=27;
         
            frameReto=ncartas;
         notificar();
    }
    public void cambiaImagenSonido()
    {
        if (!micronucleo.getPlayer().getSonando())
            lblSonido.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/nosonido.png")));
        else
            lblSonido.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/sonido.png")));
       
    }
    
    
   public Cliente getMe()
   {
       return this;
   }
   public void setCartaTirada(CartaGrafica c)
   {
       cartaUsada=c;
       banNoCerrar=false;
       btnCerrar.setEnabled(true);
       btnDesconectar.setEnabled(true);
   }
   @Override
    public void run() 
    {
        String mensaje;
        byte msg[];
        while(activo)
        {
            synchronized(hiloCliente)
            {
                try
                {
                    hiloCliente.wait();
                } catch (InterruptedException ex) {}
            }
            if (!activo)
                return;
            switch(CODOP)
            {
               
                case 1:{
                    char valor[];
                    //mandamos a llamar al resguardo del cliente
                    valor=conectar(foto,nombres[foto-1].toCharArray());
                    if (valor!=null)
                    {
                        new Aviso(this,String.valueOf(valor).trim(),0);
                    }
                    break;
                }
                case 2:{
                    if (!cerrar(foto,nombres[foto-1].toCharArray(),banTurno))
                    {
                       new Aviso(this,"Error al intentar desconectar el jugador!",0); 
                    }
                    break;
                }
                case 5:{
                    if (!desconectar(foto,nombres[foto-1].toCharArray(),banTurno))
                    {
                       new Aviso(this,"Error al intentar desconectar el jugador!",0); 
                    }
                    break;
                }
                case 6:{
                    votarInicio();
                    break;
                }
                case 13:{
                    ejecutaGrito();
                    break;
                }
                 case 15:{
                    cancelaVoto();
                    break;
                }
                 case 19:{
                            if (cartaUsada.getCarta().getColor()!=0)
                            {
                                for (int i=0;i<cartas.size();i++)
                                {
                                    CartaGrafica c=cartas.get(i);
                                    c.setActivo(false);

                                }
                                char valor[]=cartaPila.getCarta().toCharArray();
                                if (tiraCarta(valor))
                                {
                                    AudioFilePlayer player;
                                    player = new AudioFilePlayer(getClass().getResource("sonidos/ponerCarta.wav"),micronucleo,0);
                                    quitaCarta(cartaUsada);
                                    if (!banCambioSentido)
                                        pasaTurno();
                                    else
                                        ponInicial(cartaUsada.getCarta().getCarta());
                                    

                                }
                                else
                                {
                                   AudioFilePlayer player;
                                   player = new AudioFilePlayer(getClass().getResource("sonidos/error.wav"),micronucleo,0);

                                }
                                lblAcumulador.setVisible(false);
                                pnlCarga.setVisible(false);
                                btnRobar.setVisible(false);
                                btnGrito.setVisible(true);
                                btnReto.setVisible(true);
                                btnDesconectar.setVisible(true);
                                btnCerrar.setVisible(true);
                                cartaUsada=null;
                            }
                            else
                            {
                                new CambiaColor(this,cartaUsada.getCarta().getValor());
                            }
                        
                     break;
                 }
                 case 23:{
                        pideCarta();
                        break;
                 }
                 case 27:{
                        mandaReto(txtIP.getText().toCharArray(),txtID.getText().toCharArray(),0);
                        break;
                 }
                 case 29:{
                        mandaRetoUno(txtIP.getText().toCharArray(),txtID.getText().toCharArray());
                        break;
                 }
                
            }
        }
        
    }
    
    
            
    public void tiraComodin(int color)
    {
        Carta c=new Carta(color,13);
        char valor[]=c.getCarta().toCharArray();
        cartaUsada=new CartaGrafica(c,this,0,true);
        if (tiraCarta(valor))
        {
            AudioFilePlayer player;
            player = new AudioFilePlayer(getClass().getResource("sonidos/ponerCarta.wav"),micronucleo,0);
            pasaTurno();
            player = new AudioFilePlayer(getClass().getResource("sonidos/"+color+".wav"),micronucleo,0);
            cartaUsada=null;
        }
        else
        {
            AudioFilePlayer player;
            player = new AudioFilePlayer(getClass().getResource("sonidos/error.wav"),micronucleo,0);
        }
        lblAcumulador.setVisible(false);
        pnlCarga.setVisible(false);
        btnRobar.setVisible(false);
        btnGrito.setVisible(true);
        btnReto.setVisible(true);
        btnDesconectar.setVisible(true);
        btnCerrar.setVisible(true);
    }
    public void tiraComodinCome4(int color)
    {
        Carta c=new Carta(color,14);
        char valor[]=c.getCarta().toCharArray();
        cartaUsada=new CartaGrafica(c,this,0,true);
        if (tiraCarta(valor))
        {
            AudioFilePlayer player;
            player = new AudioFilePlayer(getClass().getResource("sonidos/ponerCarta.wav"),micronucleo,0);
            pasaTurno();
            cartaUsada=null;
        }
        else
        {
            AudioFilePlayer player;
            player = new AudioFilePlayer(getClass().getResource("sonidos/error.wav"),micronucleo,0);
        }
        lblAcumulador.setVisible(false);
        pnlCarga.setVisible(false);
        btnRobar.setVisible(false);
        btnGrito.setVisible(true);
        btnReto.setVisible(true);
        btnDesconectar.setVisible(true);
        btnCerrar.setVisible(true);
    }
    
    public void come4(int ncartas)
     {
         lblCome.setText("+"+ncartas);
         come=ncartas;
         frameCome=1;
         AudioFilePlayer player;
         if (!banCastigo) 
         {
             if (reglas[0]) {
                 if (!acumular(0)) {
                     player = new AudioFilePlayer(getClass().getResource("sonidos/come.wav"), micronucleo, 0);
                     tmrCome.start();
                     pideCartasComer(ncartas);
                     come = 4;
                 }
             } else {
                 player = new AudioFilePlayer(getClass().getResource("sonidos/come.wav"), micronucleo, 0);
                 tmrCome.start();
                 pideCartasComer(ncartas);
                 come = 4;
             }
         }
         else
         {
            if(!banReto)
                ncartas+=2;
            lblCome.setText("+"+ncartas);
            come+=2;
            player = new AudioFilePlayer(getClass().getResource("sonidos/come.wav"), micronucleo, 0);
            tmrCome.start();
            pideCartasComer(ncartas);
            come = 4;
            banCastigo=false;
            banReto=false;
         }
         
     }
    
   public void precome4(int come,List cards)
   {
       if (reglas[3])
           come4(come);
       else
       {
           new AccionCarta(this,come,cards);
       }
           
   }
   public void reto(int come,List cards,AccionCarta ac)
   {
       AudioFilePlayer player;
       boolean decision=false;
       ac.dispose();
       System.out.print(cartaAnterior);
       for (int i = 0; i < cards.size(); i++) 
       {
           
           if (cartaAnterior.getColor()==((Carta)cards.get(i)).getColor())
           {
               decision=true;
               break;
           }
       }
       if (decision)
       {
            reto.iniciar(1,come,false);
            
       }
       else
       {
           reto.iniciar(2,come,false);
           frameReto=come;
           banCastigo=true;
       }
       
   }
    public void iniciaReto(int ncartas)
    {
       if (ncartas==0)
            reto.iniciar(1,0,true);
       else if (ncartas==-1)
           reto.iniciar(0, 0, true);
       else
       {
            banReto=true;
            reto.iniciar(2,ncartas,true);
       }
            
    }
    public void grita()
    {
            AudioFilePlayer player;
            player = new AudioFilePlayer(getClass().getResource("sonidos/uno.wav"),micronucleo,0);
           
    
    }
    public void notificar()
    {
        synchronized(hiloCliente)
        {
            hiloCliente.notify();
        }
    }
    public void setCODOP(int CODOP)
    {
        this.CODOP=CODOP;
    }
    public int getID()
    {
        return idProceso;
    }
    public boolean getTurno()
    {
        return banTurno;
    }
    public boolean getActivo()
    {
        return activo;
    }
    protected void agregaCarta()
    {
        int i,offSetX,tamCarta;
        CartaGrafica tmp;
        quitaCartasVisual();
        tamCarta=577/cartasMano.size();
        offSetX=pnlCartas.getSize().width;
        offSetX-=(tamCarta*(cartasMano.size()-1))+90;
        offSetX/=2;
        for (i=0;i<cartasMano.size();i++)
        {
            tmp=cartas.get(i);
            tmp.setActivo(banTurno);
            tmp.setLocation((offSetX)+(tamCarta*i),48);
            pnlCartas.add(tmp,0);
        }   
        repaint();
        boolean temp=false;
        for (i = 0; i < cartas.size() && !temp; i++) 
            {
                CartaGrafica c=(CartaGrafica)cartas.get(i);
                if (cartaPila.getColor()==c.getCarta().getColor())
                {
                    temp=true;
                    break;
                }
                else if (cartaPila.getValor()==c.getCarta().getValor())
                {
                    temp=true;
                    break;
                }
                else if (c.getCarta().getColor()==0)
                {
                    temp=true;
                    break;
                }    
            }
            if (!temp)
            {
                lblMano.setVisible(true);
                tmrMano.start();
            }
            else
            {
                lblMano.setVisible(false);
                tmrMano.stop();
            }
    }
    protected void agregaCartas(Carta c)
    {
        int i,offSetX,tamCarta;
        CartaGrafica tmp;
        for (i=0;i<cartas.size();i++)
            pnlCartas.remove(cartas.get(i));
        cartas.add(new CartaGrafica(c,this,cartas.size(),false));
        cartasMano.inserta(c);
        tamCarta=577/cartas.size();
        offSetX=pnlCartas.getSize().width;
        offSetX-=(tamCarta*(cartas.size()-1))+90;
        offSetX/=2;
        for (i=cartas.size()-1;i>=0;i--)
        {
            tmp=cartas.get(i);
            tmp.setPoint(new Point((offSetX)+(tamCarta*i),48));
            tmp.setLocation(pnlCartas.getSize().width+45,48);
            if(cartas.size()>6)
                    tmp.activaTimers(5);
            pnlCartas.add(tmp,0);
        }
        if (cartas.size()>6)
        {
            AudioFilePlayer player;
            player = new AudioFilePlayer(getClass().getResource("sonidos/tirarCartas.wav"),micronucleo,0);
        }
        repaint();
        
    }
    
    public void quitaCarta(CartaGrafica c)
    {
        int i,offSetX,tamCarta;
        CartaGrafica tmp;
        quitaCartasVisual();
        cartas.remove(c);
        cartasMano.remove(c.getCarta());
        if (cartas.isEmpty())
        {
            repaint();
            return;
        }
        tamCarta=577/cartas.size();
        offSetX=pnlCartas.getSize().width;
        offSetX-=(tamCarta*(cartas.size()-1))+90;
        offSetX/=2;
        for (i=0;i<cartas.size();i++)
        {
            tmp=cartas.get(i);
            tmp.setLocation((offSetX)+(tamCarta*i),48);
            pnlCartas.add(tmp,0);
        }
        repaint();    
    }
     protected void ponInicial(String cad)
     {
        Image img= new ImageIcon(getClass().getResource("imagenes/baraja/"+cad+".png")).getImage();
        ImageIcon img2=new ImageIcon(img.getScaledInstance(90, 135, Image.SCALE_FAST));
        cartaAnterior=cartaPila;
        cartaPila=new Carta(cad);
        lblCarta.setIcon(img2);
        if (cartaPila.getValor()>12)
        {
            AudioFilePlayer player;
            player = new AudioFilePlayer(getClass().getResource("sonidos/"+cartaPila.getColor()+".wav"),micronucleo,0);
        }
            
     }
    protected void reiniciar()
    {
        txtIP.setEditable(true);
        txtIP.setFont(new java.awt.Font("Arial",Font.PLAIN, 12));
        txtIP.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtIP.setText("");
        txtID.setEditable(true);
        txtID.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
        txtID.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtID.setText("");
        lblMazo.setEnabled(false);
        lblCarta.setEnabled(false);
        pnlCartas.setEnabled(false);
        pnlJugadores.setEnabled(false);
        btnReto.setEnabled(false);
        btnGrito.setEnabled(false);
        btnConectar.setEnabled(true);
        btnDesconectar.setEnabled(false);
        quitaJugadores();
        btnConectar.setText("Conectar");
        btnDesconectar.setEnabled(false);
        Image img= new ImageIcon(getClass().getResource("imagenes/baraja/fondo.png")).getImage();
        ImageIcon img2=new ImageIcon(img.getScaledInstance(90, 135, Image.SCALE_FAST));
        lblCarta.setIcon(img2);
        lblMano.setVisible(false);
        tmrMano.stop();
        quitaCartas();
        modelo=(MiModelo)tblJugadores.getModel();
        modelo.setColumnIdentifiers(nombresCol);
   }
    protected void semiReiniciar()
    {
        lblMazo.setEnabled(false);
        lblCarta.setEnabled(false);
        pnlCartas.setEnabled(false);
        pnlJugadores.setEnabled(false);
        btnReto.setEnabled(false);
        btnGrito.setEnabled(false);
        btnConectar.setEnabled(true);
        btnDesconectar.setEnabled(true);
        lblGanaste.setVisible(false);
        lblPerdiste.setVisible(false);
        banAcumulado=false;
        banAcumuladoC4=false;
        banCambioSentido=false;
        banCastigo=false;
        banGrito=false;
        banIniciado=false;
        banNoCerrar=false;
        banReto=false;
        banTurno=false;
        btnConectar.setText("Votar para iniciar!");
        Image img= new ImageIcon(getClass().getResource("imagenes/baraja/fondo.png")).getImage();
        ImageIcon img2=new ImageIcon(img.getScaledInstance(90, 135, Image.SCALE_FAST));
        lblCarta.setIcon(img2);
        quitaCartas();
        lblMano.setVisible(false);
        tmrMano.stop();
        modelo=(MiModelo)tblJugadores.getModel();
        modelo.setColumnIdentifiers(nombresCol);
     }
    protected void iniciarJuego()
    {
        cartaAnterior=cartaPila;
        lblMazo.setEnabled(true);
        lblCarta.setEnabled(true);
        pnlCartas.setEnabled(true);
        pnlJugadores.setEnabled(true);
        btnDesconectar.setEnabled(true);
        btnConectar.setEnabled(false);
        modelo=(MiModelo)tblJugadores.getModel();
        modelo.setColumnIdentifiers(nombresCol2);
        for (int i = 0; i < tblJugadores.getRowCount(); i++) {
           // modelo.setValueAt("7", i, 3);
            modelo.setValueAt(" ",i,0);
        }
       
    }
    public JPanel getPanel()
    {
        return pnlCartas;
    }   
    protected void setTurno(boolean turno)
    {
        if (turno)
        {
            AudioFilePlayer player;
            player = new AudioFilePlayer(getClass().getResource("sonidos/turno.wav"),micronucleo,0);
            boolean temp=false;
            banReto=false;
            for (int i = 0; i < cartas.size() && !temp; i++) 
            {
                CartaGrafica c=(CartaGrafica)cartas.get(i);
                if (cartaPila.getColor()==c.getCarta().getColor())
                {
                    temp=true;
                    break;
                }
                else if (cartaPila.getValor()==c.getCarta().getValor())
                {
                    temp=true;
                    break;
                }
                else if (c.getCarta().getColor()==0)
                {
                    temp=true;
                    break;
                }    
            }
            if (!temp)
            {
                lblMano.setVisible(true);
                tmrMano.start();
            }
            else if (cartas.size()==2)
               btnGrito.setEnabled(true);
        }
        else
        {
            btnGrito.setEnabled(false);
            lblMano.setVisible(false);
            tmrMano.stop();
        }
        for (int i=0;i<cartas.size();i++)
        {
            CartaGrafica c=cartas.get(i);
            c.setActivo(turno);
            
        }
        repaint();
        banTurno=turno;
        
    }
     protected void setTurno2(boolean turno)
    {
        boolean temp=false;
            for (int i = 0; i < cartas.size() && !temp; i++) 
            {
                CartaGrafica c=(CartaGrafica)cartas.get(i);
                if (cartaPila.getColor()==c.getCarta().getColor())
                {
                    temp=true;
                    break;
                }
                else if (cartaPila.getValor()==c.getCarta().getValor())
                {
                    temp=true;
                    break;
                }
                else if (c.getCarta().getColor()==0)
                {
                    temp=true;
                    break;
                }    
            }
            if (!temp)
            {
                lblMano.setVisible(true);
                tmrMano.start();
            }
       cartas.stream().forEach((c) -> {
            c.setActivo(turno);
        });
        banTurno=turno;
        
    }
     protected void agregaJugador(String foto,String avatar,String voto)
    {
        Object[] fila=new Object[4];
        if (voto.equals("0"))
            fila[0]= new JLabel(new ImageIcon(getClass().getResource("imagenes/otros/no.png")));
        else
            fila[0]= new JLabel(new ImageIcon(getClass().getResource("imagenes/otros/ok.png")));
        fila[1]=new JLabel(new ImageIcon(getClass().getResource("imagenes/avatar2/"+foto+".png")));
        fila[2] = avatar;
        fila[3] = "-";
        modelo.addRow(fila);
        
    }
     protected void agregaJugador2(String foto,String avatar,String turno,String ncartas)
    {
        Object[] fila=new Object[4];
        if (turno.trim().equals("0"))
            fila[0]= "";
        else
            fila[0]= new Cristal(true);
        fila[1]=new JLabel(new ImageIcon(getClass().getResource("imagenes/avatar2/"+foto+".png")));
        fila[2] = avatar;
        if (!banIniciado)
            fila[3] = "-";
        else
            fila[3]=new NumCartas(Integer.parseInt(ncartas.trim()));
        modelo.addRow(fila);
        
         
    }
    protected void setIniciado(boolean iniciado)
    {
        banIniciado=iniciado;
    }
    protected void quitaJugadores()
    {
            modelo=(MiModelo) tblJugadores.getModel();
            while(tblJugadores.getRowCount()>0) 
            {
                modelo.removeRow(0);
            }
    }
    protected void quitaCartas()
    {
        pnlCartas.removeAll();
        pnlCartas.repaint();
        while(cartas.size()>0){
            cartas.remove(0);
            cartasMano.remove(0);
        }
        
    }
     protected void quitaCartasVisual()
    {
        pnlCartas.removeAll();
        pnlCartas.repaint();
       
    }
     public void cancelaTurno()
     {
        frameCancelar=1;
        tmrCancelar.start();
        
     }
     public void come2(int ncartas)
     {
         lblCome.setText("+"+ncartas);
         come=ncartas;
         frameCome=1;
         AudioFilePlayer player;
         if (reglas[0])
         {
             if (!acumular(12))
             {
                player = new AudioFilePlayer(getClass().getResource("sonidos/come.wav"),micronucleo,0);
                tmrCome.start();
                pideCartasComer(ncartas);
                come=2;
             }
         }
         else
         {
            player = new AudioFilePlayer(getClass().getResource("sonidos/come.wav"),micronucleo,0);
            tmrCome.start();
            pideCartasComer(ncartas);
            come=2;
         }
         
     }
     public void come2RU()
     {
         lblCome.setText("+"+2);
         come=2;
         frameCome=1;
         AudioFilePlayer player;
         player = new AudioFilePlayer(getClass().getResource("sonidos/come.wav"),micronucleo,0);
         tmrCome.start();
         pideCartasComer(2);
         banReto=true;
         come=2;
     }
    private void pasaTurno()
    {
        String mensaje="";
        byte[] msg;
        if (cartaUsada==null)
        {
            if (cartaPila.getValor()==12)
                mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|1";
            else if (cartaPila.getValor()==11){
                mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|1";
            }
            else
                mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|1";
        }
        else
        {
            
            if (cartaUsada.getCarta().getValor()==10)
            {
                banCambioSentido=true;
                return;
            }
            if (cartaUsada.getCarta().getValor()==11)
                mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|0&0";
            else if (cartaUsada.getCarta().getValor()==12)
            {
                if (banAcumulado)
                {
                    come+=2;
                    banAcumulado=false;
                    lblAcumulador.setVisible(false);
                    pnlCarga.setVisible(false);
                    btnRobar.setVisible(false);
                    tmrContador.stop();
                    mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|2&"+come;
                    
                }
                else
                {
                    if (!reglas[0])
                        mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|2&2";
                    else
                        mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|2&"+come;
                }
            }
            else if (cartaUsada.getCarta().getValor()==14)
            {
                if (banAcumuladoC4)
                {
                    if(come==2)
                        come=4;
                    come+=4;
                    banAcumuladoC4=false;
                    lblAcumulador.setVisible(false);
                    pnlCarga.setVisible(false);
                    btnRobar.setVisible(false);
                    tmrContador.stop();
                    mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|4&"+come+"&";
                    for (int i = 0; i < cartas.size(); i++) {
                        mensaje+=((((CartaGrafica)cartas.get(i)).getCarta().getCarta())+"&");
                    }
                    mensaje=mensaje.substring(0,mensaje.lastIndexOf("&"));
                    
                }
                else
                {
                    if (!reglas[0])
                        mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|4&4&";
                    else
                        mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|4&"+come+"&";
                    for (int i = 0; i < cartas.size(); i++) {
                        mensaje+=((((CartaGrafica)cartas.get(i)).getCarta().getCarta())+"&");
                    }
                    mensaje=mensaje.substring(0,mensaje.lastIndexOf("&"));

                }
            }
            else
                mensaje= (Integer.parseInt(this.idSiguiente)*-1) +"|"+idProceso+"|"+"18|1";
        }
        come=2;
       
        if (cartas.size()==1)
        {
            if (!banGrito)
            {
                String mensaje2= txtID.getText()+"|"+idProceso+"|"+"31|";
                msg=mensaje2.getBytes();
                micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
            }
            msg=mensaje.getBytes();
            micronucleo.send(Integer.parseInt(this.idSiguiente)*-1, ipSiguiente,msg);
            setTurno(false);
        }
        else if (cartas.isEmpty())
        {
             String mensaje2= txtID.getText()+"|"+idProceso+"|"+"33|";
             msg=mensaje2.getBytes();
             micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
        }
        else
        {
            msg=mensaje.getBytes();
            micronucleo.send(Integer.parseInt(this.idSiguiente)*-1, ipSiguiente,msg);
            setTurno(false);
        }
    }
    
    public void ponCartaPila(Carta c)
    {
        AudioFilePlayer player;
        player = new AudioFilePlayer(getClass().getResource("sonidos/ponerCarta.wav"),micronucleo,0);
        cartaUsada=new CartaGrafica(c,this,0,true);
        CODOP=19;
        notificar();
    }
    
    public void ponCartaPila2(Carta c)
    {
        AudioFilePlayer player;
        player = new AudioFilePlayer(getClass().getResource("sonidos/ponerCarta.wav"),micronucleo,0);
        cartaUsada=new CartaGrafica(c,this,0,true);
        char valor[]=cartaPila.getCarta().toCharArray();
        if (tiraCarta(valor))
            pasaTurno();
        
         
    }
    
    public void ponCartaMano(Carta c)
    {
        AudioFilePlayer player;
        player = new AudioFilePlayer(getClass().getResource("sonidos/tomarCarta.wav"),micronucleo,0);
        cartasMano.inserta(c);
        cartas.add(cartasMano.indexOf(c),new CartaGrafica(c,this,cartas.size(),true));
        agregaCarta();
        if (!reglas[1])
        {
            cambiaTurno();
            setTurno(false);
            pasaTurno();
        }
        
    }
    
    public void ponCartaManoComer(Carta c)
    {
        AudioFilePlayer player;
        player = new AudioFilePlayer(getClass().getResource("sonidos/tomarCarta.wav"),micronucleo,0);
        cartasMano.inserta(c);
        cartas.add(cartasMano.indexOf(c),new CartaGrafica(c,this,cartas.size(),true));
        agregaCarta();
  
    }
    
    
    private void pideCartas(int ncartas)
    {
        String mensaje;
        byte[] msg;
        for (int i = 0; i < ncartas; i++) 
        {
            mensaje= txtID.getText()+"|"+idProceso+"|"+"23|";
            msg=mensaje.getBytes();
            micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
            Mensaje resp=micronucleo.receive(idProceso, msg);
            if (resp.getCodop()==24)
            {
                Carta c=new Carta(resp.getMensaje());
                ponCartaMano(c);  
            }
       }
    }
    
    private void pideCartasComer(int ncartas)
    {
        String mensaje;
        byte[] msg;
        for (int i = 0; i < ncartas; i++) 
        {
            mensaje= txtID.getText()+"|"+idProceso+"|"+"23|";
            msg=mensaje.getBytes();
            micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
            Mensaje resp=micronucleo.receive(idProceso, msg);
            if (resp.getCodop()==24)
            {
                Carta c=new Carta(resp.getMensaje());
                ponCartaManoComer(c);  
            }
       }
    }
    
    public boolean acumular(int come)
    {
        boolean ban=false;
        if (come==0)
        {
            for (int i = 0; i < cartas.size(); i++) 
            {
                CartaGrafica c=cartas.get(i);
                if ((c.getCarta().getColor()!=0||c.getCarta().getValor()!=1))
                    c.setBN(true);
                else
                {
                    ban=true;
                    c.setActivo(true);
                }
            }
        }
        else
        {
            for (int i = 0; i < cartas.size(); i++) {
                CartaGrafica c = cartas.get(i);
                if (c.getCarta().getValor() != come) {
                    c.setBN(true);
                } else {
                    ban = true;
                    c.setActivo(true);
                }
            }
        }
        if (ban)
        {
            lblAcumulador.setVisible(true);
            pnlCarga.setVisible(true);
            btnRobar.setVisible(true);
            btnGrito.setVisible(false);
            btnReto.setVisible(false);
            btnDesconectar.setVisible(false);
            btnCerrar.setVisible(false);
            contador=1;
            tmrContador.start();

        }
        return ban;
    }
    
    //SECCION DEL RESGUARDO DEL CLIENTE
    
    private char[] conectar (int foto, char[] nombre)
    {
        char[] valor=null;
        String mensaje;
        byte[] msg;
        Pila pila= new Pila();
        //aqui metemos los parametros a la pila
        pila.push(nombre);
        pila.push(foto);
        //preparamos el mensaje
        mensaje= txtID.getText()+"|"+idProceso+"|"+"1|";
        //agregamos los parametros
        while(!pila.empty())
        {
            mensaje+=String.valueOf(pila.pop());
            if (!pila.empty())
                mensaje+="&";
        }
        //convertimos a bytes
        msg=mensaje.getBytes();
        micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
        Mensaje resp=micronucleo.receive(idProceso, msg);
        //extraemos el resultado y revisamos la respuesta
        switch(resp.getCodop())
        {
            case 3:
            {
                txtIP.setEditable(false);
                txtIP.setFont(new java.awt.Font("Arial", 1, 12));
                txtIP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                txtID.setEditable(false);
                txtID.setFont(new java.awt.Font("Arial", 1, 12));
                txtID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                btnConectar.setText("Votar para iniciar!");
                btnDesconectar.setEnabled(true);
                String cad=resp.getMensaje().trim();
                reglas=new boolean[cad.length()];
                for (int i = 0; i < reglas.length; i++) {
                  if (cad.charAt(i)=='1')
                      reglas[i]=true;
                  else
                      reglas[i]=false;
                }
                break;
            }
            case 4:
            {
                //extraemos los resultados y los metemos a la pila
                pila.push(resp.getMensaje().toCharArray());
                //Como no hay mas procesamiento de la salida
                //regresamos lo que esta contenido en la pila
                valor=String.valueOf(pila.pop()).toCharArray();
                break;
            }
        }
        return valor;
    }
    
    private boolean cerrar(int foto, char[] nombre, boolean turno)
    {
        String mensaje,tmp;
        byte[] msg;
        Pila pila= new Pila();
        tmp = "";
        //aqui metemos los parametros a la pila
        
        if (!cartas.isEmpty())
        {
            tmp = cartas.stream().map((c) -> c.getCarta().getCarta()+"&").reduce(tmp, String::concat);
            pila.push(tmp.substring(0, tmp.length()-1));
        }
        if(turno)
            pila.push("1");
        else
            pila.push("0");
        pila.push(nombres[foto-1]);
        pila.push(foto);
        //preparamos el mensaje
        mensaje= txtID.getText()+"|"+idProceso+"|"+"2|";
        //agregamos los parametros
        while(!pila.empty())
        {
            mensaje+=String.valueOf(pila.pop());
            if (!pila.empty())
                mensaje+="&";
        }
            
        //convertimos a bytes
        msg=mensaje.getBytes();
        micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
        Mensaje resp=micronucleo.receive(idProceso, msg);     
        if (resp.getCodop()==8)
        {
            if (banTurno)
                pasaTurno();
            activo=false;
            hiloEscuchador.notificar();
            micronucleo.requestFocus();
            dispose();
            return true;
        }
        return false;
    }
    
    private void votarInicio()
    {
        String mensaje;
        byte[] msg;
        mensaje= txtID.getText()+"|"+idProceso+"|"+"6|";
        msg=mensaje.getBytes();
        micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
    }
    
    public boolean desconectar(int foto, char[] nombre,boolean turno)
    {
        String mensaje,tmp;
        byte[] msg;
        Pila pila= new Pila();
        tmp="";
        //aqui metemos los parametros a la pila
        
         if (!cartas.isEmpty())
        {
            tmp = cartas.stream().map((c) -> c.getCarta().getCarta()+"&").reduce(tmp, String::concat);
            pila.push(tmp.substring(0, tmp.length()-1));
        }
        if(turno)
            pila.push("1");
        else
            pila.push("0");
        pila.push(nombres[foto-1]);
        pila.push(foto);
        //preparamos el mensaje
        mensaje= txtID.getText()+"|"+idProceso+"|"+"5|";
        //agregamos los parametros
        while(!pila.empty())
        {
            mensaje+=String.valueOf(pila.pop());
            if (!pila.empty())
                mensaje+="&";
        }
        //convertimos a bytes
        msg=mensaje.getBytes();
        micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
        Mensaje resp=micronucleo.receive(idProceso, msg);
        if (resp.getCodop()==8)
        {
             if (banTurno)
                pasaTurno();
            reiniciar();
            quitaCartas();
            return true;
        }
        return false;
    }
    private void ejecutaGrito()
    {
        String mensaje;
        byte[] msg;
        mensaje= txtID.getText()+"|"+idProceso+"|"+"13|";
        msg=mensaje.getBytes();
        micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
    }
    
    private void cancelaVoto()
    {
        String mensaje;
        byte[] msg;
        mensaje= txtID.getText()+"|"+idProceso+"|"+"15|";
        msg=mensaje.getBytes();
        micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
    }
    private boolean tiraCarta(char[] carta)
    {
        String mensaje;
        byte[] msg;
        Carta c=new Carta(new String(carta));
        Carta ct=cartaUsada.getCarta();
        if(ct.getColor()!=0)
        {
            if ((c.getColor()==ct.getColor())||(c.getColor()!=0&&c.getValor()==ct.getValor()))
            {
                mensaje= txtID.getText()+"|"+idProceso+"|"+"19|"+ct.getCarta();    
                msg=mensaje.getBytes();
                micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
                Mensaje resp=micronucleo.receive(idProceso, msg);
                //extraemos el resultado y revisamos la respuesta
                switch(resp.getCodop())
                {
                    case 20:{
                        return true;
                    }
                }
                return true;
            }
            else
            {
                cartaUsada.agita();
                return  false;
            }
        }
        else
        {
            mensaje= txtID.getText()+"|"+idProceso+"|"+"19|"+c.getCarta();    
            msg=mensaje.getBytes();
            micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
            Mensaje resp=micronucleo.receive(idProceso, msg);
            //extraemos el resultado y revisamos la respuesta
            switch(resp.getCodop())
            {
                case 20:{
                     return true;
                }
            }
            return false;
        }
                
    }
    
    private boolean pideCarta()
    {
        String mensaje;
        byte[] msg;
        mensaje= txtID.getText()+"|"+idProceso+"|"+"23|";
        msg=mensaje.getBytes();
        micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
        Mensaje resp=micronucleo.receive(idProceso, msg);
        if (resp.getCodop()==24)
        {
            Carta c=new Carta(resp.getMensaje());
            if ((c.getColor()==cartaPila.getColor())||(c.getColor()!=0&&c.getValor()==cartaPila.getValor()))
            {
                if (!reglas[2])
                {
                    banNoCerrar=true;
                    new AccionCarta(c,this);
                }    
                    
                else
                    ponCartaPila2(c);
                return false;
            }
            else
            {
                ponCartaMano(c);  
            }
            
        }
        return true;
    }
    
    
    public void setNoCerrar(boolean ban)
    {
        banNoCerrar=ban;
    }
    public void cambiaTurno()
    {
        String mensaje;
        byte[] msg;
        mensaje= txtID.getText()+"|"+idProceso+"|"+"26|";
        msg=mensaje.getBytes();
        micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg);
    }
    
    
    
    public void mandaReto(char[] ip,char[] id,int ncartas)
    {
        
        String mensaje;
        byte[] msg;
        mensaje= txtID.getText()+"|"+idProceso+"|"+"27|"+frameReto;
        msg=mensaje.getBytes();
        micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg); 
    }
    
     public void mandaRetoUno(char[] ip,char[] id)
    {
        
        String mensaje;
        byte[] msg;
        mensaje= txtID.getText()+"|"+idProceso+"|"+"29|";
        msg=mensaje.getBytes();
        micronucleo.send(Integer.parseInt(txtID.getText()), txtIP.getText(),msg); 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnConectar;
    private javax.swing.JButton btnDesconectar;
    private javax.swing.JButton btnGrito;
    private javax.swing.JButton btnReto;
    private javax.swing.JButton btnRobar;
    private javax.swing.JLabel lblAcumulador;
    private javax.swing.JLabel lblCancelado;
    private javax.swing.JLabel lblCarta;
    private javax.swing.JLabel lblCome;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblFoto1;
    private javax.swing.JLabel lblGanaste;
    private javax.swing.JLabel lblGiro;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIDCliente1;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblMano;
    private javax.swing.JLabel lblMazo;
    private javax.swing.JLabel lblNick;
    private javax.swing.JLabel lblPerdiste;
    private javax.swing.JLabel lblReto3;
    private javax.swing.JLabel lblReto4;
    private javax.swing.JLabel lblSonido;
    private javax.swing.JPanel pnlCarga;
    private javax.swing.JPanel pnlCartas;
    private javax.swing.JPanel pnlJugador;
    private javax.swing.JPanel pnlJugadores;
    private javax.swing.JPanel pnlServidor;
    private javax.swing.JScrollPane scrJugadores;
    private javax.swing.JTable tblJugadores;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDCliente;
    private javax.swing.JTextField txtIP;
    // End of variables declaration//GEN-END:variables
}
