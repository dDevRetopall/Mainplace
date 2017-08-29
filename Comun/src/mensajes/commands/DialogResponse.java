package mensajes.commands;

import java.io.Serializable;

public class DialogResponse implements Serializable{
	private String mensaje;
	private String titulo;

	public DialogResponse(String mensaje,String titulo){
		this.mensaje = mensaje;
		this.titulo = titulo;
		
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getTitulo() {
		return titulo;
	}
	
}
