package ar.edu.unlu.parade.controlador;

import java.rmi.RemoteException; 
import java.util.ArrayList;

import ar.edu.unlu.parade.modelo.Carnaval;
import ar.edu.unlu.parade.modelo.Carta;
import ar.edu.unlu.parade.modelo.EventoParade;
import ar.edu.unlu.parade.modelo.IJugador;
import ar.edu.unlu.parade.modelo.IParade;
import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.modelo.MensajeDeError;
import ar.edu.unlu.parade.modelo.ObserverParade;
import ar.edu.unlu.parade.modelo.Parade;
import ar.edu.unlu.parade.ranking.HistorialGanadores;
import ar.edu.unlu.parade.ranking.RankingPuntajes;
import ar.edu.unlu.parade.vistas.IVistaParade;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public class Controlador implements IControladorRemoto {
	private IParade juego;
	private IVistaParade vista;
	private IJugador jugadorActual;
	private IJugador miJugador;
	
	public Controlador() {
		super();
	}
	
	public void setVista(IVistaParade vista) {
		this.vista = vista;
	}
	
	private void notificarEventoVista(EventoParade e) {
		switch(e) {
		case JUGADOR_ELIMINADO:
		case NUEVO_JUGADOR:
			try {
				vista.jugadorAgregado(juego.getJugadores());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;	
		case INICIAR_JUEGO:
			try {
				jugadorActual = juego.getJugadorActual();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				vista.juegoIniciado(juego.getCarnaval());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case JUGADA_REALIZADA:
			Carnaval carnaval = null;
			try {
				carnaval = juego.getCarnaval();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			vista.jugadaRealizada(carnaval);
			break;
		case ULTIMA_RONDA:
			vista.ultimaRonda();
			break;
		case CAMBIO_TURNO:
			try {
				jugadorActual = juego.getJugadorActual();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			vista.cambioDeTurno(jugadorActual);
			break;
		case ETAPA_DESCARTE:
			vista.inicioEtapaDescarte();
			break;
		case DESCARTE_REALIZADO:
			try {
				vista.descarteRealizado(juego.getCarnaval());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case FIN_DE_JUEGO:
			vista.finalDeJuego();
			break;
			
		}
		
	}


	public IJugador getGanador() {
		try {
			return juego.getGanador();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public IJugador getJugadorActual() {
		return jugadorActual;
	}

	public void jugarCarta(Carta carta) {
		if(!jugadorActual.getNombre().equals(miJugador.getNombre())) 
			vista.mostrarError("No es tu turno");
		else
			try {
				juego.jugarCarta(carta, miJugador.getNombre());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public boolean registrarJugador(String nombre) {
		boolean ret = false;
		try {
			miJugador = juego.registrarJugador(nombre);
			if(miJugador != null)
				ret = true;
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
		return ret;
	}
	
	public boolean nombreExistente(String nombre) {
		try {
			return juego.yaExiste(nombre);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public void comenzarJuego() {	
		try {
			juego.comenzarJuego(miJugador.getNombre());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public IJugador getJugador(String nombre) {
		try {
			return juego.getJugador(nombre);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Carnaval getCarnaval() {
		try {
			return juego.getCarnaval();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean desconectarJugador() {
		try {
			return juego.eliminarJugador(miJugador.getNombre());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	public void descartar(Carta c1, Carta c2) {
			try {
				juego.descartar(miJugador.getNombre(), c1, c2);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

//	@Override
//	public void actualizar(EventoParade e) {
//		notificarEventoVista(e);	
//	}
//
//	@Override
//	public void actualizar(Jugador jugador, String msjError) {
//		vista.notificarError(jugador, msjError);
//		
//	}

	public ArrayList<Jugador> getJugadores() {
		try {
			return juego.getJugadores();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void actualizar(IObservableRemoto modelo, Object objeto) throws RemoteException {
		
		if (miJugador != null) {      //Actualiza la instacia de jugador dentro del controlador
			IParade juego = (IParade) modelo;
			miJugador = juego.getJugador(miJugador.getNombre());
		}
		if(vista == null)
			return;
		
		if(objeto instanceof EventoParade) {
			EventoParade evento = (EventoParade) objeto;
			notificarEventoVista(evento);
		}
		
		if(objeto instanceof MensajeDeError) {
			MensajeDeError error = (MensajeDeError) objeto;
			if (error.getReceptor().getNombre().equals(miJugador.getNombre()))
				vista.mostrarError(error.getMensaje());
		}
		
	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
		this.juego = (IParade) modeloRemoto;
	}
	
	public IJugador getMiJugador() {
		return miJugador;
	}

	public RankingPuntajes getRankingPuntaje() {
		try {
			return juego.getRankingPuntaje();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public HistorialGanadores getRankingGanadores() {
		try {
			return juego.getRankingGanadores();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
