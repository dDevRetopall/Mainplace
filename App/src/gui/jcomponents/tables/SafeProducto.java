package gui.jcomponents.tables;

import java.awt.Image;

import sharedUtils.TimeCalendar;

public class SafeProducto {
	private String usuario;
	private int idProducto;
	private String nameProducto;
	private String informacion;
	private boolean negociable;
	private int categoria;
	private int tipo;
	private double precio;
	private Image i;
	private byte[] imgbytes;
	private int status;
	private TimeCalendar createDate;

	public SafeProducto(String usuario, int idProducto, String nameProducto, String informacion, boolean negociable,
			int categoria, int tipo, double precio, Image i, byte[] imgbytes, int status,TimeCalendar createDate) {
		this.usuario = usuario;
		this.idProducto = idProducto;
		this.nameProducto = nameProducto;
		this.informacion = informacion;
		this.negociable = negociable;
		this.categoria = categoria;
		this.tipo = tipo;
		this.precio = precio;
		this.i = i;
		this.imgbytes = imgbytes;
		this.status = status;
		this.createDate = createDate;

	}

	public String getUsuario() {
		return usuario;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public String getNameProducto() {
		return nameProducto;
	}

	public String getInformacion() {
		return informacion;
	}

	public boolean isNegociable() {
		return negociable;
	}

	public int getCategoria() {
		return categoria;
	}

	public int getTipo() {
		return tipo;
	}

	public double getPrecio() {
		return precio;
	}

	public Image getImage() {
		return i;
	}

	public byte[] getImgbytes() {
		return imgbytes;
	}

	public int getStatus() {
		return status;
	}

	public TimeCalendar getCreateDate() {
		return createDate;
	}
	
	

}
