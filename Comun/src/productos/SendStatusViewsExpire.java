package productos;

import java.io.Serializable;

public class SendStatusViewsExpire implements Serializable{
	private int status;
	private int views;
	private int expirationTime;

	public SendStatusViewsExpire(int status,int views,int hoursExpiration){
		this.status = status;
		this.views = views;
		this.expirationTime= hoursExpiration;
		
	}

	public int getStatus() {
		return status;
	}

	public int getViews() {
		return views;
	}

	public int getExpirationTime() {
		return expirationTime;
	}
	
	
	
}
