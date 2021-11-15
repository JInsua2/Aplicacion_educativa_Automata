/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P1;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author jorgel
 */

public class ficheros {

    ArrayList<String> aux = new ArrayList<>();
    static ArrayList<Punto> puntos = new ArrayList<>();
    File archivo;
    FileReader fr;
    BufferedReader br;
	private String[] tours;

    ficheros() {
        archivo = null;
        fr = null;
        br = null;
    }

    public void cargafichero(String archivo1) {
        puntos.clear();

        try {
            archivo = new File(archivo1);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            Boolean cord = false;
            boolean a = false;
            while ((linea = br.readLine()) != null) {
                
                if (linea.equals("TYPE : TOUR")) {
                    a = true;
                }
                if (a == true) {
                    if (linea.equals("TOUR_SECTION")) {
                        cord = true;
                    } else if (linea.equals("EOF") | linea.equals("") | linea.equals("-1")) {
                        cord = false;
                    } else if (cord == true) {
                        aux.add(linea);
                    }
                } else if (a == false && linea.equals("NODE_COORD_SECTION")) {
                    cord = true;
                } else if (linea.equals("EOF") | linea.equals("")) {
                    cord = false;
                } else if (cord == true) {
                    aux.add(linea);
                }
            }
            if (a == true) {
                string2puntotour(aux);
            } else {
                string2punto(aux);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void string2punto(ArrayList<String> l) {
        double x, y;
        for (int i = 0; i < l.size(); i++) {
            String cadena = l.get(i);
            String[] partes = cadena.split(" ");
            x = Double.parseDouble(partes[1]);
            y = Double.parseDouble(partes[2]);
            Punto p = new Punto(x, y);
            puntos.add(p);
        }
    }

    public void string2puntotour(ArrayList<String> l) {
        double x, y;
        for (int i = 0; i < l.size(); i++) {
            String cadena = l.get(i);
            String[] partes = cadena.split(",");
            x = Double.parseDouble(partes[0]);
            y = Double.parseDouble(partes[1]);
            Punto p = new Punto(x, y);
            puntos.add(p);
        }
    }
    
    public ArrayList<Punto> getpuntos(){
        return puntos;
    }
    
    public void guardarFichero(Arista[] ar, File ruta, JFileChooser guardar, double coste,String tours) {

        String n = guardar.getSelectedFile().getName();

        File archivo = new File(n);
        FileWriter escribir;
        int[] costetour = new int[ar.length];
        try {

            escribir = new FileWriter(ruta+"/"+archivo, true);
            escribir.write("NAME : "+n+"\nTYPE : TOUR\nDIMENSION : "+ ar.length+"\nSOLUTION : "+coste+"\nTOUR_SECTION\n");
            escribir.write(tours);
  
            escribir.write("-1\nEOF");

            escribir.close();

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar, ponga nombre al archivo");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar, en la salida");
        }

    }
}
