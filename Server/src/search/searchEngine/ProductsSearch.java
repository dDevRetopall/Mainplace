package search.searchEngine;

public class ProductsSearch {


	private String username;
	private int id;
	private String nameProduct;
	private double precio;
	private int categoria;
	private int tipo;
	private boolean negociable;
	private byte[] imageBytes;
	private String informacion;
	private int views;
	private int status;

	public ProductsSearch(String username,int id,String nameProduct,String informacion,double precio,int categoria,int tipo,boolean negociable,byte[]imageBytes,int views,int status){
		this.username = username;
		this.id = id;
		this.nameProduct = nameProduct;
		this.informacion = informacion;
		this.precio = precio;
		this.categoria = categoria;
		this.tipo = tipo;
		this.negociable = negociable;
		this.imageBytes = imageBytes;
		this.views = views;
		this.status = status;

		
	}
	
	public String getInformacion() {
		return informacion;
	}

	public String getUsername() {
		return username;
	}

	public int getId() {
		return id;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public double getPrecio() {
		return precio;
	}

	public int getCategoria() {
		return categoria;
	}

	public int getTipo() {
		return tipo;
	}

	public boolean isNegociable() {
		return negociable;
	}

	public byte[] getImageBytes() {
		return imageBytes;
	}

	public int getViews() {
		return views;
	}

	public int getStatus() {
		return status;
	}
	
	
	
	
}
