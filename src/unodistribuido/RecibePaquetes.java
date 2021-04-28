/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

/**
 *
 * @author Luis
 */
public class RecibePaquetes extends Thread
{
   
     private boolean activo;
     private ArrayList esperando;
     private ArrayList mensajes;
     private DatagramPacket dp;
     private DatagramSocket dsReceive;
     private Micronucleo middleware;
 
 
     public RecibePaquetes(DatagramSocket dsReceive,Micronucleo middleware)
     {
        this.middleware=middleware;
        this.dsReceive=dsReceive;
      	activo = true;
      	esperando=new ArrayList();
     	mensajes = new ArrayList();
     	setDaemon(true);
     }
     
     @Override
     public void run()
     {
      	while (activo)
      	{
       		dp = new DatagramPacket(new byte[1024], 1024);
      		try
       		{	
      	  		dsReceive.receive(dp);
      	  		if (!activo)
                            return;
                        Mensaje msg=new Mensaje(dp.getData(),dp.getAddress().getHostAddress());
                        mensajes.add(msg);
       		}catch(IOException e){}
  		    
                synchronized (esperando)
       		{
           		entregaMensajes();          
       		}
        }
    }
   
     private void entregaMensajes()
     {
     	
         for(int i = 0; i < esperando.size() &&  i > -1; i++)
      	 {
            ProcesoEspera proceso=(ProcesoEspera)(esperando.get(i));
            for (int j = 0; j < mensajes.size(); j++)
            {
                Mensaje msg = (Mensaje)(mensajes.get(j));
           	
            	if (proceso.getID() == msg.getDestino())
           	{
                  	synchronized(proceso)
                  	{
                            proceso.setMensaje(msg);
                            proceso.notify();                  
                        }
                  	esperando.remove(i--);
                  	mensajes.remove(j);
                  	break;
           		}
       		}
      	}
    
     }
     public void poner (ProcesoEspera proceso)
     {
     	synchronized (esperando)
        {
        	esperando.add(proceso);
      	}
     }
 
     public void paraHilo()
     {
     	activo = false;
     }
}


