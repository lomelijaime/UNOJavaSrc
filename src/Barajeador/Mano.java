package Barajeador;

import java.util.ArrayList;

public class Mano extends ArrayList
{
    
    public void inserta(Carta c)
    {
        if (isEmpty())
            add(c);
        else
        {
            int inicial,fin,i;
            Carta tmp;
            inicial=getInicial(c.getColor());
            fin=getFinal(c.getColor());
            for (i=inicial;i<size();i++)
            {
                
                tmp=(Carta)get(i);
                if (tmp.getColor()!=c.getColor()) break;
                if (tmp.getValor()>c.getValor())
                {
                    add(i,c);
                    return;
                }
            }
           add(i,c);
        }
    }
   
    public Carta dameCarta(int n)
    {
        Carta tmp;
        if (isEmpty()) 
            return null;
        tmp=(Carta)get(n);
        return tmp;
    }
    
    @Override
    public String toString()
    {
        String cad="";
        if (isEmpty()) return cad;
        int i=0;
        do
        {
            cad+=((Carta)(get(i++))).toString();
            cad+="\n";
        }while(i<size());
        return cad;
    }
    public String getMano()
    {
        String cad="";
        Carta tmp;
        if (isEmpty()) return cad;
        int i=0;
        do
        {
            tmp=(Carta)get(i++);
            switch(tmp.getColor())
            {
                case 0: cad+="E"; break; //ESPECIAL (ESPECIAL)
                case 1: cad+="R"; break; //RED (ROJO)
                case 2: cad+="G"; break; //GREEN (VERDE)
                case 3: cad+="B"; break; //BLUE (AZUL)
                case 4: cad+="Y"; break; //YELLOW (AMARILLO)
            }
            String valor=String.valueOf(tmp.getValor());
            if(tmp.getValor()<10) valor="0"+valor;
            cad+=valor;
            cad+="&";
        }while(i<size());
        cad=cad.substring(0,cad.length()-1);
        return cad;
    }
    
    public int getInicial(int color)
    {
        if (isEmpty()) return 0;
        int i;
        Carta tmp;
        for (i=0;i<size();i++)
        {
            tmp=(Carta)get(i);
            if (color<=tmp.getColor())
                return i;
            
        }
        return i;
    }
    public int getFinal(int color)
    {
        if (isEmpty()) return 0;
        int i;
        Carta tmp;
        for (i=getInicial(color);i<size();i++)
        {
            tmp=(Carta)get(i);
            if (tmp.getColor()!=color)
                return i-1;
            
        }
        return i;
        
    }
}
