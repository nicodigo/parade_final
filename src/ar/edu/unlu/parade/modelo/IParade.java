package ar.edu.unlu.parade.modelo;

import java.io.Serializable; 
import java.rmi.RemoteException;
import java.util.ArrayList;

import ar.edu.unlu.parade.ranking.HistorialGanadores;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public interface IParade extends IObservableRemoto, Serializable{

	IJugador registrarJugador(String nombre) throws RemoteException, LogInException;

	boolean eliminarJugador(String nombre) throws RemoteException;

	void comenzarJuego(String nombre) throws RemoteException;

	void jugarCarta(Carta c, String nombre) throws RemoteException;

	void descartar(String nombre, Carta c1, Carta c2) throws RemoteException;

	IJugador getGanador() throws RemoteException;

	IJugador getJugadorActual() throws RemoteException;

	Carnaval getCarnaval() throws RemoteException;

	ArrayList<IJugador> getJugadores() throws RemoteException;

	IJugador getJugador(String nombre) throws RemoteException;

	boolean yaExiste(String nombre) throws RemoteException;
	 
	HistorialGanadores getRankingGanadores() throws RemoteException;
}