package ar.edu.unlu.parade.modelo;

import java.io.Serializable;

public class Jugador implements Serializable, IJugador {
	private String nombre;
	private Mano miMano;
	private AreaDeJuego miAreaJuego;
	private int puntaje;
	
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.miAreaJuego = new AreaDeJuego();
		this.miMano = new Mano();
		puntaje = -1;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public Mano getMano() {
		return miMano;
	}

	@Override
	public AreaDeJuego getAreaJuego() {
		return miAreaJuego;
	}

	@Override
	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
	public void sumarPuntos(int cantidad) {
		this.puntaje += cantidad;
	}

	public void inicializar() {
		setPuntaje(0);
		miAreaJuego.clear();
		miMano.clear();
		
	}
	

	@Override
	public boolean mismoNombre(IJugador j) {
		return(j.getNombre().toLowerCase().equals(nombre.toLowerCase()));
	}
	
	
}
