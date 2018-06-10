package main.mainApplication;

import java.util.ArrayList;

import gui.jcomponents.tables.View;

public class Constantes {
	//Editable
	public static final String nameFolderImg="img";
	public static final String nameFolderFonts="resources.fonts";
	public static final boolean DEBUG = false;
	public static String tablaUsuarios= "usuarios";
	
	
	
	//No editable
	public static boolean controlUsuario= false;
	public static boolean controlPassword= false;
	public static boolean controlPasswordR= false;
	public static boolean controlTelefono= false;
	public static boolean controlEmail= false;
	public static int timeForCode= 120;
	public static int WIDTHRequiredImg=400; 
	public static int HEIGHTRequiredImg=400; 
	public static int ImgProfileY=240; 
	public static int ImgProfileX=240; 
	public static String defaultProductPath="defaultProduct.jpg";
	public static boolean productRequestForPreview=false;
	public static ArrayList<View>viewsLocalCache = new ArrayList<>();
	public static String version = "1.4.0_Beta";
	public static int updateRequest = 60000;
	public static int startUpdater = updateRequest;
	public static boolean updaterActive=true;
	
	//Properties client -No editable
	public static String usuario="";
	public static String phone="";
	public static String email="";
}
