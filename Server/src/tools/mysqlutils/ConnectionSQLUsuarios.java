package tools.mysqlutils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import server.net.clientHandler.Usuario;
import tools.datautils.EncriptarPasswords;
import tools.datautils.MessageUtils;

public class ConnectionSQLUsuarios {
	public static boolean escribirMensajesEnConsola = false;
	private static String url;
	private static String usuario;
	private static String password;
	private static Connection con;
	private static Statement st;

	public static void inicializarConexion(Connection con) {

		try {
			ConnectionSQLUsuarios.con = con;
			st = con.createStatement();
			SQLConnection.escribirMensaje("Conexion para la tabla de usuarios establecida");
		} catch (SQLException e) {
			SQLConnection.escribirMensaje("Error al tratar de inicializar la conexion a la tabla de usuarios");
			e.printStackTrace();
		}
	}

	public static void crearTablaUsuarios(String nombreTabla) {

		try {

			if (!existeTabla(nombreTabla)) {
				st.executeUpdate("CREATE TABLE " + nombreTabla + " (" + "id INT AUTO_INCREMENT, " + "PRIMARY KEY(id), "
						+ "usuario VARCHAR(20), " + "password VARCHAR(50), " + "phone VARCHAR(50), "
						+ "email VARCHAR(50), " + "rango VARCHAR(10), productstable VARCHAR(20))");

				SQLConnection.escribirMensaje("Tabla de usuarios creada");

			} else {
				SQLConnection.escribirMensaje("No se ha creado la tabla porque ya existe");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ArrayList<String> getUsuariosDeMySQL(Connection con, String nombreTabla) {
		ResultSet rs;

		ArrayList<String> datos = new ArrayList<>();
		SQLConnection.escribirMensaje("Cogiendo informacion de la base de datos para sincronizar con el archivo.");
		try {
			rs = st.executeQuery("select * from " + nombreTabla);
			String user, pwd, rango;
			int posicion = 0;
			while (rs.next()) {
				user = rs.getString(2);
				datos.add(user);
				posicion++;
				pwd = rs.getString(3);
				datos.add(pwd);
				posicion++;
				posicion++;
				posicion++;
				posicion++;
			}
			SQLConnection.escribirMensaje("Datos cogidos correctamente.");
		} catch (SQLException e1) {
			SQLConnection.escribirMensaje("Error mientras se intentaban coger los datos.");
			e1.printStackTrace();
		}
		return datos;

	}

	public static boolean existeTabla(String nombreTabla) {
		boolean existe = false;

		ResultSet rs;

		try {
			DatabaseMetaData dbmd = con.getMetaData();
			rs = dbmd.getTables(null, null, null, null);

			while (rs.next()) {
				String tabla = rs.getObject(3).toString();
				if (tabla.equals(nombreTabla)) {
					existe = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return existe;
	}

	public static boolean aņadirUsuario(String nombreTabla, String username, String password, String rango) {
		String passwordEncriptada = EncriptarPasswords.encriptarPassword(password);
		try {
			st.executeUpdate("INSERT INTO " + nombreTabla + " (" + "usuario, " + "password, " + "rango" + ")"
					+ "VALUES (" + "'" + username + "','" + passwordEncriptada + "','" + rango + "')");
			SQLConnection.escribirMensaje(
					"Se ha insertado el usuario " + username + " con su password encriptada " + passwordEncriptada);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean aņadirUsuario(String nombreTabla, String username, String password, String telefono,
			String email, String nombreTableProductos) {
		String passwordEncriptada = EncriptarPasswords.encriptarPassword(password);
		try {

			st.executeUpdate("INSERT INTO " + nombreTabla + " (" + "usuario, " + "password, " + "phone, " + "email, "
					+ "productstable)" + "VALUES (" + "'" + username + "','" + passwordEncriptada + "','" + telefono
					+ "','" + email + "','" + nombreTableProductos + "')");
			SQLConnection.escribirMensaje(
					"Se ha insertado el usuario " + username + " con su password encriptada " + passwordEncriptada);

			return true;
		} catch (SQLException e) {

			e.printStackTrace();
			SQLConnection.escribirMensaje("Error while trying to create the account for " + username);
			return false;
		}

	}

	public static boolean consultarUsuario(String nombreTabla, String username, String password) {
		String passwordEncriptada = EncriptarPasswords.encriptarPassword(password);

		String user, pwd, email;
		try {
			ResultSet rs = st.executeQuery("select * from " + nombreTabla);
			while (rs.next()) {
				user = rs.getString(2);
				pwd = rs.getString(3);
				email = rs.getString(5);
				System.out.println(pwd + "  ->  " + passwordEncriptada);
				if ((user.equals(username) || email.equals(username)) && pwd.equals(passwordEncriptada)) {
					SQLConnection.escribirMensaje("Usuario aceptado " + username);
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnection.escribirMensaje("Usuario incorrecto " + username);
		return false;

	}

	public static boolean existeUsuario(String nombreTabla, String username) {

		String user;
		try {
			ResultSet rs = st.executeQuery("select * from " + nombreTabla);
			while (rs.next()) {
				user = rs.getString(2);
				if (user.equals(username)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		SQLConnection.escribirMensaje("Usuario no encontrado: " + username);
		return false;

	}

	public static Integer getId(String nombreTabla, String username) {

		String user;
		try {
			ResultSet rs = st.executeQuery("select * from " + nombreTabla);
			while (rs.next()) {
				user = rs.getString(2);
				if (user.equals(username)) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		SQLConnection.escribirMensaje("Usuario no encontrado: " + username);
		return 0;

	}

	public static boolean emailInscrito(String nombreTabla, String email) {

		String pemail;
		try {
			ResultSet rs = st.executeQuery("select * from " + nombreTabla);
			while (rs.next()) {
				pemail = rs.getString(5);
				if (email.equals(pemail)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		SQLConnection.escribirMensaje("Email no existe: " + email);
		return false;

	}

	public static String devolverRangoUsuario(String nombreTabla, String username) {

		String rank, user;
		try {
			ResultSet rs = st.executeQuery("select * from " + nombreTabla);
			while (rs.next()) {
				user = rs.getString(2);
				if (username.equals(user)) {
					rank = rs.getString(4);
					return rank;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static ArrayList<Usuario> returnUsuarios(String nombreTabla) {
		ArrayList<Usuario>usuarios =new ArrayList<>();
		String  user,nombreTablaProductos;
		int id;
		try {
			ResultSet rs = st.executeQuery("select * from " + nombreTabla);
			while (rs.next()) {
				user = rs.getString(2);
				id= rs.getInt(1);
				nombreTablaProductos=rs.getString(7);
				usuarios.add(new Usuario(id,user,nombreTablaProductos));
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarios;
		
	}
	public static Usuario returnUsuarioByName(String nombreTabla,String usuario) {
		
		String  user,nombreTablaProductos,phone,email;
		int id;
		try {
			ResultSet rs = st.executeQuery("select * from " + nombreTabla);
			while (rs.next()) {
				user = rs.getString(2);
				if(user.equals(usuario)){
				
				id= rs.getInt(1);
				phone = rs.getString(4);
				email = rs.getString(5);
				nombreTablaProductos=rs.getString(7);
				return new Usuario(id,user,nombreTablaProductos,phone,email);
				}
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("No se ha encontrado el usuario");
		return null;
		
	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}
