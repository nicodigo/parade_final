package ar.edu.unlu.parade.modelo;

public interface ObserverParade {
	public void actualizar(EventoParade e);
	public void actualizar(IJugador jugador, String msjError);
	
}
