package server.net.clientHandler;

public class Usuario {
	private int id;
	private String username;
	private String nombreTablaProductos;
	private String email;
	private String phone;

	public Usuario(int id,String username,String nombreTablaProductos,String phone,String email){
		this.id = id;
		this.username = username;
		this.nombreTablaProductos = nombreTablaProductos;
		this.phone = phone;
		this.email = email;
		
	}
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

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}
	
	
}
