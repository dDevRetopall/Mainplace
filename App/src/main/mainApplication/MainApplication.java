package main.mainApplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import cliente.net.connection.MainCliente;
import cliente.net.login.Launcher;
import cliente.net.register.RegisterActivity;
import findProducts.search.SearchWindow;
import products.myProducts.createProduct.CreateProductWindow;
import products.myProducts.editProduct.ProductEditWindow;
import products.myProducts.main.MyProductWindow;
import products.myProducts.preview.PreviewWindow;
import products.myProducts.updatestatus.UpdateStatusWindow;
import utils.dataUtils.MessageUtils;
import utils.emailUtils.Verification;


public class MainApplication {
	public static RegisterActivity ra ;
	public static Launcher l;
	public static MyProductWindow pw;
	public static CreateProductWindow pww;
	public static ProductEditWindow pew;
	private static MainApplicationWindow maw;
	public static PreviewWindow pview;
	public static SearchWindow sw;
	public static UpdateStatusWindow usw;
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		MessageUtils.logn("Establishing connection to the Server");
		boolean r3=MainCliente.establecerConexion();
		if(r3){
			MessageUtils.logn("Established conection");
			MessageUtils.logn("Connection to Server : Succesful");
		}else{
			MessageUtils.logn("Connection to Server : Error");
			MessageUtils.logn("Connection to Server : Stopping application");
			System.exit(0);
		}	
		if(!Constantes.DEBUG){
		createLoginActivity();
		}else{
			System.out.println("Debug Mode: ON");
			createLoginActivity();
			MainCliente.sendLoginRequest("diego2", "diego2442.b");
		}

	}
	public static void createRegisterActivity(){
		MessageUtils.logn("Starting register activity");
		ra = new RegisterActivity();
		ra.setVisible(true);
		MessageUtils.logn("Register acitivity initialized");
		
	}
	public static  void createLoginActivity(){
		MessageUtils.logn("Starting launcher");
		l = new Launcher();
		l.setVisible(true);
		MessageUtils.logn("Launcher initialized");
	}
	public static void openMainApplicationWindow(String username) {
		MessageUtils.logn("Starting MainApplication Window");
		maw = new MainApplicationWindow(username);
		maw.setVisible(true);
		MessageUtils.logn("MainApplication Window initialized");
		
	}
	public static void openProductWindow(boolean visible) {
		MessageUtils.logn("Starting product Window");
		pw = new MyProductWindow();
		if(visible){
		pw.setVisible(true);
		}
		MessageUtils.logn("Product Window initialized");
		
	}
	public static void openNewProductWindow() {
		MessageUtils.logn("Starting new product Window");
		pww = new CreateProductWindow();
		pww.setVisible(true);
		MessageUtils.logn("New product window initialized");
		
	}
	public static void openEditProductWindow(int id,String producto,double price,String informacion,boolean negociable,int tipo,int categoria,Image general,Image foto) {
		MessageUtils.logn("Starting edit product Window");
		pew = new ProductEditWindow(id,producto,price,informacion,negociable,tipo,categoria,general,foto);
		pew.setVisible(true);
		MessageUtils.logn("Edit product window initialized");
		
	}
	public static void openPreview(int id){
		MessageUtils.logn("Starting preview product Window");
		if(pview!=null){
		pview.dispose();
		}
		pview = new PreviewWindow(id);
		MessageUtils.logn("Preview product window initialized");
	}
	public static void openSearchWindow(){
		MessageUtils.logn("Starting search Window");
		sw = new SearchWindow();
		sw.setVisible(true);
		MessageUtils.logn("Search window initialized");
	}
	public static void updateStatusWindow(int id){
		MessageUtils.logn("Starting preview product Window");
		if(usw!=null){
		usw.dispose();
		}
		usw = new UpdateStatusWindow(id);
		MessageUtils.logn("Preview product window initialized");
	}
	
	

}
