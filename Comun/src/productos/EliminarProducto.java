package productos;

import java.io.Serializable;

public class EliminarProducto implements Serializable{
	private int id;

	public EliminarProducto(int id){
		this.id = id;
		
	}

	public int getId() {
		return id;
	}
	

}
