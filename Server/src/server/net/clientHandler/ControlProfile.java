package server.net.clientHandler;

import java.sql.Connection;
import java.util.ArrayList;

import constantesLocalesServidor.ConstantesServer;
import productos.Producto;
import productos.ProductoParaEditar;
import tools.datautils.MessageUtils;
import tools.mysqlutils.ConnectionSQLProducts;
import tools.mysqlutils.ConnectionSQLUsuarios;
import tools.mysqlutils.SQLConnection;

public class ControlProfile {
	public String username;
	public int idUsuario;
	public String password;
	public ControlProfile(){
		
	}
	public ConnectionSQLProducts setup(Connection con,String nombreUsuario,int id,String email,String nombreTablaProductos){
		ConnectionSQLProducts p = new ConnectionSQLProducts();
		p.inicializarConexion(con);
		this.idUsuario=p.configurarProfile(nombreUsuario, id, email,nombreTablaProductos);
	
		return p;
	}
	public ConnectionSQLProducts login(Connection con,String nombreUsuario,String password){
		this.username=nombreUsuario;
		this.password=password;
		this.idUsuario=ConnectionSQLUsuarios.getId(ConstantesServer.nombreTabla, nombreUsuario);
		if(idUsuario==0){
			MessageUtils.logn("Error obteniendo id usuario");
		}
		ConnectionSQLProducts p = new ConnectionSQLProducts();
		p.inicializarConexion(con);
		p.initVariables(nombreUsuario);
		return p;
	}
	public ArrayList<Producto> getProducts(ConnectionSQLProducts p,Connection con,String nombreUsuario){
		ArrayList<Producto>productos=p.getProducts(con, nombreUsuario);
		return productos;
	
	}
	public Producto getProduct(ConnectionSQLProducts p,Connection con,int id){
		Producto producto = p.getProductById(id );
		return producto;
	
	}
	public boolean editarProducto(ConnectionSQLProducts p,Connection con,int id,ProductoParaEditar producto){
		return p.editarProducto(con, id, producto.getName(), producto.getPrice(), producto.getInformacion(), producto.getTipo(), producto.getCategoria(), producto.isNegociable(),producto.getImageBytes());
	}
	public boolean eliminarProducto(ConnectionSQLProducts p,Connection con,int idProducto){
		return p.eliminarProducto(con, idProducto);
		
	}
	public boolean addView(String username,Connection con,int idProducto){
		Usuario u=ConnectionSQLUsuarios.returnUsuarioByName(ConstantesServer.nombreTabla, username);
		return SQLConnection.addView(con, u.getNombreTablaProductos(), u.getUsername(), idProducto);
		
	}
	public int getStatusById(int id,ConnectionSQLProducts c){
		int status =c.getStatusById(id);
		return status;
		
	}
	public int getViewsById(int id,ConnectionSQLProducts c){
		int views =c.getViewsById(id);
		return views;
		
	}
	public boolean updateStatus(int id,int status,ConnectionSQLProducts c,Connection con){
		boolean resultado =c.updateStatus(con, id, status);
		return resultado;
		
	}
}
