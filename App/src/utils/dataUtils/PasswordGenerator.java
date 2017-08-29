package utils.dataUtils;

public class PasswordGenerator {
	private static  String code="";
	public static String createPassword(int digits){
		int digitos=digits;
		String code="";
		for(int i =0;i<digitos/2;i++){
			String s = "abcdefgyijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			String s2="1234567890";
			int pos = (int)(Math.random()*52);
		    code= code+s.charAt(pos);
			int pos2 = (int)(Math.random()*10);
			  code= code+s2.charAt(pos2);
		
		}
		PasswordGenerator.code=code;
		return code;
		
	}
	public static String getCode(){
		return PasswordGenerator.code;
	}
}
