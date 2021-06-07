/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;

/**
 *
 * @author roafi
 */
public class Opciones extends javax.swing.JFrame {

    private Point initialClick;
    private Micronucleo micronucleo;
    private String[] mensajes={"Acumula cartas y comodines Roba para pasar la penalizacion al siguiente jugador",
        "Si robas una carta, tendras que seguir robando hasta que saques una que puedas jugar",
        "Si robas una carta en tu turno y puedes jugarla, lo haras automaticamente",
        "Puedes jugar comodines Roba sin que te desafien"
    };

    public Opciones(Micronucleo micronucleo) {
        initComponents();
        this.micronucleo=micronucleo;
        setSize(852, 480);
        Toolkit tk=Toolkit.getDefaultToolkit();
        Dimension d=tk.getScreenSize();
        setLocation((d.width-getSize().width)/2,(d.height-getSize().height)/2);
        
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
        if (micronucleo.getReglas()[0])
            lblAcumulacion.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/2.png")));
        else
            lblAcumulacion.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/2D.png")));
        if (micronucleo.getReglas()[1])
            lblRobar.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/1.png")));
        else
            lblRobar.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/1D.png")));
        if (micronucleo.getReglas()[2])
            lblForzar.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/3.png")));
        else
            lblForzar.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/3D.png")));
        if (micronucleo.getReglas()[3])
            lblFaroles.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/4.png")));
        else
            lblFaroles.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/4D.png")));
        setVisible(true);
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblFaroles = new javax.swing.JLabel();
        lblRobar = new javax.swing.JLabel();
        lblAcumulacion = new javax.swing.JLabel();
        lblForzar = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        lblRegla3 = new javax.swing.JLabel();
        lblRegla2 = new javax.swing.JLabel();
        lblRegla1 = new javax.swing.JLabel();
        btnAvanzadas = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3), "Reglas Adicionales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Britannic Bold", 1, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);

        lblFaroles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/reglas/4D.png"))); // NOI18N
        lblFaroles.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFaroles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFarolesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblFarolesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblFarolesMouseExited(evt);
            }
        });
        jPanel1.add(lblFaroles);
        lblFaroles.setBounds(630, 40, 95, 92);

        lblRobar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/reglas/1D.png"))); // NOI18N
        lblRobar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblRobar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRobarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRobarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRobarMouseExited(evt);
            }
        });
        jPanel1.add(lblRobar);
        lblRobar.setBounds(260, 40, 95, 92);

        lblAcumulacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/reglas/2D.png"))); // NOI18N
        lblAcumulacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAcumulacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAcumulacionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAcumulacionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAcumulacionMouseExited(evt);
            }
        });
        jPanel1.add(lblAcumulacion);
        lblAcumulacion.setBounds(60, 40, 95, 92);

        lblForzar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/reglas/3D.png"))); // NOI18N
        lblForzar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblForzar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblForzarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblForzarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblForzarMouseExited(evt);
            }
        });
        jPanel1.add(lblForzar);
        lblForzar.setBounds(440, 40, 95, 92);

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sin faroles");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(640, 140, 90, 20);

        lblDescripcion.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        lblDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        lblDescripcion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescripcion.setText("Selecciona una regla");
        jPanel1.add(lblDescripcion);
        lblDescripcion.setBounds(30, 180, 730, 40);

        lblRegla3.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        lblRegla3.setForeground(new java.awt.Color(255, 255, 255));
        lblRegla3.setText("Forzar tirada");
        jPanel1.add(lblRegla3);
        lblRegla3.setBounds(440, 140, 100, 20);

        lblRegla2.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        lblRegla2.setForeground(new java.awt.Color(255, 255, 255));
        lblRegla2.setText("Robar hasta jugar");
        jPanel1.add(lblRegla2);
        lblRegla2.setBounds(240, 140, 140, 20);

        lblRegla1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        lblRegla1.setForeground(new java.awt.Color(255, 255, 255));
        lblRegla1.setText("Acumulación");
        jPanel1.add(lblRegla1);
        lblRegla1.setBounds(60, 140, 100, 20);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(30, 170, 790, 250);

        btnAvanzadas.setBackground(new java.awt.Color(230, 7, 12));
        btnAvanzadas.setFont(new java.awt.Font("Britannic Bold", 1, 12)); // NOI18N
        btnAvanzadas.setForeground(new java.awt.Color(255, 255, 255));
        btnAvanzadas.setText("Avanzadas");
        btnAvanzadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvanzadasActionPerformed(evt);
            }
        });
        getContentPane().add(btnAvanzadas);
        btnAvanzadas.setBounds(700, 440, 120, 30);

        btnAceptar.setBackground(new java.awt.Color(230, 7, 12));
        btnAceptar.setFont(new java.awt.Font("Britannic Bold", 1, 12)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAceptar);
        btnAceptar.setBounds(340, 440, 120, 30);

        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("a las reglas oficiales estos cambios aplicaran a salas de juego");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(320, 90, 490, 20);

        jLabel11.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Selecciona las reglas con las que deseas jugar, adicionales");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(330, 60, 460, 20);

        jLabel8.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("que se inicien a partir de este momento");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(390, 120, 310, 20);

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/fondos/fondo.png"))); // NOI18N
        lblFondo.setToolTipText("");
        getContentPane().add(lblFondo);
        lblFondo.setBounds(0, 0, 852, 480);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblAcumulacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAcumulacionMouseClicked
        micronucleo.getReglas()[0]=!micronucleo.getReglas()[0];
        if (micronucleo.getReglas()[0])
            lblAcumulacion.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/2.png")));
        else
            lblAcumulacion.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/2D.png")));
    }//GEN-LAST:event_lblAcumulacionMouseClicked

    private void lblRobarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRobarMouseClicked
        micronucleo.getReglas()[1]=!micronucleo.getReglas()[1];
        if (micronucleo.getReglas()[1])
            lblRobar.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/1.png")));
        else
            lblRobar.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/1D.png")));
    }//GEN-LAST:event_lblRobarMouseClicked

    private void lblForzarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForzarMouseClicked
        micronucleo.getReglas()[2]=!micronucleo.getReglas()[2];
        if (micronucleo.getReglas()[2])
            lblForzar.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/3.png")));
        else
            lblForzar.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/3D.png")));
    }//GEN-LAST:event_lblForzarMouseClicked

    private void lblFarolesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFarolesMouseClicked
        micronucleo.getReglas()[3]=!micronucleo.getReglas()[3];
        if (micronucleo.getReglas()[3])
            lblFaroles.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/4.png")));
        else
            lblFaroles.setIcon(new ImageIcon(getClass().getResource("imagenes/reglas/4D.png")));
    }//GEN-LAST:event_lblFarolesMouseClicked

    private void lblAcumulacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAcumulacionMouseEntered
        lblDescripcion.setText(mensajes[0]);
    }//GEN-LAST:event_lblAcumulacionMouseEntered

    private void lblAcumulacionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAcumulacionMouseExited
        lblDescripcion.setText("Selecciona o deselecciona una regla");
    }//GEN-LAST:event_lblAcumulacionMouseExited

    private void lblRobarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRobarMouseEntered
        lblDescripcion.setText(mensajes[1]);
    }//GEN-LAST:event_lblRobarMouseEntered

    private void lblRobarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRobarMouseExited
        lblDescripcion.setText("Selecciona o deselecciona una regla");
    }//GEN-LAST:event_lblRobarMouseExited

    private void lblForzarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForzarMouseEntered
        lblDescripcion.setText(mensajes[2]);
    }//GEN-LAST:event_lblForzarMouseEntered

    private void lblForzarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForzarMouseExited
        lblDescripcion.setText("Selecciona o deselecciona una regla");
    }//GEN-LAST:event_lblForzarMouseExited

    private void lblFarolesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFarolesMouseEntered
        lblDescripcion.setText(mensajes[3]);
    }//GEN-LAST:event_lblFarolesMouseEntered

    private void lblFarolesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFarolesMouseExited
        lblDescripcion.setText("Selecciona o deselecciona una regla");
    }//GEN-LAST:event_lblFarolesMouseExited

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        micronucleo.guardarConf();
        dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       micronucleo.guardarConf();
    }//GEN-LAST:event_formWindowClosing

    private void btnAvanzadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanzadasActionPerformed
        micronucleo.guardarConf();
        dispose();
        new OpcionesAvanzadas (micronucleo);
    }//GEN-LAST:event_btnAvanzadasActionPerformed

    public Opciones getMe()
    {
        return this;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAvanzadas;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAcumulacion;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblFaroles;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblForzar;
    private javax.swing.JLabel lblRegla1;
    private javax.swing.JLabel lblRegla2;
    private javax.swing.JLabel lblRegla3;
    private javax.swing.JLabel lblRobar;
    // End of variables declaration//GEN-END:variables
}
