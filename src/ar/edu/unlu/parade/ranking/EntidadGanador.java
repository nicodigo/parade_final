package ar.edu.unlu.parade.ranking;

import java.io.Serializable;

public class EntidadGanador implements Serializable{
	private static final long serialVersionUID = -3110720862058328947L;
	private int partidasGanadas;
	private String nombre;
	
	public EntidadGanador(String nombre, int ganadas) {
		this.nombre = nombre;
		this.partidasGanadas = ganadas;
	}
	
	public int getPartidasGanadas() {
		return partidasGanadas;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void incrementarPartidasGanadas() {
		partidasGanadas++;
	}
}
