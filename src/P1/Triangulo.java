/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P1;

/**
 *
 * @author Jorge Lara Ochoa Javier Insua Salcedo
 */
public class Triangulo {

    private Punto p[] = new Punto[3];
    private Double area, perimetro;

    public Triangulo() {
    }

    ;
    public Triangulo(Punto p1, Punto p2, Punto p3) {
        this.p[0] = p1;
        this.p[1] = p2;
        this.p[2] = p3;
    }

    ;
    public Triangulo(Triangulo t) {
        this.p[0] = t.getp()[0];
        this.p[1] = t.getp()[1];
        this.p[2] = t.getp()[2];
    }

    ;
    public double area() {

        this.area = Math.abs((p[0].getx()) * (p[1].gety() - p[2].gety()) + p[1].getx() * (p[2].gety() - p[0].gety()) + p[2].getx() * (p[0].gety() - p[1].gety())) / 2d;
        return area;
    }

    public double perimetro() {

        double da = Math.sqrt(Math.abs(Math.pow((p[1].getx() - p[0].getx()), 2) + Math.pow(p[1].gety() - p[0].gety(), 2)));
        double db = Math.sqrt(Math.abs(Math.pow(p[2].getx() - p[0].getx(), 2) + Math.pow(p[2].gety() - p[0].gety(), 2)));
        double dc = Math.sqrt(Math.abs(Math.pow(p[2].getx() - p[1].getx(), 2) + Math.pow(p[2].gety() - p[1].gety(), 2)));
        perimetro = da + db + dc;

        return perimetro;
    }

    ;

    ;
    public Punto[] getp() {
        return p;
    }

    ;
    public boolean compara(Triangulo t) {
        return this.perimetro() < t.perimetro() || (this.perimetro() == t.perimetro() && this.area() > t.area());
    }
    
    public Punto puntoMinimo(Triangulo t) {
    	Punto[] puntos = t.getp();
    	double distancia=-10000;
    	double distancia2=0;
    	int contador=0;
    	int vertice=5;
    	for(Punto x:puntos) {
    		
    		distancia2=x.distancia(puntos[0]);
    		distancia2=distancia2+x.distancia(puntos[1]);
    		distancia2=distancia2+x.distancia(puntos[2]);
    		if(distancia==-10000  ) {
    			distancia=distancia2;
    			vertice=contador;
    			
    		}
    		if(distancia2<distancia) {
    			distancia=distancia2;
    			vertice=contador;
    		}
    		contador++;
    		
    	}
    	
    	return puntos[vertice];
    	
    	
    }
    
    
}





















