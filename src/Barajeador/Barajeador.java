/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Barajeador;

import java.util.Stack;

public class Barajeador {

    private final int[][] mazoa,mazob,mazoe;
    private final int njugadores;
    private final Mano[] cartasJugadores;
    private final Stack mazo;
    private int cartasDesechadas;
   
    public Barajeador(int njugadores)
    {
        int i,j;
        this.njugadores=njugadores;
        cartasJugadores=new Mano[njugadores];
        cartasDesechadas=0;
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
        int i,ncartas,tmazo,color,valor;
        Carta c;
        
        for (i=0;i<njugadores;i++)
        {
            ncartas=0;
            do
            {
                /*if(ncartas==0)
                {
                    cartasJugadores[i].inserta(new Carta(1,12));
                    cartasJugadores[i].inserta(new Carta(2,12));
                    cartasJugadores[i].inserta(new Carta(3,12));
                    cartasJugadores[i].inserta(new Carta(4,12));
                    ncartas+=4;
                    continue;
                }*/
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
    public void rebarajea()
    {
        int i,ncartas,tmazo,color,valor;
        Carta c;
        i=108-cartasDesechadas;
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
        cartasDesechadas=0;
    }
    public Carta getCartaMazoInicial()
    {
        Stack temp=new Stack();
        Carta tmp=null;
        do{
            tmp=(Carta)mazo.peek();
            if (esEspecial(tmp))
                temp.push(tmp);
            mazo.pop();
        }while(esEspecial(tmp));
        while(!temp.empty())
            mazo.push(temp.pop());
        setCartaMazo(tmp);
        return tmp;
    }
    public Carta getCartaMazo()
    {
        Carta tmp=null;
        if (mazo.empty())
            rebarajea();
        tmp=(Carta)mazo.pop();
        return tmp;
    }
    public void setCartaMazo(Carta c)
    { 
        if (esComodin(c))
        {
            for (int i=0;i<4;i++)
                if (mazoe[c.getValor()][i]==1)
                {
                    mazoe[c.getValor()][i]=0;
                    break;
                }
        }
        else
        {
            if (c.getValor()==0)
                mazoa[c.getColor()-1][0]=0;
            else
            {    
                if (mazoa[c.getColor()-1][c.getValor()]==1)
                {
                    mazoa[c.getColor()-1][c.getValor()]=0;
                }
                else if (mazob[c.getColor()-1][c.getValor()-1]==1)
                {
                    mazob[c.getColor()-1][c.getValor()-1]=0; 
                }
                
            }
        }   
        cartasDesechadas++;
    }
    
    public boolean esEspecial(Carta c)
    {
        return c.getColor()==0||c.getValor()>9;
    }
    public boolean esComodin(Carta c)
    {
        return c.getColor()==0;
    }
}
