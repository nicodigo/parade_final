package ar.edu.unlu.parade.vistas.log_in;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ar.edu.unlu.parade.controlador.Controlador;
import ar.edu.unlu.parade.modelo.Carnaval;
import ar.edu.unlu.parade.modelo.IJugador;
import ar.edu.unlu.parade.vistas.IVistaParade;
import ar.edu.unlu.parade.vistas.vistaGUI.ParadeVistaGUI;
import ar.edu.unlu.parade.vistas.vista_consola.ParadeVistaConsola;
import net.miginfocom.swing.MigLayout;

public class ParadeLogIn extends JFrame implements IVistaParade {

	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private JPanel contentPane;
	private JTextField campoUsuario;
	private JButton enviar;
	
	public ParadeLogIn(Controlador c) {
		this.controlador = c;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0,0, 500, 300);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controlador.desconectar();
			}
		});
		contentPane = new JPanel();
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][shrink, 100::400]"));
		contentPane.setBackground(new Color(12, 25, 40));
		setContentPane(contentPane);
		
		JPanel panelUsuario = new JPanel();
		panelUsuario.setLayout(new MigLayout("","[]","[]"));
		panelUsuario.setBackground(Color.WHITE);
		//panelUsuario.setPreferredSize(new Dimension(500, 200));
		
		JLabel lblUsuario = new JLabel("Ingrese su nombre: ", SwingConstants.CENTER);
		lblUsuario.setForeground(new Color(239,225,205));
		lblUsuario.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		panelUsuario.add(lblUsuario, "north, shrinky, growx");
		this.campoUsuario = new JTextField();
		campoUsuario.setBackground(new Color(239,225,205));
		campoUsuario.setForeground(Color.BLACK);
		campoUsuario.setCaretColor(Color.BLACK);
		panelUsuario.add(campoUsuario , "growx, h ::30, center");
		
		contentPane.add(panelUsuario, "grow, center,wrap");
		
		enviar = new JButton();
		enviar.setBackground(new Color(255,228,196));
		enviar.setForeground(Color.BLACK);
		enviar.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
		enviar.setText("Log In");
		contentPane.add(enviar, "center, h ::100");
		enviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				registrarJugador(campoUsuario.getText());
			}
			
		});
		
	}
	
	private void registrarJugador(String nombre) {
		if(nombre == null || nombre.length() < 1)
			mostrarError("Tiene que ingresar un nombre");
		else
			if(nombre.length() > 15)
				mostrarError("Longitud maxima: 15 caracteres");
				else
					if(controlador.registrarJugador(nombre)) {
						mostrarMensaje("Jugador registrado con Exito");
						IVistaParade vistaParade = new ParadeVistaGUI(controlador);
						vistaParade.inicializar();
						this.dispose();
					}
		
	}
	
	@Override
	public void inicializar() {
		this.setLocationRelativeTo(null);
		this.setFocusable(true);
		this.setVisible(true);
		
	}

	@Override
	public void actualizarJugadoresConectados(ArrayList<IJugador> jugadores) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void juegoIniciado(Carnaval carnaval) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizarCarnaval(Carnaval carnaval)  {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizarMiJugador(IJugador miJugador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ultimaRonda() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finalDeJuego(IJugador ganador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cambioDeTurno(IJugador jugadorTurno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarError(String errorMsj) {
		JOptionPane.showMessageDialog(null, errorMsj, "Error", JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, "Nuevo Mensaje", JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public void inicioEtapaDescarte() {
		// TODO Auto-generated method stub
		
	}

}
