package server.main;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import constantes.NetConstants;
import constantesLocalesServidor.ConstantesServer;
import search.searchEngine.SearchHandler;
import server.net.clientHandler.Cliente;
import server.terminal.Console;
import tools.datautils.MessageUtils;
import tools.mysqlutils.ConnectionSQLUsuarios;
import tools.mysqlutils.SQLConnection;

public class Main {
	public static boolean connectionAlive = false;
	public static ServerSocket ss;
	public static Thread t;
	public static ArrayList<Cliente> clientes = new ArrayList<>();

	public static void main(String[] args) {

		MessageUtils.logn("Welcome to the server. Diego Berrocal. All rights reserved");
		// Console.startConsole();

		try {
			try {
				MessageUtils.logn("Server starting");
				MessageUtils.logn("Server starting with port "+NetConstants.PORT+" in the IP "+NetConstants.HOST);
				ss = new ServerSocket(NetConstants.PORT);
				Connection con = SQLConnection.connect();
				ConnectionSQLUsuarios.inicializarConexion(con);
				if (!ConnectionSQLUsuarios.existeTabla(ConstantesServer.nombreTabla)) {
					ConnectionSQLUsuarios.crearTablaUsuarios(ConstantesServer.nombreTabla);
				}
				MessageUtils.logn("Connection to SQL databse succesfully created");
				connectionAlive = true;

				MessageUtils.logn("Server started");
			} catch (BindException e) {
				MessageUtils.logn("There is already a server being executed with the port "+NetConstants.PORT);
				MessageUtils.logn("Stopping server ");
			}
		} catch (IOException e) {
			MessageUtils.logn("You don´t have connection to the Internet");
			e.printStackTrace();
		}

		t = new Thread(new Runnable() {

			@Override
			public void run() {

				while (connectionAlive) {
					acceptConexions();
				}

			}
		});

		t.start();

	}

	public static void acceptConexions() {
		try {
			Socket s = ss.accept();
			Main.clientes.add(new Cliente(s));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
