package utils.threadUtils;

import java.util.ArrayList;

import constantesLocalesServidor.ConstantesServer;

import productos.Producto;
import server.net.clientHandler.Cliente;
import tools.datautils.MessageUtils;
import tools.mysqlutils.SQLConnection;

public class ExpirationCoolDown implements Runnable {
	public Thread t;
	public String threadName;
	boolean suspended = false;
	int contador = ConstantesServer.timeToExpireProduct;
	int segundos = 0;
	int minutos = 0;
	public boolean continuing = true;

	public int idProducto;
	public int horaEnMillis = 3600000;
	public String username;
	public String tablaProductos;
	public String tablaProductosVendidos;

	public ExpirationCoolDown(String username, int idProducto, String tablaProductos,
			String tablaProductosVenidos,String name) {
		this.username = username;
		this.idProducto = idProducto;
		this.threadName = name;
		this.tablaProductos = tablaProductos;
		this.tablaProductosVendidos = tablaProductosVenidos;

	}

	public ExpirationCoolDown(String username, int idProducto,  String name, int timeInHoursToExpire,
			String tablaProductos, String tablaProductosVenidos) {
		this.username = username;
		this.idProducto = idProducto;
		this.threadName = name;
		this.contador = timeInHoursToExpire;
		this.tablaProductos = tablaProductos;
		this.tablaProductosVendidos = tablaProductosVenidos;
	}

	public void run() {
		while (continuing) {

			try {
				Thread.sleep(horaEnMillis);
			} catch (InterruptedException e) {
				System.out.println("Error thread sleep");
				e.printStackTrace();
			}
			contador--;
			if (contador == 0) {
				
				boolean resultado=SQLConnection.eliminarProducto(SQLConnection.getConnection(), idProducto, tablaProductos);
				if(resultado) {
					MessageUtils.logn("Product eliminado por expiracion correctamente con "+"ID: "+idProducto+" from username "+ username);
				}else {
					MessageUtils.logn("Error eliminado el producto por expiracion con "+"ID: "+idProducto+" from username "+ username);
				}
				continuing=false;
			}

			synchronized (this) {
				while (suspended) {
					try {
						wait();
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			}

		}

	}

	public void start() {

		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	public void stop() {

	}

	public void suspend() {
		suspended = true;
	}

	public synchronized void resume() {
		suspended = false;
		notify();
	}

	public int getHoursLeft() {
		return contador;
	}
}
