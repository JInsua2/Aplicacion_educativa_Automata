package P1;

/**
*
* @author Jorge Lara Ochoa Javier Insua Salcedo
*/

public class Punto {
    private static final double MINIMO_COMPARACION = 0.000001;
    
    private final double x, y;
    
    public Punto(double x, double y){
        this.x = x;
        this.y = y;
    }

    
    
    public double getx(){
        return x;
    }
    public double gety(){
        return y;
    }
    
    public double distancia(Punto p){
        return Math.sqrt(Math.pow(x-p.x, 2)+Math.pow(y-p.y, 2));
    }
    

    public boolean comparar(Punto p){
        return ((x < p.x || (x == p.x && y <= p.y))) || ((y < p.y || (y == p.y && x <= p.x)));
    }
    
    
    public String ver(){
        return "("+x+", "+y+")";
    }

    String getX() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
