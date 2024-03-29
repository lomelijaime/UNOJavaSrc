/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author Luis
 */
public class Aviso extends javax.swing.JDialog {
    
    private final Component c;
    private final int accion;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCerrar = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("imagenes/fondos/logo.png")).getImage());
        setUndecorated(true);
        setResizable(false);
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
        btnCerrar.setBounds(110, 140, 90, 30);

        lblMensaje.setFont(new java.awt.Font("Britannic Bold", 0, 14)); // NOI18N
        lblMensaje.setForeground(new java.awt.Color(255, 255, 255));
        lblMensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensaje.setText("Mensaje de error");
        getContentPane().add(lblMensaje);
        lblMensaje.setBounds(0, 60, 320, 50);

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/fondos/fondochico.png"))); // NOI18N
        getContentPane().add(lblFondo);
        lblFondo.setBounds(0, 0, 320, 180);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public Aviso(Component c, String msg,int accion) {
        initComponents();
        this.c=c;
        this.accion=accion;
        c.setEnabled(false);
        setSize(320,180);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(c);
        lblMensaje.setText(msg);
        setVisible(true);
        Toolkit.getDefaultToolkit().beep();
        this.requestFocus();
    }
    
    public Aviso getMe()
    {
        return this;
    }
    
    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        c.setEnabled(true);
        if(accion==0)
            dispose();
        else
            System.exit(-1);
    }//GEN-LAST:event_btnCerrarActionPerformed

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblMensaje;
    // End of variables declaration//GEN-END:variables
}
