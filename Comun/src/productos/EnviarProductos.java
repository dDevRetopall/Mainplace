package productos;

import java.io.Serializable;
import java.util.ArrayList;

public class EnviarProductos implements Serializable{
	private ArrayList<Producto> productos;

	public EnviarProductos(ArrayList<Producto>productos){
		this.productos = productos;
		
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}
	
}
