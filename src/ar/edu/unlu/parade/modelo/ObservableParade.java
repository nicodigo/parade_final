package ar.edu.unlu.parade.modelo;

import ar.edu.unlu.parade.modelo.eventos.EventoParade;

public interface ObservableParade {
	public void notificar(EventoParade e);
}
