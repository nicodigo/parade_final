package ar.edu.unlu.parade.vistas.vistaGUI;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;

import ar.edu.unlu.parade.modelo.Carta;

public class CartaClickeableGUI extends CartaGUI{
	private boolean seleccionada;

	public CartaClickeableGUI(Carta c) {
		super(c);
		seleccionada = false;
		setFocusable(true);
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				clicked(e);
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	private void clicked(MouseEvent e){
		if(!seleccionada) {
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true));
			seleccionada = true;
		}else {
			setBorder(BorderFactory.createEmptyBorder());
			seleccionada = false;
		}
	}
	
	public boolean estaSeleccionada() {
		return seleccionada;
	}
	
}
