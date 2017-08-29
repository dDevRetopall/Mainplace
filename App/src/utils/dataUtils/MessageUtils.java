package utils.dataUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageUtils {
	public static void logn(String message){
		String date = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
		System.out.println(date + " -> " + new Exception().getStackTrace()[1].toString()+ "   " + message);
	}
}
