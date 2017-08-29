package productos;

import java.io.Serializable;

public class InformacionProducto implements Serializable{
	private Producto p;

	public InformacionProducto(Producto p){
		this.p = p;
		
	}

	public Producto getProducto() {
		return p;
	}
	
}
