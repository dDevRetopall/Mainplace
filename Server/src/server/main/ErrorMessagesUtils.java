package server.main;

import constantesLocalesServidor.ConstantesServer;

public class ErrorMessagesUtils {
	public static String[] errors = { "User or password wrong", "The user is already in the app",
			"The application is on maintenance", "The client is outdated. RequiredVersion: "+ConstantesServer.requiredVersion ,"You can't log in now"};
}
