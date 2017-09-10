package tools.datautils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.plaf.synth.SynthSpinnerUI;

import constantesLocalesServidor.ConstantesServer;

public class FileUtils {
	// Rehacer
	//// Settings
	
	static String prefix="FileUtils";
	static String nombreArchivo = "settings.ini";
	static File f = new File(nombreArchivo);
	static String[] keys = { "serverOperational", "requiredVersion", "requiredDaysToBeRecent",
			"timeInHoursToExpireProduct", "timeInMinutesToTransferProduct", "nameCooldownTransferationFile",
			"nameCooldownExpirationFile" };
	static String[] values = { "true", ConstantesServer.defaultRequiredVersionNonEditable, "3", "24", "1",
			"transferations.cd", "expirations.cd" };

	public static HashMap<String, String> loadSettings() {
		HashMap<String, String> settings = new HashMap<>();

		MessageUtils.logn("Settings loader started",prefix);
		MessageUtils.logn("Checking " + nombreArchivo,prefix);
		if (!f.exists()) {
			try {
				MessageUtils.logn("File doesnt exist. Creating new one with name " + nombreArchivo,prefix);
				f.createNewFile();
				createDefaultSettings();
			} catch (IOException e) {
				e.printStackTrace();
				return null;

			}
		} else {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				String linea;
				MessageUtils.logn("Loading settings",prefix);
				while ((linea = br.readLine()) != null) {
					String[] valores = linea.trim().replace(" ", "").split(":");
					settings.put(valores[0], valores[1]);

				}
				br.close();
			} catch (FileNotFoundException e) {
				MessageUtils.logn("Settings File not found",prefix);
				return null;

			} catch (IOException e) {
				MessageUtils.logn("Error while reading settings file",prefix);
				return null;
			}
		}
		MessageUtils.logn("Settings loaded",prefix);
		saveSettingsInConstants(settings);
		return settings;
	}

	public static void saveSettingsInConstants(HashMap<String, String> settings) {
		MessageUtils.logn("Saving settings in cache",prefix);
		MessageUtils.logn("Reading settings data",prefix);
		for (int i = 0; i < settings.size(); i++) {
			String value;
			if (settings.containsKey(keys[0])) {
				value = settings.get(keys[0]);
				ConstantesServer.serverOperational = Boolean.parseBoolean(value);
			}
			if (settings.containsKey(keys[1])) {
				value = settings.get(keys[1]);
				ConstantesServer.requiredVersion = value;
			}
			if (settings.containsKey(keys[2])) {
				value = settings.get(keys[2]);
				ConstantesServer.requiredDaysToBeRecent = Integer.parseInt(value);
			}
			if (settings.containsKey(keys[3])) {
				value = settings.get(keys[3]);
				ConstantesServer.timeToExpireProduct = Integer.parseInt(value);
			}
			if (settings.containsKey(keys[4])) {
				value = settings.get(keys[4]);
				ConstantesServer.timeToTransferProduct = Integer.parseInt(value);
			}
			if (settings.containsKey(keys[5])) {
				value = settings.get(keys[5]);
				ConstantesServer.nameCooldownTransferationFile = value;
			}
			if (settings.containsKey(keys[6])) {
				value = settings.get(keys[6]);
				ConstantesServer.nameCooldownExpirationFile = value;
			}
			

		}
		MessageUtils.logn("Settings successfuly saved in cache",prefix);
	}

	public static void clearFile() {
		PrintWriter writer;
		MessageUtils.logn("Clearing file",prefix);
		try {
			writer = new PrintWriter(f);
			writer.print("");
			writer.close();
			MessageUtils.logn("File cleared",prefix);
		} catch (FileNotFoundException e) {
			MessageUtils.logn("Error while clearing file",prefix);

		}

	}

	public static void resetSettings() {
		MessageUtils.logn("Resetting settings",prefix);
		clearFile();
		createDefaultSettings();
		MessageUtils.logn("Settings reset successfuly",prefix);
	}

	public static void createDefaultSettings() {
		MessageUtils.logn("Saving default settings",prefix);
		try {
			MessageUtils.logn("Writing settings",prefix);
			PrintWriter pw = new PrintWriter(new FileWriter(f));

			for (int i = 0; i < keys.length; i++) {
				pw.println(keys[i] + " : " + values[i]);
			}
			pw.close();
			MessageUtils.logn("Default settings successfuly saved",prefix);
		} catch (IOException e) {

			MessageUtils.logn("Error while saving settings",prefix);
		}
	}

	public static void saveSettings() {
		MessageUtils.logn("Saving settings",prefix);
		try {
			MessageUtils.logn("Writing settings",prefix);
			PrintWriter pw = new PrintWriter(new FileWriter(f));
			pw.println(keys[0]+" : "+ConstantesServer.serverOperational);
			pw.println(keys[1]+" : "+ConstantesServer.requiredVersion);
			pw.println(keys[2]+" : "+ConstantesServer.requiredDaysToBeRecent);
			pw.println(keys[3]+" : "+ConstantesServer.timeToExpireProduct);
			pw.println(keys[4]+" : "+ConstantesServer.timeToTransferProduct);
			pw.println(keys[5]+" : "+ConstantesServer.nameCooldownTransferationFile);
			pw.println(keys[6]+" : "+ConstantesServer.nameCooldownExpirationFile);
			
			pw.close();
			MessageUtils.logn("Settings successfuly saved",prefix);
		} catch (IOException e) {

			MessageUtils.logn("Error while saving settings",prefix);
		}
	}

}