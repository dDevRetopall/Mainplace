package findProducts.search;

public class View {
	private String username;
	private int idProducto;

	public View(String username,int idProducto){
		this.username = username;
		this.idProducto = idProducto;
		
	}

	public String getUsername() {
		return username;
	}

	public int getIdProducto() {
		return idProducto;
	}
	
}
