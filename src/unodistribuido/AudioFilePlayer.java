/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class AudioFilePlayer extends Thread 
{
    private Clip sonido;
    private boolean activo,sonando;
    private int accion,loop;
    private URL archivo;
    private long posicion;
    private Micronucleo micronucleo;
    
    public AudioFilePlayer(URL archivo,Micronucleo micronucleo,int loop)
    {
        this.archivo=archivo;
        this.loop=loop;
        this.micronucleo=micronucleo;
        activo=true;
        accion=0;
        posicion=0;
        activo=true;
        sonando=false;
        try
        {
            sonido=AudioSystem.getClip();
            sonido.open(AudioSystem.getAudioInputStream(archivo));
            sonido.loop(loop);
            
            start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
        }
       
    }
    
    public void run()
    {
      
        sonido.start();
        sonando=true;
        while (activo)
        {
            synchronized(this)
            {
                try {
                    wait();
                } catch (InterruptedException ex) {}
            }
            if (!activo)
                return;
            switch (accion)
            {
                case 1: sonido.start(); break;
                case 2: sonido.close(); break;
            }
        }    
        
    }
    private synchronized void notificar()
    {
        notify();
    }
    public boolean estaCorriendo()
    {
        return sonido.isRunning();
    }
    public void detener()
    {
        accion=2;
        posicion=sonido.getMicrosecondPosition();
        sonando=false;
        notificar();
    }
    public void play()
    {
        accion=1;
        try {
           
            sonido.open(AudioSystem.getAudioInputStream(archivo));
            sonido.setMicrosecondPosition(posicion);
            sonido.loop(loop);
             
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        sonando=true;
        notificar();
    }
    public void setActivo(boolean activo)
    {
        this.activo=activo;
        notificar();
    }
    public boolean getSonando()
    {
        return sonando;
    }
    
}