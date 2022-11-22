package ar.edu.unlu.parade.ranking;

import java.io.Serializable;
import java.util.ArrayList;

public class HistorialGanadores implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<EntidadGanador> ganadoresHistoricos;
	
	public HistorialGanadores() {
		ganadoresHistoricos = new ArrayList<EntidadGanador>();
	}

	public ArrayList<EntidadGanador> getGanadoresHistoricos() {
		return ganadoresHistoricos;
	}
	
	public void procesarGanador(String nombre) {
		EntidadGanador j = buscar(nombre);
		if(j != null) {
			if(ganadoresHistoricos.size() == 1) 
				ganadoresHistoricos.get(0).incrementarPartidasGanadas();
			else {
				j.incrementarPartidasGanadas();
				int i = ganadoresHistoricos.indexOf(j)-1;
				while(j.getPartidasGanadas() > ganadoresHistoricos.get(i).getPartidasGanadas())
					i--;
				if(i < 0)
					i = 0;
				ganadoresHistoricos.remove(j);
				ganadoresHistoricos.add(i, j);
				
			}
		}else {
			j = new EntidadGanador(nombre, 1);
			int ind = 0;
			boolean insertado = false;
			for(EntidadGanador ganador: ganadoresHistoricos) {
				if(j.getPartidasGanadas() > ganador.getPartidasGanadas()) {
					ganadoresHistoricos.add(ind, j);
					insertado = true;
					break;
				}	
				ind++;
			}
			if(!insertado) {
				ganadoresHistoricos.add(j);
			}
		}
	}

	private EntidadGanador buscar(String nombre) {
		EntidadGanador ret = null;
		for(EntidadGanador j: ganadoresHistoricos) 
			if (j.getNombre().equals(nombre)) {
				ret = j;
				break;
			}
		return ret;
	}
	
	public ArrayList<EntidadGanador> getTopGanadores(){
		ArrayList<EntidadGanador> topGanadores = new ArrayList<EntidadGanador>();
		for(int i = 0; i<10; i++) {
			topGanadores.add(ganadoresHistoricos.get(i));
		}
		return topGanadores;
	}
}
