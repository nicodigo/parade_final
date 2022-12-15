package ar.edu.unlu.parade.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Mano implements Serializable{
	private ArrayList<Carta> cartas;
	
	public Mano() {
		cartas = new ArrayList<Carta>();
	}

	public boolean agregarCarta(Carta c) {
		return this.cartas.add(c);
	}
	
	public boolean jugarCarta(Carta c) {
		boolean ret = false;
		for(Carta carta: cartas) {
			if(carta.esMismoColor(c) && carta.getNumero() == c.getNumero()) {
				ret = cartas.remove(carta);
				break;
			}
		}
		return ret;
	}
	
	
	public ArrayList<Carta> getCartas() {
		ArrayList<Carta> res = new ArrayList<Carta>();
		for (Carta c: cartas) {
			res.add(c);
		}
		return res;
	}

	public void clear() {
		cartas.clear();
	}
	
	public Carta getCarta(int indice) {
		return this.cartas.get(indice);
		
	}
	
}
