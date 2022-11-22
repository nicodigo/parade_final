package ar.edu.unlu.parade.ranking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import java.util.ArrayList;

public class RankingPuntajes implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<EntidadJugador> topPuntajes;
	private static int MAX_SIZE = 10;
	
	public RankingPuntajes() {
		topPuntajes = new ArrayList<EntidadJugador>();
	}
	
	public ArrayList<EntidadJugador> getTopPuntajes() {
		return topPuntajes;
	}
	
	public void procesarGanador(String nombre, int puntaje) {
		boolean insertado = false;
		int indice = 0;
		for(EntidadJugador j: topPuntajes) {
			if(puntaje < j.getPuntaje()) {
				EntidadJugador jAgregar = new EntidadJugador(nombre, puntaje);
				topPuntajes.add(indice, jAgregar);
				insertado = true;
				break;
			}
			indice ++;
		}
		if(!insertado && topPuntajes.size() < MAX_SIZE) 
			topPuntajes.add(new EntidadJugador(nombre, puntaje));
			
		if(topPuntajes.size() > MAX_SIZE) 
			topPuntajes.remove(topPuntajes.size()-1);
	}
	
}
