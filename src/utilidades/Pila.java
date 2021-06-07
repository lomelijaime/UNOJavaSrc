
package utilidades;

import java.util.Stack;


public class Pila extends Stack 
{
    @Override
    public Object pop()
    {
        Object tmp=super.pop();
        if (tmp instanceof char[])
            return String.valueOf((char[])tmp);
        return tmp;
        
    }
    
    public Pila()
    {
        super();
    }
}
