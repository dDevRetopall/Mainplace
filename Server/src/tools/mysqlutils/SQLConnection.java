package tools.mysqlutils;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import search.searchEngine.ProductsSearch;
import tools.datautils.MessageUtils;

public class SQLConnection {
	private static String url;
	private static String usuario;
	private static String password;
	private static Connection con;
	private static Statement st;
	private static boolean escribirMensajesEnConsola = false;

	public static Connection connect() {
		con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			url = "jdbc:mysql://localhost/server";
			usuario = "root";
			password = "";

			escribirMensaje("Leyendo datos");

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		try {
			escribirMensaje("Tratando de conectar");
			con = DriverManager.getConnection(url, usuario, password);
			st = con.createStatement();
			escribirMensaje("Conexion creada correctamente a la base de datos MySQL");

		} catch (SQLException e) {
			escribirMensaje("No se ha podido conectar a la base de datos");
			System.err.println("Error al intentar conectarse a la base de datos");
			JOptionPane.showMessageDialog(null,
					"Error al intentar establecer conexion a la base de datos. Revisa tu conexion");
			System.exit(0);
			e.printStackTrace();
		}
		return con;

	}

	public static Connection getConnection() {
		return con;
	}

	public static void escribirMensaje(String mensaje) {
		String prefijo = "[MySQL] ";
		MessageUtils.logn(prefijo + mensaje);
		if (escribirMensajesEnConsola) {
			// MainServidor.escribirEnServidorMensajeCustom(prefijo+mensaje);

		}

	}

	public static ArrayList<ProductsSearch> getProductsForSearch(Connection con, String tableProductsName,
			String usuario) {
		ArrayList<ProductsSearch> productos = new ArrayList<>();
		try {
			String query = "SELECT * FROM " + tableProductsName;

			// create the java statement

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				// añadir id
				int id = rs.getInt("id");
				String productName = rs.getString("productname");
				double precio = rs.getDouble("precio");
				String info = rs.getString("infobreve");
				int categoria = rs.getInt("categoria");
				int tipo = rs.getInt("tipo");
				boolean negociable = rs.getBoolean("negociable");
				Blob imageBlob = rs.getBlob("image");
				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				int views = rs.getInt("views");
				int status = rs.getInt("status");
				productos.add(new ProductsSearch(usuario, id, productName, info, precio, categoria, tipo, negociable,
						imageBytes, views,status));

				// print the results

			}

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		
		return productos;

	}

	public static boolean addView(Connection con, String tableProductsName, String usuario, int idProducto) {
		int views = 0;
		try {
			String query = "SELECT * FROM " + tableProductsName;

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt("id");
				if (id == idProducto) {
					views = rs.getInt("views");
					views++;
					PreparedStatement ps = con.prepareStatement(
							"UPDATE " + tableProductsName + " SET views = ? WHERE id = " + idProducto);

					ps.setInt(1, views);
					ps.executeUpdate();
					ps.close();
					return true;
				}

			}

		} catch (Exception e) {
			
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return false;

	}
}
