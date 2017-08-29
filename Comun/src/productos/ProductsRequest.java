package productos;

import java.io.Serializable;

public class ProductsRequest implements Serializable {
	private String username;

	public ProductsRequest(String username){
		this.username = username;
		
	}

	public String getUsername() {
		return username;
	}
	
}
