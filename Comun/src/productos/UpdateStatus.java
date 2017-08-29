package productos;

import java.io.Serializable;

public class UpdateStatus implements Serializable {
	private int status;
	private int idProducto;

	public UpdateStatus(int status,int idProducto){
		this.status = status;
		this.idProducto = idProducto;
		
	}

	public int getStatus() {
		return status;
	}

	public int getIdProducto() {
		return idProducto;
	}
	
	
}
