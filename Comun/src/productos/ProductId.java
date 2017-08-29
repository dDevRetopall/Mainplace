package productos;

import java.io.Serializable;

public class ProductId implements Serializable{
	private int id;

	public ProductId(int id){
		this.id = id;
		
	}
	public int getId(){
		return id;
	}
}
