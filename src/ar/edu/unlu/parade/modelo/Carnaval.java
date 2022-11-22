package ar.edu.unlu.parade.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Carnaval implements Serializable {
	private ArrayList<Carta> cartas;
	
	public Carnaval() {
		this.cartas = new ArrayList<Carta>();
	}
	
	public boolean agregarCarta(Carta c) {
		return this.cartas.add(c);
	}
	
	public ArrayList<Carta> jugada(Carta c) {
		ArrayList<Carta> res = new ArrayList<Carta>();
		if(c.getNumero() < cartas.size()) {
			int posMax = cartas.size() - c.getNumero();
			for (int i = 0; i < posMax; i++) {
				Carta cartaPosI = cartas.get(i);
				if (cartaPosI.getColor() == c.getColor() || cartaPosI.getNumero() < c.getNumero()) {
					res.add(cartaPosI);	
				}
			}
		}
		agregarCarta(c);
		for(Carta cart: res) {
			cartas.remove(cart);
		}
		
		return res;
	}
	
	public ArrayList<Carta> getCartas(){
		return this.cartas;
	}

	public void clear() {
		cartas.clear();
		
	}
	
}
