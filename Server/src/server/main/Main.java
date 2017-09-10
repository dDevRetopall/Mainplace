package server.main;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import constantes.NetConstants;
import constantesLocalesServidor.ConstantesServer;
import search.searchEngine.SearchHandler;
import server.net.clientHandler.Cliente;
import server.terminal.Console;
import server.window.MainWindow;
import tools.datautils.FileUtils;
import tools.datautils.MessageUtils;
import tools.mysqlutils.ConnectionSQLUsuarios;
import tools.mysqlutils.SQLConnection;
import utils.threadUtils.CooldownLoader;
import utils.threadUtils.ExpirationCoolDown;
import utils.threadUtils.TransferationCoolDown;

public class Main {
	public static boolean connectionAlive = false;
	public static ServerSocket ss;
	public static Thread t;
	public static ArrayList<Cliente> clientes = new ArrayList<>();
	public static ArrayList<TransferationCoolDown> onCooldownProducts = new ArrayList<>();
	public static ArrayList<ExpirationCoolDown> onExpirationTimeProducts = new ArrayList<>();
	public static MainWindow mw;
	public static boolean aceptarConexiones = true;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mw = new MainWindow();
		MessageUtils.logn("Welcome to the server. Diego Berrocal. All rights reserved");
		MessageUtils.logn("Loading cooldowns from users. " + "When the server is offline the cooldown stop");

		FileUtils.loadSettings();
		ArrayList<TransferationCoolDown> temporalTransferationCooldowns = CooldownLoader.readCooldownsTransferation();
		if (temporalTransferationCooldowns != null) {
			for (TransferationCoolDown tc : temporalTransferationCooldowns) {
				onCooldownProducts.add(tc);
				tc.start();
			}
			MessageUtils.logn("Transferation cooldowns loaded");
		} else {
			MessageUtils.logn("Error while loading transferation cooldowns");
			MessageUtils.logn("Stopping server");
			System.exit(0);
		}
		ArrayList<ExpirationCoolDown> temporalExpirationCooldowns = CooldownLoader.readCooldownsExpiration();
		if (temporalExpirationCooldowns != null) {
			for (ExpirationCoolDown ec : temporalExpirationCooldowns) {
				onExpirationTimeProducts.add(ec);
				ec.start();
			}
			MessageUtils.logn("Expiration cooldowns loaded");
		} else {
			MessageUtils.logn("Error while loading expiration cooldowns");
			MessageUtils.logn("Stopping server");
			System.exit(0);
		}

		// Console.startConsole();

		try {
			try {
				MessageUtils.logn("Server starting");
				MessageUtils.logn("Server starting with port " + NetConstants.PORT + " in the IP " + NetConstants.HOST);
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
				MessageUtils.logn("There is already a server being executed with the port " + NetConstants.PORT);
				MessageUtils.logn("Stopping server ");
				System.exit(0);
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
		mw.setVisible(true);

	}

	public static void acceptConexions() {
		try {
			Socket s = ss.accept();
			Main.clientes.add(new Cliente(s));
			MessageUtils.logn("Nueva conexion aceptada");
		} catch (IOException e) {
			connectionAlive = false;
			MessageUtils.logn("Servidor cerrado en error de acceptConnections");
		}
	}

	public static void actualizarStatusServerEnClientes() {
		int idStatusServer = Main.getIdOperationServer();
		for (Cliente c : Main.clientes) {
			if (!c.online) {
				c.sendStatusServer(idStatusServer);
			}

		}
	}

	public static int getIdOperationServer() {
		int idServerStatus = 0;
		if (!ConstantesServer.serverOperational) {
			idServerStatus = 1;

		}
		if (Main.mw.getSchedulerWindow() != null && Main.mw.getSchedulerWindow().getCountdownWindow() != null) {
			if (Main.mw.getSchedulerWindow().getCountdownWindow().isStop() == false) {
				idServerStatus = 2;
			}
		}
		if (!aceptarConexiones) {

			idServerStatus = 4;

		}
		return idServerStatus;
	}

	public static void enviarWarningATodos(String mensaje) {

		for (Cliente c : Main.clientes) {
			if (c.online) {

				c.enviarDialogResponse(mensaje, "Scheduled maintenance");
			}

		}
	}
	public static int getPersonasConectadas() {
		int contador=0;
		for (Cliente c : Main.clientes) {
			if (c.online) {

				contador++;
			}

		}
		return contador;
	}


	public static void logoutAllAccounts(String mensaje, boolean openLauncher) {

		if (ConstantesServer.serverOperational) {
			mw.l2.setText("AVAILABLE");
			mw.l2.setForeground(new Color(0, 153, 0));

		} else {
			mw.l2.setText("NOT AVAILABLE");
			mw.l2.setForeground(new Color(204, 0, 0));
		}
		for (Cliente c : Main.clientes) {
			if (c.online) {

				c.enviarDialogResponse(mensaje, "Scheduled maintenance");
				c.logOutAccount(openLauncher);
			}
		}
	}

	public static final String SUN_JAVA_COMMAND = "sun.java.command";

	/**
	 * Restart the current Java application
	 * 
	 * @param runBeforeRestart
	 *            some custom code to be run before restarting
	 * @throws IOException
	 */
	public static void restartApplication(Runnable runBeforeRestart) throws IOException {
		try {
			// java binary
			String java = System.getProperty("java.home") + "/bin/java";
			// vm arguments
			List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
			StringBuffer vmArgsOneLine = new StringBuffer();
			for (String arg : vmArguments) {
				// if it's the agent argument : we ignore it otherwise the
				// address of the old application and the new one will be in conflict
				if (!arg.contains("-agentlib")) {
					vmArgsOneLine.append(arg);
					vmArgsOneLine.append(" ");
				}
			}
			// init the command to execute, add the vm args
			final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);

			// program main and program arguments
			String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");
			// program main is a jar
			if (mainCommand[0].endsWith(".jar")) {
				// if it's a jar, add -jar mainJar
				cmd.append("-jar " + new File(mainCommand[0]).getPath());
			} else {
				// else it's a .class, add the classpath and mainClass
				cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
			}
			// finally add program arguments
			for (int i = 1; i < mainCommand.length; i++) {
				cmd.append(" ");
				cmd.append(mainCommand[i]);
			}
			System.out.println(cmd.toString());
			// execute the command in a shutdown hook, to be sure that all the
			// resources have been disposed before restarting the application
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					try {
						Runtime.getRuntime().exec(cmd.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			// execute some custom code before restarting
			if (runBeforeRestart != null) {
				runBeforeRestart.run();
			}
			// exit
			System.exit(0);
		} catch (Exception e) {
			// something went wrong
			throw new IOException("Error while trying to restart the application", e);
		}
	}

}
