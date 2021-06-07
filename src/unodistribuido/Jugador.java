/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

/**
 *
 * @author roafi
 */
public class Jugador 
{
    private String ID;
    private String IP;
    private String nick;
    private String sig;
    private String num;
    private int ncartas;
    public Jugador(String IP,String ID,String nick,int ncartas,String num,String sig)
    {
        this.ID=ID;
        this.IP=IP;
        this.nick=nick;
        this.ncartas=ncartas;
        this.num=num;
        this.sig=sig;
    }
    public Jugador(String IP,String ID)
    {
        this.ID=ID;
        this.IP=IP;
        this.nick="";
        this.ncartas=0;
        this.num="";
        this.sig="";
    }
    public String getSig()
    {
        return sig;
    }
    public String getNum()
    {
        return num;
    }
    public int getNCartas()
    {
        return ncartas;
    }
    public String getID()
    {
        return ID;
    }
    public String getIP()
    {
        return IP;
    }
    public String getNick()
    {
        return nick;
    }
    public void setNCartas(int ncartas)
    {
        this.ncartas=ncartas;
    }
    public void setID(String ID)
    {
        this.ID=ID;
    }
    public void setIP(String IP)
    {
        this.IP=IP;
    }
    public void setNick(String nick)
    {
        this.nick=nick;
    }
}
