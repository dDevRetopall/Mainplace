package search.searchEngine;

import java.util.ArrayList;

import productos.Producto;
import search.ProductoSearch;
import server.net.clientHandler.Cliente;
import server.net.clientHandler.Usuario;
import tools.mysqlutils.SQLConnection;

public class SearchEngine {
	String posibilidad;
	String modelo;
	ArrayList<Character> repeatedCharacters;
	int strike = /* numero seguidas */0;
	boolean repetidoChar = false;
	int strikeActual = 0;
	boolean encontrada = false;
	int posicionStrike = 0;
	int contadorChar = 0;

	public SearchEngine(Cliente c, ProductsSearch ps, String key, String posible, Usuario u) {
		double similarity = search(key, posible);
		if (similarity > 0.4f) {
			System.out.println(ps.getId() + ps.getNameProduct() + ps.getUsername());
			System.out.println("Coincidencia encontrada");

			c.enviarCoincidenciarSearch(new ProductoSearch(ps.getUsername(), ps.getId(), ps.getNameProduct(),
					ps.getPrecio(), ps.getInformacion(), ps.getTipo(), ps.getCategoria(), ps.isNegociable(),
					ps.getImageBytes(), ps.getViews(),ps.getStatus(),ps.getTime()));

		} else {
			System.out.println("No Coincidencia encontrada");
		}
	}

	public double search(String key, String posible) {
		return (similarity(key, posible));
	
	}
	public static double similarity(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) {
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0;
			/* both strings are zero length */ }

		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

	}

	public static int editDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	public int detectarRepeticionesDeLetraEnModelo(char c) {
		int repeticiones = 0;
		for (int x = 0; x < modelo.length(); x++) {
			if (c == modelo.charAt(x)) {
				repeticiones++;
			}
		}
		return repeticiones;
	}

	public int detectarRepeticionesDeLetraEnArrayList(char c) {
		int repeticiones = 0;
		for (int x = 0; x < repeatedCharacters.size(); x++) {
			if (c == repeatedCharacters.get(x)) {
				repeticiones++;
			}
		}
		return repeticiones;
	}

	public void sobraRepeticion(int repeticionesConArrayList, int repeticionesConModelo) {

		if (repeticionesConArrayList > repeticionesConModelo) {
			repetidoChar = true;
			System.out.println("Detectada repeticion: ");
		}
		// return false;
	}

}
