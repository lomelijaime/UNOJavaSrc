/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unodistribuido;

public class ProcesoEspera 
{
        private int idProceso;
	private Mensaje msg;
	
	public ProcesoEspera(int idProceso, Mensaje msg)
	{
	    this.idProceso = idProceso;
            this.msg = msg;
	}
	    
	public int getID()
	{ 
	    return idProceso;	    	
	}
	    
	public Mensaje getMensaje()
	{
	    return msg;
	}    
        public void setMensaje(Mensaje msg)
	{
	    this.msg=msg;
	}
}

