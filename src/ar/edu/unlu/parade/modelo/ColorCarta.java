package ar.edu.unlu.parade.modelo;

import java.awt.Color;

public enum ColorCarta {
	ROJO, AZUL, VIOLETA, CELESTE, AMARILLO, VERDE;
	
	public Color toAWTColor() {
		switch (this) {
		case ROJO: return Color.RED;
		case AZUL: return Color.BLUE;
		case VIOLETA: return Color.MAGENTA;
		case CELESTE: return Color.CYAN;
		case AMARILLO: return Color.YELLOW;
		case VERDE: return Color.GREEN;
		default:
			return Color.WHITE;
		}
	}

}
