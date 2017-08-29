package mensajes.commands;

import java.io.Serializable;

public class UserResponse implements Serializable{
	private boolean respuesta;

	public UserResponse(boolean respuesta){
		this.respuesta = respuesta;
		
	}

	public boolean getUserExistResult() {
		return respuesta;
	}
}
