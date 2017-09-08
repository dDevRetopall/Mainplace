package mensajes.commands;

import java.io.Serializable;

public class LogOutCommand implements Serializable{
	private boolean abrirLauncher;

	public LogOutCommand(boolean abrirLauncher) {
		this.abrirLauncher=abrirLauncher;
				
		
	}

	public boolean abrirLauncher() {
		return abrirLauncher;
	}
	
}
