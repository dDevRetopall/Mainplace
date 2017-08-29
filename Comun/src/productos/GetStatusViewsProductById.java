package productos;

import java.io.Serializable;

public class GetStatusViewsProductById implements Serializable{
	private int id;

	public GetStatusViewsProductById(int id){
		this.id = id;
		
	}

	public int getId() {
		return id;
	}
	
}
