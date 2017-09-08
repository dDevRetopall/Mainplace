package search.searchEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import constantesLocalesServidor.ConstantesServer;
import server.main.Main;
import server.net.clientHandler.Cliente;
import server.net.clientHandler.Usuario;
import sharedUtils.TimeCalendar;
import tools.mysqlutils.ConnectionSQLProducts;
import tools.mysqlutils.ConnectionSQLUsuarios;
import tools.mysqlutils.SQLConnection;

public class SearchHandler {

	public static ArrayList<ProductsSearch> getCoincidenciasProductos(Cliente c, String key) {
		ArrayList<ProductsSearch> coincidencias = new ArrayList<>();
		ArrayList<Usuario> usuarios = ConnectionSQLUsuarios.returnUsuarios(ConstantesServer.nombreTabla);

		for (Usuario u : usuarios) {

			ArrayList<ProductsSearch> productos = SQLConnection.getProductsFromUser(SQLConnection.getConnection(),
					u.getNombreTablaProductos(), u.getUsername());
			for (ProductsSearch ps : productos) {

				new SearchEngine(c, ps, ps.getNameProduct(), key, u);

			}
		}
		return coincidencias;
	}

	public static ArrayList<ProductsSearch> searchRecents() {
		ArrayList<ProductsSearch> recents = new ArrayList<>();
		ArrayList<Usuario> usuarios = ConnectionSQLUsuarios.returnUsuarios(ConstantesServer.nombreTabla);

		for (Usuario u : usuarios) {

			ArrayList<ProductsSearch> productos = SQLConnection.getProductsFromUser(SQLConnection.getConnection(),
					u.getNombreTablaProductos(), u.getUsername());
			for (ProductsSearch ps : productos) {
				TimeCalendar t = new TimeCalendar();
				int daysActual = t.getTotalDaysOfYear();
				int daysCreated = ps.getTime().getTotalDaysOfYear();
				
				if (daysActual - daysCreated <= ConstantesServer.requiredDaysToBeRecent) {
					recents.add(ps);
				}
			}
		}
		return recents;
	}

}
