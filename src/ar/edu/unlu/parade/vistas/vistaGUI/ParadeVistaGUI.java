package ar.edu.unlu.parade.vistas.vistaGUI;


import java.awt.Color; 
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import ar.edu.unlu.parade.controlador.Controlador;
import ar.edu.unlu.parade.modelo.Carnaval;
import ar.edu.unlu.parade.modelo.Carta;
import ar.edu.unlu.parade.modelo.IJugador;
import ar.edu.unlu.parade.ranking.HistorialGanadores;
import ar.edu.unlu.parade.vistas.IVistaParade;
import net.miginfocom.swing.MigLayout;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class ParadeVistaGUI extends JFrame implements IVistaParade {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelSuperior;
	private JPanel panelMedio;
	private JPanel panelInferior;
	private JScrollPane scrollPane;
	private JTextPane areaMensajes;
	private JButton btnTirarCarta;
	private JButton btnDescartar;
	private JButton btnComenzarJuego;
	private JButton btnMostrarArea;
	private JButton btnToggleMiArea;
	private JButton btnMostrarRanking;
	private JPanel pnlBotones;
	private JPanel pnlMiMano;
	private JPanel pnlCarnaval;
	private JPanel panelTitulo;
	
	private JPanel panelInformacion;
	private JTextArea turno;
	private JTextArea jugadoresConectados;
	
	private AreaDeJuegoGUI miAreaDeJuego;
	private ArrayList<CartaClickeableGUI> miMano;
	
	private Controlador controlador;


	public ParadeVistaGUI(Controlador controlador) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		setTitle("Juego Parade :D");
		contentPane = new JPanel();
		contentPane.setLayout(new MigLayout("inset 0", "[grow]", "[grow]1[grow]"));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setBackground(Color.DARK_GRAY);
		
		this.controlador = controlador;
		controlador.setVista(this);
		
		this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        controlador.desconectar();
		        System.exit(0);
		    }
		    
		});
		
		panelTitulo= new JPanel();
		panelTitulo.setBorder(BorderFactory.createEmptyBorder());
		panelTitulo.setBackground(new Color(250, 244, 212));
		panelTitulo.setLayout(new MigLayout("", "[grow]", "[grow]"));
		contentPane.add(panelTitulo,"grow,north");
		
		
		JLabel lblUsername = new JLabel("Nombre de Usuario: " + controlador.getMiJugador().getNombre(), SwingConstants.CENTER);
		lblUsername.setForeground(Color.BLACK);
		lblUsername.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
		panelTitulo.add(lblUsername,"shrink,left");
		
		
		panelSuperior = new JPanel();
		panelSuperior.setBorder(BorderFactory.createEmptyBorder());
		panelSuperior.setBackground(new Color(158,129,112));
		panelSuperior.setLayout(new MigLayout("insets 0", "[grow]", "[][grow]"));
		contentPane.add(panelSuperior, "grow,wrap");
		
		
		panelMedio = new JPanel();
		panelMedio.setBorder(BorderFactory.createEmptyBorder());
		panelMedio.setBackground(new Color(246, 215, 176));
		panelMedio.setLayout(new MigLayout("insets 0", "[grow]", "[][grow]"));
		contentPane.add(panelMedio, "grow");
		
		
		panelInferior = new JPanel();
		panelInferior.setBorder(BorderFactory.createEmptyBorder());
		panelInferior.setBackground(new Color(107,68,35));
		panelInferior.setLayout(new MigLayout("insets 5", "[grow][shrink]", "[grow]"));
		contentPane.add(panelInferior, "grow, south, h 150::200");
		
		
		panelInformacion = new JPanel();
		panelInformacion.setLayout(new MigLayout("", "[grow]", "[shrink][grow]"));
		panelInformacion.setBorder(BorderFactory.createEmptyBorder());
		panelInformacion.setBackground(new Color(239,222,205));
		contentPane.add(panelInformacion,"growx, east, w 200::250");
		
		miAreaDeJuego = new AreaDeJuegoGUI(controlador.getMiJugador());
		miAreaDeJuego.setVisible(false);
		
		miMano = new ArrayList<CartaClickeableGUI>();
		
		inicializarPanelSuperior();
		inicilaizarPanelMedio();
		inicializarPanelInferior();
		inicializarPanelInfo();
		
		
	}
	
	private void inicializarPanelInferior() {
		areaMensajes = new JTextPane();
		areaMensajes.setBorder(BorderFactory.createEmptyBorder());
		areaMensajes.setBackground(new Color(12,25,35));
		areaMensajes.setForeground(Color.WHITE);
		areaMensajes.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 12));
		areaMensajes.setText("Log de mensajes\n");
		areaMensajes.setEditable(false);
		
		scrollPane = new JScrollPane(areaMensajes);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		panelInferior.add(scrollPane, "grow");
		
		
		pnlBotones = new JPanel();
		pnlBotones.setLayout(new MigLayout("inset 1", "[grow][grow][grow]", "[grow][grow]"));
		pnlBotones.setBackground(new Color(107,68,35));
		pnlBotones.setBorder(BorderFactory.createEmptyBorder());
		panelInferior.add(pnlBotones, "grow");
		
		Color botonesBG = new Color(181, 166, 66);
		
		btnTirarCarta = new JButton();
		btnTirarCarta.setText("Tirar");
		btnTirarCarta.setBackground(botonesBG);
		pnlBotones.add(btnTirarCarta, "cell 0 0 1 1,grow,w 140!");
		btnTirarCarta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tirarCarta();
			}			
		});
		
		btnDescartar = new JButton();
		btnDescartar.setText("Descartar");
		btnDescartar.setBackground(botonesBG);
		pnlBotones.add(btnDescartar,"cell 1 0 1 1,grow,w 140!");
		btnDescartar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				descartar();
			}			
		});
		
		btnMostrarArea = new JButton();
		btnMostrarArea.setText("Ver Area De Juego");
		btnMostrarArea.setBackground(botonesBG);
		pnlBotones.add(btnMostrarArea, "cell 2 0 1 1,grow,w 140!");
		btnMostrarArea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarAreaJuegoJugador();
			}
		});
		
		btnComenzarJuego = new JButton();
		btnComenzarJuego.setText("Comenzar Partida");
		btnComenzarJuego.setBackground(new Color(205, 127, 50));
		pnlBotones.add(btnComenzarJuego, "cell 2 1 1 1,grow,w 140!");
		btnComenzarJuego.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.comenzarJuego();				
			}
			
		});
		
		btnToggleMiArea = new JButton();
		btnToggleMiArea.setText("Mostrar Mi Area");
		btnToggleMiArea.setBackground(botonesBG);
		pnlBotones.add(btnToggleMiArea, "cell 0 1 1 1, grow, w 140!");
		btnToggleMiArea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleMiAreaJuego();
				
			}
		});
		
		btnMostrarRanking = new JButton();
		btnMostrarRanking.setText("Mostrar Rankings");
		btnMostrarRanking.setBackground(botonesBG);
		pnlBotones.add(btnMostrarRanking, "cell 1 1 1 1, grow, w 140!");
		btnMostrarRanking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarRanking();
				
			}
		});
	}

	private void inicilaizarPanelMedio() {				
		JLabel lblTuMano = new JLabel("TU MANO", SwingConstants.CENTER);
		lblTuMano.setForeground(Color.BLACK);
		lblTuMano.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		panelMedio.add(lblTuMano, "cell 0 0 1 1, center");
		
		pnlMiMano = new JPanel();
		pnlMiMano.setLayout(new MigLayout("", "[grow ,50::500]", "[grow]"));
		pnlMiMano.setBackground(new Color(222, 184, 135));
		panelMedio.add(pnlMiMano, "cell 0 1 1 1, grow");
		
		
	}

	private void inicializarPanelSuperior() {		
		JLabel lblCarnaval = new JLabel("CARNAVAL", SwingConstants.CENTER);
		lblCarnaval.setForeground(Color.BLACK);
		lblCarnaval.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		panelSuperior.add(lblCarnaval, "cell 0 0 1 1, center");
		
		pnlCarnaval = new JPanel();
		pnlCarnaval.setLayout(new MigLayout("", "[grow, 50::500]", "[grow]"));
		pnlCarnaval.setBackground(new Color(111,78,45));
		panelSuperior.add(pnlCarnaval, "cell 0 1 1 1, grow");
		
	}
	
	private void inicializarPanelInfo(){
		turno = new JTextArea();
		turno.setBorder(BorderFactory.createEmptyBorder());
		turno.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		turno.setBackground(new Color(61, 43, 31));
		turno.setForeground(new Color(245, 222, 179));
		turno.setText("Turno de:\n");
		panelInformacion.add(turno, "grow, h 80:100:120, wrap");
		
		jugadoresConectados = new JTextArea();
		jugadoresConectados.setBorder(BorderFactory.createEmptyBorder());
		jugadoresConectados.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		jugadoresConectados.setBackground(new Color(61, 43, 31));
		jugadoresConectados.setForeground(new Color(245, 222, 179));
		jugadoresConectados.setText("Jugadores Conectados:\n");
		panelInformacion.add(jugadoresConectados, "grow");
		System.out.println(controlador.getJugadores().size());
		actualizarJugadoresConectados(controlador.getJugadores());
	}

	
	@Override
	public void inicializar() {
		actualizarPanelCarnaval(controlador.getCarnaval());
		actualizarPanelMano(controlador.getMiJugador());
		miAreaDeJuego.actualizarAreaDejuego(controlador.getMiJugador());
		
		setLocationRelativeTo(null);
		setFocusable(true);
		setVisible(true);
	}

	@Override
	public void actualizarJugadoresConectados(ArrayList<IJugador> jugadores) {
		if(jugadores != null && !jugadores.isEmpty()) {
			jugadoresConectados.setText("Jugadores Conectados:");
			for(IJugador j: jugadores) {
				jugadoresConectados.append("\n	" + j.getNombre());
			}
		}

	}

	@Override
	public void juegoIniciado(Carnaval carnaval) {
		actualizarPanelCarnaval(carnaval);
		actualizarPanelMano(controlador.getMiJugador());
		cambioDeTurno(controlador.getJugadorActual());
		mostrarMensaje("El juego ha iniciado");
		
	}

	private void actualizarPanelCarnaval(Carnaval carnaval) {
		if(carnaval != null) {
			pnlCarnaval.removeAll();
			for(Carta carta: carnaval.getCartas()) {
				pnlCarnaval.add(new CartaGUI(carta), "growy, w 50::500");
			}
			revalidate();
		}
	}

	private void actualizarPanelMano(IJugador miJugador) {
		if(miJugador != null && miJugador.getMano() != null) {
			pnlMiMano.removeAll();
			if(miJugador.getMano().getCartas().size()>0) {
				miMano.clear();
				for(Carta carta: miJugador.getMano().getCartas()) {
					CartaClickeableGUI cartaClick = new CartaClickeableGUI(carta);
					pnlMiMano.add(cartaClick, "growy, w 50::500");
					miMano.add(cartaClick);
				}
			}
			revalidate();
		}
		
	}	
	
	@Override
	public void actualizarCarnaval(Carnaval carnaval) {
		actualizarPanelCarnaval(carnaval);
	}


	@Override
	public void ultimaRonda() {
		mostrarMensaje("** ESTA ES LA ULTIMA RONDA DE LA PARTIDA **\n Luego de que todos jueguen una carta\n empieza la ronda de descarte");
	}

	@Override
	public void finalDeJuego(IJugador ganador) {
		mostrarMensaje("** JUEGO FINALIZADO!! **");
		mostrarMensaje("    El Ganador fue:  ");
		mostrarMensaje("\t "+ ganador.getNombre());
		mostrarMensaje("\n Gracias por haber jugado!!");
		
	}

	@Override
	public void cambioDeTurno(IJugador jugadorTurno) {
		turno.setText("Turno de:\n");
		turno.append(jugadorTurno.getNombre());
		//revalidate();
		
	}

	@Override
	public void mostrarError(String errorMsj) {
		Document doc = areaMensajes.getDocument();
		areaMensajes.setForeground(Color.RED);
		areaMensajes.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		try {
			doc.insertString(doc.getLength(), "Error: "+errorMsj + "\n", null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		Document doc = areaMensajes.getDocument();
		areaMensajes.setForeground(Color.YELLOW);
		areaMensajes.setFont(new Font(Font.DIALOG_INPUT, Font.ITALIC, 12));
		try {
			doc.insertString(doc.getLength(), mensaje + "\n", null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void inicioEtapaDescarte() {
		mostrarMensaje("** INICIO DE LA ETAPA DE DESCARTE **\n Selecciona dos cartas que desees y presiona el boton \"Descartar\"\n para descartarlas, las dos restantes se sumaran a tu area de juego.");
	}
	
	private void mostrarRanking() {
		HistorialGanadores ranking = controlador.getRankingGanadores();
		if(ranking != null && ranking.getTopGanadores().size() != 0) {
			RankingGUI r = new RankingGUI(ranking);
			r.setVisible(true);
		}else
			mostrarMensaje("Aun no existe un ranking de ganadores, se creara luego de jugar una partida.");
	}
	
	

	private void toggleMiAreaJuego() {
		if(miAreaDeJuego != null) {
			miAreaDeJuego.setVisible(!miAreaDeJuego.isVisible());
		}else {
			System.out.println("es nula");
			miAreaDeJuego = new AreaDeJuegoGUI(controlador.getMiJugador());
		}
	}

	
	private void mostrarAreaJuegoJugador() {
		ArrayList<String> jugadores = new ArrayList<String>();
		for(IJugador j: controlador.getJugadores()) {
			if(!j.getNombre().equals(controlador.getMiJugador().getNombre()))
				jugadores.add(j.getNombre());
		}
		String nombre = (String) JOptionPane.showInputDialog(
				null,
				"Seleccione el Nombre del Jugador:",
				"Mostrar Area De Juego",
				JOptionPane.QUESTION_MESSAGE,
				null,
				jugadores.toArray(),
				null
		);
		if(nombre != null && !nombre.equals("")) {
			nombre = nombre.trim();
			if(controlador.nombreExistente(nombre)) {
				new AreaDeJuegoGUI(controlador.getJugador(nombre));
			}else
				mostrarError("No existe ese jugador");
		}else
			mostrarError("El nombre es nulo");
	}	
	
	
	private void tirarCarta() {	
		ArrayList<CartaClickeableGUI> seleccionadas = new ArrayList<CartaClickeableGUI>(); 
		for(CartaClickeableGUI carta: miMano) {
			if(carta.estaSeleccionada())
				seleccionadas.add(carta);
		}
		if(seleccionadas.size() == 1) {
			controlador.jugarCarta(seleccionadas.get(0).getCarta());
		}else
			mostrarError("Debes seleccionar UNA carta para tirar.");
	}
	
	private void descartar() {
		ArrayList<CartaClickeableGUI> seleccionadas = new ArrayList<CartaClickeableGUI>(); 
		for(CartaClickeableGUI carta: miMano) {
			if(carta.estaSeleccionada())
				seleccionadas.add(carta);
		}
		if(seleccionadas.size() == 2) {
			controlador.descartar(seleccionadas.get(0).getCarta(), seleccionadas.get(1).getCarta());
		}else
			mostrarError("Debes seleccionar DOS carta para descartar.");
		
	}

	@Override
	public void actualizarMiJugador(IJugador miJugador) {
		actualizarPanelMano(miJugador);
		miAreaDeJuego.actualizarAreaDejuego(miJugador);
		
	}
	
}