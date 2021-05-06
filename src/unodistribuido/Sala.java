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
public class Sala extends javax.swing.JDialog{

    private Micronucleo  micronucleo;
    private Point initialClick;

    public Sala(Micronucleo micronucleo) {
        initComponents();
       this.micronucleo=micronucleo;
        setSize(320,180);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(micronucleo);
        setVisible(true);
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
        btnCrear = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
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
        btnCerrar.setBounds(180, 130, 90, 30);

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
        btnCrear.setBounds(50, 130, 90, 30);

        lblTitulo.setFont(new java.awt.Font("Britannic Bold", 1, 14)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("ESCRIBE EL NOMBRE DE LA SALA");
        getContentPane().add(lblTitulo);
        lblTitulo.setBounds(0, 60, 320, 20);

        txtNombre.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        getContentPane().add(txtNombre);
        txtNombre.setBounds(80, 90, 150, 20);

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unodistribuido/imagenes/fondos/fondochico.png"))); // NOI18N
        getContentPane().add(lblFondo);
        lblFondo.setBounds(0, 0, 320, 180);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private Sala getMe()
    {
        return this;
    }
    
    
    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        micronucleo.requestFocus();
        dispose();

    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        if (txtNombre.getText().trim().equals(""))
        {    
            new Aviso(this,"Coloca un nombre para la sala de juego!",0);
            txtNombre.setText("");
        }
        else
        {
            if (micronucleo.buscar(txtNombre.getText()))
                new Aviso(this,"Ya existe la sala de juego!",0);
            else
            {
                int valor=(int)(Math.random()*Integer.MAX_VALUE);
                micronucleo.almacenaDestinoLocal(valor);
                micronucleo.almacenaServidor(new Servidor(micronucleo,this,valor));
                setVisible(false);
            }
        }
       
    }//GEN-LAST:event_btnCrearActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
            micronucleo.requestFocus();
    }//GEN-LAST:event_formWindowClosing
    
    
    
    public String getNombre()
    {
        return txtNombre.getText();
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
