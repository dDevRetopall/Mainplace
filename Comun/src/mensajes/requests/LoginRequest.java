package mensajes.requests;

import java.io.Serializable;

public class LoginRequest implements  Serializable{
	
	private String pwd;
	private String username;
	private String version;
	public LoginRequest(String username,String pwd){
		this.username = username;
		this.pwd = pwd;
	}
	public LoginRequest(String username,String pwd,String version){
		this.username = username;
		this.pwd = pwd;
		this.version = version;
	}
	
	
	public String getPwd() {
		return pwd;
	}
	public String getUsername() {
		return username;
	}
	public String getVersion() {
		return version;
	}
	
	
}
