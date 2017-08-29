package main.mainApplication;

import java.util.ArrayList;

import findProducts.search.View;

public class Constantes {
	public static final String nameFolderImg="img";
	public static final String nameFolderFonts="resources.fonts";
	public static final boolean DEBUG = false;
	public static String tablaUsuarios= "usuarios";
	public static boolean controlUsuario= false;
	public static boolean controlPassword= false;
	public static boolean controlPasswordR= false;
	public static boolean controlTelefono= false;
	public static boolean controlEmail= false;
	public static int timeForCode= 120;
	public static String usuario="";
	public static int WIDTHRequiredImg=400; 
	public static int HEIGHTRequiredImg=400; 
	public static int ImgProfileY=240; 
	public static int ImgProfileX=240; 
	public static String defaultProductPath="defaultProduct.jpg";
	public static boolean productRequestForPreview=false;
	public static ArrayList<View>viewsLocalCache = new ArrayList<>();
	public static String version = "1.0.0_beta";
}
