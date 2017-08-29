package mensajes.commands;

import java.io.Serializable;

public class EmailResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4340032637424888958L;
	private boolean email;

	public EmailResponse(boolean email){
		this.email = email;
		
	}

	public boolean getEmailExistResult() {
		return email;
	}
}
