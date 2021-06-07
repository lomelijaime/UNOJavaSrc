/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;


public class CambiaColor extends javax.swing.JDialog {

    private Cliente cliente;
    private Point initialClick;
    
    private Timer tmrAnimacion;
    private int frameAnimacion,tipoComodin;
    private Color color;
    
    private boolean ban;
    private int col;

    
    public CambiaColor(Cliente cliente,int tipoComodin) {
        super(cliente);
        initComponents();
        ban=false;
        frameAnimacion=1;
        this.cliente=cliente;
        this.tipoComodin=tipoComodin;
        setSize(310,310);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(cliente);
        lblColor.setVisible(false);
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
        tmrAnimacion=new Timer(50,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (frameAnimacion==1)
                {
                    lblColor.setVisible(true);
                    AudioFilePlayer player;
                    player = new AudioFilePlayer(getClass().getResource("sonidos/comodin.wav"),null,0);
                }
                if (col==1) {
                    lblColor.setText("ROJO");
                    switch (frameAnimacion) {
                        
                        case 8: {
                            lblAmarillo.setSize(lblAmarillo.getSize().width + 15, lblAmarillo.getSize().height + 15);
                            lblAmarillo.setLocation(lblAmarillo.getLocation().x - 5, lblAmarillo.getLocation().y - 5);
                            lblAmarillo.setBackground(color);
                            break;
                        }
                        case 16: {
                            lblVerde.setSize(lblVerde.getSize().width + 15, lblVerde.getSize().height + 15);
                            lblVerde.setLocation(lblVerde.getLocation().x - 5, lblVerde.getLocation().y - 5);
                            lblVerde.setBackground(color);
                            break;
                        }
                        case 24: {
                            lblAzul.setSize(lblAzul.getSize().width + 15, lblAzul.getSize().height + 15);
                            lblAzul.setLocation(lblAzul.getLocation().x - 5, lblAzul.getLocation().y - 5);
                            lblAzul.setBackground(color);
                            break;
                        }
                        case 32: {
                            if(tipoComodin==0)
                                cliente.tiraComodin(1);
                            else
                                cliente.tiraComodinCome4(1);
                            tmrAnimacion.stop();
                            dispose();
                        }
                    }
                }
                else if (col==2) {
                    lblColor.setText("AMARILLO");
                    switch (frameAnimacion) {
                        case 8: {
                            lblVerde.setSize(lblVerde.getSize().width + 15, lblVerde.getSize().height + 15);
                            lblVerde.setLocation(lblVerde.getLocation().x - 5, lblVerde.getLocation().y - 5);
                            lblVerde.setBackground(color);
                            break;
                        }
                        case 16: {
                            lblAzul.setSize(lblAzul.getSize().width + 15, lblAzul.getSize().height + 15);
                            lblAzul.setLocation(lblAzul.getLocation().x - 5, lblAzul.getLocation().y - 5);
                            lblAzul.setBackground(color);
                            break;
                        }
                        case 24: {
                            lblRojo.setSize(lblRojo.getSize().width + 15, lblRojo.getSize().height + 15);
                            lblRojo.setLocation(lblRojo.getLocation().x - 5, lblRojo.getLocation().y - 5);
                            lblRojo.setBackground(color);
                            break;
                        }
                        case 32: {
                            if(tipoComodin==0)
                                cliente.tiraComodin(4);
                            else
                                cliente.tiraComodinCome4(4);
                            tmrAnimacion.stop();
                            dispose();
                        }
                    }
                }
                else if (col==3) {
                    lblColor.setText("VERDE");
                    switch (frameAnimacion) {
                         case 8: {
                            lblAzul.setSize(lblAzul.getSize().width + 15, lblAzul.getSize().height + 15);
                            lblAzul.setLocation(lblAzul.getLocation().x - 5, lblAzul.getLocation().y - 5);
                            lblAzul.setBackground(color);
                            break;
                        }
                        case 16: {
                            lblRojo.setSize(lblRojo.getSize().width + 15, lblRojo.getSize().height + 15);
                            lblRojo.setLocation(lblRojo.getLocation().x - 5, lblRojo.getLocation().y - 5);
                            lblRojo.setBackground(color);
                            break;
                        }
                        case 24: {
                            lblAmarillo.setSize(lblAmarillo.getSize().width + 15, lblAmarillo.getSize().height + 15);
                            lblAmarillo.setLocation(lblAmarillo.getLocation().x - 5, lblAmarillo.getLocation().y - 5);
                            lblAmarillo.setBackground(color);
                            break;
                        }
                       
                        case 32: {
                            if(tipoComodin==0)
                                cliente.tiraComodin(2);
                            else
                                cliente.tiraComodinCome4(2);
                            tmrAnimacion.stop();
                            dispose();
                        }
                    }
                }
                else if (col==4) {
                    lblColor.setText("AZUL");
                    switch (frameAnimacion) {
                         
                        case 8: {
                            lblRojo.setSize(lblRojo.getSize().width + 15, lblRojo.getSize().height + 15);
                            lblRojo.setLocation(lblRojo.getLocation().x - 5, lblRojo.getLocation().y - 5);
                            lblRojo.setBackground(color);
                            break;
                        }
                        case 16: {
                            lblAmarillo.setSize(lblAmarillo.getSize().width + 15, lblAmarillo.getSize().height + 15);
                            lblAmarillo.setLocation(lblAmarillo.getLocation().x - 5, lblAmarillo.getLocation().y - 5);
                            lblAmarillo.setBackground(color);
                            break;
                        }
                       case 24: {
                            lblVerde.setSize(lblVerde.getSize().width + 15, lblVerde.getSize().height + 15);
                            lblVerde.setLocation(lblVerde.getLocation().x - 5, lblVerde.getLocation().y - 5);
                            lblVerde.setBackground(color);
                            break;
                        }
                       case 32: {
                           if(tipoComodin==0)
                                cliente.tiraComodin(3);
                            else
                                cliente.tiraComodinCome4(3);
                            tmrAnimacion.stop();
                            dispose();
                        }
                    }
                }
                if (frameAnimacion<25)
                lblColor.setFont(new Font(lblColor.getFont().getFamily(),lblColor.getFont().getStyle(),2*frameAnimacion));
                frameAnimacion++;
            }
            
        });
        setVisible(true);
        requestFocus();
    }
    public CambiaColor getMe()
    {
        return this;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblColor = new javax.swing.JLabel();
        lblRojo = new javax.swing.JLabel();
        lblAmarillo = new javax.swing.JLabel();
        lblVerde = new javax.swing.JLabel();
        lblAzul = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(310, 310));
        getContentPane().setLayout(null);

        lblColor.setFont(new java.awt.Font("Comic Sans MS", 1, 8)); // NOI18N
        lblColor.setForeground(new java.awt.Color(255, 255, 255));
        lblColor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblColor.setText("COLOR");
        getContentPane().add(lblColor);
        lblColor.setBounds(0, 122, 310, 60);

        lblRojo.setBackground(new java.awt.Color(218, 37, 28));
        lblRojo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblRojo.setOpaque(true);
        lblRojo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRojoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRojoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRojoMouseExited(evt);
            }
        });
        getContentPane().add(lblRojo);
        lblRojo.setBounds(10, 10, 130, 130);

        lblAmarillo.setBackground(new java.awt.Color(246, 211, 18));
        lblAmarillo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAmarillo.setOpaque(true);
        lblAmarillo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRojoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAmarilloMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAmarilloMouseExited(evt);
            }
        });
        getContentPane().add(lblAmarillo);
        lblAmarillo.setBounds(10, 165, 130, 130);

        lblVerde.setBackground(new java.awt.Color(91, 147, 3));
        lblVerde.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblVerde.setOpaque(true);
        lblVerde.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRojoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblVerdeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblVerdeMouseExited(evt);
            }
        });
        getContentPane().add(lblVerde);
        lblVerde.setBounds(165, 165, 130, 130);

        lblAzul.setBackground(new java.awt.Color(56, 77, 204));
        lblAzul.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAzul.setOpaque(true);
        lblAzul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRojoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAzulMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAzulMouseExited(evt);
            }
        });
        getContentPane().add(lblAzul);
        lblAzul.setBounds(165, 10, 130, 130);

        lblFondo.setBackground(new java.awt.Color(0, 0, 0));
        lblFondo.setOpaque(true);
        getContentPane().add(lblFondo);
        lblFondo.setBounds(0, 0, 310, 310);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblRojoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRojoMouseEntered
        if (!ban) {
            lblRojo.setSize(lblRojo.getSize().width + 15, lblRojo.getSize().height + 15);
            lblRojo.setLocation(lblRojo.getLocation().x - 5, lblRojo.getLocation().y - 5);
            col=1;
        }
       
        
    }//GEN-LAST:event_lblRojoMouseEntered

    private void lblRojoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRojoMouseExited
        if (!ban) {
            lblRojo.setSize(lblRojo.getSize().width - 15, lblRojo.getSize().height - 15);
            lblRojo.setLocation(lblRojo.getLocation().x + 5, lblRojo.getLocation().y + 5);
        }
        
        
    }//GEN-LAST:event_lblRojoMouseExited

    private void lblAmarilloMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAmarilloMouseEntered
        if (!ban) {
            lblAmarillo.setSize(lblAmarillo.getSize().width + 15, lblAmarillo.getSize().height + 15);
            lblAmarillo.setLocation(lblAmarillo.getLocation().x - 5, lblAmarillo.getLocation().y - 5);
            col=2;
        }
    }//GEN-LAST:event_lblAmarilloMouseEntered

    private void lblAmarilloMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAmarilloMouseExited
        if (!ban) {
            lblAmarillo.setSize(lblAmarillo.getSize().width - 15, lblAmarillo.getSize().height - 15);
            lblAmarillo.setLocation(lblAmarillo.getLocation().x + 5, lblAmarillo.getLocation().y + 5);
        }
    }//GEN-LAST:event_lblAmarilloMouseExited

    private void lblAzulMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAzulMouseEntered
        if (!ban) {
            lblAzul.setSize(lblAzul.getSize().width + 15, lblAzul.getSize().height + 15);
            lblAzul.setLocation(lblAzul.getLocation().x - 5, lblAzul.getLocation().y - 5);
            col=4;
        }
    }//GEN-LAST:event_lblAzulMouseEntered

    private void lblAzulMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAzulMouseExited
        if (!ban) {
            lblAzul.setSize(lblAzul.getSize().width - 15, lblAzul.getSize().height - 15);
            lblAzul.setLocation(lblAzul.getLocation().x + 5, lblAzul.getLocation().y + 5);
        }
    }//GEN-LAST:event_lblAzulMouseExited

    private void lblVerdeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVerdeMouseEntered
        if (!ban) {
            lblVerde.setSize(lblVerde.getSize().width + 15, lblVerde.getSize().height + 15);
            lblVerde.setLocation(lblVerde.getLocation().x - 5, lblVerde.getLocation().y - 5);
            col=3;
        }
    }//GEN-LAST:event_lblVerdeMouseEntered

    private void lblVerdeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVerdeMouseExited
        if (!ban) {
            lblVerde.setSize(lblVerde.getSize().width - 15, lblVerde.getSize().height - 15);
            lblVerde.setLocation(lblVerde.getLocation().x + 5, lblVerde.getLocation().y + 5);
        }
    }//GEN-LAST:event_lblVerdeMouseExited

    private void lblRojoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRojoMouseClicked
        color=((JLabel)evt.getComponent()).getBackground();
        frameAnimacion=1;
        ban=true;
        tmrAnimacion.start();
    }//GEN-LAST:event_lblRojoMouseClicked

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblAmarillo;
    private javax.swing.JLabel lblAzul;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblRojo;
    private javax.swing.JLabel lblVerde;
    // End of variables declaration//GEN-END:variables
}
