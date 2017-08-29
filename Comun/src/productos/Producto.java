package productos;

import java.io.Serializable;

public class Producto implements Serializable{
	private String name;
	private double price;
	private String informacion;
	private int tipo;
	private int categoria;
	private boolean negociable;
	private int id;
	private byte[] imageBytes;
	private int status;


	public Producto(int id,String name,double price,String informacion,int tipo,int categoria,boolean negociable,byte[]imageBytes,int status){
		this.id = id;
		this.name = name;
		this.price = price;
		this.informacion = informacion;
		this.tipo = tipo;
		this.categoria = categoria;
		this.negociable = negociable;
		this.imageBytes = imageBytes;
		this.status = status;
		
		
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

	public int getStatus() {
		return status;
	}
	


	
	
	
}
