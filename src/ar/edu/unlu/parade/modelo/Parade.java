package ar.edu.unlu.parade.modelo;

import java.rmi.RemoteException; 
import java.util.ArrayList;

import ar.edu.unlu.parade.modelo.eventos.EventoParade;
import ar.edu.unlu.parade.modelo.eventos.MensajeDeError;
import ar.edu.unlu.parade.modelo.eventos.MensajeGlobal;
import ar.edu.unlu.parade.ranking.HistorialGanadores;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import ar.edu.unlu.utils.serializador.Serializador;

public class Parade extends ObservableRemoto implements IParade{
	private static final long serialVersionUID = 1L;
	private ArrayList<Jugador> jugadores;
	private IJugador ganador = null;
	private Carnaval carnaval;
	private Mazo mazo;
	private int jugadorActual;
	private EstadosDeJuego estado;
	private final String archivoHistorialGanadores = "rankingGanadores.dat";
	private HistorialGanadores rankingGanadores;
	public final int MIN_JUGADORES = 1;

	
	public Parade() {
		ganador = null;
		jugadorActual = -1;
		jugadores = new ArrayList<Jugador>();
		carnaval = new Carnaval();
		mazo = new Mazo();
		estado = EstadosDeJuego.CONFIGURANDO;
		
		Serializador serializador = new Serializador(archivoHistorialGanadores);
		rankingGanadores = ((HistorialGanadores) serializador.leerObjeto());
		if (rankingGanadores == null) {
			System.out.println("nulo");
			rankingGanadores = new HistorialGanadores();
		}
		
		
	}


	@Override
	public IJugador registrarJugador(String nombre) throws RemoteException, LogInException {
		IJugador res = null;
		if(jugadores.size() > 5){
			throw new LogInException("La sala esta llena");
		}else {
		if(estado.equals(EstadosDeJuego.CONFIGURANDO)) {
			if (!yaExiste(nombre)) {
				Jugador jugadorAgregado = new Jugador(nombre);
				jugadores.add(jugadorAgregado);
				res = jugadorAgregado;
				notificarObservadores(EventoParade.NUEVO_JUGADOR);
				notificarObservadores(new MensajeGlobal(nombre + " se ha unido a la partida"));
			}else
				throw new LogInException("El nombre ya se encuentra registrado");
		}else
			throw new LogInException("No te puedes unir a una partida comenzada");
		}
		return res;
		
	}
	

	public boolean yaExiste(String nombre) throws RemoteException {
		boolean res = false;
		for (IJugador j: jugadores)
			if (j.getNombre().toLowerCase().equals(nombre.toLowerCase())) 
				res = true;
			
		return res;
	}
	
	@Override
	public boolean eliminarJugador(String nombre) throws RemoteException {
		boolean res = false;
		IJugador j = getJugador(nombre);
		if(j != null) {
			int indiceEliminado = jugadores.indexOf(j);
			res = jugadores.remove(j);
			
			if (res && !jugadores.isEmpty()) {
				notificarObservadores(EventoParade.JUGADOR_ELIMINADO);
				notificarObservadores(new MensajeGlobal(nombre + " se ha desconectado de la partida"));
				if (indiceEliminado == jugadorActual)
					if(indiceEliminado == jugadores.size())
						jugadorActual = 0;
					notificarObservadores(EventoParade.CAMBIO_TURNO);
			}else
				if(jugadores.size() == 0) {
					reiniciarEstado();
				}
					
		}
		return res;
	}
	
	private void reiniciarEstado() {
		ganador = null;
		jugadorActual = -1;
		inicializarJugadores();
		carnaval = new Carnaval();
		mazo = new Mazo();
		estado = EstadosDeJuego.CONFIGURANDO;
	}
	
	@Override
	public void comenzarJuego(String nombre) throws RemoteException { 
		if(jugadores.size() < MIN_JUGADORES || jugadores.size() > 6) {
			notificarObservadores(new MensajeDeError(getJugador(nombre), "No hay suficientes jugadores"));
		}else
			if (estado.equals(EstadosDeJuego.CONFIGURANDO) || estado.equals(EstadosDeJuego.FIN_DE_JUEGO)) {
				reiniciarEstado();
				mazo.inicializar();
				mazo.mezclar();
				inicializarCarnaval();
				inicializarJugadores();
				repartirCartas();
				jugadorActual = 0;
				estado = EstadosDeJuego.JUGANDO;
				notificarObservadores(EventoParade.INICIAR_JUEGO);
			}else
				notificarObservadores(new MensajeDeError(getJugador(nombre), "No se puede comenzar una partida en esta etapa"));
	}
	
	@Override
	public HistorialGanadores getRankingGanadores() throws RemoteException{
		return rankingGanadores;
	}
	
	private void inicializarJugadores() {
		for (Jugador j: jugadores) {
			j.inicializar();
		}
		
	}

	private void repartirCartas() {
		for(Jugador j: jugadores) 
			for(int i = 0; i<5; i++) 
				j.getMano().agregarCarta(mazo.sacarCarta());
	}

	private void inicializarCarnaval() {
		carnaval.clear();
		for (int i = 0; i<6; i++) {
			carnaval.agregarCarta(mazo.sacarCarta());
		}
	}
	
	
	@Override
	public void jugarCarta(Carta c, String nombre) throws RemoteException {
		IJugador j = getJugador(nombre);
		if (j == null)
			return;
		if (estado.equals(EstadosDeJuego.JUGANDO)) {
			if(j.getMano().jugarCarta(c)) {//si el jugador tiene la carta especificada la juega 
				
				j.getAreaJuego().agregarCartas(carnaval.jugada(c)); //toma las cartas correspondientes del carnaval
				j.getMano().agregarCarta(mazo.sacarCarta());     //levanta una carta del mazo
				notificarObservadores(EventoParade.JUGADA_REALIZADA);   //notifico que se realizo una jugada
				
				if(mazo.estaVacio() || j.getAreaJuego().tieneTodosLosColores()) {
					jugadorActual = 0;
					notificarObservadores(EventoParade.CAMBIO_TURNO);
					estado = EstadosDeJuego.ULTIMA_RONDA;
					notificarObservadores(EventoParade.ULTIMA_RONDA);
				}else
					turnoSiguiente();
				
			}
		}else
			if(estado.equals(EstadosDeJuego.ULTIMA_RONDA)) {
				if(j.getMano().jugarCarta(c)) {
					j.getAreaJuego().agregarCartas(carnaval.jugada(c));
					notificarObservadores(EventoParade.JUGADA_REALIZADA);
				}
				if(jugadorActual == jugadores.size()-1) {
					estado = EstadosDeJuego.DESCARTE;
					notificarObservadores(EventoParade.ETAPA_DESCARTE);
				}else
					turnoSiguiente();
			}else {
				MensajeDeError error = new MensajeDeError(j,"No se puede tirar una carta en esta etapa de la partida");
				notificarObservadores(error);
			}
		
	}

	@Override
	public void descartar(String nombre, Carta c1, Carta c2) throws RemoteException {
		IJugador j = getJugador(nombre);
		if (j == null)
			return;
		if(!estado.equals(EstadosDeJuego.DESCARTE)) {
			MensajeDeError error = new MensajeDeError(j,"No se puede descartar en esta etapa de la partida");
			notificarObservadores(error);
			return;
		}
		if(j.getMano().getCartas().size() != 4) {
			notificarObservadores(new MensajeDeError(j, "Ya realizaste un descarte."));
			return;
		}
		if(estado.equals( EstadosDeJuego.DESCARTE )){ 
				j.getMano().jugarCarta(c1);
				j.getMano().jugarCarta(c2);	//Descarto las 2 cartas pasadas
				
				j.getAreaJuego().agregarCartas(j.getMano().getCartas()); //agrego las otras 2 cartas al area de juego del jugador
				
				j.getMano().clear(); //elimino las cartas que agregue al area de juego
				
				
				//verifico si todos los jugadores descartaron
				boolean finDeJuego = true;
				for(IJugador jugador: jugadores) {		
					if (jugador.getMano().getCartas().size() > 2)
						finDeJuego = false;
				}
				
				//si descartaron es el fin de juego y puedo buscar el ganador
				if (finDeJuego) {
					estado = EstadosDeJuego.FIN_DE_JUEGO;
					calcularGanador();
					notificarObservadores(EventoParade.FIN_DE_JUEGO);
				} 
		}
	}
	
	private void turnoSiguiente() throws RemoteException {
		jugadorActual ++;
		if (jugadorActual >= jugadores.size()) 
			jugadorActual = 0;
		notificarObservadores(EventoParade.CAMBIO_TURNO);
	}

	private void calcularPuntajes() {
		ArrayList<Jugador> jMayores = new ArrayList<Jugador>(jugadores.size());
		
		for (Jugador j: jugadores) //inicializo el puntaje de los jugadores
			j.setPuntaje(0); 
		
		
		for (ColorCarta col: ColorCarta.values()) { //por cada color 
			jMayores.clear(); //inicializo el array
			jMayores.add(jugadores.get(0)); //agrego el primer jugador
			for(int i = 1; i < jugadores.size(); i++) { //recorro todos los jugadores a partir del segundo
				
				Jugador jugador_i = jugadores.get(i);
				
				if (jugador_i.getAreaJuego().getFilaCartas(col).size() >= jMayores.get(0).getAreaJuego().getFilaCartas(col).size()) //si la cantidad de cartas de ese color del jugador(i) es mayor o igual a la cantidad de cartas del mayor
					if (jugador_i.getAreaJuego().getFilaCartas(col).size() == jMayores.get(0).getAreaJuego().getFilaCartas(col).size()) //si es igual lo agrego a la lista de mayores
						jMayores.add(jugador_i);
					else {																																		
						for (Jugador j: jMayores) 															//si es mayor le sumo los puntos a todos los que tenia como mayores y limpio la lista
							j.sumarPuntos(j.getAreaJuego().getTotalFila(col));
						jMayores.clear();
						jMayores.add(jugador_i);															//agrego al nuevo mayor
					}
				else
					jugador_i.sumarPuntos(jugador_i.getAreaJuego().getTotalFila(col));                  //si es menor entonces le sumo los puntos directamente
				
			}
			for(Jugador j: jMayores)  //finalmente proceso los puntos de los que tienen la mayor cantidad de cartas
			j.sumarPuntos(j.getAreaJuego().getFilaCartas(col).size());
			
		}
	}
	
	
	private void calcularGanador() {
		if(estado.equals(EstadosDeJuego.FIN_DE_JUEGO)) {
			calcularPuntajes();
			ganador = jugadores.get(0);
			for (IJugador j: jugadores) {
				if (ganador.getPuntaje() >= j.getPuntaje())
					if(ganador.getPuntaje() > j.getPuntaje())
						ganador = j;
					else
						if(ganador.getAreaJuego().cantidadTotalCartas() > j.getAreaJuego().cantidadTotalCartas())
							ganador = j;
			}
			try {
				notificarObservadores(new MensajeGlobal("El Ganador fue... " + ganador.getNombre()));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			rankingGanadores.procesarGanador(ganador.getNombre());
			Serializador serializador = new Serializador(archivoHistorialGanadores);
			serializador.escribirObjeto(rankingGanadores);
		}
		
	}
	
	@Override
	public IJugador getGanador() throws RemoteException {
		return ganador;
	}
	
	
	@Override
	public IJugador getJugadorActual() throws RemoteException {
		return jugadores.get(jugadorActual);
	}
	
	@Override
	public Carnaval getCarnaval() throws RemoteException {
		return this.carnaval;
	}

	@Override
	public ArrayList<IJugador> getJugadores() throws RemoteException {
		ArrayList<IJugador> resultado = new ArrayList<IJugador>();
		for (Jugador j: jugadores) 
			resultado.add(j);
		return resultado;
	}
	
	@Override
	public IJugador getJugador(String nombre) throws RemoteException {
		IJugador result = null;
		for(IJugador j: jugadores)
			if(j.getNombre().toLowerCase().equals(nombre.toLowerCase())) {
				result = j;
				break;
			}
			
		return result;
	}

}
