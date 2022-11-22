package ar.edu.unlu.parade.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AreaDeJuego implements Serializable {
	HashMap<ColorCarta,ArrayList<Carta>> filas;
	
	public AreaDeJuego() {
		filas = new HashMap<ColorCarta, ArrayList<Carta>>();
		for(ColorCarta col: ColorCarta.values()) {
			filas.put(col, new ArrayList<Carta>());
		}	
	}
	
	public void agregarCartas(ArrayList<Carta> cartas) {
		for(Carta car: cartas) {
			filas.get(car.getColor()).add(car);
		}
	}
	
	public void agregarCarta(Carta c) {
		filas.get(c.getColor()).add(c);
	}
	
	public boolean tieneTodosLosColores() {
		boolean res = true;
		for(ColorCarta col: ColorCarta.values()) {
			if(filas.get(col).isEmpty()) {
				res = false;
				break;
			}
		}
		return res;
	}
	
	public ArrayList<Carta> getFilaCartas(ColorCarta col){
		return filas.get(col);
	}

	public int getTotalFila(ColorCarta col) {
		int suma = 0;
		for(Carta c: filas.get(col)) {
			suma += c.getNumero();
		}
		return suma;
	}
	
	public int cantidadTotalCartas() {
		int res = 0;
		for(ColorCarta col: ColorCarta.values()) {
			res += filas.get(col).size();	
		}
		return res;
		
	}

	public void clear() {
		for(ColorCarta col: ColorCarta.values()) {
			filas.get(col).clear();	
		}
		
	}
	
	
}
