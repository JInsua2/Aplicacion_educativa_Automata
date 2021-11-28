/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P1;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import javax.swing.JTextArea;
import P1.Nodo;


/**
 *
 * @author 
 */
public class Algoritmos {
	
	public static String caminoFinal="";
	public static double costefinal=0;

    public static Arista[] generarAristas(Punto[] puntos) {
        Arista[] aristas = new Arista[puntos.length * (puntos.length - 1) / 2];
        int z = 0;
        for (int x = 0; x < puntos.length; x++) {
            for (int y = x + 1; y < puntos.length; y++) {
                aristas[z++] = new Arista(puntos[x], puntos[y]);
            }
        }
        return aristas;
    }

    public static double[][] generarMatriz(Punto[] puntos) {
        double[][] matriz = new double[puntos.length][puntos.length];
        for (int x = 0; x < puntos.length; x++) {
            matriz[x][x] = Double.POSITIVE_INFINITY;
            for (int y = x + 1; y < puntos.length; y++) {
                matriz[x][y] = matriz[y][x] = puntos[x].distancia(puntos[y]);
            }
        }
        return matriz;
    }
    
    public static double[][] generarMatrizDijkstra(Punto[] puntos) {
        double[][] matriz = new double[puntos.length][puntos.length];
        for (int x = 0; x < puntos.length; x++) {
            matriz[x][x] = Double.POSITIVE_INFINITY;
            for (int y = x + 1; y < puntos.length; y++) {
            	int longitud_modificada=(int)(puntos[x].distancia(puntos[y])*100);
                matriz[x][y] = matriz[y][x] = ((longitud_modificada % 100)+1);
            }
        }
        return matriz;
    }


  
  
    
    private static int minDistance(double[] distancia, boolean[] visitados,int NNodos)
    {
       double min = Double.POSITIVE_INFINITY; int min_index=0;
     
       for (int v = 0; v < NNodos; v++)
         if (visitados[v] == false && distancia[v] <= min) {
             min = distancia[v];
             min_index = v;
          }
     
       return min_index;
    }
    
    
    
    public static Arista[] dijkstra(Punto[] puntos, Dibujo dibujo, JTextArea mensaje) throws InterruptedException {
		
        int NNodes = puntos.length;
//        Arista[] T = new Arista[puntos.length];
        Arista[] T = new Arista[puntos.length-1];

        double[][] longitudesAristas = generarMatrizDijkstra(puntos);
        //ArrayList<Integer> visitados=new ArrayList<Integer>();
        boolean[] visitados= new boolean[puntos.length];
        Random rn = new Random();
        int n = NNodes + 1;
   //     int aleatorio = Math.abs(rn.nextInt() % n);
        int aleatorio=0;
        double[] distanciaMinima = new double[NNodes];
        int[] padre = new int[NNodes];

        for (int i = 0; i < NNodes; i++) {
            distanciaMinima[i] = Double.POSITIVE_INFINITY;
            visitados[i]=false;
            padre[i]=-1;
        }
        distanciaMinima[aleatorio]=0;
        padre[aleatorio]=-2;

        
    	for(int i=0;i<NNodes;i++) {
    		
    		int tmp=minDistance(distanciaMinima, visitados, NNodes);    		
    		visitados[tmp]=true;
    		
    		for(int c=0;c<NNodes;c++) {
    			if((!visitados[c])  &&  (distanciaMinima[tmp]!=Double.POSITIVE_INFINITY) 
    					&&  (distanciaMinima[tmp]+longitudesAristas[tmp][c] < distanciaMinima[c])) {
    				
    				distanciaMinima[c]=distanciaMinima[tmp]+longitudesAristas[tmp][c];
    				padre[c]=tmp;
    				
    			}
    		}
    		
    	}  
        	
        
        int z=0;
        for(int i=0;i<NNodes;i++) {
        	if(padre[i]!=-2) {
        	T[z]=new Arista(puntos[i], puntos[padre[i]]);
        	T[z].setcosteDijkstra(longitudesAristas[i][padre[i]]);
        	T[z].setTout(i,padre[i]);
            dibujo.addLinea(T[z].getLinea());
            z++;
            
            if(NNodes<10){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    return null;
                }
            }
            
        	}
            costefinal=costefinal+distanciaMinima[i];

        }
        
        recorrido(NNodes,distanciaMinima,padre);
        dibujo.setArista(T);
    	return T; 
    
    
    }
    
    public static void recorrido(int NNodes,double[] distanciaMinima,int[] padre) {
    	for(int i=0;i<NNodes;i++) {
    		if(padre[i]!=-2) {
    			caminoFinal=caminoFinal+(int)distanciaMinima[i]+" - ";
    			recorrido(i,padre);
    			caminoFinal=caminoFinal+"\n";

    		}
    	}
    	
    	
    }
    
    public static String getTours() {
    	return caminoFinal;
    }
    public static double getCostefinal(){
    	return costefinal;
    }
    
    public static void recorrido(int actual,int[] padre) {
    	if(actual==-2) {
    		return;
    	}
    	recorrido(padre[actual], padre);
		caminoFinal=caminoFinal+actual+" , ";

    	
    }

    public static Triangulo exaustivoT(Punto[] puntos, JTextArea mensaje) {
        return exaustivoT(puntos, 0, puntos.length - 1, mensaje);
    }

    private static Triangulo exaustivoT(Punto[] puntos, int ini, int fin, JTextArea mensaje) {
        Punto pu = new Punto(0, 0);
        Punto pu1 = new Punto(0, 600);
        Punto pu2 = new Punto(600, 0);
        Triangulo t = new Triangulo(pu, pu1, pu2);
        Triangulo aux;
        for (int x = ini; x <= fin; x++) {
            for (int y = x + 1; y <= fin; y++) {
                for (int z = y + 1; z <= fin; z++) {
                    aux = new Triangulo(puntos[x], puntos[y], puntos[z]);
                    boolean f = (((puntos[y].getx() == puntos[z].getx()) && (puntos[x].getx() == puntos[y].getx())) || ((puntos[y].gety() == puntos[z].gety()) && (puntos[x].gety() == puntos[y].gety())));
                    if (!f) {
                        t = aux.compara(t) ? aux : t;
                        /*if (aux.perimetro() < t.perimetro()) {

                            t = aux;
                        } else if (aux.perimetro() == t.perimetro() && aux.area() > t.area()) {
                            t = aux;
                        }
                        */
                    }
                }
            }
        }
        return t;

    }



    public static void ordenaQuick(Punto[] puntos) {
        ordenaQuickP(puntos, 0, puntos.length - 1);
    }

    public static void ordenaQuick(Arista[] puntos) {
        ordenaQuickP(puntos, 0, puntos.length - 1);
    }

    private static void ordenaQuickP(Arista puntos[], int izq, int der) {
        Arista pivote = puntos[izq];
        int i = izq;
        int j = der;
        Arista aux;
        while (i < j) {
            while (puntos[i].coste() <= pivote.coste() && i < j) {
                i++;
            }
            while (puntos[j].coste() > pivote.coste()) {
                j--;
            }
            if (i < j) {
                aux = puntos[i];
                puntos[i] = puntos[j];
                puntos[j] = aux;

            }

        }
        puntos[izq] = puntos[j];
        puntos[j] = pivote;

        if (izq <= j - 1) {
            ordenaQuickP(puntos, izq, j - 1);
        }
        if (j + 1 < der) {
            ordenaQuickP(puntos, j + 1, der);
        }

    }

    private static void ordenaQuickP(Punto puntos[], int izq, int der) {
        Punto pivote = puntos[izq];
        int i = izq;
        int j = der;
        Punto aux;
        while (i < j) {
            while (puntos[i].getx() <= pivote.getx() && i < j) {
                i++;
            }
            while (puntos[j].getx() > pivote.getx()) {
                j--;
            }
            if (i < j) {
                aux = puntos[i];
                puntos[i] = puntos[j];
                puntos[j] = aux;

            }

        }
        puntos[izq] = puntos[j];
        puntos[j] = pivote;

        if (izq <= j - 1) {
            ordenaQuickP(puntos, izq, j - 1);
        }
        if (j + 1 < der) {
            ordenaQuickP(puntos, j + 1, der);
        }

    }

    public static Triangulo DyVT(Punto[] puntos, JTextArea mensaje) {

        ordenaQuick(puntos);
        return DyVT(puntos, 0, puntos.length - 1, mensaje);
    }
    

    private static Triangulo DyVT(Punto[] puntos1, int izq, int der, JTextArea mensaje) {
        int total = der - izq + 1;
        if (total <= 5) {
            return exaustivoT(puntos1, izq, der, mensaje);
        }
        int mitad = (izq + der) / 2;

        Triangulo minizq = DyVT(puntos1, izq, mitad, mensaje);
        Triangulo minder = DyVT(puntos1, mitad + 1, der, mensaje);
        Triangulo minimo = minizq.compara(minder) ? minizq : minder;

        /*if (minizq.perimetro() < minder.perimetro()) {

            minimo = minizq;
        } else if (minizq.perimetro() == minder.perimetro() && minizq.area() > minder.area()) {
            minimo = minizq;
        } else {
            minimo = minder;
        }*/

        Punto[] cr = new Punto[total];
        int n = 0;
        for (int x = mitad; x >= izq; x--) {
            if (puntos1[mitad + 1].getx() - puntos1[x].getx() <= minimo.perimetro()) {
                cr[n++] = puntos1[x];
            } else {
                break;
            }
        }

        for (int x = mitad + 1; x <= der; x++) {
            if (puntos1[x].getx() - puntos1[mitad].getx() <= minimo.perimetro()) {
                cr[n++] = puntos1[x];
            } else {
                break;
            }
        }

        Triangulo aux;
        for (int x = 0; x < n; x++) {
            for (int y = x + 1; y < n; y++) {
                for (int z = y + 1; z < n; z++) {
                    aux = new Triangulo(cr[x], cr[y], cr[z]);
                    boolean f = (((cr[y].getx() == cr[z].getx()) && (cr[x].getx() == cr[y].getx())) || ((cr[y].gety() == cr[z].gety()) && (cr[x].gety() == cr[y].gety())));
                    if (!f) {
                        minimo = aux.compara(minimo) ? aux : minimo;
                    }
                }
            }
        }
        
        return minimo;
    }

   

    public static boolean enlinea(Punto x, Punto y, Punto z) {
        boolean a = false;
        if (x.getx() == y.getx() && z.getx() == x.getx() && z.getx() == y.getx()) {
            a = true;
        } else if (x.gety() == y.gety() && z.gety() == x.gety() && z.gety() == y.gety()) {
            a = true;
        }
        return a;
    }

    

    public static Punto[] randomMap(int n) {
        Punto[] puntos = new Punto[n];
        Random r = new Random();
        r.setSeed(System.nanoTime());

        for (int i = 0; i < n; i++) {
            puntos[i] = new Punto(r.nextDouble(), r.nextDouble());
        }
        return puntos;
    }
}
