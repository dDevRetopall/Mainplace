package mensajes.commands;

import java.io.Serializable;

public class LoginResponse implements Serializable {
	private boolean respuesta;
	private String mensajeError;
	private String username;
	private String phone;
	private String email;

	public LoginResponse(boolean respuesta,String mensajeError,String username,String phone,String email){
		this.respuesta = respuesta;
		this.mensajeError = mensajeError;
		this.username = username;
		this.phone = phone;
		this.email = email;
	
		
	}
	public LoginResponse(boolean respuesta,String mensajeError,String username){
		this.respuesta = respuesta;
		this.mensajeError = mensajeError;
		this.username = username;
	
	
		
	}
	public LoginResponse(boolean respuesta,String username,String phone,String email){
		this.respuesta = respuesta;
		this.username = username;
		this.phone = phone;
		this.email = email;
	
		
	}

	public boolean getRespuesta() {
		return respuesta;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public String getUsername() {
		return username;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}
	
	
	

	
}
