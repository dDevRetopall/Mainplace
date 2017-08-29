package productos;

import java.io.Serializable;

public class EditarProducto implements Serializable{
	private int id;
	private ProductoParaEditar p;

	public EditarProducto(int id, ProductoParaEditar p){
		this.id = id;
		this.p = p;
		
	}

	public int getId() {
		return id;
	}

	public ProductoParaEditar getProducto() {
		return p;
	}
	

}
