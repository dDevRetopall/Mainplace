package tools.mysqlutils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import constantesLocalesServidor.ConstantesServer;
import productos.Producto;
import search.searchEngine.ProductsSearch;
import sharedUtils.TimeCalendar;
import tools.datautils.EncriptarPasswords;
import tools.datautils.MessageUtils;

public class ConnectionSQLProducts {
	public boolean escribirMensajesEnConsola = false;
	private String usuario;
	private Connection con;
	public Statement st;

	public String tablaUsuarioProductos = /* NombreUsuario */"products";
	public String tablaUsuarioValoraciones = /* NombreUsuario */"valorations";
	public String tablaUsuarioLocalizacion = /* NombreUsuario */"locations";
	public String tablaUsuarioProfile = /* NombreUsuario */"profile";

	public void inicializarConexion(Connection con) {

		try {
			this.con = con;
			st = con.createStatement();
			SQLConnection.escribirMensaje("Conexion para la tabla de usuarios establecida");
		} catch (SQLException e) {
			SQLConnection.escribirMensaje("Error al tratar de inicializar la conexion a la tabla de usuarios");
			e.printStackTrace();
		}
	}

	public void initVariables(String nombreUsuario) {

		tablaUsuarioProductos = nombreUsuario + ConstantesServer.nombreTablaProductos;
	}

	public int configurarProfile(String nombreUsuario, int id, String nombreTablaProductos) {
		this.usuario = usuario;
		// try {
		// SQLConnection.escribirMensaje("Creando tabla de informacionProfile");
		//
		// st.executeUpdate("CREATE TABLE "+ nombreUsuario+tablaProfile+" (" +
		// "id INT(10), " + "PRIMARY KEY(id), "
		// + "usuario VARCHAR(20), " + "email VARCHAR(50), " + "productos
		// VARCHAR(50), "
		// +"null1 VARCHAR(50), " + "null2 VARCHAR(10))");
		//
		// SQLConnection.escribirMensaje("InformacionProfile tabla creada");
		//
		// tablaUsuarioProfile=nombreUsuario+tablaProfile;
		//
		// } catch (SQLException e) {
		// SQLConnection.escribirMensaje("Error al tratar de crear la tabla
		// informacionProfile");
		// e.printStackTrace();
		// }
		try {
			SQLConnection.escribirMensaje("Creando tabla de productos");

			st.executeUpdate("CREATE TABLE " + nombreTablaProductos + " (" + "id INT AUTO_INCREMENT, "
					+ "PRIMARY KEY(id), " + "productname VARCHAR(20), " + "precio DOUBLE PRECISION, "
					+ "infobreve VARCHAR(200), " + "categoria INTEGER, " + "tipo INTEGER, " + "negociable BOOL, "
					+ "image BLOB, " + "views INTEGER, " + "status INTEGER, "
					+ "creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
					+ "closedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

			SQLConnection.escribirMensaje("TablaProductos creada");
			tablaUsuarioProductos = nombreTablaProductos;

		} catch (SQLException e) {
			SQLConnection.escribirMensaje("Error al tratar de crear la tablaProductos");
			e.printStackTrace();
		}

		// try {
		// SQLConnection.escribirMensaje("Creando tabla de valoraciones");
		//
		// st.executeUpdate("CREATE TABLE "+ nombreUsuario+tablaValoraciones+"
		// (" + "idProducto INT(10), " + "PRIMARY KEY(idProducto), "
		// + "valoracionUser VARCHAR(20), " + "comentario VARCHAR(50), " +
		// "puntuacion VARCHAR(50), "
		// +"null1 VARCHAR(50), " + "null2 VARCHAR(10))");
		//
		// SQLConnection.escribirMensaje("Tabla de valoraciones creada");
		// tablaUsuarioValoraciones=nombreUsuario+tablaValoraciones;
		//
		//
		// } catch (SQLException e) {
		// SQLConnection.escribirMensaje("Error al tratar de crear la tabla de
		// valoraciones");
		// e.printStackTrace();
		// }
		//
		// try {
		// SQLConnection.escribirMensaje("Creando localizaciones de venta");
		//
		// st.executeUpdate("CREATE TABLE "+ nombreUsuario+tablaLocalizacion+"
		// (" + "id INT AUTO_INCREMENT, " + "PRIMARY KEY(id), "
		// + "calle VARCHAR(20), " + "comentario VARCHAR(50), " +
		// "informacionExtra VARCHAR(50), "
		// +"null1 VARCHAR(50), " + "null2 VARCHAR(10))");
		//
		// SQLConnection.escribirMensaje("Tabla de localizaciones tabla
		// creada");
		// tablaUsuarioLocalizacion=nombreUsuario+tablaLocalizacion;
		//
		//
		// } catch (SQLException e) {
		// SQLConnection.escribirMensaje("Error al tratar de crear la tabla de
		// localizaciones");
		// e.printStackTrace();
		// }
		return id;
	}

	public int insertarProducto(Connection con, String nombre, double price, String info, int tipo, int categoria,
			boolean negociable, byte[] imageInByte) {
		String[] generatedId = { "id" };
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(imageInByte);
			PreparedStatement ps = con
					.prepareStatement(
							"INSERT INTO " + this.tablaUsuarioProductos + " (" + "productname, " + "precio, "
									+ "infobreve, " + "categoria, " + "tipo, " + "negociable, " + "image, " + "views, "
									+ "status," + "creationDate)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
							generatedId);
			ps.setString(1, nombre);
			ps.setDouble(2, price);
			ps.setString(3, info);
			ps.setInt(4, categoria);
			ps.setInt(5, tipo);
			ps.setBoolean(6, negociable);
			ps.setBinaryStream(7, bais);
			ps.setInt(8, 0);
			ps.setInt(9, 0);
			ps.setTimestamp(10, date);
			int result = ps.executeUpdate();
			if (result > 0) {

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					return (int) rs.getLong(1);
				}

			}
			ps.close();
			

			SQLConnection.escribirMensaje("Se ha insertado el producto " + nombre);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Got an exception! ");

		}

		return -1;
	}

	public boolean eliminarProducto(Connection con, int idProducto) {
		try {
			String query = "DELETE FROM " + this.tablaUsuarioProductos + " WHERE id = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, idProducto);
			preparedStmt.execute();
			return true;
		} catch (Exception e) {
			System.err.println("Got an exception! ");
		}
		return false;
	}

	public boolean editarProducto(Connection con, int id, String nombre, double precio, String info, int tipo,
			int categoria, boolean negociable, byte[] imageInByte) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(imageInByte);
			PreparedStatement ps = con.prepareStatement("UPDATE " + this.tablaUsuarioProductos
					+ " SET productname = ?, precio = ?,infobreve = ?, categoria = ?, tipo = ?, negociable = ?, image = ? WHERE id = "
					+ id);

			ps.setString(1, nombre);
			ps.setDouble(2, precio);
			ps.setString(3, info);
			ps.setInt(4, categoria);
			ps.setInt(5, tipo);
			ps.setBoolean(6, negociable);
			ps.setBinaryStream(7, bais);
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (Exception e) {

			System.out.println("We got an exception");

		}
		return false;
	}

	public boolean updateStatus(Connection con, int id, int status) {
		try {

			PreparedStatement ps = con
					.prepareStatement("UPDATE " + this.tablaUsuarioProductos + " SET status = ? WHERE id = " + id);

			ps.setInt(1, status);

			ps.executeUpdate();
			ps.close();
			return true;
		} catch (Exception e) {

			System.out.println("We got an exception");

		}
		return false;
	}

	public ArrayList<Producto> getProducts(Connection con, String username) {
		ArrayList<Producto> productos = new ArrayList<>();
		try {
			String query = "SELECT * FROM " + this.tablaUsuarioProductos;

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
				int status = rs.getInt("status");
				Timestamp date = rs.getTimestamp("creationDate");
				TimeCalendar t = new TimeCalendar(date);
				productos.add(new Producto(id, productName, precio, info, tipo, categoria, negociable, imageBytes,
						status, t));

				// print the results

			}

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return productos;

	}

	public Producto getProductById(int id) {
		try {
			String query = "SELECT * FROM " + this.tablaUsuarioProductos;

			// create the java statement

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				if (rs.getInt("id") == id) {
					String productName = rs.getString("productname");
					double precio = rs.getDouble("precio");
					String info = rs.getString("infobreve");
					int categoria = rs.getInt("categoria");
					int tipo = rs.getInt("tipo");
					boolean negociable = rs.getBoolean("negociable");
					Blob imageBlob = rs.getBlob("image");
					byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
					int status = rs.getInt("status");
					Timestamp date = rs.getTimestamp("creationDate");
					TimeCalendar t = new TimeCalendar(date);
					return new Producto(id, productName, precio, info, tipo, categoria, negociable, imageBytes, status,
							t);

					// print the results

				}

			}
			MessageUtils.logn("We couldnt find any products in " + this.tablaUsuarioProductos + " with de Id " + id);
			return null;

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return null;

	}

	public int getStatusById(int id) {
		try {
			String query = "SELECT * FROM " + this.tablaUsuarioProductos;

			// create the java statement

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				if (rs.getInt("id") == id) {

					int status = rs.getInt("status");
					return status;

					// print the results

				}

			}
			MessageUtils.logn("We couldnt find any products in " + this.tablaUsuarioProductos + " with de Id " + id
					+ " for searching the status");
			return -1;

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return -1;
	}

	public int getViewsById(int id) {
		try {
			String query = "SELECT * FROM " + this.tablaUsuarioProductos;

			// create the java statement

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				if (rs.getInt("id") == id) {

					int views = rs.getInt("views");
					return views;

					// print the results

				}

			}
			MessageUtils.logn("We couldnt find any products in " + this.tablaUsuarioProductos + " with de Id " + id
					+ " for searching the views");
			return -1;

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return -1;
	}

	public String getTablaUsuarioProductos() {
		return tablaUsuarioProductos;
	}

	public String getTablaUsuarioValoraciones() {
		return tablaUsuarioValoraciones;
	}

	public String getTablaUsuarioLocalizacion() {
		return tablaUsuarioLocalizacion;
	}

	public String getTablaUsuarioProfile() {
		return tablaUsuarioProfile;
	}

}
