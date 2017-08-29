package productos;

import java.io.Serializable;

public class SendStatusViews implements Serializable{
	private int status;
	private int views;

	public SendStatusViews(int status,int views){
		this.status = status;
		this.views = views;
		
	}

	public int getStatus() {
		return status;
	}

	public int getViews() {
		return views;
	}
	
	
}
