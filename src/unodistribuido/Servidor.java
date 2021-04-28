/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import Barajeador.Barajeador;
import Barajeador.Carta;
import MiTabla.IconCellRenderer;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Luis
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
 * 
*/

public class Servidor extends javax.swing.JFrame implements Runnable {

    private Point initialClick;
    private Micronucleo micronucleo;
    private int idProceso;
    private int jugadores;
    private int seg;
    private boolean activo;
    private boolean banIniciado,banContador;
    private Thread hiloServidor;
    
    private final int JUGADORES_MAX=4;
    
    private String[] nombresCol= {"No.","Avatar", "Nickname", "IP","ID","Listo"};
    private String[] nombres={"KAREN","ANGEL","ALLAN","MARIA","ROSA","LUIS"};
    private Timer tmrContador;
    private Barajeador barajeador;
    
    private DefaultTableModel modelo;
    
    
    public Servidor(Micronucleo micronucleo,Sala sala, int idProceso) {
        initComponents();
        banIniciado=false;
        banContador=false;
        seg=30;
        jugadores=1;
        this.micronucleo=micronucleo;
        this.idProceso=idProceso;
        hiloServidor=new Thread(this);
        activo=true;
        setSize(852, 480);
        Toolkit tk=Toolkit.getDefaultToolkit();
        Dimension d=tk.getScreenSize();
        setLocation((d.width-getSize().width)/2,(d.height-getSize().height)/2);
        
        modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(nombresCol);
        tblJugadores.setModel(modelo);
        tblJugadores.setDefaultRenderer(Object.class,new IconCellRenderer());
        
        TableColumnModel columnModel = tblJugadores.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(25);
        columnModel.getColumn(1).setPreferredWidth(50);
        
        tmrContador = new Timer(1000,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) 
            {
               
                JLabel tmp=getContador();
                seg--;
                if (seg<=0)
                {
                    String respuesta,tmp3;
                    byte[] msg;
                    for (int i = 0; i < tblJugadores.getRowCount(); i++) 
                    {    
                        String tmp2=((JLabel)modelo.getValueAt(i, 5)).getIcon().toString();
                        tmp2=tmp2.substring(tmp2.lastIndexOf("/")+1);
                        if(!tmp2.equals("ok.png"))
                        {
                            tmp2=(String)modelo.getValueAt(i, 3);
                            tmp3=(String)modelo.getValueAt(i, 4);
                            respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"10|Bye";
                            msg=respuesta.getBytes();
                            micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
                            modelo.removeRow(i--);
                        }
                    }
                    for (int i = 0; i < tblJugadores.getRowCount(); i++) 
                    {    
                        String tmp2=((JLabel)modelo.getValueAt(i, 5)).getIcon().toString();
                        tmp2=tmp2.substring(tmp2.lastIndexOf("/")+1);
                        if(tmp2.equals("ok.png"))
                        {
                            tmp2=(String)modelo.getValueAt(i, 3);
                            tmp3=(String)modelo.getValueAt(i, 4);
                            respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"7|"+extraeJugadores();
                            msg=respuesta.getBytes();
                            micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
                        }
                    }
                   barajeador=new Barajeador(tblJugadores.getRowCount());
                   barajeador.barajea();
                   Carta c=barajeador.getCartaMazo();
                   banContador=false;
                   tmrContador.stop();
                   lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/no.png")));
                   lblContador.setText("00:30");
                   String tmp2=null;
                   tmp3=null;
                   for (int i = 0; i < tblJugadores.getRowCount(); i++) 
                   {
                        tmp2=(String)modelo.getValueAt(i, 3);
                        tmp3=(String)modelo.getValueAt(i, 4);
                        respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"11|"+barajeador.getManoJugador(i).getMano()+"&"+c.getCarta();
                        msg=respuesta.getBytes();
                        micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
                   }    
                   banIniciado=true;
                   seg=30;
                   lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/no.png")));
                   tmp.setText("00:"+seg);
                   tmrContador.stop();
                   banContador=false;
                   return;
                }   
                if (seg<10)
                    tmp.setText("00:0"+seg);
                else
                    tmp.setText("00:"+seg);
                
            }
            
        });
        
        addMouseListener(new MouseAdapter() {
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
        
        
        txtIP.setText(micronucleo.getIP());
        txtNombre.setText(sala.getNombre());
        txtID.setText(String.valueOf(idProceso));
        sala.dispose();
        
        setVisible(true);
        hiloServidor.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlJugadores = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblJugadores = new javax.swing.JTable();
        pnlTimer = new javax.swing.JPanel();
        lblTimer = new javax.swing.JLabel();
        lblContador = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        pnlServidor = new javax.swing.JPanel();
        lblIP = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        lblID = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("imagenes/fondos/logo.png")).getImage());
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        pnlJugadores.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3), "Jugadores en la Sala", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Britannic Bold", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlJugadores.setOpaque(false);
        pnlJugadores.setLayout(null);

        tblJugadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblJugadores.setEnabled(false);
        jScrollPane1.setViewportView(tblJugadores);

        pnlJugadores.add(jScrollPane1);
        jScrollPane1.setBounds(20, 30, 440, 402);

        getContentPane().add(pnlJugadores);
        pnlJugadores.setBounds(355, 20, 480, 450);

        pnlTimer.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3), "Timer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Britannic Bold", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlTimer.setOpaque(false);
        pnlTimer.setLayout(null);

        lblTimer.setFont(new java.awt.Font("Britannic Bold", 1, 18)); // NOI18N
        lblTimer.setForeground(new java.awt.Color(255, 255, 255));
        lblTimer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTimer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/no.png"))); // NOI18N
        pnlTimer.add(lblTimer);
        lblTimer.setBounds(20, 30, 30, 30);

        lblContador.setFont(new java.awt.Font("Britannic Bold", 1, 18)); // NOI18N
        lblContador.setForeground(new java.awt.Color(255, 255, 255));
        lblContador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContador.setText("00:30");
        pnlTimer.add(lblContador);
        lblContador.setBounds(50, 30, 70, 30);

        getContentPane().add(pnlTimer);
        pnlTimer.setBounds(170, 350, 140, 80);

        btnSalir.setBackground(new java.awt.Color(255, 109, 0));
        btnSalir.setFont(new java.awt.Font("Britannic Bold", 1, 12)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/cerrar.png"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir);
        btnSalir.setBounds(80, 360, 60, 60);

        pnlServidor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3), "Datos de la Sala de Juego", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Britannic Bold", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlServidor.setOpaque(false);
        pnlServidor.setLayout(null);

        lblIP.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        lblIP.setForeground(new java.awt.Color(255, 255, 255));
        lblIP.setText("IP");
        pnlServidor.add(lblIP);
        lblIP.setBounds(20, 30, 80, 20);

        txtIP.setEditable(false);
        txtIP.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtIP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pnlServidor.add(txtIP);
        txtIP.setBounds(110, 30, 110, 20);

        lblID.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        lblID.setForeground(new java.awt.Color(255, 255, 255));
        lblID.setText("ID");
        pnlServidor.add(lblID);
        lblID.setBounds(20, 60, 80, 20);

        txtID.setEditable(false);
        txtID.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pnlServidor.add(txtID);
        txtID.setBounds(110, 60, 110, 20);

        lblNombre.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre.setText("NOMBRE");
        pnlServidor.add(lblNombre);
        lblNombre.setBounds(20, 90, 80, 20);

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pnlServidor.add(txtNombre);
        txtNombre.setBounds(110, 90, 110, 20);

        getContentPane().add(pnlServidor);
        pnlServidor.setBounds(70, 200, 240, 130);

        lblFondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/fondos/fondo.png"))); // NOI18N
        getContentPane().add(lblFondo);
        lblFondo.setBounds(0, 0, 852, 480);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
      activo=false;
      notificar();
      String tmp2=null,tmp3=null,respuesta;
      byte[] msg;
      for (int i = 0; i < tblJugadores.getRowCount(); i++) 
      {
        tmp2=(String)modelo.getValueAt(i, 3);
        tmp3=(String)modelo.getValueAt(i, 4);
        respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"9|Bye";
        msg=respuesta.getBytes();
        micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
      }    

      micronucleo.eliminaServidor(this);
      micronucleo.eliminaDestinoLocal(idProceso);
      micronucleo.requestFocus();   
      dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      activo=false;
      notificar();
        String tmp2=null,tmp3=null,respuesta;
      byte[] msg;
      for (int i = 0; i < tblJugadores.getRowCount(); i++) 
      {
        tmp2=(String)modelo.getValueAt(i, 3);
        tmp3=(String)modelo.getValueAt(i, 4);
        respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"9|Bye";
        msg=respuesta.getBytes();
        micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
      }    

      micronucleo.eliminaServidor(this);
      micronucleo.eliminaDestinoLocal(idProceso);
      micronucleo.requestFocus();
      dispose();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
   public Servidor getMe()
    {
        return this;
    }
   public void setIP(String IP)
   {
       txtIP.setText(IP);
   }
   public String getNombre()
   {
       return txtNombre.getText().trim();
   }
    @Override
    public void run() 
    {
        String respuesta="";
        Mensaje tmp;
        while(activo)
        {
            byte[] msg=new byte[1024];
            Mensaje resp=micronucleo.receive(idProceso, msg);
            if (!activo)
              return;
           switch(resp.getCodop())
            {
                case 1:
                {
                    StringTokenizer st=new StringTokenizer(resp.getMensaje(),"&");
                    if (tblJugadores.getRowCount()<JUGADORES_MAX)
                    {
                        if (banIniciado)
                        {
                            respuesta= resp.getOrigen()+"|"+resp.getDestino()+"|"+"4|El juego ya inicio, imposible unirse!";
                            msg=respuesta.getBytes();
                            micronucleo.send(resp.getOrigen(), resp.getIP(),msg);
                        }
                        else
                        {
                            agregaJugador(Integer.parseInt(st.nextToken()),st.nextToken(),resp.getIP(),String.valueOf(resp.getOrigen()));
                            revisarInicio();
                            respuesta= resp.getOrigen()+"|"+resp.getDestino()+"|"+"3|Ok";
                            msg=respuesta.getBytes();
                            micronucleo.send(resp.getOrigen(), resp.getIP(),msg);
                            String tmp2=null,tmp3=null;
                            for (int i = 0; i < tblJugadores.getRowCount(); i++) 
                            {
                                tmp2=(String)modelo.getValueAt(i, 3);
                                tmp3=(String)modelo.getValueAt(i, 4);
                                respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"7|"+extraeJugadores();
                                msg=respuesta.getBytes();
                                micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
                            }                            
                        }
                    }
                    
                    else
                    {
                        respuesta= resp.getOrigen()+"|"+resp.getDestino()+"|"+"4|La sala de juegos esta llena!";
                        msg=respuesta.getBytes();
                        micronucleo.send(resp.getOrigen(), resp.getIP(),msg);
                    }
                    break;
                }
                case 2: case 5:
                {
                    StringTokenizer st=new StringTokenizer(resp.getMensaje(),"&");
                    quitaJugador(resp.getIP(),String.valueOf(resp.getOrigen()));
                    if (banIniciado)
                    {
                        if (tblJugadores.getRowCount()<2)
                        {
                                String tmp2=null,tmp3=null;
                                for (int i = 0; i < tblJugadores.getRowCount(); i++) 
                                {
                                  modelo.setValueAt(new JLabel(new ImageIcon(getClass().getResource("imagenes/otros/no.png"))), i, 5);
                                  tmp2=(String)modelo.getValueAt(i, 3);
                                  tmp3=(String)modelo.getValueAt(i, 4);
                                  respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"12|Bye";
                                  msg=respuesta.getBytes();
                                  micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
                                }    
                                banIniciado=false;
                                banContador=false;
                        }
                            
                    }
                    revisarInicio();
                    String tmp2=null,tmp3=null;
                     for (int i = 0; i < tblJugadores.getRowCount(); i++) 
                        {
                            tmp2=(String)modelo.getValueAt(i, 3);
                            tmp3=(String)modelo.getValueAt(i, 4);
                            respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"7|"+extraeJugadores();
                            msg=respuesta.getBytes();
                            micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
                        }  
                    respuesta= resp.getOrigen()+"|"+resp.getDestino()+"|"+"8|ok";
                    msg=respuesta.getBytes();
                    micronucleo.send(resp.getOrigen(), resp.getIP(),msg); 
                    break;
                }
               
                case 6:
                {
                    ponListo(resp.getIP(),String.valueOf(resp.getOrigen()));
                    if (tblJugadores.getRowCount()>=2)
                    {
                        int i,j,total,votos;
                        total=tblJugadores.getRowCount();
                        votos=0;
                        for (i = 0; i < tblJugadores.getRowCount(); i++) 
                        {    
                            String tmp2=((JLabel)modelo.getValueAt(i, 5)).getIcon().toString();
                            tmp2=tmp2.substring(tmp2.lastIndexOf("/")+1);
                            if(tmp2.equals("ok.png"))
                                votos++;
                        }
                        if(votos==total)
                        {
                            barajeador=new Barajeador(tblJugadores.getRowCount());
                            barajeador.barajea();
                            Carta c=barajeador.getCartaMazo();
                            banContador=false;
                            tmrContador.stop();
                            lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/no.png")));
                            lblContador.setText("00:30");
                            String tmp2=null,tmp3=null;
                            for (i = 0; i < tblJugadores.getRowCount(); i++) 
                            {
                                tmp2=(String)modelo.getValueAt(i, 3);
                                tmp3=(String)modelo.getValueAt(i, 4);
                                respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"11|"+barajeador.getManoJugador(i).getMano()+"&"+c.getCarta();
                                msg=respuesta.getBytes();
                                micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
                            }       
                            banIniciado=true;
                            
                        }
                        else
                        {
                            if (total>2)
                            {
                                if(votos>=(total/2)+1)
                                {
                                    lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/ok.png")));
                                    banContador=true;
                                    tmrContador.start();
                                }
                            }
                        }
                    }
                    break;
                }
                case 13:{
                    String tmp2=null,tmp3=null;
                    for (int i = 0; i < tblJugadores.getRowCount(); i++) 
                    {
                        tmp2=(String)modelo.getValueAt(i, 3);
                        tmp3=(String)modelo.getValueAt(i, 4);
                        respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|14|UNO";
                        msg=respuesta.getBytes();
                        micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
                    }  
                    break;
                }
                case 15:{
                    quitaListo(resp.getIP(),String.valueOf(resp.getOrigen()));
                    if (tblJugadores.getRowCount()>=2)
                    {
                        int i,j,total,votos;
                        total=tblJugadores.getRowCount();
                        votos=0;
                        for (i = 0; i < tblJugadores.getRowCount(); i++) 
                        {    
                            String tmp2=((JLabel)modelo.getValueAt(i, 5)).getIcon().toString();
                            tmp2=tmp2.substring(tmp2.lastIndexOf("/")+1);
                            if(tmp2.equals("ok.png"))
                                votos++;
                        }
                        if(votos==total)
                        {
                            barajeador=new Barajeador(tblJugadores.getRowCount());
                            barajeador.barajea();
                            Carta c=barajeador.getCartaMazo();
                            banContador=false;
                            tmrContador.stop();
                            lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/no.png")));
                            lblContador.setText("00:30");
                            String tmp2=null,tmp3=null;
                            for (i = 0; i < tblJugadores.getRowCount(); i++) 
                            {
                                tmp2=(String)modelo.getValueAt(i, 3);
                                tmp3=(String)modelo.getValueAt(i, 4);
                                respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"11|"+barajeador.getManoJugador(i).getMano()+"&"+c.getCarta();
                                msg=respuesta.getBytes();
                                micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
                            }       
                            banIniciado=true;
                            
                        }
                        else
                        {
                            if (total>2)
                            {
                                if(votos>=(total/2)+1)
                                {
                                    lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/ok.png")));
                                    banContador=true;
                                    tmrContador.start();
                                }
                                else
                                {
                                    seg=30;
                                    lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/no.png")));
                                    lblContador.setText("00:30");
                                    tmrContador.stop();
                                    banContador=false;
                                }
                            }
                        }
                    }
                    break;
                }
                
            }
                            
        }
        
    }
    private void revisarInicio()
    {
        if (banIniciado)
            return;
        int i,j,total,votos;
        total=tblJugadores.getRowCount();
        votos=0;
        for (i = 0; i < tblJugadores.getRowCount(); i++) 
        {    
            String tmp2=((JLabel)modelo.getValueAt(i, 5)).getIcon().toString();
            tmp2=tmp2.substring(tmp2.lastIndexOf("/")+1);
            if(tmp2.equals("ok.png"))
                votos++;
        }
        if (total>2)
        {
            if(votos==total)
            {
                barajeador=new Barajeador(tblJugadores.getRowCount());
                barajeador.barajea();
                Carta c=barajeador.getCartaMazo();
                banContador=false;
                tmrContador.stop();
                lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/no.png")));
                lblContador.setText("00:30");
                String tmp2=null,tmp3=null;
                for (i = 0; i < tblJugadores.getRowCount(); i++) 
                {
                    tmp2=(String)modelo.getValueAt(i, 3);
                    tmp3=(String)modelo.getValueAt(i, 4);
                    String respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"11|"+barajeador.getManoJugador(i).getMano()+"&"+c.getCarta();
                    byte [] msg=respuesta.getBytes();
                    micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
                }    
                banIniciado=true;
            }
            else if(votos<(total/2)+1)
            {
                lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/no.png")));
                banContador=false;
                tmrContador.stop();
                lblContador.setText("00:30");
                seg=30;
            }
            else if(votos>=(total/2)+1)
            {
                lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/ok.png")));
                banContador=true;
                tmrContador.start();
            }
        }
        else
        {
            
            if(votos==total&&total>=2)
            {
                barajeador=new Barajeador(tblJugadores.getRowCount());
                barajeador.barajea();
                Carta c=barajeador.getCartaMazo();
                banContador=false;
                tmrContador.stop();
                lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/no.png")));
                lblContador.setText("00:30");
                String tmp2=null,tmp3=null;
                for (i = 0; i < tblJugadores.getRowCount(); i++) 
                {
                    tmp2=(String)modelo.getValueAt(i, 3);
                    tmp3=(String)modelo.getValueAt(i, 4);
                    String respuesta= (Integer.parseInt(tmp3)*-1)+"|"+idProceso+"|"+"11|"+barajeador.getManoJugador(i).getMano()+"&"+c.getCarta();
                    byte [] msg=respuesta.getBytes();
                    micronucleo.send((Integer.parseInt(tmp3)*-1), tmp2,msg);
                }    
                banIniciado=true; 
            }
            else if (banContador)
            {
                banContador=false;
                tmrContador.stop();
                lblTimer.setIcon(new ImageIcon(getClass().getResource("imagenes/otros/no.png")));
                lblContador.setText("00:30");
            }
        }
    }
     private void agregaJugador(int foto,String avatar, String IP, String ID)
    {
        Object fila[]=new Object[6];
        fila[0]=String.valueOf(jugadores++);
        fila[1]=new JLabel(new ImageIcon(getClass().getResource("imagenes/avatar2/"+foto+".png")));
        fila[2]=avatar;
        fila[3]=IP;
        fila[4]=ID;
        fila[5]=new JLabel(new ImageIcon(getClass().getResource("imagenes/otros/no.png")));
        modelo.addRow(fila);
    }
    private JLabel getContador()
    {
        return lblContador;
    }
     private void ponListo(String IP,String ID)
     {
         String tmp;
        for (int i = 0; i < tblJugadores.getRowCount(); i++) 
        {
            tmp=(String)modelo.getValueAt(i, 3);
            if (tmp.equals(IP))
            {
                tmp=(String)modelo.getValueAt(i, 4);
                if(tmp.equals(ID))
                {
                    modelo.setValueAt(new JLabel(new ImageIcon(getClass().getResource("imagenes/otros/ok.png"))), i, 5);
                    return;
                }
            }
        }
     }
     private void quitaListo(String IP,String ID)
     {
         String tmp;
        for (int i = 0; i < tblJugadores.getRowCount(); i++) 
        {
            tmp=(String)modelo.getValueAt(i, 3);
            if (tmp.equals(IP))
            {
                tmp=(String)modelo.getValueAt(i, 4);
                if(tmp.equals(ID))
                {
                    modelo.setValueAt(new JLabel(new ImageIcon(getClass().getResource("imagenes/otros/no.png"))), i, 5);
                    return;
                }
            }
        }
     }
     private String extraeJugadores()
     {
        String tmp="";
        int i,j;
        for (i = 0; i < tblJugadores.getRowCount(); i++) 
        {    
            for (j = 0; j < nombres.length; j++) 
            {
                if (nombres[j].equals(((String)modelo.getValueAt(i, 2)).trim()))
                    break;
            }
            tmp+=(j+1)+"&";
        }
        tmp=tmp.substring(0,tmp.lastIndexOf("&"));
        return tmp;
     }
     
     
    private void quitaJugador(String IP,String ID)
    {
            String tmp;
            for (int i = 0; i < tblJugadores.getRowCount(); i++) {
                tmp = (String) modelo.getValueAt(i, 3);
                if (tmp.equals(IP)) {
                    tmp = (String) modelo.getValueAt(i, 4);
                    if (tmp.equals(ID)) {
                             modelo.removeRow(i);
                             return;
                    }
                }
            }
     
    }
    public void notificar()
    {
        synchronized(hiloServidor)
        {
            hiloServidor.notify();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblContador;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTimer;
    private javax.swing.JPanel pnlJugadores;
    private javax.swing.JPanel pnlServidor;
    private javax.swing.JPanel pnlTimer;
    private javax.swing.JTable tblJugadores;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
