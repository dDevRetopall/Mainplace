package mensajes.commands;

import java.io.Serializable;

public class EmailCodeVerification implements Serializable{
	private String code;
	private boolean status;

	public EmailCodeVerification(String code,boolean status){
		this.code = code;
		this.status = status;
		
	}

	public String getCode() {
		return code;
	}
	public boolean getStatus() {
		return status;
	}
	
}
