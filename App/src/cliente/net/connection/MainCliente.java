package cliente.net.connection;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.mysql.jdbc.UpdatableResultSet;

import cliente.net.register.RegisterActivity;
import constantes.NetConstants;
import main.mainApplication.MainApplication;
import mensajes.commands.DialogResponse;
import mensajes.commands.EmailCodeVerification;
import mensajes.commands.EmailResponse;
import mensajes.commands.LoginResponse;
import mensajes.commands.RegisterCommand;
import mensajes.commands.UserResponse;
import mensajes.requests.CreateProductRequest;
import mensajes.requests.EmailExistRequest;
import mensajes.requests.LoginRequest;
import mensajes.requests.RegisterRequest;
import mensajes.requests.SendEmailRequest;
import mensajes.requests.UserExistRequest;
import productos.InformacionProducto;
import productos.ProductId;
import productos.Producto;
import productos.ProductoParaEditar;
import productos.ProductsRequest;

import productos.SendStatusViews;
import productos.UpdateStatus;
import products.myProducts.main.ConstantesCategorias;
import productos.GetStatusViewsProductById;
import search.ProductoSearch;
import search.SearchInfo;
import utils.dataUtils.MessageUtils;
import utils.imageUtils.ImageUtils;
import utils.imageUtils.ImageUtilsUpdater;
import productos.AddViews;
import productos.EditarProducto;
import productos.EliminarProducto;
import productos.EnviarProductos;

public class MainCliente {

	private static Socket s;
	private static String ip;
	private static boolean connected = false;
	private static OutputStream os;
	private static InputStream is;
	public static ObjectOutputStream oos;
	public static ObjectInputStream ois;
	static ImageUtils iu = new ImageUtils();

	public static boolean establecerConexion() {

		try {
			Socket s = new Socket(NetConstants.HOST, NetConstants.PORT);
			MainCliente.s = s;
			MainCliente.initComponents();
			connected = true;
		} catch (UnknownHostException e) {
			MessageUtils.logn("No hay conexion a Internet o el servidor no está disponible");
			return false;
		} catch (IOException e) {
			MessageUtils.logn("Servidor no disponible");
			MessageUtils.logn("Error Cliente 1");
			return false;
		}

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (connected) {
					try {
						Object o = ois.readObject();
						System.out.println(o.getClass().getName());

						MessageUtils.logn("Element received");
						if (o instanceof RegisterCommand) {
							RegisterCommand rc = (RegisterCommand) o;

							if (rc.getRegisterResult()) {
								MainApplication.ra.registerCreated();
							} else {
								MainApplication.ra.registerError();
							}

						}
						if (o instanceof UserResponse) {
							UserResponse ur = (UserResponse) o;
							if (ur.getUserExistResult()) {
								MainApplication.ra.updateUserLabel(false);
							} else {
								MainApplication.ra.updateUserLabel(true);
							}

						}
						if (o instanceof EmailResponse) {
							EmailResponse ur = (EmailResponse) o;
							if (ur.getEmailExistResult()) {
								MainApplication.ra.updateEmailLabel(false);
							} else {
								MainApplication.ra.updateEmailLabel(true);
							}

						}
						if (o instanceof EmailCodeVerification) {
							EmailCodeVerification ecv = (EmailCodeVerification) o;
							MainApplication.ra.updateUIPendingCode(ecv.getCode(), ecv.getStatus());
						}
						if (o instanceof LoginResponse) {
							LoginResponse lr = (LoginResponse) o;
							if (lr.getRespuesta()) {
								MainApplication.l.abrirPerfil(lr.getUsername());
							} else {
								MainApplication.l.enviarMensajeDeError(lr.getMensajeError());
							}
						}
						if (o instanceof EnviarProductos) {
							EnviarProductos ep = (EnviarProductos) o;
							MainApplication.pw.tm.cleanTable();

							MainApplication.pw.tm.ids.clear();
							for (Producto p : ep.getProductos()) {

								MainApplication.pw.tm.createNewRow(p.getId(), p.getName(), p.getInformacion(),
										p.isNegociable(), p.getCategoria(), p.getTipo(), p.getPrice(),
										p.getImageBytes());

							}
							if (MainApplication.pw != null) {
								MainApplication.pw.setVisible(true);
							}
						}
						if (o instanceof InformacionProducto) {
							InformacionProducto pp = (InformacionProducto) o;
							Producto p = pp.getProducto();
							
							if (main.mainApplication.Constantes.productRequestForPreview) {
								MainApplication.pview.update(p);
								main.mainApplication.Constantes.productRequestForPreview = false;
							} else {
								MainApplication.pw.setProductInformation(p);
							}

						}
						if (o instanceof ProductoSearch) {
							ProductoSearch ps = (ProductoSearch) o;
							MainApplication.sw.t.createNewRow(ps.getOwner(), ps.getId(), ps.getName(),
									ps.getInformacion(), ps.isNegociable(), ps.getCategoria(), ps.getTipo(),
									ps.getPrice(), ps.getImageBytes(),ps.getViews(),ps.getStatus());

						}
						if (o instanceof SendStatusViews) {
							SendStatusViews ss = (SendStatusViews) o;
							MainApplication.pw.updateStatus(ss.getStatus(),ss.getViews());

						}
						if (o instanceof DialogResponse) {
							DialogResponse dd = (DialogResponse) o;
							JOptionPane.showMessageDialog(null, dd.getMensaje(), dd.getTitulo(), JOptionPane.WARNING_MESSAGE);

						}
						

					} catch (ClassNotFoundException | IOException e) {
						MessageUtils.logn("Error Cliente 3");
						connected = false;
						e.printStackTrace();
					}
				}

			}
		});
		t.start();

		return true;
	}

	public static void initComponents() {

		try {

			os = s.getOutputStream();
			is = s.getInputStream();
			ois = new ObjectInputStream(is);
			oos = new ObjectOutputStream(os);
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 2");
			e.printStackTrace();
		}
	}

	public static void createRegisterRequest(String username, String pwd, String telefono, String email) {
		try {
			oos.writeObject(new RegisterRequest(username, pwd, email, telefono));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}

	public static void sendUserRequest(String username) {
		try {
			oos.writeObject(new UserExistRequest(username));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}

	public static void sendEmailRequest(String email) {
		try {
			oos.writeObject(new EmailExistRequest(email));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}

	public static void sendEmailSendRequest(String email, String username) {
		try {
			oos.writeObject(new SendEmailRequest(email, username));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}

	public static void sendLoginRequest(String username, String password) {
		try {
			oos.writeObject(new LoginRequest(username, password,main.mainApplication.Constantes.version));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}

	public static void createProduct(String nombre, double precio, String info, int categoria, int tipo,
			boolean negociable, Image i) {

		byte[] imageInByte = null;
		if (i != null) {

			BufferedImage bi = ImageUtilsUpdater.imageToBufferedImage(i);
			imageInByte = ImageUtilsUpdater.ImageToByteArray(bi);

		} else {

			BufferedImage bi = iu.getBufferedImage(main.mainApplication.Constantes.defaultProductPath);
			imageInByte = ImageUtilsUpdater.ImageToByteArray(bi);
		}
		try {
			oos.writeObject(new CreateProductRequest(nombre, precio, info, tipo, categoria, negociable, imageInByte));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}

	public static void enviarProductRequestById(int id) {
		try {
			oos.writeObject(new ProductId(id));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}

	public static void editProduct(int id, String nombre, double precio, String info, int categoria, int tipo,
			boolean negociable, Image i) {
		byte[] imageInByte = null;
		if (i != null) {

			BufferedImage bi = ImageUtilsUpdater.imageToBufferedImage(i);
			imageInByte = ImageUtilsUpdater.ImageToByteArray(bi);

		} else {

			BufferedImage bi = iu.getBufferedImage(main.mainApplication.Constantes.defaultProductPath);
			imageInByte = ImageUtilsUpdater.ImageToByteArray(bi);
		}
		try {
			oos.writeObject(new EditarProducto(id,
					new ProductoParaEditar(id, nombre, precio, info, tipo, categoria, negociable, imageInByte)));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}

	public static void removeProduct(int id) {
		try {
			oos.writeObject(new EliminarProducto(id));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}

	public static void getProducts(String username) {
		try {
			oos.writeObject(new ProductsRequest(username));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}

	public static void requestSearch(String search) {
		try {
			oos.writeObject(new SearchInfo(search));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}
	public static void addView(int idProducto,String usuario) {
		try {
			oos.writeObject(new AddViews(idProducto,usuario));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}
	public static void requestStatusViewsProductById(int idProducto) {
		try {
			oos.writeObject(new GetStatusViewsProductById(idProducto));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}

	}
	public static void updateStatus(int status,int id){
		try {
			oos.writeObject(new UpdateStatus(status,id));
		} catch (IOException e) {
			MessageUtils.logn("Error Cliente 4");
			e.printStackTrace();
		}
	}
}
