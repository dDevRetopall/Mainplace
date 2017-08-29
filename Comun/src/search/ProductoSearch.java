package search;


import java.io.Serializable;

public class ProductoSearch implements Serializable{
	private String name;
	private double price;
	private String informacion;
	private int tipo;
	private int categoria;
	private boolean negociable;
	private int id;
	private byte[] imageBytes;
	private String owner;
	private int views;
	private int status;


	public ProductoSearch(String owner,int id,String name,double price,String informacion,int tipo,int categoria,boolean negociable,byte[]imageBytes,int views,int status){
		this.owner = owner;
		this.id = id;
		this.name = name;
		this.price = price;

		this.informacion = informacion;
		this.tipo = tipo;
		this.categoria = categoria;
		this.negociable = negociable;
		this.imageBytes = imageBytes;
		this.views = views;
		this.status = status;
		
	}
	
	public String getOwner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getInformacion() {
		return informacion;
	}

	public int getTipo() {
		return tipo;
	}

	public int getCategoria() {
		return categoria;
	}

	public boolean isNegociable() {
		return negociable;
	}

	public int getId() {
		return id;
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
