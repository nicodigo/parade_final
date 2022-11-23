package ar.edu.unlu.parade.modelo;

import ar.edu.unlu.parade.modelo.eventos.EventoParade;

public interface ObserverParade {
	public void actualizar(EventoParade e);
	public void actualizar(IJugador jugador, String msjError);
	
}
