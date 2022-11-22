package ar.edu.unlu.parade.modelo;

import java.rmi.RemoteException;
import java.util.ArrayList;

import ar.edu.unlu.parade.ranking.HistorialGanadores;
import ar.edu.unlu.parade.ranking.RankingPuntajes;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import ar.edu.unlu.utils.serializador.Serializador;

public class Parade extends ObservableRemoto implements IParade{
	private ArrayList<Jugador> jugadores;
	private IJugador ganador = null;
	private Carnaval carnaval;
	private Mazo mazo;
	private int jugadorActual;
	private int estado;
	private final String archivoRankingPuntajes = "rankingPuntajes.dat";
	private RankingPuntajes rankingPuntajes;
	private final String archivoHistorialGanadores = "rankingGanadores.dat";
	private HistorialGanadores rankingGanadores;
	public static final int CONFIGURANDO = 0;
	public static final int JUGANDO = 1;
	public static final int ULTIMA_RONDA = 2;
	public static final int DESCARTE = 3;
	public static final int FIN_DE_JUEGO = 4;

	
	public Parade() {
		jugadores = new ArrayList<Jugador>();
		carnaval = new Carnaval();
		mazo = new Mazo();
		jugadorActual = -1;
		
		Serializador serializador = new Serializador(archivoHistorialGanadores);
		rankingGanadores = ((HistorialGanadores) serializador.leerObjeto());
		if (rankingGanadores == null) {
			System.out.println("nulo");
			rankingGanadores = new HistorialGanadores();
		}
		
		Serializador serializador2 = new Serializador(archivoRankingPuntajes);
		rankingPuntajes = ((RankingPuntajes) serializador2.leerObjeto());
		if (rankingPuntajes == null) {
			System.out.println("nulopunt");
			rankingPuntajes = new RankingPuntajes();
		}
		estado = CONFIGURANDO;
	}
	
	
	@Override
	public IJugador registrarJugador(String nombre) throws RemoteException {
		IJugador res = null;
		if(estado == CONFIGURANDO) {
			if (!yaExiste(nombre)) {
				Jugador jugadorAgregado = new Jugador(nombre);
				jugadores.add(jugadorAgregado);
				res = jugadorAgregado;
				notificarObservadores(EventoParade.NUEVO_JUGADOR);
			}
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
				if (indiceEliminado == jugadorActual)
					notificarObservadores(EventoParade.CAMBIO_TURNO);
			}//else
				//if(jugadores.isEmpty())
					//System.exit(0);
		}
		return res;
	}
	
	@Override
	public void comenzarJuego(String nombre) throws RemoteException { 
		if(jugadores.size() < 1 || jugadores.size() > 6) {
			return;
		}
		if (estado == CONFIGURANDO || estado == FIN_DE_JUEGO) {
			mazo.inicializar();
			mazo.mezclar();
			inicializarCarnaval();
			incializarJugadores();
			repartirCartas();
			jugadorActual = 0;
			estado = JUGANDO;
			notificarObservadores(EventoParade.INICIAR_JUEGO);
		}
	}
	
	@Override
	public RankingPuntajes getRankingPuntaje() throws RemoteException {
		return rankingPuntajes;
	}
	
	@Override
	public HistorialGanadores getRankingGanadores() throws RemoteException{
		return rankingGanadores;
	}
	
	private void incializarJugadores() {
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
		if (estado == JUGANDO) {
			if(j.getMano().jugarCarta(c)) {//si el jugador tiene la carta especificada la juega 
				
				j.getAreaJuego().agregarCartas(carnaval.jugada(c)); //toma las cartas correspondientes del carnaval
				j.getMano().agregarCarta(mazo.sacarCarta());     //levanta una carta del mazo
				notificarObservadores(EventoParade.JUGADA_REALIZADA);   //notifico que se realizo una jugada
				
				turnoSiguiente();


				if(mazo.estaVacio() || j.getAreaJuego().tieneTodosLosColores()) {
					jugadorActual = 0;
					estado = ULTIMA_RONDA;
					notificarObservadores(EventoParade.ULTIMA_RONDA);
				}
			}
		}else
			if(estado==ULTIMA_RONDA) {
				if(j.getMano().jugarCarta(c)) {
					j.getAreaJuego().agregarCartas(carnaval.jugada(c));
					notificarObservadores(EventoParade.JUGADA_REALIZADA);
				}
				if(jugadorActual == jugadores.size()-1) {
					estado = DESCARTE;
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
		if(estado != DESCARTE) {
			MensajeDeError error = new MensajeDeError(j,"No se puede descartar en esta etapa de la partida");
			notificarObservadores(error);
			return;
		}
		if(estado == DESCARTE ) 
				j.getMano().jugarCarta(c1);
				j.getMano().jugarCarta(c2);	//Descarto las 2 cartas pasadas
				
				
				j.getAreaJuego().agregarCartas(j.getMano().getCartas()); //agrego las otras 2 cartas al area de juego del jugador
				
				ArrayList<Carta> manoDuplicada = j.getMano().getCartas(); //Elimino las cartas en la mano que agrege al area de juego.
				for(Carta c: manoDuplicada) 
					j.getMano().jugarCarta(c);
				
				
				
				notificarObservadores(EventoParade.DESCARTE_REALIZADO);	//notifico que hubo un descarte
				
				//verifico si todos los jugadores descartaron
				boolean finDeJuego = true;
				for(IJugador jugador: jugadores) {		
					if (jugador.getMano().getCartas().size() > 2)
						finDeJuego = false;
				}
				
				//si descartaron es el fin de juego y puedo buscar el ganador
				if (finDeJuego) {
					estado = FIN_DE_JUEGO;
					calcularGanador();
					notificarObservadores(EventoParade.FIN_DE_JUEGO);
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
		if(estado == FIN_DE_JUEGO) {
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
			rankingPuntajes.procesarGanador(ganador.getNombre(), ganador.getPuntaje());
			Serializador serializador = new Serializador (archivoRankingPuntajes);
			System.out.println(serializador.escribirObjeto(rankingPuntajes));
			
			rankingGanadores.procesarGanador(ganador.getNombre());
			serializador = new Serializador(archivoHistorialGanadores);
			System.out.println(serializador.escribirObjeto(rankingGanadores));
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
	public ArrayList<Jugador> getJugadores() throws RemoteException {
		ArrayList<Jugador> resultado = new ArrayList<Jugador>();
		for (Jugador j: jugadores) 
			resultado.add(j);
		return resultado;
	}
	
//	@Override
//	public void agregarObservador(ObserverParade o) {
//		misObservadores.add(o);
//	}

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

	

//	public void notificar(EventoParade evento) {
//		for(ObserverParade o: misObservadores) {
//			o.actualizar(evento);
//		}
//		
//	}
//	
//	@Override
//	public void notificarError(Jugador j, String msjError) {
//		for(ObserverParade o: misObservadores) {
//			o.actualizar(j, msjError);
//		}
//		
//	}

	
	
}
