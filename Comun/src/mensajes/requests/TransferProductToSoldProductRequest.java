package mensajes.requests;

import java.io.Serializable;

public class TransferProductToSoldProductRequest implements Serializable {
	private String username;
	private int idProducto;

	public TransferProductToSoldProductRequest(int idProducto) {
	
		this.idProducto = idProducto;
		
	}

	
	public int getIdProducto() {
		return idProducto;
	}
	
}
