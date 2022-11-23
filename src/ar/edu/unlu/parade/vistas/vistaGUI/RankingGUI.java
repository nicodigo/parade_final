package ar.edu.unlu.parade.vistas.vistaGUI;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ar.edu.unlu.parade.ranking.EntidadGanador;
import ar.edu.unlu.parade.ranking.HistorialGanadores;
import net.miginfocom.swing.MigLayout;

public class RankingGUI extends JFrame{
	private JPanel contentPane;

	public RankingGUI(HistorialGanadores ranking) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0,400, 720);
		setTitle("Rankings");

		contentPane = new JPanel();
		contentPane.setLayout(new MigLayout("","[grow][grow]", "[shrink]"));
		contentPane.setBorder(BorderFactory.createEmptyBorder());
		contentPane.setBackground(new Color(111,78,45));
		setContentPane(contentPane);
		mostrarRanking(ranking);

		setFocusable(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void mostrarRanking(HistorialGanadores ranking) {
		if(ranking != null) {
			ArrayList<EntidadGanador> topGanadores = ranking.getTopGanadores();
			if(topGanadores.size() > 0) {
				
				JLabel label = new JLabel("-- NOMBRE --", SwingConstants.CENTER);
				label.setForeground(Color.BLACK);
				label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
				contentPane.add(label, "shrinky, growx, h 15:40:40");
				
				label = new JLabel("-- VICTORIAS --", SwingConstants.CENTER);
				label.setForeground(Color.BLACK);
				label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
				contentPane.add(label, "shrinky, growx, h 15:40:40, wrap");
				
				for(EntidadGanador ganador: ranking.getTopGanadores()) {
					label = new JLabel(ganador.getNombre(), SwingConstants.CENTER);
					label.setForeground(Color.BLACK);
					label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
					contentPane.add(label, "shrinky, growx, h 15:40:40");
					
					label = new JLabel(Integer.toString(ganador.getPartidasGanadas()), SwingConstants.CENTER);
					label.setForeground(Color.BLACK);
					label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
					contentPane.add(label, "shrinky, growx, h 15:40:40, wrap");
				}
			}
		}
		
	}

}
