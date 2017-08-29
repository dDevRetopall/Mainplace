package mensajes.requests;

import java.io.Serializable;

public class UserExistRequest implements Serializable {
	private String username;

	public UserExistRequest(String username){
		this.username = username;
		
	}

	public String getUsername() {
		return username;
	}
	
}
