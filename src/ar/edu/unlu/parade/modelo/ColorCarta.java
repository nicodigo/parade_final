package ar.edu.unlu.parade.modelo;

import java.awt.Color;

public enum ColorCarta {
	ROJO, AZUL, VIOLETA, CELESTE, AMARILLO, VERDE;
	
	public Color toAWTColor() {
		switch (this) {
		case ROJO: return new Color(251,66,54);
		case AZUL: return new Color(63,94,247);
		case VIOLETA: return new Color(157,56,232);
		case CELESTE: return new Color(93,190,227);
		case AMARILLO: return new Color(231,235,99);
		case VERDE: return new Color(85,232,80);
		default:
			return Color.WHITE;
		}
	}

}
