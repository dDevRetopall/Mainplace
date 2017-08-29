package server.terminal;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JOptionPane;

import constantes.NetConstants;
import server.main.Main;
import tools.datautils.MessageUtils;

public class Console {
	static boolean cmdAlive=true;
	public static void startConsole() {
		if (System.console() == null) {
			Path currentRelativePath = Paths.get("");
			String s = currentRelativePath.toAbsolutePath().toString();
			String[] command = new String[] { "cd", s };
//			try {
//			//	Process q = Runtime.getRuntime().exec("cmd.exe /c start cmd /k java -jar " + s + "\\Server.jar");
//			
//			} catch (IOException e1) {
//				System.out.println("Error. Continuing without console");
//				e1.printStackTrace();
//			}
			initConsole();
			MessageUtils.logn("Console enabled. Type help to know the commands");
		}else{
			MessageUtils.logn("Console already created from another Java proccess");
			MessageUtils.logn("Executing other proccess");
			MessageUtils.logn("Enabling console");
			boolean r=initConsole();
			if(r){
			MessageUtils.logn("Console enabled. Type help to know the commands");
			}
		}
	}

	private static boolean initConsole() {
		Scanner sc = new Scanner(System.in);
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(cmdAlive){
					String s = sc.nextLine();
					if(s.equals("stopServer")){
						System.out.println("Stopping server");
						try {
							Main.ss.close();
							Main.connectionAlive=false;
						} catch (IOException e) {
							System.out.println("The server couldn't stop");
							e.printStackTrace();
						}
						System.out.println("Server stopped");
					}
					if(s.equals("startServer")){
						System.out.println("Starting server");
						try {
							Main.connectionAlive=true;
							
							Main.ss= new ServerSocket(NetConstants.PORT);
							
							
						} catch (IOException e) {
							System.out.println("The server couldn't start");
							e.printStackTrace();
						}
						System.out.println("Server started");
					}
				}
				
			}
		});
		t.start();
		return true;
	}
}
