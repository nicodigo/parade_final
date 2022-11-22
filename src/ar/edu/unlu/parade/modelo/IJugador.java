package ar.edu.unlu.parade.modelo;

import java.io.Serializable;


public interface IJugador extends Serializable {

	String getNombre();

	Mano getMano();

	AreaDeJuego getAreaJuego();

	int getPuntaje();
	
	boolean mismoNombre(IJugador j);
}