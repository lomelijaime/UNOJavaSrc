
package Barajeador;


public class Carta 
{
    /*
    *  VALORES DE COLOR:
    *
    * 0 = ESPECIAL
    * 1 = ROJO
    * 2 = VERDE
    * 3 = AZUL
    * 4 = AMARILLO
    */
    private int color;
    /*
    * VALORES DE CARTAS (NO CARTAS ESPECIALES)
    *
    * 0-9: VALORES NUMERICOS
    * 10: CAMBIO DE SENTIDO
    * 11: NO JUEGA
    * 12: COME 2 
    *
    * VALORES DE CARTAS ESPECIALES
    *
    * 0: COMODIN CAMBIA DE COLOR
    * 1: COMODIN COME 4
    *
    */
    private int valor;
    public Carta()
    {
    
        color=(int)(Math.random()*4);
        if (color>0)
            valor=(int)(Math.random()*12);
        else
            valor=(int)(Math.random()*1);
    }
    public Carta (String carta)
    {
        String valor;
        valor=carta.substring(1);
        switch (carta.charAt(0))
         {
                case 'E': this.color=0; break; //ESPECIAL (ESPECIAL)
                case 'R': this.color=1; break; //RED (ROJO)
                case 'G': this.color=2; break; //GREEN (VERDE)
                case 'B': this.color=3; break; //BLUE (AZUL)
                case 'Y': this.color=4; break; //YELLOW (AMARILLO)
         }
        
        this.valor=Integer.parseInt(valor);
        
    }
    public Carta(int color, int valor)
    {
        this.color=color;
        this.valor=valor;
    }
    public int getColor()
    {
        return color;
    }
    public int getValor()
    {
        return valor;
    }
    public String getCarta()
    {
        String cad="";
            switch(getColor())
            {
                case 0: cad+="E"; break; //ESPECIAL (ESPECIAL)
                case 1: cad+="R"; break; //RED (ROJO)
                case 2: cad+="G"; break; //GREEN (VERDE)
                case 3: cad+="B"; break; //BLUE (AZUL)
                case 4: cad+="Y"; break; //YELLOW (AMARILLO)
            }
            String valor=String.valueOf(getValor());
            if(getValor()<10) valor="0"+valor;
            cad+=valor;
        return cad;
    }
    @Override
    public String toString()
    {
        String cad="";
        switch(color)
        {
            case 0: cad="ESPECIAL "; break;
            case 1: cad="ROJO "; break;
            case 2: cad="VERDE "; break;
            case 3: cad="AZUL "; break;
            case 4: cad="AMARILLO "; break;
        }
        if (color>0)
        {
            if (valor<10)
                cad+=valor;
            else
            {
                switch (valor)
                {
                    case 10: cad+="CAMBIO DE SENTIDO"; break;
                    case 11: cad+="NO JUEGA"; break;
                    case 12: cad+="COME 2"; break;
                    
                }
            }
        }
        else
        {
            if (valor==0)
                cad="COMODIN CAMBIO DE COLOR";
            else
                cad="COMODIN COME 4";
        }
        return cad;
    }
    
    
}
