package ar.edu.unlu.parade.modelo;

import java.io.Serializable;

public class Carta implements Serializable {
	private ColorCarta color;
	private int numero;
	
	public Carta(ColorCarta c, int numero) {
		this.color = c;
		this.numero = numero;
	}

	public ColorCarta getColor() {
		return color;
	}

	public int getNumero() {
		return numero;
	}
	
	public int compararNumero(Carta c) {
		int res = -2;
		
		if (c.getNumero() == this.numero) {
			res = 0;
		}else 
			if(c.getNumero() > this.numero) {
				res = 1;
			}else {
				res = -1;
			}
		return res;
	}
	
	public boolean esMismoColor(Carta c) {
		boolean res = false;
		if (c.getColor().equals(this.color)) {
			res = true;
		}
		return res;
	}
}
