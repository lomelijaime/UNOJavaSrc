/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import Barajeador.Carta;
import Barajeador.CartaGrafica;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import MiTabla.IconCellRenderer;
import java.awt.Font;
import java.util.ArrayList;
import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import javax.swing.table.TableColumnModel;
import utilidades.Pila;
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
 * 4: Negacion de conexion al servidor
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
 * 
*/

/**
 * RESGUARDO DEL CLIENTE
 * 
 * cadena conectar(in entero,in cadena)
 * booleano cerrar(in entero,in cadena)
 * booleano desconectar(in entero,in cadena)
 * vacio votarInicio(vacio)
 * vacia ejecutaGrito(vacio)
 * vacia cancelaVoto(vacio)
 * 
 */

public final class Cliente extends javax.swing.JFrame implements Runnable {

    private final Micronucleo micronucleo;
    private final int idProceso;
    private final int foto;
    private final Thread hiloCliente;
    private final HiloEscuchador hiloEscuchador;
    private final String[] nombres={"JOEL","JENNIE","OSVALDO","YOLANDA","DIANA","MARIO"};
    private final String[] nombresCol= {"Voto","Avatar", "Nombre", "# Cartas"};
    private final ArrayList<CartaGrafica> cartas;
    
    private int CODOP;
    private Clip sonido;
    private boolean activo;
    private Point initialClick;
    private DefaultTableModel modelo;
    private boolean banGrito;
   
    
    public Cliente(Micronucleo micronucleo,Avatar avatar,int idProceso) 
    {
        initComponents();
        this.micronucleo=micronucleo;
        this.idProceso=idProceso;
        activo=true;
        CODOP=0;
        cartas=new ArrayList<>();
        setSize(1080, 608);
        Toolkit tk=Toolkit.getDefaultToolkit();
        Dimension d=tk.getScreenSize();
        setLocation((d.width-getSize().width)/2,(d.height-getSize().height)/2);
        txtIDCliente.setText(String.valueOf(idProceso));
        lblNick.setText(nombres[avatar.getAvatar()-1]);
        Image img= new ImageIcon(getClass().getResource("imagenes/avatar/"+avatar.getAvatar()+".png")).getImage();
        ImageIcon img2=new ImageIcon(img.getScaledInstance(91, 91, Image.SCALE_FAST));
        lblFoto.setIcon(img2);
        foto=avatar.getAvatar();
        avatar.dispose();
        cambiaImagenSonido();
        
        modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(nombresCol);
        tblJugadores.setModel(modelo);
        tblJugadores.setDefaultRenderer(Object.class,new IconCellRenderer());
        
        TableColumnModel columnModel = tblJugadores.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
         
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
        setVisible(true);
    }
    
   
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlJugadores = new javax.swing.JPanel();
        scrJugadores = new javax.swing.JScrollPane();
        tblJugadores = new javax.swing.JTable();
        pnlJugador = new javax.swing.JPanel();
        lblNick = new javax.swing.JLabel();
        txtIDCliente = new javax.swing.JTextField();
        lblFoto = new javax.swing.JLabel();
        lblIDCliente1 = new javax.swing.JLabel();
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
        lblFondo = new javax.swing.JLabel();

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

        pnlJugadores.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3), "Jugadores en la Sala", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Britannic Bold", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlJugadores.setEnabled(false);
        pnlJugadores.setOpaque(false);
        pnlJugadores.setLayout(null);

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
        lblFoto.setBounds(20, 60, 91, 91);

        lblIDCliente1.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        lblIDCliente1.setForeground(new java.awt.Color(255, 255, 255));
        lblIDCliente1.setText("ID JUGADOR");
        pnlJugador.add(lblIDCliente1);
        lblIDCliente1.setBounds(20, 30, 90, 20);

        getContentPane().add(pnlJugador);
        pnlJugador.setBounds(50, 240, 270, 170);

        pnlCartas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        pnlCartas.setEnabled(false);
        pnlCartas.setMaximumSize(new java.awt.Dimension(705, 233));
        pnlCartas.setMinimumSize(new java.awt.Dimension(705, 233));
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
        getContentPane().add(btnReto);
        btnReto.setBounds(640, 520, 60, 60);

        lblMazo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/baraja/fondo2.png"))); // NOI18N
        lblMazo.setText("jLabel2");
        lblMazo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        lblMazo.setEnabled(false);
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

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/fondos/fondogrande.png"))); // NOI18N
        getContentPane().add(lblFondo);
        lblFondo.setBounds(0, 0, 1080, 608);

        pack();
    }// </editor-fold>//GEN-END:initComponents
  
    private void btnDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesconectarActionPerformed
        CODOP=5;
        notificar();
    }//GEN-LAST:event_btnDesconectarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
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
            btnConectar.setEnabled(true);
            btnConectar.setText("Votar para iniciar!!");
            notificar();
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
       
        CODOP=13;
        notificar();       
    }//GEN-LAST:event_btnGritoActionPerformed

    private void lblSonidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSonidoMouseClicked
        micronucleo.cambiaImagenSonido();
    }//GEN-LAST:event_lblSonidoMouseClicked
   
    public void cerrar()
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
                    if (!cerrar(foto,nombres[foto-1].toCharArray()))
                    {
                       new Aviso(this,"Error al intentar desconectar el jugador!",0); 
                    }
                    break;
                }
                case 5:{
                    if (!desconectar(foto,nombres[foto-1].toCharArray()))
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
            }
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
    
    public int getID()
    {
        return idProceso;
    }
    public boolean getActivo()
    {
        return activo;
    }
    
    protected void agregaCarta(Carta c)
    {
        int i,offSetX,tamCarta;
        CartaGrafica tmp;
        for (i=0;i<cartas.size();i++)
            pnlCartas.remove(cartas.get(i));
        cartas.add(new CartaGrafica(c,this,cartas.size()));
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
    protected void agregaCartas(Carta c)
    {
        int i,offSetX,tamCarta;
        CartaGrafica tmp;
        for (i=0;i<cartas.size();i++)
            pnlCartas.remove(cartas.get(i));
        cartas.add(new CartaGrafica(c,this,cartas.size()));
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
                    tmp.activaTimers(15);
            pnlCartas.add(tmp,0);
        }
        if (cartas.size()>6)
        {
            AudioFilePlayer player;
            player = new AudioFilePlayer(getClass().getResource("sonidos/tirarCartas.wav"),micronucleo,0);
        }
        repaint();
        
    }
    protected void quitaCarta()
    {
        CartaGrafica tmp;
        int i,offSetX,tamCarta;
        for (i=0;i<cartas.size();i++)
            pnlCartas.remove(cartas.get(i));
        cartas.remove(cartas.size()-1);
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
    protected void quitaCarta(CartaGrafica c)
    {
        int i,offSetX,tamCarta;
        CartaGrafica tmp;
        for (i=0;i<cartas.size();i++)
            pnlCartas.remove(cartas.get(i));
        cartas.remove(c);
        if (cartas.isEmpty())
        {
            repaint();
            return;
        }
        tamCarta=770/cartas.size();
        offSetX=pnlCartas.getSize().width;
        offSetX-=(tamCarta*(cartas.size()-1))+120;
        offSetX/=2;
        for (i=0;i<cartas.size();i++)
        {
            tmp=cartas.get(i);
            tmp.setLocation((offSetX)+(tamCarta*i),65);
            pnlCartas.add(tmp,0);
        }
        repaint();    
    }
     protected void ponInicial(String cad)
     {
        Image img= new ImageIcon(getClass().getResource("imagenes/baraja/"+cad+".png")).getImage();
        ImageIcon img2=new ImageIcon(img.getScaledInstance(90, 135, Image.SCALE_FAST));
        lblCarta.setIcon(img2);
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
        quitaCartas();
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
        btnConectar.setText("Votar para iniciar!");
        Image img= new ImageIcon(getClass().getResource("imagenes/baraja/fondo.png")).getImage();
        ImageIcon img2=new ImageIcon(img.getScaledInstance(90, 135, Image.SCALE_FAST));
        lblCarta.setIcon(img2);
        quitaCartas();
    }
    protected void iniciarJuego()
    {
        lblMazo.setEnabled(true);
        lblCarta.setEnabled(true);
        pnlCartas.setEnabled(true);
        pnlJugadores.setEnabled(true);
        btnReto.setEnabled(true);
        btnGrito.setEnabled(true);
        btnDesconectar.setEnabled(true);
        btnConectar.setEnabled(false);
        for (int i = 0; i < tblJugadores.getRowCount(); i++) {
            modelo.setValueAt("7", i, 3);
        }
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
    
    protected void quitaJugadores()
    {
        modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(nombresCol);
        tblJugadores.setModel(modelo);
        tblJugadores.setDefaultRenderer(Object.class,new IconCellRenderer());
         TableColumnModel columnModel = tblJugadores.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
    }
    protected void quitaCartas()
    {
        pnlCartas.removeAll();
        pnlCartas.repaint();
        while(cartas.size()>0)
        cartas.remove(0);
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
    
    private boolean cerrar(int foto, char[] nombre)
    {
        String mensaje;
        byte[] msg;
        Pila pila= new Pila();
        //aqui metemos los parametros a la pila
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
    
    public boolean desconectar(int foto, char[] nombre)
    {
        String mensaje;
        byte[] msg;
        Pila pila= new Pila();
        //aqui metemos los parametros a la pila
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnConectar;
    private javax.swing.JButton btnDesconectar;
    private javax.swing.JButton btnGrito;
    private javax.swing.JButton btnReto;
    private javax.swing.JLabel lblCarta;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIDCliente1;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblMazo;
    private javax.swing.JLabel lblNick;
    private javax.swing.JLabel lblSonido;
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
