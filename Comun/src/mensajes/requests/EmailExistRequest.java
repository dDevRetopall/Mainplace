package mensajes.requests;

import java.io.Serializable;

public class EmailExistRequest implements Serializable {
	private String email;

	public EmailExistRequest(String email) {
		this.email=email;
	}

	public String getEmail() {
		return email;
	}
	
}
