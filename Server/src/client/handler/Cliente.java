package client.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import constantesLocalesServidor.ConstantesServer;
import mensajes.RegisterCommand;
import mensajes.RegisterRequest;
import tools.datautils.MessageUtils;
import tools.mysqlutils.ConnectionSQLUsuarios;

public class Cliente {

	private Socket s;
	private String ip;
	private boolean connected = false;
	private OutputStream os;
	private InputStream is;
	public ObjectOutputStream oos;
	public ObjectInputStream ois;

	public Cliente(Socket s) {
		this.s = s;
		ip = s.getInetAddress().getHostAddress();
		connected = true;
		try {
			os = s.getOutputStream();
			is = s.getInputStream();
			oos = new ObjectOutputStream(os);
			ois = new ObjectInputStream(is);
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}

		Thread obtenerMensajes = new Thread(new Runnable() {

			@Override
			public void run() {
				while (connected) {
					try {
						Object o = ois.readObject();
						MessageUtils.logn("Detectado un mensaje");
						if (o instanceof RegisterRequest) {
							RegisterRequest rr = (RegisterRequest) o;
							boolean respuesta = ConnectionSQLUsuarios.añadirUsuario(ConstantesServer.nombreTabla,
									rr.getUser(), rr.getPwd(), rr.getTelefono(), rr.getEmail());
							System.out.println("Respuesta: "+respuesta+"  "+rr.getUser()+"  "+rr.getPwd()+"  "+rr.getTelefono()+"  "+rr.getEmail());
							enviarRespuesta(respuesta);
						}
					} catch (ClassNotFoundException | IOException e) {
						System.out.println("Error 2");
						connected=false;
						e.printStackTrace();
					}
				}

			}
		});
		obtenerMensajes.start();

	}

	public void enviarRespuesta(boolean respuesta) {
		try {
			oos.writeObject(new RegisterCommand(respuesta));
		} catch (IOException e) {
			System.out.println("Error 3");
			e.printStackTrace();
		}
	}

}
