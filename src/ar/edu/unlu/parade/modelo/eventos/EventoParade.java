package ar.edu.unlu.parade.modelo.eventos;

import java.io.Serializable;

public enum EventoParade implements Serializable {
	NUEVO_JUGADOR, INICIAR_JUEGO, JUGADA_REALIZADA, ULTIMA_RONDA, CAMBIO_TURNO, FIN_DE_JUEGO,ETAPA_DESCARTE, JUGADOR_ELIMINADO;
}
