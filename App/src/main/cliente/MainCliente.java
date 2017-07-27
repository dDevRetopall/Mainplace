package main.cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import constantes.Constantes;
import main.mainApplication.RegisterActivity;
import mensajes.RegisterCommand;
import mensajes.RegisterRequest;
import tools.datautils.MessageUtils;

public class MainCliente {

	private static Socket s;
	private static String ip;
	private static boolean connected = false;
	private static OutputStream os;
	private static InputStream is;
	public static ObjectOutputStream oos;
	public static ObjectInputStream ois;
	private static RegisterActivity ra;

	public static void establecerConexion(RegisterActivity ra) {
		MainCliente.ra = ra;
		try {
			Socket s = new Socket(Constantes.HOST, Constantes.PORT);
			MainCliente.s = s;
			MainCliente.initComponents();
			connected=true;
		} catch (UnknownHostException e) {
			MessageUtils.logn("No hay conexion a Internet o el servidor no está disponible");
			e.printStackTrace();
		} catch (IOException e) {
			MessageUtils.logn("Servidor no disponible");
			MessageUtils.logn("Error Cliente 1");
			e.printStackTrace();
		}
		
		Thread t = new Thread (new Runnable() {
			
			@Override
			public void run() {
				while(connected){
					try {
						MessageUtils.logn("Element received");
						Object o = ois.readObject();
						 if (o instanceof RegisterCommand) {
							RegisterCommand rc = (RegisterCommand) o;
							if(rc.getRegisterResult()){
								ra.registerCreated();
							}else{
								ra.registerError();
							}
							
						}
					} catch (ClassNotFoundException | IOException e) {
						MessageUtils.logn("Error Cliente 3");
						connected=false;
						e.printStackTrace();
					}
				}
				
			}
		});
		t.start();

	}

	public static void initComponents(){
		
		try {
			
			os = s.getOutputStream();
			is = s.getInputStream();
			ois = new ObjectInputStream(is);
			oos = new ObjectOutputStream(os);
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 2");
			e.printStackTrace();
		}
	}
	public static void createRegisterRequest(String username,String pwd,String telefono,String email){
		try {
			oos.writeObject(new RegisterRequest(username, pwd, email, telefono));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}
		
	}
}
