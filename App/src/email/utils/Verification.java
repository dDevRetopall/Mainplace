package email.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Verification {
	public static boolean isValidEmailAddress(String email) {
		   boolean result = false;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		      result = true;
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
	public static void sendEmail(String email,String pwd,String username){
			String host="smtp.gmail.com";  
		  final String user="mainplace2442@gmail.com";//change accordingly  
		  final String password="MainPlace.db1234";//change accordingly  
		  
		    
		  String to=email;//change accordingly  
		  
		   //Get the session object  
		   Properties props = new Properties();  
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.from", "myemail@gmail.com");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.port", "587");
		    //props.setProperty("mail.debug", "true");
		    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		    props.put("mail.smtp.auth", "true");
		   Session session = Session.getDefaultInstance(props,  
		    new javax.mail.Authenticator() {  
		      protected PasswordAuthentication getPasswordAuthentication() {  
		    return new PasswordAuthentication(user,password);  
		      }  
		    });  
		  
		   //Compose the message  
		    try {  
		     MimeMessage message = new MimeMessage(session);  
		     message.setFrom(new InternetAddress(username));  
		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		     message.setSubject("Verification");  
		     message.setText("Hi "+username+", \n" +"Here is the verification code that you need to type in the application to finish the regist"+"\n "+pwd);  
		       
		    //send the message  
		     Transport.send(message);  
		  
		     System.out.println("message sent successfully...");  
		   
		     } catch (MessagingException e) {e.printStackTrace();}  
		 }  
	}
	

