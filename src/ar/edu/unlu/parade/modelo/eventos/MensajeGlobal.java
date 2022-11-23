package ar.edu.unlu.parade.modelo.eventos;

import java.io.Serializable;

import ar.edu.unlu.parade.modelo.IJugador;

public class MensajeGlobal implements Serializable {
	private String mensaje;
	
	public MensajeGlobal(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}
}
