package productos;

import java.io.Serializable;

public class AddViews implements Serializable {
	private int id;
	private String username;

	public AddViews(int idProducto,String username){
		this.id = idProducto;
		this.username = username;
		
	}

	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	
}
