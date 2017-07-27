package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import client.handler.Cliente;
import constantes.Constantes;
import constantesLocalesServidor.ConstantesServer;
import tools.datautils.MessageUtils;
import tools.mysqlutils.ConnectionSQLUsuarios;

public class Main {
	static boolean connectionAlive = false;
	static ServerSocket ss;
	static ArrayList<Cliente> clientes = new ArrayList<>();
	public static void main(String[] args) {
		
		MessageUtils.logn("Welcome to the server. Diego Berrocal. All rights reserved");
		try {
			ss = new ServerSocket(Constantes.PORT);
			ConnectionSQLUsuarios.connect();
			if(!ConnectionSQLUsuarios.existeTabla(ConstantesServer.nombreTabla)){
			ConnectionSQLUsuarios.crearTablaUsuarios(ConstantesServer.nombreTabla);
			}
			MessageUtils.logn("Connection to SQL databse succesfully created");
			connectionAlive=true;
			MessageUtils.logn("Servidor ejecutandose");
		} catch (IOException e) {
			MessageUtils.logn("You don´t have connection to the Internet");
			e.printStackTrace();
		}
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(connectionAlive){
					acceptConexions();
				}
				
			}
		});
		t.start();

	}
	public static void acceptConexions(){
		try {
			Socket s = ss.accept();
			clientes.add(new Cliente(s));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
