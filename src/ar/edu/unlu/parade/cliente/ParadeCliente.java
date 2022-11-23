package ar.edu.unlu.parade.cliente;

import java.awt.EventQueue;
import java.rmi.RemoteException; 
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ar.edu.unlu.parade.controlador.Controlador;

import ar.edu.unlu.parade.vistas.IVistaParade;
import ar.edu.unlu.parade.vistas.log_in.ParadeLogIn;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;

public class ParadeCliente {
	
	public static void main(String[] args) {
		ArrayList<String> ips = Util.getIpDisponibles();
		String ip = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la que escuchar� peticiones el cliente", "IP del cliente", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				ips.toArray(),
				null
		);
		String port = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que escuchar� peticiones el cliente", "Puerto del cliente", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				9999
		);
		String ipServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la corre el servidor", "IP del servidor", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				null,
				"127.0.0.1"
		);
		String portServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que corre el servidor", "Puerto del servidor", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				8888
		);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Controlador controlador = new Controlador();
				Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
				try {
					c.iniciar(controlador);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RMIMVCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				IVistaParade vistaParade = new ParadeLogIn(controlador);
				vistaParade.inicializar();
			}
		});
		System.out.println("final");
		
		
	}
		
}
