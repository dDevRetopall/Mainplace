package constantesLocalesServidor;

public class ConstantesServer {
	public static String nombreTabla = "usuarios";
	public static String nombreTablaProductos ="products";
	public static String nombreTablaProductosVendidos ="soldproducts";
	public static boolean serverOperational =true;
	public static String requiredVersion = "1.2.0_beta";
	public static int requiredDaysToBeRecent = 3;
	public static int timeToExpireProduct = 24;/*In hours*/
	public static int timeToTransferProduct = 1;/*In minutes*/
	public static String nameCooldownTransferationFile="transferations.cd";
	public static String nameCooldownExpirationFile="expirations.cd";
}
