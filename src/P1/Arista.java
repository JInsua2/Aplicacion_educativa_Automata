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
public class Arista {
    private final Punto vertice1, vertice2;
    private final Linea linea;
    private double costeDijkstra;
    private int sucesor;
    private int inicio;


    public Arista(Punto v1, Punto v2) {
        vertice1 = v1;
        vertice2 = v2;
        linea = new Linea(vertice1, vertice2);
        costeDijkstra=0;
        sucesor=-1;
        inicio=-1;
        
    }

    public Punto getVertice1() {
        return vertice1;
    }

    public Punto getVertice2() {
        return vertice2;
    }

    public Linea getLinea() {
        return linea;
    }

    public double coste() {
        return linea.longitud();
    }
    public void setcosteDijkstra(double costeDijkstra) {
         this.costeDijkstra=costeDijkstra;
    }
    public double costeDijkstra() {
        return costeDijkstra;
    }
    public void setTout(int sucesor,int inicio) {
    	
    	this.sucesor=sucesor;
    	this.inicio=inicio;
    	
    }
    public int getinicio() {
    	return this.inicio;
    }
    
    public int getsucesor() {
    	return this.sucesor;
    }
    
}
