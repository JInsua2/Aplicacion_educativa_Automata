/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P1;

import static P1.Algoritmos.randomMap;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Jorge Lara Ochoa Javier Insua Salcedo
 */
public class Grafica extends JFrame implements ActionListener, ItemListener {

    private JLabel label1, label2, label3;
    private JButton abrir, cerrar, aleatorio, comenzar;
    private JTextField talla;
    private JComboBox c1;
    private String selec;
    private Punto[] puntos;
    private final Dibujo dibujo;
    private Thread hilo;
    private JTextArea mensaje;

    public Grafica() {
        setLayout(null);
        mensaje = new JTextArea();

        mensaje.setBounds(1000, 100, 400, 300);
        add(mensaje);
        dibujo = new Dibujo();
        abrir = new JButton("Abrir Archivo");
        aleatorio = new JButton("Aleatorio");
        cerrar = new JButton("Cerrar");
        abrir.setBounds(250, 750, 200, 30);
        add(abrir);
        abrir.addActionListener(this);

        comenzar = new JButton("Comenzar");
        comenzar.setBounds(600, 750, 200, 30);
        add(comenzar);
        comenzar.addActionListener(this);

        cerrar.setBounds(700, 900, 200, 30);
        add(cerrar);
        cerrar.addActionListener(this);

        aleatorio.setBounds(250, 800, 200, 30);
        add(aleatorio);
        aleatorio.addActionListener(this);

        label3 = new JLabel("Talla:");
        label3.setBounds(100, 800, 30, 20);
        add(label3);

        talla = new JTextField();
        talla.setBounds(150, 800, 80, 20);
        add(talla);

        label1 = new JLabel("Jorge Lara Ochoa y Javier Insua Salcedo");
        label1.setBounds(500, 0, 120, 30);
        add(label1);
        label2 = new JLabel("PRACTICA 1 AMC");
        label2.setBounds(500, 30, 100, 30);
        add(label2);

        c1 = new JComboBox();
        c1.setBounds(600, 800, 200, 40);
        add(c1);
        c1.addItem("Algoritmo");
        c1.addItem("DyV Triangulo");


        c1.addItem("Exaustivo Triangulo");
        c1.addItem("dijkstra");

        c1.addItemListener(this);

        JPanel panel = new JPanel();
        panel.setBounds(100, 100, 1000, 950);
        panel.setLayout(null);

        panel.add(dibujo);
        add(panel);

    }
/*
    public static void main(String args[]) {
        Grafica gr = new Grafica();
        gr.setBounds(0, 0, 1000, 1000);
        gr.setVisible(true);
        gr.setLocationRelativeTo(null);
        gr.setResizable(false);
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == abrir) {
            dibujo.borrar();
            String ruta = null;
            JFileChooser f = new JFileChooser();
            int seleccion = f.showOpenDialog(f);
            try {
                ruta = f.getSelectedFile().getAbsolutePath();
            } catch (Exception x) {
                System.out.println("Cancelado " + x.getMessage());

            }
            ficheros fi = new ficheros();
            fi.cargafichero(ruta);
            System.out.println("NAME: " + ruta);
            ArrayList<Punto> puntos1 = new ArrayList<>();
            puntos1 = fi.getpuntos();
            //cambiar de arraylist a array
            puntos = new Punto[puntos1.size()];
            puntos = puntos1.toArray(puntos);

            dibujo.mapapuntos(puntos);
        }
        if (e.getSource() == cerrar) {
            System.exit(0);
        }
        if (e.getSource() == aleatorio) {
            dibujo.borrar();
            String texto = talla.getText();
            int n = Integer.parseInt(texto);
            
            puntos = randomMap(n);
            dibujo.mapapuntos(puntos);
            pararhilo();
        }

        if (e.getSource() == comenzar) {
            if (null != selec) {
                hilo = new Thread() {
                    @Override
                    public void run() {
                        switch (selec) {
                            case "DyV Triangulo": {
                                setTitle(selec);
                                long inicio=System.nanoTime();
                                Triangulo x = Algoritmos.DyVT(puntos, mensaje);
                                Punto puntofinal=x.puntoMinimo(x);
                                dibujo.setVerticeFinal(x,puntofinal);
                                long fin=System.nanoTime();
                                double tiempo = (double) ((fin - inicio) / 1000000);
                                mensaje.setText("El mejor triangulo tiene:" + "\n" + "Perimetro: " + x.perimetro() + "\n" + "Area: " + x.area()+"\nPunto 1: ("+x.getp()[0].getx()+", "+x.getp()[0].gety()+")\nPunto 2: ("+x.getp()[1].getx()+", "+x.getp()[1].gety()+")\nPunto 3: ("+x.getp()[2].getx()+", "+x.getp()[2].gety()+"\n\nEl vertice con menor distancia a sus adyacentes es :\n"+puntofinal.getx()+"  "+puntofinal.gety()+"\nTiempo de ejecucion: "+tiempo+" milisegundos");

                                break;
                            }

                            case "Exaustivo Triangulo": {
                                setTitle(selec);
                                long inicio=System.nanoTime();
                                Triangulo x = Algoritmos.exaustivoT(puntos, mensaje);
                                Punto puntofinal=x.puntoMinimo(x);
                                System.out.println(puntofinal.getx()+puntofinal.gety());
                                long fin=System.nanoTime();
                                double tiempo = (double) ((fin - inicio) / 1000000);
                                dibujo.setVerticeFinal(x,puntofinal);
                                mensaje.setText("El mejor triangulo tiene:" + "\n" + "Perimetro: " + x.perimetro() + "\n" + "Area: " + x.area()+"\nPunto 1: ("+x.getp()[0].getx()+", "+x.getp()[0].gety()+")\nPunto 2: ("+x.getp()[1].getx()+", "+x.getp()[1].gety()+")\nPunto 3: ("+x.getp()[2].getx()+", "+x.getp()[2].gety()+"\n\nEl vertice con menor distancia a sus adyacentes es :\n"+puntofinal.getx()+"  "+puntofinal.gety()+"\nTiempo de ejecucion: "+tiempo+" milisegundos");

                                break;
                            }
                        
                            
                            case "dijkstra": {
                            	
                                setTitle(selec);
                                Arista[] a = null;
                                dibujo.borrar();
                                
                                try {
                                    long inicio;
                                    long fin;
  
                                        inicio=System.nanoTime();
                                        a = Algoritmos.dijkstra(puntos, dibujo, mensaje);
                                        fin=System.nanoTime();
                                        String tours =Algoritmos.getTours();
                                    
                                    double tiempo = (double) ((fin - inicio) / 1000000);
                                    double coste = 0;
                                    for (Arista a1 : a) {
                                        coste = coste + a1.costeDijkstra();
                                    }
                                    mensaje.setText("Coste: " + coste+"\nTiempo de ejecución: "+tiempo+" milisegundos");
                                    try {
                                        ficheros f = new ficheros();
                                        JFileChooser guardar = new JFileChooser();
                                        guardar.showSaveDialog(null);
                                        guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                                        File ruta = guardar.getCurrentDirectory();
                                        f.guardarFichero(a, ruta, guardar, coste,tours);

                                    } catch (Exception exp) {
                                        System.out.println("ERROR: El usuario no ha querido guardar el archivo" );
                                    }
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Grafica.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                //dibujo.setArista(a, temporal);
                                break;
                            }
                            

                        
                            default:
                                break;
                        }
                    }
                };
                hilo.start();
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == c1) {
            selec = c1.getSelectedItem().toString();

        }
    }

    private void pararhilo() {
        if (hilo != null && hilo.isAlive()) {
            hilo.interrupt();
            hilo = null;
        }
    }

   

}
