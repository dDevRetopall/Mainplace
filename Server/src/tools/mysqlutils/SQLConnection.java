package tools.mysqlutils;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import productos.Producto;
import search.ProductoSearch;
import search.searchEngine.ProductsSearch;
import sharedUtils.TimeCalendar;
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

	public static ArrayList<ProductsSearch> getProductsFromUser(Connection con, String tableProductsName,
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
				Timestamp date = rs.getTimestamp("creationDate");
				TimeCalendar t = new TimeCalendar(date);
				productos.add(new ProductsSearch(usuario, id, productName, info, precio, categoria, tipo, negociable,
						imageBytes, views, status, t));

				// print the results

			}

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}

		return productos;

	}

	public static ProductsSearch getProductsFromIdUser(Connection con, String tableProductsName, String usuario,
			int id) {
	
		try {
			String query = "SELECT * FROM " + tableProductsName;

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				int idPosible = rs.getInt("id");
				if (id == idPosible) {
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
					Timestamp date = rs.getTimestamp("creationDate");
					TimeCalendar t = new TimeCalendar(date);
					return (new ProductsSearch(usuario, id, productName, info, precio, categoria, tipo, negociable,
							imageBytes, views, status, t));

				}

			}

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}

		return null;
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

	public static boolean eliminarProducto(Connection con, int idProducto, String tablaProductos) {
		try {
			String query = "DELETE FROM " + tablaProductos + " WHERE id = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, idProducto);
			preparedStmt.execute();
			return true;
		} catch (Exception e) {
			System.err.println("Got an exception! ");
		}
		return false;
	}

	public static boolean insertarProductoVendido(Connection con, String tablaProductsVendidos, String nombre,
			String informacion, byte[] imageInByte, int views) {

		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(imageInByte);
			PreparedStatement ps = con.prepareStatement("INSERT INTO " + tablaProductsVendidos + " (" + "productname, "
					+ "infobreve, " + "image, " + "views, " + "soldtime)" + "VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, nombre);
			ps.setString(2, informacion);
			ps.setBinaryStream(3, bais);
			ps.setInt(4, views);
			ps.setTimestamp(5, date);

			ps.execute();
			ps.close();
			SQLConnection.escribirMensaje("Se ha insertado el producto vendido " + nombre);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Got an exception! ");

		}

		return false;
	}

	public static boolean transferProductToSoldProductTable(String usuario, int idProducto, String tablaProductos,
			String tablaProductsVendidos) {
		ProductsSearch ps = SQLConnection.getProductsFromIdUser(SQLConnection.getConnection(), tablaProductos, usuario,
				idProducto);
		boolean resultado = SQLConnection.eliminarProducto(SQLConnection.getConnection(), idProducto, tablaProductos);
		if (resultado) {
			boolean resultado2 = SQLConnection.insertarProductoVendido(SQLConnection.getConnection(),
					tablaProductsVendidos, ps.getNameProduct(), ps.getInformacion(), ps.getImageBytes(), ps.getViews());
			if (resultado2) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
