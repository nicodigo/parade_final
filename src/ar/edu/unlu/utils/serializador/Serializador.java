package ar.edu.unlu.utils.serializador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializador {
	private String rutaArchivo;
	
	public Serializador(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
		File file = new File(rutaArchivo);
		try {
			if(file.createNewFile())
				escribirObjeto(null);
			file.setWritable(true);
			file.setReadable(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean escribirObjeto(Object obj) {
		boolean ret = false;
		
		try {
			FileOutputStream fileOPStream = new FileOutputStream(rutaArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fileOPStream);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			ret = true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("El archivo \""+rutaArchivo + "\" no existe pero ha sido creado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public Object leerObjeto() {
		Object ret = null;
		
		try {
			FileInputStream fileIPStream = new FileInputStream(rutaArchivo);
			ObjectInputStream oip = new ObjectInputStream(fileIPStream);
			ret = oip.readObject();
			oip.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
}
