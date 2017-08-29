package mensajes.commands;

import java.io.Serializable;

public class LoginResponse implements Serializable {
	private boolean respuesta;
	private String mensajeError;
	private String username;

	public LoginResponse(boolean respuesta,String mensajeError,String username){
		this.respuesta = respuesta;
		this.mensajeError = mensajeError;
		this.username = username;
	
		
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
	
	

	
}
