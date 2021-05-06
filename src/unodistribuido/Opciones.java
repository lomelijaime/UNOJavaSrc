
package unodistribuido;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class Opciones extends javax.swing.JFrame {

    private Micronucleo  micronucleo;
    private Point initialClick;
    public Opciones(Micronucleo micronucleo) {
        
        initComponents();
        this.micronucleo=micronucleo;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(micronucleo);
         setVisible(true);
         //this.getContentPane().setBackground(new Color(230,7,12));
         setSize(320,180);

         
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
    }

    private Opciones getMe()
    {
        return this;
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAcerca = new javax.swing.JButton();
        btnAyuda = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu de opciones");
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAcerca.setBackground(new java.awt.Color(230, 7, 12));
        btnAcerca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAcerca.setForeground(new java.awt.Color(255, 255, 255));
        btnAcerca.setText("Acerca de");
        btnAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcercaActionPerformed(evt);
            }
        });
        getContentPane().add(btnAcerca, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 137, -1));

        btnAyuda.setBackground(new java.awt.Color(230, 7, 12));
        btnAyuda.setFont(new java.awt.Font("Yu Gothic Medium", 1, 11)); // NOI18N
        btnAyuda.setForeground(new java.awt.Color(255, 255, 255));
        btnAyuda.setText("Ayuda");
        btnAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAyudaActionPerformed(evt);
            }
        });
        getContentPane().add(btnAyuda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 137, -1));

        btnRegresar.setBackground(new java.awt.Color(230, 7, 12));
        btnRegresar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 137, 20));

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 140, 10));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("    Opciones");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 121, -1));

        jRadioButton1.setBackground(new java.awt.Color(230, 7, 12));
        jRadioButton1.setFont(new java.awt.Font("Yu Gothic Medium", 1, 11)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Musica");
        jRadioButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 137, -1));

        jRadioButton2.setBackground(new java.awt.Color(230, 7, 12));
        jRadioButton2.setFont(new java.awt.Font("Yu Gothic Medium", 1, 11)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Efectos de sonidos");
        jRadioButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/fondos/fondochico.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 180));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAyudaActionPerformed
        Ayuda abrir = new Ayuda();
        abrir.setVisible(true);
        
    }//GEN-LAST:event_btnAyudaActionPerformed

    private void btnAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcercaActionPerformed
        Acerca abrir = new Acerca();
        abrir.setVisible(true);
        
    }//GEN-LAST:event_btnAcercaActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        micronucleo.requestFocus();
        dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcerca;
    private javax.swing.JButton btnAyuda;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
