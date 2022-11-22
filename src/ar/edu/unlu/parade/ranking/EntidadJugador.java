package ar.edu.unlu.parade.ranking;

import java.io.Serializable;

public class EntidadJugador implements Serializable{
	private static final long serialVersionUID = -3110720862058328947L;
	private int puntaje;
	private String nombre;
	
	public EntidadJugador(String nombre, int puntaje) {
		this.nombre = nombre;
		this.puntaje = puntaje;
	}
	
	public int getPuntaje() {
		return puntaje;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void incrementarPuntaje() {
		puntaje++;
	}
	
}
