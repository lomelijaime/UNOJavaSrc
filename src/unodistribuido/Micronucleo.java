
package unodistribuido;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 *
 * @author Luis
 */
public class Micronucleo extends javax.swing.JFrame 
{
    private DatagramPacket dp;
    private DatagramSocket dsReceive, dsSend;
    private InetAddress ipServidor;
    private final static short ENVIO = 6969;
    private final static short RECEPCION = 6968;
    private Hashtable tablaLocal,tablaRemota; 
    private RecibePaquetes recibePaquetes;
    private Point initialClick;
    private ArrayList<Servidor> servidores;
   
    public Micronucleo() 
    {
        boolean ban=false;
        initComponents();
        
        servidores=new ArrayList<Servidor>();
        
        try
        {
            dsReceive = new DatagramSocket(RECEPCION);
            dsReceive.setSoTimeout(3000);
        } catch(SocketException se)
        {
              new Aviso(this,"No se puede abrir el socket del servidor",1);
              ban=true;
        }
        try
        {
            dsSend = new DatagramSocket(ENVIO);
            ipServidor = InetAddress.getByName("127.0.0.1");                   
        } catch(SocketException se)
        {
              new Aviso(this,"No se puede abrir el socket del cliente",1);
              ban=true;
        }catch(UnknownHostException uhe)
        {
            new Aviso(this,"No se puede obtener la IP del servidor",1);
            ban=true;
        }   
        recibePaquetes=new RecibePaquetes(dsReceive,this);
        tablaLocal=new Hashtable();
        tablaRemota=new Hashtable();
        cmbIP.setModel( new javax.swing.DefaultComboBoxModel(getMachineIP()));
        setSize(716,358);
        Toolkit tk=Toolkit.getDefaultToolkit();
        Dimension d=tk.getScreenSize();
        setLocation((d.width-getSize().width)/2,(d.height-getSize().height)/2);
        
         this.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e)
		{
			tablaLocal.clear();
			tablaRemota.clear();
			recibePaquetes.paraHilo();
			System.exit(0);
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
        public void mouseDragged(MouseEvent e) {

            // get location of Window
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
         
        if(!ban)
        {
            setVisible(true);
            recibePaquetes.start();
        }
        
    } 
    public void almacenaDestinoRemoto(String ip, int id)
    {
        tablaRemota.put(new Integer(id),new DatosServidor(id,ip));
    }
    
    public void almacenaDestinoLocal(int id)
    {
        tablaLocal.put(new Integer(id),new DatosServidor(id,"127.0.0.1"));
    }
    
    public void eliminaDestinoLocal(int id)
    {
        tablaLocal.remove(new Integer(id));
    }
    
    public void almacenaServidor(Servidor srv)
    {
        servidores.add(srv);
    }
    public void eliminaServidor(Servidor srv)
    {
        servidores.remove(srv);
    }
    
   
    
    public Micronucleo getMe()
    {
        return this;
    }
    public boolean send(int idDestino,String IP,byte[] msg)
    {
        DatosServidor datos;
        if (tablaLocal.containsKey(new Integer(idDestino)))
	{
           
            try 
            {
                ipServidor = InetAddress.getByName("127.0.0.1");
            } catch (UnknownHostException ex) 
            {
                new Aviso(this,"No se puede obtener el destino",0);
                return false;
            }
        }
	else
	{
            if (tablaRemota.containsKey(new Integer(idDestino)))
            {
        	datos=(DatosServidor)tablaRemota.get(new Integer(idDestino));
                try {                   
                    ipServidor = InetAddress.getByName(datos.getIP());
                } catch (UnknownHostException ex) {
                    new Aviso(this,"No se puede obtener el destino",0);
                }
            }
            else
            {
                try
        	{
                    ipServidor = InetAddress.getByName(IP);                   
                }catch (UnknownHostException ex) 
                {
                    new Aviso(this,"No se puede obtener el destino",0);
                    return false;
                }
          	datos=new DatosServidor(idDestino,ipServidor.getHostAddress());
                tablaRemota.put(new Integer(idDestino),datos);
            }
            
        }
        DatagramPacket dp = new DatagramPacket (msg,msg.length,ipServidor,RECEPCION);
            try {
                 dsSend.send(dp);
                } catch (IOException ex) {
                    new Aviso(this,"Error al enviar el mensaje por la red",0);
                    return false;
                }
         return true;
    }
    public Mensaje receive(int id, byte mensaje[])
    {
	DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length);
        ProcesoEspera espera = new ProcesoEspera(id,null);
        recibePaquetes.poner(espera);
        synchronized(espera)
        {
            try
            {
                espera.wait();
                return espera.getMensaje();
                
            } catch(Exception e){}
        }  
        return null;
    }
    public String[] getMachineIP() 
    {
        Enumeration en=null;
        List<String> datos=new ArrayList<String>();
             try {
                 en = NetworkInterface.getNetworkInterfaces();
                 while(en.hasMoreElements()){
                     NetworkInterface ni=(NetworkInterface) en.nextElement();
                     Enumeration ee = ni.getInetAddresses();
                     while(ee.hasMoreElements()) {
                         InetAddress ia= (InetAddress) ee.nextElement();
                         if (!ia.getHostAddress().contains(":")){
                             datos.add(ia.getHostAddress());
                         }
                     }

             }
             } catch (SocketException ex) {}
             String[] tmp;
             tmp=new String[datos.size()];
             for(int i=0;i<tmp.length;i++)
                 tmp[i]=datos.get(i);
             return tmp;
    }

    public String getIP()
    {
        return cmbIP.getItemAt(cmbIP.getSelectedIndex());
    }
    public boolean buscar(String nombre)
    {
        Servidor tmp;
        for (int i = 0; i < servidores.size(); i++) 
        {
            tmp=servidores.get(i);
            if (tmp.getNombre().equalsIgnoreCase(nombre))
                return true;
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSalir = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        btnOpciones = new javax.swing.JButton();
        btnServidor = new javax.swing.JButton();
        pnlRed = new javax.swing.JPanel();
        lblIP = new javax.swing.JLabel();
        cmbIP = new javax.swing.JComboBox<>();
        lblVersion = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("imagenes/fondos/logo.png")).getImage());
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        btnSalir.setBackground(new java.awt.Color(230, 7, 12));
        btnSalir.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir!");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir);
        btnSalir.setBounds(500, 260, 190, 40);

        btnCliente.setBackground(new java.awt.Color(230, 7, 12));
        btnCliente.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        btnCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnCliente.setText("Nuevo Jugador!");
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });
        getContentPane().add(btnCliente);
        btnCliente.setBounds(500, 50, 190, 40);

        btnOpciones.setBackground(new java.awt.Color(230, 7, 12));
        btnOpciones.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        btnOpciones.setForeground(new java.awt.Color(255, 255, 255));
        btnOpciones.setText("Opciones!");
        btnOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpcionesActionPerformed(evt);
            }
        });
        getContentPane().add(btnOpciones);
        btnOpciones.setBounds(500, 190, 190, 40);

        btnServidor.setBackground(new java.awt.Color(230, 7, 12));
        btnServidor.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        btnServidor.setForeground(new java.awt.Color(255, 255, 255));
        btnServidor.setText("Nueva Sala de Juego!");
        btnServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServidorActionPerformed(evt);
            }
        });
        getContentPane().add(btnServidor);
        btnServidor.setBounds(500, 120, 190, 40);

        pnlRed.setBackground(new java.awt.Color(255, 255, 255));
        pnlRed.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Red", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Britannic Bold", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlRed.setForeground(new java.awt.Color(255, 255, 255));
        pnlRed.setFont(new java.awt.Font("Britannic Bold", 0, 14)); // NOI18N
        pnlRed.setOpaque(false);

        lblIP.setFont(new java.awt.Font("Britannic Bold", 0, 14)); // NOI18N
        lblIP.setForeground(new java.awt.Color(255, 255, 255));
        lblIP.setText("IP:");

        cmbIP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbIP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbIPItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlRedLayout = new javax.swing.GroupLayout(pnlRed);
        pnlRed.setLayout(pnlRedLayout);
        pnlRedLayout.setHorizontalGroup(
            pnlRedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIP)
                .addGap(18, 18, 18)
                .addComponent(cmbIP, 0, 144, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlRedLayout.setVerticalGroup(
            pnlRedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIP)
                    .addComponent(cmbIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlRed);
        pnlRed.setBounds(10, 10, 210, 67);

        lblVersion.setFont(new java.awt.Font("Britannic Bold", 0, 14)); // NOI18N
        lblVersion.setForeground(new java.awt.Color(255, 255, 255));
        lblVersion.setText("VERSION 0.1 (Solo fines educativos)");
        getContentPane().add(lblVersion);
        lblVersion.setBounds(20, 340, 240, 15);

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/fondos/micronucleo.jpg"))); // NOI18N
        getContentPane().add(lblFondo);
        lblFondo.setBounds(0, 0, 716, 358);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        tablaLocal.clear();
	tablaRemota.clear();
	recibePaquetes.paraHilo();
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
       new Avatar(this);
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServidorActionPerformed
        
        new Sala(this);
    }//GEN-LAST:event_btnServidorActionPerformed

    private void cmbIPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbIPItemStateChanged
         if(evt.getStateChange() == ItemEvent.SELECTED) 
         {
             Servidor tmp;
             for (int i = 0; i < servidores.size(); i++) 
             {
                tmp=servidores.get(i);
                tmp.setIP(getIP());
             }
         }
             
    }//GEN-LAST:event_cmbIPItemStateChanged

    private void btnOpcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpcionesActionPerformed
        
        new Opciones(this);        
      
    }//GEN-LAST:event_btnOpcionesActionPerformed

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnOpciones;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnServidor;
    private javax.swing.JComboBox<String> cmbIP;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JPanel pnlRed;
    // End of variables declaration//GEN-END:variables
}
