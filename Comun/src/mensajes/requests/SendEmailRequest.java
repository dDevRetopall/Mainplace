package mensajes.requests;

import java.io.Serializable;

public class SendEmailRequest implements Serializable{
	private String email;
	private String username;

	public SendEmailRequest(String email,String username){
		this.email = email;
		this.username = username;
		
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}
	
}
