/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

public class DatosServidor 
{
    private int idProceso;
    private String IP;
    public DatosServidor(int idProceso,String IP)
    {
        this.IP=IP;
        this.idProceso=idProceso;
    }
    public String getIP()
    {
        return IP;
    }
    public int getID()
    {
        return idProceso;
    }
}

