package ar.edu.unlu.parade.vistas.vistaGUI;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ar.edu.unlu.parade.modelo.AreaDeJuego;
import ar.edu.unlu.parade.modelo.Carta;
import ar.edu.unlu.parade.modelo.ColorCarta;
import ar.edu.unlu.parade.modelo.IJugador;
import net.miginfocom.swing.MigLayout;

public class AreaDeJuegoGUI extends JFrame {
	private JPanel contentPane;
	
	public AreaDeJuegoGUI(IJugador jugador) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0,800, 500);
		setTitle("Visualizador de area de juego");
		
		contentPane = new JPanel();
		contentPane.setLayout(new MigLayout("","[]", "[]"));
		contentPane.setBorder(BorderFactory.createEmptyBorder());
		contentPane.setBackground(new Color(111,78,45));
		setContentPane(contentPane);
		
		if(jugador != null)
			actualizarAreaDejuego(jugador);
		
		setFocusable(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actualizarAreaDejuego(IJugador jugador) {
		contentPane.removeAll();
		JLabel titulo = new JLabel("Area de juego de: "+ jugador.getNombre());
		titulo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		titulo.setForeground(Color.BLACK);
		contentPane.add(titulo, "h ::30, growx, wrap");
		for(ColorCarta col: ColorCarta.values()) {
			ArrayList<Carta> fila = jugador.getAreaJuego().getFilaCartas(col);
			
			for(int i = 0; i<fila.size()-1; i++) 
				contentPane.add(new CartaGUI(fila.get(i)), "grow, h ::50, w ::35, split 2");
			
			if(fila.size() > 0)
				contentPane.add(new CartaGUI(fila.get(fila.size()-1)), "grow, h ::50, w ::35, wrap");
		}
		revalidate();
	}
	
}
