/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Barajeador;

import java.util.Stack;

public class Barajeador {

    private int[][] mazoa,mazob,mazoe;
    private int njugadores;
    private Stack mazo;
    private Mano[] cartasJugadores;
    public Barajeador(int njugadores)
    {
        int i,j;
        this.njugadores=njugadores;
        cartasJugadores=new Mano[njugadores];
        mazo=new Stack();
        mazoa=new int[4][13];  //este mazo incluye del 0-9, come 2, cambio de sentido y no juega
        mazob=new int[4][12]; //este mazo incluye del 1-9, come 2, cambio de sentido y no juega
        mazoe=new int[2][4]; //estemazo solo es para los comodines cambio de cllor y comodin come 4
        for (i = 0; i < mazoa.length; i++) 
            for (j = 0; j < mazoa[i].length; j++) 
                mazoa[i][j]=0;
        for (i = 0; i < mazob.length; i++) 
            for (j = 0; j < mazob[i].length; j++) 
                mazob[i][j]=0;
        for (i = 0; i < mazoe.length; i++) 
            for (j = 0; j < mazoe[i].length; j++) 
                mazoe[i][j]=0;
        for (i=0;i<njugadores;i++)
            cartasJugadores[i]=new Mano(); 
       
    }
    public Mano getManoJugador(int njugador)
    {
        return cartasJugadores[njugador];
    }
    public void barajea()
    {
        int i=0,ncartas,tmazo,color,valor;
        Carta c;
        for (i=0;i<njugadores;i++)
        {
            ncartas=0;
            do
            {
                tmazo=(int)(Math.random()*3);
                switch (tmazo)
                {
                    case 0:{
                                color=(int)(Math.random()*4);
                                valor=(int)(Math.random()*13);
                                if (mazoa[color][valor]!=0) continue;
                                mazoa[color][valor]=1;
                                cartasJugadores[i].inserta(new Carta(color+1,valor));
                                break;
                           }
                    case 1:{
                                color=(int)(Math.random()*4);
                                valor=(int)(Math.random()*12);
                                if (mazob[color][valor]!=0) continue;
                                mazob[color][valor]=1;
                                cartasJugadores[i].inserta(new Carta(color+1,valor+1));
                                break;
                           }
                    case 2:{
                                tmazo=(int)(Math.random()*Integer.MAX_VALUE);
                                if (tmazo%2!=0&&tmazo%3!=0&&tmazo%5!=0&&tmazo%7!=0)
                                {
                                    color=(int)(Math.random()*2);
                                    valor=(int)(Math.random()*4);
                                    if (mazoe[color][valor]!=0) continue;
                                    mazoe[color][valor]=1;
                                    cartasJugadores[i].inserta(new Carta(0,color));
                                }
                                else
                                    ncartas--;
                                break;
                           }
                    
                }  
                ncartas++;
                  
            }while(ncartas<7);
           
        }
         i=108-(njugadores*7);
            do
            {
                tmazo=(int)(Math.random()*3);
                switch (tmazo)
                {
                    case 0:{
                                color=(int)(Math.random()*4);
                                valor=(int)(Math.random()*13);
                                if (mazoa[color][valor]!=0) continue;
                                mazoa[color][valor]=1;
                                mazo.push(new Carta(color+1,valor));
                                break;
                           }
                    case 1:{
                                color=(int)(Math.random()*4);
                                valor=(int)(Math.random()*12);
                                if (mazob[color][valor]!=0) continue;
                                mazob[color][valor]=1;
                                mazo.push(new Carta(color+1,valor+1));
                                break;
                           }
                    case 2:{
                                color=(int)(Math.random()*2);
                                valor=(int)(Math.random()*4);
                                if (mazoe[color][valor]!=0) continue;
                                mazoe[color][valor]=1;
                                mazo.push(new Carta(0,color));
                           }
                    
                }  
                i--;
                  
            }while(i>0);
            
              
    }
    public Carta getCartaMazo()
    {
        Carta tmp=(Carta)mazo.peek();
        mazo.pop();
        return tmp;
    }
   /* 
    public static void main(String[] args) 
    {
            int a = 5;
            Barajeador b=new Barajeador(a);
            b.barajea();
            
            Barajeador r=new Barajeador(3);
            r.barajea();
            String tmp;
            tmp=r.getManoJugador(0).getMano();
            r.getManoJugador(0);
            
            for (int i = 0; i < 5; i++) {
            System.out.println(b.getManoJugador(i).getMano());
            System.out.println(b.getManoJugador(i));
            
        }
            r.getCartaMazo().getCarta();
            System.out.println("EJEMPLO DE COMO SACAR LA MANO Y UNA CARTA DEL MAZO:");
            System.out.println(b.getManoJugador(0).getMano()+"&"+b.getCartaMazo().getCarta());
            String inicial=b.getCartaMazo().getCarta();
            String msg=b.getManoJugador(0).getMano()+"&"+inicial;
    }
    */
}
