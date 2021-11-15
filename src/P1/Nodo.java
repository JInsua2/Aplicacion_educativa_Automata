package P1;

import java.util.Comparator;

public class Nodo {

	private int id;
	private double dActual;
	private int padre;
	public Nodo(int id,double dActual,int padre) {
	
		this.id=id;
		this.dActual=dActual;
		this.padre=padre;
		
	}
	
	public double getDactual() {
		return dActual;
	}
	
	public int getid() {
		return id;
	}
	
	public int getPadre() {
		return padre;
	}
	public void modifDyP(double dnueva,int padrenuevo) {
		
		this.dActual=dnueva;
		this.padre=padrenuevo;
	}

//	@Override
//	public int compare(Object o1, Object o2) {
//		Nodo first=(Nodo) o1;
//		Nodo second=(Nodo) o2;
//		
//
//		return Double.compare(first.getDactual(), second.getDactual());
//	}



}
