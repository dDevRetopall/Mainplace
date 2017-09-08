package mensajes.commands;

import java.io.Serializable;

public class StatusServerReponse implements Serializable{
	private int idStatusServer;

	public StatusServerReponse(int idStatusServer) {
		this.idStatusServer = idStatusServer;
		
	}

	public int getStatusServer() {
		return idStatusServer;
	}
	
}
