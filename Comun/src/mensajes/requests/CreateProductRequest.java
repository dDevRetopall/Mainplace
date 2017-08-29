package mensajes.requests;

import java.awt.Image;
import java.io.Serializable;

public class CreateProductRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String info;
	private int tipo;
	private int categoria;
	private boolean negociable;
	private double price;
	private byte[] imageArray;

	public CreateProductRequest(String name,double price,String info,int tipo,int categoria,boolean negociable,byte[] imageArray){
		this.name = name;
		this.price = price;
		this.info = info;
		this.tipo = tipo;
		this.categoria = categoria;
		this.negociable = negociable;
		this.imageArray = imageArray;
		
		
	}

	public String getName() {
		return name;
	}

	public String getInfo() {
		return info;
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

	public double getPrice() {
		return price;
	}

	public byte[] getImage() {
		return imageArray;
	}
	
	
	

}
