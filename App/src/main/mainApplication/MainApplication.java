package main.mainApplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import cliente.net.connection.MainCliente;
import cliente.net.login.Launcher;
import cliente.net.register.RegisterActivity;
import products.findProducts.search.SearchWindow;
import products.myProducts.createProduct.CreateProductWindow;
import products.myProducts.editProduct.ProductEditWindow;
import products.myProducts.main.MyProductWindow;
import products.myProducts.preview.PreviewWindow;
import products.myProducts.updatestatus.UpdateStatusWindow;
import utils.dataUtils.MessageUtils;
import utils.emailUtils.Verification;

public class MainApplication {
	public static RegisterActivity ra;
	public static Launcher l;
	public static MyProductWindow pw;
	public static CreateProductWindow pww;
	public static ProductEditWindow pew;
	public static MainApplicationWindow maw;
	public static PreviewWindow pview;
	public static SearchWindow sw;
	public static UpdateStatusWindow usw;
	private static boolean successStablishingConnection=false;
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//First try to establish connection
		MessageUtils.logn("Establishing connection to the Server");
		boolean r3 = MainCliente.establecerConexion();
		if (r3) {
			MessageUtils.logn("Established conection");
			MessageUtils.logn("Connection to Server : Succesful");
			successStablishingConnection=true;
		} else {
			MessageUtils.logn("Connection to Server : Not connected");
			MessageUtils.logn("Connection to Server : Stopping application");
			MessageUtils.logn("Connection to Server : Connection to server failed");
		}
		if (!Constantes.DEBUG) {
			createLoginActivity(r3);
		} else {
			System.out.println("Debug Mode: ON");
			createLoginActivity(r3);
			MainCliente.sendLoginRequest("diego2", "diego2442.b");
		}
		Thread t = new Thread(new Runnable() {
			
			
			//Continue trying to establish connection
			@Override
			public void run() {
				while(!successStablishingConnection) {
					boolean r3 = MainCliente.establecerConexion();
					if(r3) {
						successStablishingConnection=true;
						MainCliente.requestStatusServer();
						createLoginActivity(true);
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		t.start();

	}

	public static void createRegisterActivity() {
		MessageUtils.logn("Starting register activity");
		ra = new RegisterActivity();
		ra.setVisible(true);
		MessageUtils.logn("Register acitivity initialized");

	}

	public static void createLoginActivity(boolean conectado) {
		MessageUtils.logn("Starting launcher");
		if (l != null) {
			l.dispose();
		}
		l = new Launcher(conectado);
		l.setVisible(true);
		MessageUtils.logn("Launcher initialized");
	}

	public static void closeActivities() {
		if (maw != null) {
			maw.setVisible(false);
		}
		if (pw != null) {
			pw.setVisible(false);
		}
		if (pww != null) {
			pww.setVisible(false);
		}
		if (pew != null) {
			pew.setVisible(false);
		}
		if (pview != null) {
			pview.setVisible(false);
		}
		if (sw != null) {
			sw.setVisible(false);
		}
		if (usw != null) {
			usw.setVisible(false);
		}
	}

	public static void openMainApplicationWindow(String username) {
		MessageUtils.logn("Starting MainApplication Window");
		maw = new MainApplicationWindow(username);
		maw.setVisible(true);
		MessageUtils.logn("MainApplication Window initialized");

	}

	public static void openProductWindow(boolean visible) {
		MessageUtils.logn("Starting product Window");
		pw = new MyProductWindow();
		if (visible) {
			pw.setVisible(true);
		}
		MessageUtils.logn("Product Window initialized");

	}

	public static void openNewProductWindow() {
		MessageUtils.logn("Starting new product Window");
		pww = new CreateProductWindow();
		pww.setVisible(true);
		MessageUtils.logn("New product window initialized");

	}

	public static void openEditProductWindow(int id, String producto, double price, String informacion,
			boolean negociable, int tipo, int categoria, Image general, Image foto) {
		MessageUtils.logn("Starting edit product Window");
		pew = new ProductEditWindow(id, producto, price, informacion, negociable, tipo, categoria, general, foto);
		pew.setVisible(true);
		MessageUtils.logn("Edit product window initialized");

	}

	public static void openPreview(int id) {
		MessageUtils.logn("Starting preview product Window");
		if (pview != null) {
			pview.dispose();
		}
		pview = new PreviewWindow(id);
		MessageUtils.logn("Preview product window initialized");
	}

	public static void openSearchWindow() {
		MessageUtils.logn("Starting search Window");
		sw = new SearchWindow();
		sw.setVisible(true);
		MessageUtils.logn("Search window initialized");
	}

	public static void updateStatusWindow(int id) {
		MessageUtils.logn("Starting preview product Window");
		if (usw != null) {
			usw.dispose();
		}
		usw = new UpdateStatusWindow(id);
		MessageUtils.logn("Preview product window initialized");
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
