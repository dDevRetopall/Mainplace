package server.main;

import constantesLocalesServidor.ConstantesServer;

public class ErrorMessagesUtils {
	public static String[] errors = { "User or password wrong", "The user is already in the app",
			"The application is on manitenance", "The client is outdated. RequiredVersion: "+ConstantesServer.requiredVersion };
}
