package mensajes;

import java.io.Serializable;

public class RegisterRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7861745029411823367L;
	private String user;
	private String pwd;
	private String email;
	private String telefono;

	public RegisterRequest(String user,String pwd,String email,String telefono){
		this.user = user;
		this.pwd = pwd;
		this.email = email;
		this.telefono = telefono;
		
	}

	public String getUser() {
		return user;
	}

	public String getPwd() {
		return pwd;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefono() {
		return telefono;
	}
	
}
