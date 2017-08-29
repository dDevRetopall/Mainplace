package mensajes.commands;

import java.io.Serializable;

public class RegisterCommand implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean registerCreated;

	public RegisterCommand(boolean respuesta){
		this.registerCreated = respuesta;
		
	}

	public boolean getRegisterResult() {
		return registerCreated;
	}
	
}
