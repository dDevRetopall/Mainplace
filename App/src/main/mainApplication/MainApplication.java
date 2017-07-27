package main.mainApplication;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import email.utils.Verification;
import main.cliente.MainCliente;
import main.launcher.Launcher;
import tools.datautils.MessageUtils;
import tools.mysqlutils.ConnectionSQLUsuarios;

public class MainApplication {

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
	
		ConnectionSQLUsuarios.connect();
		ConnectionSQLUsuarios.crearTablaUsuarios(Constantes.tablaUsuarios);
		Launcher l = new Launcher();
		l.setVisible(true);
//		App gui = new App();
//		gui.setVisible(true);
////		RegisterActivity ra = new RegisterActivity();
////		ra.setVisible(true);
////		MessageUtils.logn("GUI initialized");
//		
//		MainCliente.establecerConexion(ra);
//		MessageUtils.logn("Established conection");
		

	}

}
