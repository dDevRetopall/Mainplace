package server.net.clientHandler;

import java.sql.Connection;
import java.util.ArrayList;

import constantesLocalesServidor.ConstantesServer;
import productos.Producto;
import productos.ProductoParaEditar;
import tools.datautils.MessageUtils;
import tools.mysqlutils.ConnectionSQLProducts;
import tools.mysqlutils.ConnectionSQLProductsSold;
import tools.mysqlutils.ConnectionSQLUsuarios;
import tools.mysqlutils.SQLConnection;

public class ControlProfile {
	public String username;
	public int idUsuario;
	public String password;
	public String email;
	public String phone;

	public ControlProfile() {

	}

	public ConnectionSQLProducts setupProducts(Connection con, String nombreUsuario, int id, String email,
			String nombreTablaProductos) {
		ConnectionSQLProducts p = new ConnectionSQLProducts();
		p.inicializarConexion(con);
		this.idUsuario = p.configurarProfile(nombreUsuario, id, nombreTablaProductos);

		return p;
	}

	public ConnectionSQLProductsSold setupProductsSold(Connection con, String nombreUsuario, int id,
			String nombreTablaProductosSold) {
		ConnectionSQLProductsSold ps = new ConnectionSQLProductsSold();
		ps.inicializarConexion(con);
		this.idUsuario = ps.configurarProfile(nombreUsuario, id, nombreTablaProductosSold);

		return ps;
	}

	public void login(Connection con, String nombreUsuario, String password, String email,
			String phone) {
		this.username = nombreUsuario;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.idUsuario = ConnectionSQLUsuarios.getId(ConstantesServer.nombreTabla, nombreUsuario);
		if (idUsuario == 0) {
			MessageUtils.logn("Error obteniendo id usuario");
		}
		ConnectionSQLProducts p = new ConnectionSQLProducts();
		p.inicializarConexion(con);
		p.initVariables(nombreUsuario);
	

	}
	public ConnectionSQLProducts initializeProductsTable(Connection con,String username) {
		ConnectionSQLProducts p = new ConnectionSQLProducts();
		p.inicializarConexion(con);
		p.initVariables(username);
		return p;
	}
	public ConnectionSQLProductsSold initializeSoldProductsTable(Connection con,String username) {
		ConnectionSQLProductsSold ps = new ConnectionSQLProductsSold();
		ps.inicializarConexion(con);
		ps.initVariables(username);
		return ps;
	}

	public ArrayList<Producto> getProducts(ConnectionSQLProducts p, Connection con, String nombreUsuario) {
		ArrayList<Producto> productos = p.getProducts(con, nombreUsuario);
		return productos;

	}

	public Producto getProduct(ConnectionSQLProducts p, Connection con, int id) {
		Producto producto = p.getProductById(id);
		return producto;

	}

	public boolean editarProducto(ConnectionSQLProducts p, Connection con, int id, ProductoParaEditar producto) {
		return p.editarProducto(con, id, producto.getName(), producto.getPrice(), producto.getInformacion(),
				producto.getTipo(), producto.getCategoria(), producto.isNegociable(), producto.getImageBytes());
	}

	public boolean eliminarProducto(ConnectionSQLProducts p, Connection con, int idProducto) {
		return p.eliminarProducto(con, idProducto);

	}

	public boolean addView(String username, Connection con, int idProducto) {
		Usuario u = ConnectionSQLUsuarios.returnUsuarioByName(ConstantesServer.nombreTabla, username);
		return SQLConnection.addView(con, u.getNombreTablaProductos(), u.getUsername(), idProducto);

	}

	public int getStatusById(int id, ConnectionSQLProducts c) {
		int status = c.getStatusById(id);
		return status;

	}

	public int getViewsById(int id, ConnectionSQLProducts c) {
		int views = c.getViewsById(id);
		return views;

	}

	public boolean updateStatus(int id, int status, ConnectionSQLProducts c, Connection con) {
		boolean resultado = c.updateStatus(con, id, status);
		return resultado;

	}

}
