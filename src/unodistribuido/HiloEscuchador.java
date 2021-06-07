    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

import Barajeador.Carta;
import java.util.ArrayList;
import java.util.List;
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
 * 16: Actualizacion tabla de turno y ncartas en cliente
 * 17: Informe de reasignacion de siguiente jugador a dar el turno, por reinicoo de anillo
 * 18: Token de turno
 * 19: Tira carta
 * 20: Aceptacion de carta
 * 21: informe de tiro de carta
 * 22: Actualizacion de tabla jugadores en numero de cartas
 * 23: Pedir carta del mazo
 * 24: Recepcion de cartas de mazo
 * 25: Actualizacion por tomar carta en cliente
 * 26: Peticion de actualizacion por cambio de turno
 * 27: Informe de reto come 4 al servidor
 * 28: Informe del reto come 4 al cliente
 * 29: Reto por no gritar UNO
 * 30: Castigo del reto por no gritar UNO
 * 
 * 
*/


public class HiloEscuchador extends Thread 
{
    private final Cliente cliente;
    private final Micronucleo micronucleo;
    private final int idProceso;
    
    private final String[] nombres={"JOEL","JENNIE","OSVALDO","YOLANDA","DIANA","MARIO"};
   
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
                        cliente.agregaJugador(tmp.trim(),nombres[Integer.parseInt(tmp.trim())-1],st.nextToken().trim()) ;
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
                        cliente.agregaCartas(new Carta(st.nextToken().trim()));
                    cliente.ponInicial(st.nextToken().trim());
                    cliente.setSiguienteTurno(st.nextToken(), st.nextToken(),"2","1");
                    cliente.iniciarJuego();
                    if (st.nextToken().equals("1"))
                        cliente.setTurno2(true);
                    cliente.setIniciado(true);
                    
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
                case 16:
                {
                    
                    cliente.quitaJugadores();
                    StringTokenizer st=new StringTokenizer(msg.getMensaje(),"&");
                    while(st.hasMoreElements())
                    {
                        String tmp=st.nextToken();
                        String tmp2=st.nextToken();
                        cliente.agregaJugador2(tmp.trim(),nombres[Integer.parseInt(tmp.trim())-1],tmp2,"7") ;
                    }
                    break;
                }
                case 17:
                {
                    //destino|origen|11|R01&B03
                    String id,ip,sentido,otro,color;
                    StringTokenizer st=new StringTokenizer(msg.getMensaje(),"&");
                    ip=st.nextToken();
                    id=st.nextToken();
                    sentido=st.nextToken();
                    otro=st.nextToken();
                    color=st.nextToken();
                    cliente.setSiguienteTurno(ip, id,sentido,color);
                    break;
                }
                case 18:{
                    //System.out.println(new String(msg.getBytes()));
                    StringTokenizer st=new StringTokenizer(msg.getMensaje(),"&");
                    String s1=st.nextToken();
                    String retoUno="";
                    if (s1.trim().equals("1"))
                    {
                        cliente.setTurno(true);
                        cliente.setIniciado(true);
                    }
                    else if (s1.trim().equals("0"))
                    {
                        cliente.cancelaTurno();
                    }
                    else if (s1.trim().equals("2"))
                    {
                        s1=st.nextToken();
                        cliente.come2(Integer.parseInt(s1.trim()));
                    }
                    else if (s1.trim().equals("4"))
                    {
                        List<Carta> cards=new ArrayList<>();
                        s1=st.nextToken();
                        while(st.countTokens()>1)
                            cards.add(new Carta(st.nextToken().trim()));
                        if (s1.trim().equals("2"))
                            cliente.precome4(4,cards);
                        else
                            cliente.precome4(Integer.parseInt(s1.trim()),cards);
                    }
                    break;
                }
                case 22:
                {
                    String p;
                    cliente.quitaJugadores();
                    StringTokenizer st=new StringTokenizer(msg.getMensaje(),"&");
                    p=st.nextToken().trim();
                    if (!p.equals("XXX"))
                        cliente.ponInicial(p);
                    while(st.hasMoreTokens())
                    {
                        String tmp=st.nextToken();
                        String tmp2=st.nextToken();
                        String tmp3=st.nextToken();
                        cliente.agregaJugador2(tmp.trim(),nombres[Integer.parseInt(tmp.trim())-1],tmp2,tmp3) ;
                    }
                    break;
                }
                case 25:
                {
                    
                    cliente.quitaJugadores();
                    StringTokenizer st=new StringTokenizer(msg.getMensaje(),"&");
                    while(st.hasMoreTokens())
                    {
                        String tmp=st.nextToken();
                        String tmp2=st.nextToken();
                        String tmp3=st.nextToken();
                        cliente.agregaJugador2(tmp.trim(),nombres[Integer.parseInt(tmp.trim())-1],tmp2,tmp3) ;
                    }
                    break;
                }
                case 28:
                {
                    int ncartas=Integer.parseInt(msg.getMensaje().trim());
                    cliente.iniciaReto(ncartas);
                    break;
                }
                case 30:
                {
                    cliente.iniciaReto(-1);
                    break;
                }
                 case 32:
                {
                    cliente.retoUno();
                    break;
                } 
                 case 34:{
                     if (msg.getMensaje().trim().equals("0"))
                         cliente.finaliza(false);
                     else
                         cliente.finaliza(true);
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
