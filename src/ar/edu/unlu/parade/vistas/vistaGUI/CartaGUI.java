package ar.edu.unlu.parade.vistas.vistaGUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints; 

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ar.edu.unlu.parade.modelo.Carta;

public class CartaGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private Carta carta;
	private JLabel lblNumero;
	
	public CartaGUI(Carta c) {
		super();
		carta = c;
		lblNumero = new JLabel(Integer.toString(carta.getNumero()), SwingConstants.CENTER);
		lblNumero.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 24));
		lblNumero.setForeground(Color.BLACK);
		
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(800,500));
		setBackground(carta.getColor().toAWTColor());
		add(lblNumero, BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder());
		this.setOpaque(false);
	}
	
	public Carta getCarta() {
		return carta;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(carta.getColor().toAWTColor());
		g2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
	}
	
	

}
