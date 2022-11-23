package ar.edu.unlu.parade.vistas;

import java.util.ArrayList;

import ar.edu.unlu.parade.controlador.Controlador;
import ar.edu.unlu.parade.modelo.Carnaval;
import ar.edu.unlu.parade.modelo.IJugador;
import ar.edu.unlu.parade.modelo.Jugador;

public interface IVistaParade {
	
	void inicializar();
	
	void actualizarJugadoresConectados(ArrayList<IJugador> jugadores);

	void juegoIniciado(Carnaval carnaval);

	void jugadaRealizada(Carnaval carnaval);
	
	void actualizarMiJugador(IJugador miJugador);

	void ultimaRonda();

	void finalDeJuego(IJugador ganador);

	void cambioDeTurno(IJugador jugadorTurno);

	void mostrarError(String errorMsj);

	void mostrarMensaje(String mensaje);

	void inicioEtapaDescarte();

	void descarteRealizado(Carnaval carnaval);


}
