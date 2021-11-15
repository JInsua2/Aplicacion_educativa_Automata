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
public class Linea {

    private final Punto p1, p2;
    private Double longitud;

    public Punto getP1() {
        return p1;
    }

    public Punto getP2() {
        return p2;
    }

    public Linea(Punto p1, Punto p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.longitud = null;
    }

    public double longitud() {
        if (this.longitud == null) {
            this.longitud = p1.distancia(p2);
        }
        return longitud;
    }

    public boolean compara(Linea l) {
        return longitud() < l.longitud();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != Linea.class) {
            return false;
        } else {
            Linea lObj = (Linea) obj;
            return (p1.equals(lObj.p1) && p2.equals(lObj.p2)) || (p1.equals(lObj.p2) && p2.equals(lObj.p1));
        }
    }

    
    public String ver() {
        return "[" + p1.toString() + ", " + p2.toString() + "]";
    }
}
