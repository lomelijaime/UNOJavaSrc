/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;

/**
 *
 * @author Luis
 */
public class Avatar extends javax.swing.JDialog {

    private final Micronucleo  micronucleo;
    private final String[] nombres={"JOEL","JENNIE","OSVALDO","YOLANDA","DIANA","MARIO"};

    private int avatar;
    private Point initialClick;


    public Avatar(Micronucleo micronucleo) {
        super(micronucleo);
        initComponents();
        avatar=1;
        this.micronucleo=micronucleo;
        setSize(320,180);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(micronucleo);
        setVisible(true);
        cargaImg();
        lblNick.setText(nombres[0]);
        addMouseListener(new MouseAdapter() {
            @Override
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
        requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCerrar = new javax.swing.JButton();
        lblFoto = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        btnDer = new javax.swing.JButton();
        btnIzq = new javax.swing.JButton();
        lblNick = new javax.swing.JLabel();
        lblNick2 = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("imagenes/fondos/logo.png")).getImage());
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        btnCerrar.setBackground(new java.awt.Color(230, 7, 12));
        btnCerrar.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCerrar);
        btnCerrar.setBounds(20, 130, 90, 30);

        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblFoto);
        lblFoto.setBounds(180, 50, 91, 91);

        btnCrear.setBackground(new java.awt.Color(230, 7, 12));
        btnCrear.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        btnCrear.setForeground(new java.awt.Color(255, 255, 255));
        btnCrear.setText("Crear");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        getContentPane().add(btnCrear);
        btnCrear.setBounds(20, 80, 90, 30);

        btnDer.setBackground(new java.awt.Color(230, 7, 12));
        btnDer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/der.png"))); // NOI18N
        btnDer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDerActionPerformed(evt);
            }
        });
        getContentPane().add(btnDer);
        btnDer.setBounds(280, 80, 30, 30);

        btnIzq.setBackground(new java.awt.Color(230, 7, 12));
        btnIzq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/otros/izq.png"))); // NOI18N
        btnIzq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIzqActionPerformed(evt);
            }
        });
        getContentPane().add(btnIzq);
        btnIzq.setBounds(140, 80, 30, 30);

        lblNick.setFont(new java.awt.Font("Britannic Bold", 1, 18)); // NOI18N
        lblNick.setForeground(new java.awt.Color(255, 255, 255));
        lblNick.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNick.setText("SELECCIONA UN AVATAR");
        getContentPane().add(lblNick);
        lblNick.setBounds(140, 140, 170, 40);

        lblNick2.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        lblNick2.setForeground(new java.awt.Color(255, 255, 255));
        lblNick2.setText("SELECCIONA UN AVATAR");
        getContentPane().add(lblNick2);
        lblNick2.setBounds(140, 20, 180, 20);

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/fondos/fondochico.png"))); // NOI18N
        getContentPane().add(lblFondo);
        lblFondo.setBounds(0, 0, 320, 180);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private Avatar getMe()
    {
        return this;
    }
    
    
    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        micronucleo.requestFocus();
        dispose();

    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
       
            int valor=(int)(Math.random()*Integer.MAX_VALUE);
            micronucleo.almacenaCliente(new Cliente(micronucleo,this,valor));
            setVisible(false);
       
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnDerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDerActionPerformed
        avatar++;
       if(avatar==7)
            avatar=1;
        cargaImg();
    }//GEN-LAST:event_btnDerActionPerformed

    private void btnIzqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzqActionPerformed
        avatar--;
        if(avatar==0)
            avatar=6;
        cargaImg();
    }//GEN-LAST:event_btnIzqActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
            micronucleo.requestFocus();
    }//GEN-LAST:event_formWindowClosing
    
    private void cargaImg()
    {
        Image img= new ImageIcon(getClass().getResource("imagenes/avatar/"+avatar+".png")).getImage();
        ImageIcon img2=new ImageIcon(img.getScaledInstance(91, 91, Image.SCALE_FAST));
        lblFoto.setIcon(img2);
        lblNick.setText(nombres[avatar-1]);

    }
    
    public String getNick()
    {
        return lblNick.getText();
    }
    
    
    public int getAvatar()
    {
        return avatar;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnDer;
    private javax.swing.JButton btnIzq;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblNick;
    private javax.swing.JLabel lblNick2;
    // End of variables declaration//GEN-END:variables
}
