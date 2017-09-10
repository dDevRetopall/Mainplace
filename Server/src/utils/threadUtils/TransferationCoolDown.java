package utils.threadUtils;

import java.util.ArrayList;

import constantesLocalesServidor.ConstantesServer;

import productos.Producto;
import server.main.Main;
import server.net.clientHandler.Cliente;
import tools.datautils.MessageUtils;
import tools.mysqlutils.SQLConnection;

public class TransferationCoolDown implements Runnable {
	public Thread t;
	
	boolean suspended = false;
	int contador = ConstantesServer.timeToTransferProduct;
	int segundos = 0;
	int minutos = 0;
	public boolean continuing = true;

	public String threadName;
	public int idProducto;
	public int minutoEnMillis = 60000;
	public String username;
	public String tablaProductos;
	public String tablaProductosVendidos;

	public TransferationCoolDown(String username, int idProducto,String tablaProductos, String tablaProductosVenidos, String name) {
		this.idProducto = idProducto;
		this.threadName = name;
		this.tablaProductos = tablaProductos;
		this.username=username;
		this.tablaProductosVendidos = tablaProductosVenidos;
	}

	public TransferationCoolDown(String username, int idProducto, String name, int timeInHoursToTransfer,
			String tablaProductos, String tablaProductosVenidos) {
		this.username = username;
		this.idProducto = idProducto;
		this.threadName = name;
		this.contador = timeInHoursToTransfer;
		this.tablaProductos = tablaProductos;
		this.tablaProductosVendidos = tablaProductosVenidos;
	}

	public void run() {
		while (continuing) {

			try {
				Thread.sleep(minutoEnMillis);
			} catch (InterruptedException e) {
				System.out.println("Error thread sleep");
				e.printStackTrace();
			}
			contador--;
			if (contador == 0) {
				continuing=false;
				boolean resultado2 = SQLConnection.transferProductToSoldProductTable(username, idProducto, tablaProductos,
						tablaProductosVendidos);
				for (int i = 0; i < Main.onExpirationTimeProducts.size(); i++) {
					if (Main.onExpirationTimeProducts.get(i).idProducto == idProducto) {
						Main.onExpirationTimeProducts.get(i).continuing = false;
						Main.onExpirationTimeProducts.remove(i);

					}
				}
				if (!resultado2) {
					MessageUtils.logn("Error trying to transfer the product with productID " + idProducto
							+ " to soldProducts table, " + "from the user " + username);
				} else {
					MessageUtils.logn("El producto ID " + idProducto + " de " + username + " ha sido transferido");

				}
			
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
}
