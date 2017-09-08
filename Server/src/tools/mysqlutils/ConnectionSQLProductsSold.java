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

public class ConnectionSQLProductsSold {
	public boolean escribirMensajesEnConsola = false;
	private String usuario;

	private Connection con;
	public Statement st;

	public String tablaUsuarioProductosVendidos = /* NombreUsuario */"soldproducts";

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

		tablaUsuarioProductosVendidos = nombreUsuario + ConstantesServer.nombreTablaProductosVendidos;
	}

	public int configurarProfile(String nombreUsuario, int id, String nombreTablaProductosVendidos) {
		this.usuario = usuario;

		try {
			SQLConnection.escribirMensaje("Creando tabla de productos vendidos");

			st.executeUpdate("CREATE TABLE " + nombreTablaProductosVendidos + " (" + "id INT AUTO_INCREMENT, "
					+ "PRIMARY KEY(id), " + "productname VARCHAR(20), " + "infobreve VARCHAR(200), "
					+ "valoracionGeneral INTEGER, " + "valoracionProducto INTEGER, " + "valoracionServicio INTEGER, "
					+ "image BLOB, " + "views INTEGER, " + "soldtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
					+ "comentario VARCHAR(100) )");

			SQLConnection.escribirMensaje("TablaProductosVendidos creada");
			tablaUsuarioProductosVendidos = nombreTablaProductosVendidos;

		} catch (SQLException e) {
			SQLConnection.escribirMensaje("Error al tratar de crear la tabla de productos vendidos");
			e.printStackTrace();
		}

		return id;
	}



	public ArrayList<Producto> getProducts(Connection con, String username) {
		ArrayList<Producto> productos = new ArrayList<>();
		try {
			String query = "SELECT * FROM " + this.tablaUsuarioProductosVendidos;
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt("id");
				String productName = rs.getString("productname");
				String informacion = rs.getString("productname");
				int valoracionGeneral = rs.getInt("valoracionGeneral");
				int valoracionProducto = rs.getInt("valoracionProducto");
				int valoracionServicio = rs.getInt("valoracionServicio");
				int views = rs.getInt("views");
				Blob imageBlob = rs.getBlob("image");
				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				Timestamp date = rs.getTimestamp("soldtime");
				TimeCalendar t = new TimeCalendar(date);
				// add producto
				// productos.add(
				// new Producto(id, productName, precio, info, tipo, categoria, negociable,
				// imageBytes, status,t));

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
			String query = "SELECT * FROM " + this.tablaUsuarioProductosVendidos;

			// create the java statement

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				if (rs.getInt("id") == id) {
					String productName = rs.getString("productname");
					String informacion = rs.getString("productname");
					int valoracionGeneral = rs.getInt("valoracionGeneral");
					int valoracionProducto = rs.getInt("valoracionProducto");
					int valoracionServicio = rs.getInt("valoracionServicio");
					int views = rs.getInt("views");
					Blob imageBlob = rs.getBlob("image");
					byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
					Timestamp date = rs.getTimestamp("soldtime");
					TimeCalendar t = new TimeCalendar(date);

					// print the results

				}

			}
			MessageUtils.logn(
					"We couldnt find any sold products in " + this.tablaUsuarioProductosVendidos + " with de Id " + id);
			return null;

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return null;

	}

	public String getTablaUsuarioProductosVendidos() {
		return tablaUsuarioProductosVendidos;
	}

}
