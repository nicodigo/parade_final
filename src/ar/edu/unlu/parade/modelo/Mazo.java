package ar.edu.unlu.parade.modelo;

import java.util.ArrayList;
import java.util.Random;

public class Mazo {
	private ArrayList<Carta> cartas;
	
	public Mazo() {
		cartas = new ArrayList<Carta>();
		inicializar();
	}
	
	public void inicializar() {
		cartas.clear();
		for (ColorCarta c: ColorCarta.values()) {
			for(int i = 0; i<11; i++) {
				cartas.add(new Carta(c,i));
			}
		}
	}
	
	public void mezclar() {
		Random r = new Random();
		for (int i = 0; i < cartas.size()-1; i++) {
			Carta cartaI = cartas.get(i);
			int posRandom = r.nextInt(cartas.size()-i-1)+i+1;
			cartas.set(i, cartas.get(posRandom));
			cartas.set(posRandom, cartaI);
		}
	}
	
	public Carta sacarCarta() {
		Carta cartaArriba = cartas.get(cartas.size()-1);
		cartas.remove(cartaArriba);
		return cartaArriba;
	}
	
	public boolean estaVacio() {
		return cartas.isEmpty();
	}

	public Carta[] getCartas() {
		Carta[] vectorCartas = new Carta[cartas.size()];
		int i = 0;
		for (Carta c: cartas) {
			vectorCartas[i] = c;
			i++;
		}
		return vectorCartas;
	}
	
	public int cantCartas() {
		return cartas.size();
	}
}
