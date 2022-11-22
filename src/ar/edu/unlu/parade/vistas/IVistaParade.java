package ar.edu.unlu.parade.vistas;

import java.util.ArrayList;

import ar.edu.unlu.parade.controlador.Controlador;
import ar.edu.unlu.parade.modelo.Carnaval;
import ar.edu.unlu.parade.modelo.IJugador;
import ar.edu.unlu.parade.modelo.Jugador;

public interface IVistaParade {
	
	void inicializar();
	
	void jugadorAgregado(ArrayList<Jugador> jugadores);

	void juegoIniciado(Carnaval carnaval);

	void jugadaRealizada(Carnaval carnaval);

	void ultimaRonda();

	void finalDeJuego();

	void cambioDeTurno(IJugador jugadorTurno);

	void mostrarError(String errorMsj);

	void mostrarMensaje(String mensaje);

	void inicioEtapaDescarte();

	void descarteRealizado(Carnaval carnaval);

	void setControlador(Controlador controlador);


}
