package utils.threadUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import constantesLocalesServidor.ConstantesServer;
import server.main.Main;
import tools.datautils.MessageUtils;

public class CooldownLoader {
	static String prefix="Loader";
	// Solo si su continuing ==true
	// Escribir ejecutando limpieza de cooldowns en false
	public static boolean saveCooldownsTransferation(ArrayList<TransferationCoolDown> tc) {
		MessageUtils.logn("Saving transferations cooldowns",prefix);
		File f = new File(ConstantesServer.nameCooldownTransferationFile);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

		}
		try {
			MessageUtils.logn("Cleaning file",prefix);
			PrintWriter writer = new PrintWriter(f);
			writer.print("");

			MessageUtils.logn("Executing cleaning for non-continuingcooldowns",prefix);
			MessageUtils.logn("Saving cooldowns",prefix);
			for (TransferationCoolDown ttcc : Main.onCooldownProducts) {

				if (ttcc.continuing) {
					writer.println(ttcc.username + ";" + ttcc.idProducto + ";" + ttcc.contador + ";"
							+ ttcc.tablaProductos + ";" + ttcc.tablaProductosVendidos + ";" + ttcc.threadName);
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}

	public static ArrayList<TransferationCoolDown> readCooldownsTransferation() {
		ArrayList<TransferationCoolDown> transferations = new ArrayList<>();
		MessageUtils.logn("Loading transferations cooldowns",prefix);
		File f = new File(ConstantesServer.nameCooldownTransferationFile);
		BufferedReader br = null;
		try {

			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			
			} else {
				br = new BufferedReader(new FileReader(f));
				String s;
				try {
					MessageUtils.logn("Loading cooldowns",prefix);
					while ((s = br.readLine()) != null) {
						String[] argumentos = s.split(";");
						transferations.add(new TransferationCoolDown(argumentos[0], Integer.parseInt(argumentos[1]),
								argumentos[5], Integer.parseInt(argumentos[2]), argumentos[3], argumentos[4]));
					}

				} catch (IOException e) {
					e.printStackTrace();
					return null;

				}
				try {
					br.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			
				
				

			}
		
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
			return null;
		}
		return transferations;

	}
	public static boolean saveCooldownsExpiration(ArrayList<ExpirationCoolDown> ec) {
		MessageUtils.logn("Saving expiration cooldowns",prefix);
		File f = new File(ConstantesServer.nameCooldownExpirationFile);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

		}
		try {
			MessageUtils.logn("Cleaning file",prefix);
			PrintWriter writer = new PrintWriter(f);
			writer.print("");

			MessageUtils.logn("Executing cleaning for non-continuing cooldowns",prefix);
			MessageUtils.logn("Saving cooldowns",prefix);
			for (ExpirationCoolDown eecc : Main.onExpirationTimeProducts) {

				if (eecc.continuing) {
					writer.println(eecc.username + ";" + eecc.idProducto + ";" + eecc.contador + ";"
							+ eecc.tablaProductos + ";" + eecc.tablaProductosVendidos + ";" + eecc.threadName);
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}

	public static ArrayList<ExpirationCoolDown> readCooldownsExpiration() {
		ArrayList<ExpirationCoolDown> expirations = new ArrayList<>();
		MessageUtils.logn("Loading expiration cooldowns",prefix);
		File f = new File(ConstantesServer.nameCooldownExpirationFile);
		BufferedReader br = null;
		try {

			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
				
			
			} else {
				br = new BufferedReader(new FileReader(f));
				String s;
				try {
					MessageUtils.logn("Loading cooldowns",prefix);
					while ((s = br.readLine()) != null) {
						String[] argumentos = s.split(";");
						expirations.add(new ExpirationCoolDown(argumentos[0], Integer.parseInt(argumentos[1]),
								argumentos[5], Integer.parseInt(argumentos[2]), argumentos[3], argumentos[4]));
					}

				} catch (IOException e) {
					e.printStackTrace();
					return null;

				}
				try {
					br.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
				
			

			}
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
			return null;
		}
	
		return expirations;
	}
}
