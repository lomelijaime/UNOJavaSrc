/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import Barajeador.Carta;
import java.util.StringTokenizer;

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


public class HiloEscuchador extends Thread 
{
    private Cliente cliente;
    private Micronucleo micronucleo;
    private int idProceso;
    
    private String[] nombres={"KAREN","ANGEL","ALLAN","MARIA","ROSA","LUIS"};
   
    public HiloEscuchador(Cliente cliente,Micronucleo micronucleo)
    {
        super();
        idProceso=cliente.getID()*-1;
        this.cliente=cliente;
        this.micronucleo=micronucleo;
    }
    @Override
    public void run()
    {
        
        while(cliente.getActivo())
        {
            byte[] mensaje=new byte[1024];
            Mensaje msg=micronucleo.receive(idProceso, mensaje);
            if (!cliente.getActivo())
                return;
            switch(msg.getCodop())
            {
                case 7:
                {
                    
                    cliente.quitaJugadores();
                    StringTokenizer st=new StringTokenizer(msg.getMensaje(),"&");
                    while(st.hasMoreTokens())
                    {
                        String tmp=st.nextToken();
                        cliente.agregaJugador(tmp.trim(),nombres[Integer.parseInt(tmp.trim())-1]) ;
                    }
                    break;
                }
                case 9:
                {
                    cliente.reiniciar();
                    new Aviso(cliente,"La Sala de Juego se ha cerrado!",0);
                    break;
                }
                case 10:
                {
                    cliente.reiniciar();
                    new Aviso(cliente,"Fuiste expulsado por no iniciar!",0);
                    break;
                }
                case 11:
                {
                    //destino|origen|11|R01&B03
                    StringTokenizer st=new StringTokenizer(msg.getMensaje(),"&");
                    for (int i = 0; i < 7; i++) 
                        cliente.agregaCarta(new Carta(st.nextToken().trim()));
                    cliente.ponInicial(st.nextToken().trim());
                    cliente.iniciarJuego();
                    break;
                }
                case 12:
                {
                    cliente.semiReiniciar();
                    new Aviso(cliente,"Juego suspendido, faltan jugadores!",0);
                    break;
                }
                case 14:
                {
                    cliente.grita();
                    break;
                }
            }
        }
    }
     public synchronized void notificar()
    {
        notify();
    }
}
