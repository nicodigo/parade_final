package ar.edu.unlu.parade.vistas.vista_consola;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unlu.parade.controlador.Controlador;
import ar.edu.unlu.parade.modelo.Carnaval;
import ar.edu.unlu.parade.modelo.Carta;
import ar.edu.unlu.parade.modelo.ColorCarta;
import ar.edu.unlu.parade.modelo.IJugador;
import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.ranking.EntidadGanador;
import ar.edu.unlu.parade.ranking.EntidadJugador;
import ar.edu.unlu.parade.ranking.HistorialGanadores;
import ar.edu.unlu.parade.ranking.RankingPuntajes;
import ar.edu.unlu.parade.vistas.IVistaParade;

public class ParadeVistaConsola extends JFrame implements IVistaParade {
	public static final int ANCHO = 1500;
	public static final int ALTO = 720;
	
	private static final int ANCHO_PANEL_IZ = 250;
	private JPanel contentPane;
	private JPanel panelIzquierdo;
	private JPanel panelCentral;
	private JPanel panelSur;
	private JTextArea areaTexto;
	private JTextArea areaMesa;
	private JTextField lineaEntrada;
	private JTextArea areaJugadores;
	private JTextArea txtAreaDeJuego;
	private JTextArea areaTurnoActual;
	private JScrollPane scrollPane;
	private Font fuente;
	private final Color BG_COLOR = new Color(15,20,30);
	
	private Controlador controlador;
	
	private boolean jugando = false;
	
	
	
	public ParadeVistaConsola(Controlador c) {
		controlador = c;
		controlador.setVista(this);
		
	}
	
	@Override
	public void inicializar() {
		setTitle("Parade Consola");
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, ANCHO, ALTO);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(0,0,0,0));
		contentPane.setLayout(new BorderLayout(0,0));
		
		fuente = new Font(Font.DIALOG_INPUT, Font.PLAIN, 12);
		
		
		inicializarPanelIzquierdo();
		inicializarPanelCentral();
		inicializarPanelSur();
		
		this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        controlador.desconectarJugador();
		    }
		    
		});
		setLocationRelativeTo(null);
		
		setVisible(true);
		setFocusable(true);
		
	}








//	private void inicializarPanelIzquierdo() {
//		
//		panelIzquierdo = new JPanel();
//		panelIzquierdo.setBorder(new EmptyBorder(0,0,0,5));
//		panelIzquierdo.setLayout(null);
//		panelIzquierdo.setPreferredSize(new Dimension(ANCHO*1/4, ALTO));
//		
//		areaTurnoActual = new JTextArea();
//		areaTurnoActual.setBackground(BG_COLOR);
//		areaTurnoActual.setBounds(0, 0,panelIzquierdo.getPreferredSize().width-5, 40);
//		areaTurnoActual.setForeground(Color.WHITE);
//		areaTurnoActual.setText("Turno De: ");
//		panelIzquierdo.add(areaTurnoActual);
//		
//		areaJugadores = new JTextArea();
//		areaJugadores.setBackground(BG_COLOR);
//		areaJugadores.setForeground(Color.GREEN);
//		areaJugadores.setFont(fuente);
//		areaJugadores.append("Texto de: \nprueba");
//		areaJugadores.setBounds(0, areaTurnoActual.getHeight()+2, panelIzquierdo.getPreferredSize().width-5, panelIzquierdo.getPreferredSize().height/4);
//		areaJugadores.setEditable(false);
//		panelIzquierdo.add(areaJugadores);
//		
//		
//		miAreaJuego = new JTextArea();
//		miAreaJuego.setBackground(BG_COLOR);
//		miAreaJuego.setForeground(Color.WHITE);
//		miAreaJuego.setFont(fuente);
//		miAreaJuego.append("AMARILLO: 0|1|2|3|4|5|6|7|8|9|10");
//		miAreaJuego.setBounds(0, areaJugadores.getY() + areaJugadores.getHeight()+2, panelIzquierdo.getPreferredSize().width-5, panelIzquierdo.getPreferredSize().height* 3/4);
//		miAreaJuego.setEditable(false);
//		panelIzquierdo.add(miAreaJuego);
//		
//		
//		
//		contentPane.add(panelIzquierdo, BorderLayout.WEST);
//		
//	}
	
	private void inicializarPanelIzquierdo() {
	
	panelIzquierdo = new JPanel();
	panelIzquierdo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
	panelIzquierdo.setLayout(new BorderLayout());
	panelIzquierdo.setPreferredSize(new Dimension(ANCHO_PANEL_IZ, ALTO));
	
	areaTurnoActual = new JTextArea();
	areaTurnoActual.setBackground(BG_COLOR);
	areaTurnoActual.setForeground(Color.WHITE);
	areaTurnoActual.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
	areaTurnoActual.setPreferredSize(new Dimension(0,70));
	areaTurnoActual.setText("Turno De: \n");
	panelIzquierdo.add(areaTurnoActual, BorderLayout.NORTH);
	
	areaJugadores = new JTextArea();
	areaJugadores.setBackground(BG_COLOR);
	areaJugadores.setForeground(Color.GREEN);
	areaJugadores.setFont(fuente);
	areaJugadores.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
	areaJugadores.setPreferredSize(new Dimension(0,150));
	areaJugadores.setEditable(false);
	panelIzquierdo.add(areaJugadores, BorderLayout.SOUTH);
	actualizarListaJugadores(controlador.getJugadores());
	
	
	txtAreaDeJuego = new JTextArea();
	txtAreaDeJuego.setBackground(BG_COLOR);
	txtAreaDeJuego.setForeground(Color.WHITE);
	txtAreaDeJuego.setFont(fuente);
	txtAreaDeJuego.setLineWrap(true);
	txtAreaDeJuego.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
	txtAreaDeJuego.append("Mi Area de Juego: \n\n");
	txtAreaDeJuego.setEditable(false);
	panelIzquierdo.add(txtAreaDeJuego, BorderLayout.CENTER);
	
	
	
	contentPane.add(panelIzquierdo, BorderLayout.WEST);
	
}






	private void inicializarPanelSur() {
		panelSur = new JPanel();
		panelSur.setLayout(new BorderLayout(0,0));
		panelSur.setBorder(new EmptyBorder(0,0,0,0));
		panelSur.setPreferredSize(new Dimension(ANCHO,30));
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		lineaEntrada = new JTextField();
		lineaEntrada.setBackground(BG_COLOR);
		lineaEntrada.setForeground(Color.WHITE);
		lineaEntrada.setCaretColor(Color.WHITE);
		lineaEntrada.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		lineaEntrada.setText("Escriba los comandos aqui...");
		lineaEntrada.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String texto = lineaEntrada.getText();
				procesarComando(texto);
				lineaEntrada.setText("");
			}
			
		});
		panelSur.add(lineaEntrada, BorderLayout.CENTER);
		
		JPanel padding = new JPanel();
		padding.setPreferredSize(new Dimension(ANCHO_PANEL_IZ, 30));
		padding.setBackground(BG_COLOR);
		padding.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.GRAY));
		panelSur.add(padding, BorderLayout.WEST);
	}





	private void inicializarPanelCentral() {
		panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		panelCentral.setBorder(new EmptyBorder(0,0,0,0));
		panelCentral.setPreferredSize(new Dimension(ANCHO,ALTO));
		contentPane.add(panelCentral, BorderLayout.CENTER);
		
		
		areaMesa = new JTextArea();
		areaMesa.setFont(fuente);
		areaMesa.setBackground(BG_COLOR);
		areaMesa.setEditable(false);
		areaMesa.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.GRAY));
		areaMesa.setForeground(Color.WHITE);
		areaMesa.setPreferredSize(new Dimension(ANCHO, 200));
		panelCentral.add(areaMesa, BorderLayout.NORTH);
		areaMesa.append("\n");
		
		areaTexto = new JTextArea();
		areaTexto.setFont(fuente);
		areaTexto.setBackground(BG_COLOR);
		areaTexto.setForeground(new Color(220,220,220));
		areaTexto.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.GRAY));
		areaTexto.setEditable(false);
		areaTexto.append("Bienvenido a la vista de consola del juego Parade!! \n "
				+ "A continuacion podra ver una lista con los comandos disponibles para jugar al Parade. \n "
				+ "Espero que disfrute jugando :D");
		mostrarListaComandos();
		
		scrollPane = new JScrollPane(areaTexto);
		scrollPane.setBorder(new EmptyBorder(0,0,0,0));
		scrollPane.getVerticalScrollBar().setBackground(Color.GRAY);
		panelCentral.add(scrollPane, BorderLayout.CENTER);
		
		
		
	}


	//funcion encargada de procesar los comandos ingresados en la linea de texto
	private void procesarComando(String lineaTexto) {
		String comando = "";
		ArrayList<String> args = new ArrayList<String>();
		//hago un parsing de la linea de texto ingresada 
		lineaTexto = lineaTexto.trim(); //quito los espacios del inicio y el final
		int indSiguienteArg = lineaTexto.lastIndexOf(" "); //el caracter por el cual voy a parsear es el espacio
		
		while (indSiguienteArg != -1) { //mientras haya espacios tomo la ultima cadena de texto y la guardo como argumento
			args.add(lineaTexto.substring(indSiguienteArg+1, lineaTexto.length()));
			lineaTexto = lineaTexto.substring(0, indSiguienteArg);
			lineaTexto = lineaTexto.trim();
			indSiguienteArg = lineaTexto.lastIndexOf(" ");
		}
		comando = lineaTexto; //cuando solo queda una cadena texto la guardo como el comando y ejecuto dicho comando con sus anteriores guardados argumentos
		
		switch(comando.toLowerCase()) {
		case "ayuda":
			mostrarListaComandos();
			return;
		case "clear":
			areaTexto.setText("");
			return;
		case "reglas":
			mostrarMensaje("Reglas del juego Parade: https://www.youtube.com/watch?v=xyQ0Blrrfus");
			return;
		case "ranking":
			mostrarRankingPuntaje(controlador.getRankingPuntaje());
			mostrarRankingGanadores(controlador.getRankingGanadores());
			return;
		}
		
		if(!jugando)
			switch(comando.toLowerCase()) {
			case "play":
				comenzarJuego();
				break;
			default:
				mostrarError("El comando \""+comando+"\" no se reconoce como un comando valido, para mas informacion ingrese \"ayuda\"");
				break;
			}
		else
			switch(comando.toLowerCase()) {
			case "tirar":
				tirarCarta(args);
				break;
			case "desc":
				descartar(args);
				break;
			case "area":
				getAreaJuego(args);
				break;
			default:
				mostrarError("El comando \""+comando+"\" no se reconoce como un comando valido, para mas informacion ingrese \"ayuda\"");
				break;
			}
		
	}


	//descarta una carta indicada en los argumentos
	private void descartar(ArrayList<String> args) {
		if(args.isEmpty() || args.size()!=2) { 
			mostrarError("Argumento no valido. Para mas informacion ingrese \"ayuda\"");
		}
		else
			try {
				IJugador miJugador = controlador.getMiJugador();
				int indC1 = Integer.valueOf(args.get(0));
				int indC2 = Integer.valueOf(args.get(1));
				if(indC1 < 0 || indC1 >= miJugador.getMano().getCartas().size() || indC2 < 0 || indC2 >= miJugador.getMano().getCartas().size() || indC1 == indC2) {
					mostrarError("Los indices ingresados no son validos");
				}else {
					controlador.descartar(miJugador.getMano().getCarta(indC1), miJugador.getMano().getCarta(indC2));	
					miJugador = controlador.getJugador(miJugador.getNombre());
				}
			}catch(Exception e) {
				e.printStackTrace();
				mostrarError("Argumento no valido. Para mas informacion ingrese \"ayuda\"");
			}
		
	}




	//recupera el area de juego del jugador cuyo nombre esta pasado por en los argumentos y la muestra por pantalla
	private void getAreaJuego(ArrayList<String> args) {
		if(args.isEmpty() || args.size()>1) 
			mostrarError("Argumento no valido. Para mas informacion ingrese \"ayuda\"");
		else {
			IJugador j = controlador.getJugador(args.get(0));  //recupero el jugador con el nombre pasado
			if (j == null) 
				mostrarError("No existe un jugador con ese nombre"); //si no existe lo informo
			else {
				mostrarAreaJuego(j);  //si existe muestro su area de juego por pantalla
			}
			
		}	
	}




	//puestra el area de juego del jugador j en pantalla
	private void mostrarAreaJuego(IJugador j) {
		areaTexto.append("\n-----     Area de juego de: "+j.getNombre()+"     -----");
		for(ColorCarta col: ColorCarta.values()) {
			areaTexto.append("\n" + col.toString() +": ");
			for(Carta car: j.getAreaJuego().getFilaCartas(col)) 
				areaTexto.append(String.valueOf(car.getNumero())+ " | ");	
		}
		areaTexto.append("\n---------------------------------------------------------------\n");
	}
	
	
	//actualiza el panel donde se encunetra el area de juego de miJugador
	private void actualizarMiAreaJuego() {
		IJugador miJugador = controlador.getMiJugador();
		txtAreaDeJuego.setText("Mi Area de Juego: ");
		if(miJugador != null) {
			for(ColorCarta col: ColorCarta.values()) {
				txtAreaDeJuego.append("\n\n" + col.toString() +": ");
				
				for(Carta car: miJugador.getAreaJuego().getFilaCartas(col)) 
					txtAreaDeJuego.append(String.valueOf(car.getNumero()) + " | ");	
			}	
		}
	}
		
	//comienza el juego
	private void comenzarJuego() {
		IJugador miJugador = controlador.getMiJugador();
		if(miJugador != null) {
			controlador.comenzarJuego();
		}
		else
			mostrarError("Para jugar primero debes ingresar tu nombre. Para mas informacion ingresa el comando \"ayuda\"");
		
	}




//	//registra a miJugador con el nombre pasado por parametros
//	private void registrarJugador(ArrayList<String> args) {
//		if(args.isEmpty() || args.size()>1) 
//			mostrarError("Argumento no valido. Para mas informacion ingrese \"ayuda\"");
//		else {
//			String nombre = args.get(0).trim();
//			if(nombre.length()>25 || nombre.length()<1) 
//				mostrarError("El nombre excede los limites de longitud (1-25)");
//			else {
//				Jugador j = new Jugador(nombre);
//				
//				if(miJugador == null) //si mi jugador es nulo no esta registrado, por lo tanto lo agrego directamente
//					if(controlador.agregarJugador(j)) 
//						miJugador = j;
//					else
//						mostrarError("No se ha podido crear el jugador, intente nuevamente");
//				else
//					//si no es nulo significa que si esta registrado, por lo tanto si agrego el nuevo jugador debo borrar el jugador viejo.
//					if(controlador.agregarJugador(j)) {
//						controlador.eliminarJugador(miJugador);
//						miJugador = j;
//					}
//					else
//						mostrarError("No se ha podido crear el jugador, intente nuevamente");
//						
//			}
//		}
//	}
//	
//	private void crearJugador(String nombre) {
//		Jugador j = new Jugador(nombre);
//		if(controlador.agregarJugador(j)) 
//			miJugador = j;
//		else
//			mostrarError("No se ha podido crear el jugador, intente nuevamente");
//	}



	//tira la carta que se encuntra en la posicion pasada por parametros
	private void tirarCarta(ArrayList<String> args) {
		if(args.isEmpty() || args.size()>1) 
			mostrarError("Argumento no valido. Para mas informacion ingrese \"ayuda\"");
		else
			try {
				IJugador miJugador = controlador.getMiJugador();
				int indice = Integer.valueOf(args.get(0));
				if(indice < 0 || indice > miJugador.getMano().getCartas().size()) {
					mostrarError("El indice ingresado exede los limites");
				}else {
					controlador.jugarCarta(miJugador.getMano().getCartas().get(indice));
					miJugador = controlador.getJugador(miJugador.getNombre());
				}	
			}catch(Exception e) {
				mostrarError("Argumento no valido. Para mas informacion ingrese \"ayuda\"");
			}
			
		
	}




	//imprime la lista de comandos por pantalla
	private void mostrarListaComandos() {
		mostrarMensaje("\n\nLista de comandos:\n"
				+ "\t\"clear\": limpia el area de texto\n\n"
				+ "\t\"reglas\": link al video de Youtube que contiene las reglas del juego.\n\n"
				+ "\t\"ayuda\": muestra la lista de comandos disponibles\n");
		
		if(!jugando)
			mostrarMensaje( "\t\"play\": comienza un juego nuevo con los jugadores registrados.\n");
		else
			mostrarMensaje("\t\"tirar <indice de la carta>\": juega la carta que corresponde con el indice ingresado (ej: \"tirar 3\")\n\n"
					+ "\t\"area <nombre del jugador>\": muestra por pantalla el area de juego del jugador de nombre <nombre del jugador>\n\n"
					+ "\t\"desc <indice carta1> <indice carta2>\": descarta las cartas que corresponden con los indices ingresados*\n"
					+ "\t\t*solo funciona en la etapa de descarte. Para mas info vea las reglas");

		
	}





	//actualiza el estado del carnaval y de mi mano
	private void actualizarAreaMesa(Carnaval carnaval) {
		IJugador miJugador = controlador.getMiJugador();
		//actualizo el carnaval
		areaMesa.setText("\n\n");
		String strCarnaval ="| Carnaval: ";
		for(Carta c: carnaval.getCartas()) {
			strCarnaval = strCarnaval.concat(c.getNumero() + " " + c.getColor().toString() + "|");
		}
		strCarnaval += " |";
		for(int i = 0; i<strCarnaval.length(); i++) {
			areaMesa.append("-");
		}
		areaMesa.append("\n"+ strCarnaval + "\n");
		for(int i = 0; i<strCarnaval.length(); i++) {
			areaMesa.append("-");
		}
		areaMesa.append("\n\n\n\n");
		
		if(miJugador != null) {
			//actualizo la mano de mi jugador
			areaMesa.append("Mi mano: ");
			int i = 0;
			for(Carta c: miJugador.getMano().getCartas()) {
				 areaMesa.append(" [IND "+i+"] = " + c.getNumero() + " " + c.getColor().toString() + " |");
				 i++;
			}
		}
			
		
	}
	
	//actualiza el nombre del jugador actual
	private void actualizarAreaTurno(String nombre) {
		areaTurnoActual.setText("Turno De: \n " + nombre);
	}
	
	//actualiza la lista de jugadores
	private void actualizarListaJugadores(ArrayList<IJugador> jugadores) {
		areaJugadores.setForeground(Color.CYAN);
		areaJugadores.setText("Vista del Jugador: \n");
		areaJugadores.append(controlador.getMiJugador().getNombre());
		areaJugadores.setForeground(Color.GREEN);
		areaJugadores.append("\nJugadores: \n");
		for(IJugador j: jugadores) {
			areaJugadores.append(j.getNombre() + "\n");
		}
	}
	
	
	
	private void mostrarRankingPuntaje(RankingPuntajes rankingPuntaje) {
		
		if(rankingPuntaje == null || rankingPuntaje.getTopPuntajes().isEmpty()) {
			mostrarMensaje("NO Existe un ranking de puntajes aun, luego de jugar una partida, se creara automaticamente");
			return;
		}
		System.out.println(rankingPuntaje.getTopPuntajes().size());
		areaTexto.append("\n\n-------------- RANKING PUNTAJE --------------\n");
		areaTexto.append("-- NOMBRE -- \t\t -- PUNTAJE --\n");
		for(EntidadJugador j: rankingPuntaje.getTopPuntajes())
			areaTexto.append(" " + j.getNombre() +" \t\t\t " + j.getPuntaje() + "\n");
		areaTexto.append("---------------------------------------------");
		
	}
	
	private void mostrarRankingGanadores(HistorialGanadores rankingGanadores) {
		
		if(rankingGanadores == null || rankingGanadores.getGanadoresHistoricos().isEmpty()) {
			mostrarMensaje("NO Existe un ranking de ganadores aun, luego de jugar una partida, se creara automaticamente");
			return;
		}
		System.out.println(rankingGanadores.getGanadoresHistoricos().size());
		areaTexto.append("\n\n-------------- RANKING GANADORES --------------\n");
		areaTexto.append("-- NOMBRE -- \t\t -- PARTIDAS GANADAS --\n");
		for(EntidadGanador j: rankingGanadores.getGanadoresHistoricos())
			areaTexto.append(" " + j.getNombre() +" \t\t\t " + j.getPartidasGanadas() + "\n");
		areaTexto.append("-----------------------------------------------");

	}


	
	

	@Override
	public void actualizarJugadoresConectados(ArrayList<IJugador> jugadores) {
		actualizarListaJugadores(jugadores);
	}



	@Override
	public void juegoIniciado(Carnaval carnaval) {
		actualizarAreaMesa(carnaval);
		actualizarMiAreaJuego();
		actualizarAreaTurno(controlador.getJugadorActual().getNombre());
		areaTexto.setText("");
		jugando = true;
		mostrarMensaje("Juego comenzado. Nueva lista de comandos");
		mostrarListaComandos();
		
	}




	@Override
	public void jugadaRealizada(Carnaval carnaval) {
		actualizarAreaMesa(carnaval);
		actualizarMiAreaJuego();
		actualizarAreaTurno(controlador.getJugadorActual().getNombre());
	}

	@Override
	public void ultimaRonda() {
		mostrarMensaje("** ESTA ES LA ULTIMA RONDA DE JUEGO **");
		
	}

	@Override
	public void finalDeJuego(IJugador ganador) {
		mostrarMensaje("** JUEGO FINALIZADO ** ");
		mostrarMensaje("EL GANADOR FUE... "+ ganador.getNombre()+ "!!!");
		mostrarMensaje("GRACIAS POR HABER JUGADO :D");
		
	}

	@Override
	public void cambioDeTurno(IJugador jugadorTurno) {
		actualizarAreaTurno(jugadorTurno.getNombre());
		
	}


	@Override
	public void mostrarError(String errorMsj) {
		areaTexto.append("\nError: " + errorMsj);
		
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		areaTexto.append("\n"+mensaje);
		
	}
	


	@Override
	public void inicioEtapaDescarte() {
		mostrarMensaje("Etapa de descarte... \n"
						+ "Debe elegir 2 cartas para descartarlas, las 2 restantes se sumaran a su area de juego\n"
						+ "para descartar una carta utilice el comando \"desc <indice de la carta>\"");
		
	}


	@Override
	public void descarteRealizado(Carnaval carnaval) {
		actualizarAreaMesa(carnaval);
		actualizarMiAreaJuego();
	}



	

}
