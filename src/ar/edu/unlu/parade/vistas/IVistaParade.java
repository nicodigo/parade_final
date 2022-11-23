package ar.edu.unlu.parade.vistas;

import java.util.ArrayList; 

import ar.edu.unlu.parade.modelo.Carnaval;
import ar.edu.unlu.parade.modelo.IJugador;

public interface IVistaParade {
	
	void inicializar();
	
	void actualizarJugadoresConectados(ArrayList<IJugador> jugadores);

	void juegoIniciado(Carnaval carnaval);

	void actualizarCarnaval(Carnaval carnaval);
	
	void actualizarMiJugador(IJugador miJugador);

	void ultimaRonda();

	void finalDeJuego(IJugador ganador);

	void cambioDeTurno(IJugador jugadorTurno);

	void mostrarError(String errorMsj);

	void mostrarMensaje(String mensaje);

	void inicioEtapaDescarte();

}
