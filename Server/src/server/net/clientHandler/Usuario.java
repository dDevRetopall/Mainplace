package server.net.clientHandler;

public class Usuario {
	private int id;
	private String username;
	private String nombreTablaProductos;

	public Usuario(int id,String username,String nombreTablaProductos){
		this.id = id;
		this.username = username;
		this.nombreTablaProductos = nombreTablaProductos;
		
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getNombreTablaProductos() {
		return nombreTablaProductos;
	}
	
}
