package utils.dataUtils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
	public static Font createFont(String name,int size,String type){
		Font font = null;
		try {
			InputStream is = FontLoader.class.getResourceAsStream(name);
			Font f = Font.createFont(Font.TRUETYPE_FONT, is);
			if(type.equals("Plain")){
				font = f.deriveFont(Font.PLAIN, size);
			}else if(type.equals("Bold")){
				font = f.deriveFont(Font.BOLD, size);
			}
			
		} catch (FontFormatException e) {
			System.out.println("Formato invalido de fuente");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("No se ha encontrado la fuente");
			e.printStackTrace();
		}
		return font;
	}
}
