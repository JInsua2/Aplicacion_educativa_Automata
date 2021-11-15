/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P1;

import java.awt.*;
import static java.lang.Thread.sleep;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Lara Ochoa Javier Insua Salcedo
 */
public class Dibujo extends Canvas {

    private final ArrayList<Punto> puntos;
    private Linea lineaMejor;
    private final ArrayList<Linea> lineas;
    private Triangulo  trianguloMejor;
    private Punto verticefinal;
    private Triangulo  trianguloVertice;

    private Arista[] a;
    private int ladox = 800;
    private int ladoy = 600;
    private double menegX, meposX, menegY, meposY;
    
    

    public Dibujo() {
        setSize(1000, 1000);
        puntos = new ArrayList<>();
        lineas = new ArrayList<>();
        lineaMejor = null;
        trianguloMejor = null;
        verticefinal=null;
        a = null;

    }

    public void mapapuntos(Punto[] puntos) {
        this.puntos.clear();
        menegX = menegY = meposX = meposY = 0;
        for (Punto punto : puntos) {
            if (punto.getx() < menegX) {
                menegX = punto.getx();
            } else if (punto.getx() > meposX) {
                meposX = punto.getx();
            }
            if (punto.gety() < menegY) {
                menegY = punto.gety();
            } else if (punto.gety() > meposY) {
                meposY = punto.gety();
            }
            this.puntos.add(punto);
        }
        menegX = -menegX;
        menegY = -menegY;

        repaint();
    }

    public void setLinea(Linea linea) {
        this.lineaMejor = linea;
        repaint();
    }

    public void addLinea(Linea l) {
        lineas.add(l);
        repaint();
    }

    public void setTriangulo(Triangulo triangulo) {
        this.trianguloMejor = triangulo;
        repaint();
    }
    
    public void setVerticeFinal(Triangulo triangulo,Punto p) {
        this.trianguloVertice = triangulo;
        this.verticefinal=p;
        repaint();
    }
    public void borrar() {
        this.lineas.clear();
        a=null;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g);

        g.setColor(Color.white);
        g.fillRect(0, 0, ladox, ladoy);
        g.setColor(Color.BLACK);

        for (Punto punto : puntos) {
            g.fillOval(cordX(punto.getx()), cordY(punto.gety()), 5, 5);
        }
        if(verticefinal !=null) {
        	dibujarVerticeFinal(trianguloVertice, verticefinal, g);
        	g.setColor(Color.blue);
        	g.drawOval(cordX(verticefinal.getx()), cordY(verticefinal.gety()), 10, 10);
        	trianguloVertice=null;
        	verticefinal=null;
            g.setColor(Color.BLACK);

        }
        if (lineaMejor != null) {
            dibujaLinea(lineaMejor, g);
            lineaMejor = null;
        }
        if (trianguloMejor != null) {
            dibujaTriangulo(trianguloMejor, g);
            trianguloMejor = null;
        }
        if (a != null) {
            dibujaAristas(a, g);
            a = null;
            
        }
        for (Linea l : lineas) {
            dibujaLinea(l, g);
        }

    }

    public void setArista(Arista[] a) {
        this.a = a;

        repaint();
    }

    private void dibujaAristas(Arista[] a, Graphics g) {
        g.setColor(Color.blue);

        for (Arista a1 : a) {
            dibujaLinea(a1.getLinea(), g);
        }

    }

    private void dibujaLinea(Linea l, Graphics g) {
        g.setColor(Color.red);
        g.drawLine(cordX(l.getP1().getx()), cordY(l.getP1().gety()), cordX(l.getP2().getx()), cordY(l.getP2().gety()));
    }

    private void dibujaTriangulo(Triangulo t, Graphics g) {
        g.setColor(Color.red);
        g.drawLine(cordX(t.getp()[0].getx()), cordY(t.getp()[0].gety()), cordX(t.getp()[1].getx()), cordY(t.getp()[1].gety()));
        g.drawLine(cordX(t.getp()[1].getx()), cordY(t.getp()[1].gety()), cordX(t.getp()[2].getx()), cordY(t.getp()[2].gety()));
        g.drawLine(cordX(t.getp()[2].getx()), cordY(t.getp()[2].gety()), cordX(t.getp()[0].getx()), cordY(t.getp()[0].gety()));
    }

    private void dibujarVerticeFinal(Triangulo t,Punto p, Graphics g) {
        g.setColor(Color.red);
        if((t.getp()[0].getx()==p.getx()) && (t.getp()[0].gety()==p.gety())) {
        	 g.drawLine(cordX(t.getp()[0].getx()), cordY(t.getp()[0].gety()), cordX(t.getp()[1].getx()), cordY(t.getp()[1].gety()));
        	 g.drawLine(cordX(t.getp()[2].getx()), cordY(t.getp()[2].gety()), cordX(t.getp()[0].getx()), cordY(t.getp()[0].gety()));
        }
        else if(t.getp()[1].getx()==p.getx() && (t.getp()[1].gety()==p.gety())){
            g.drawLine(cordX(t.getp()[1].getx()), cordY(t.getp()[1].gety()), cordX(t.getp()[2].getx()), cordY(t.getp()[2].gety()));
            g.drawLine(cordX(t.getp()[0].getx()), cordY(t.getp()[0].gety()), cordX(t.getp()[1].getx()), cordY(t.getp()[1].gety()));

        }
        else {
        	g.drawLine(cordX(t.getp()[1].getx()), cordY(t.getp()[1].gety()), cordX(t.getp()[2].getx()), cordY(t.getp()[2].gety()));
            g.drawLine(cordX(t.getp()[2].getx()), cordY(t.getp()[2].gety()), cordX(t.getp()[0].getx()), cordY(t.getp()[0].gety()));
        }
    
    }
    private int cordX(double cord) {
        return (int) (cord / ((meposX - menegX) / (ladox - 5)));
    }

    private int cordY(double cord) {
        return (int) (cord / ((meposY - menegY) / (ladoy - 5)));
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }



}
