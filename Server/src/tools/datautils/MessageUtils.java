package tools.datautils;

import java.text.SimpleDateFormat;
import java.util.Date;

import server.main.Main;

public class MessageUtils {
	public static void logn(String message){
		String date = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
		System.out.println(date + " -> " + new Exception().getStackTrace()[1].toString()+ "   " + message);
		if((Main.mw!=null)) {
			Main.mw.ta.setText(Main.mw.ta.getText()+date+" : "+message+"\n");
			 Main.mw.ps.getVerticalScrollBar().setValue( Main.mw.ps.getVerticalScrollBar().getMaximum());
		}
	}
	public static void logn(String message,String prefix){
		String date = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
		System.out.println(date + " -> " + new Exception().getStackTrace()[1].toString()+ "   " + message);
		if((Main.mw!=null)) {
			Main.mw.ta.setText(Main.mw.ta.getText()+date+" : ["+prefix+"] "+message+"\n");
			 Main.mw.ps.getVerticalScrollBar().setValue( Main.mw.ps.getVerticalScrollBar().getMaximum());
		}
	}
}
