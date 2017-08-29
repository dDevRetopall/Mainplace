package server.net.clientHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import constantesLocalesServidor.ConstantesServer;
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
import productos.ProductsRequest;
import productos.SendStatusViews;
import productos.UpdateStatus;
import search.ProductoSearch;
import search.SearchInfo;
import search.searchEngine.ProductsSearch;
import search.searchEngine.SearchHandler;
import server.main.ErrorMessagesUtils;
import server.main.Main;
import productos.AddViews;
import productos.EditarProducto;
import productos.EliminarProducto;
import productos.EnviarProductos;

import productos.GetStatusViewsProductById;
import tools.datautils.EncriptarPasswords;
import tools.datautils.MessageUtils;
import tools.datautils.PasswordGenerator;
import tools.emailUtils.Verification;
import tools.mysqlutils.ConnectionSQLProducts;
import tools.mysqlutils.ConnectionSQLUsuarios;
import tools.mysqlutils.SQLConnection;

public class Cliente {

	private Socket s;
	private String ip;
	private boolean connected = false;
	private OutputStream os;
	private InputStream is;
	public ObjectOutputStream oos;
	public ObjectInputStream ois;
	public ConnectionSQLProducts p;
	public ControlProfile sp;
	public boolean online = false;

	public Cliente(Socket s) {
		this.s = s;
		ip = s.getInetAddress().getHostAddress();
		connected = true;
		sp = new ControlProfile();
		try {
			os = s.getOutputStream();
			is = s.getInputStream();
			oos = new ObjectOutputStream(os);
			ois = new ObjectInputStream(is);
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
		Thread obtenerMensajes = new Thread(new Runnable() {

			@Override
			public void run() {
				while (connected) {
					try {

						Object o = ois.readObject();
						MessageUtils.logn("Detectado un mensaje: " + o.getClass().getName());
						// revisar
						if (o instanceof RegisterRequest) {

							RegisterRequest rr = (RegisterRequest) o;
							String nombreTableProductos = rr.getUser() + ConstantesServer.nombreTablaProductos;
							boolean respuesta = ConnectionSQLUsuarios.añadirUsuario(ConstantesServer.nombreTabla,
									rr.getUser(), rr.getPwd(), rr.getTelefono(), rr.getEmail(), nombreTableProductos);
							if (respuesta) {
								p = sp.setup(SQLConnection.getConnection(), rr.getUser(),
										ConnectionSQLUsuarios.getId(ConstantesServer.nombreTabla, rr.getUser()),
										rr.getEmail(), nombreTableProductos);
							}
							System.out.println("Respuesta: " + respuesta);// +"
																			// "+rr.getUser()+"
																			// "+rr.getPwd()+"
																			// "+rr.getTelefono()+"
																			// "+rr.getEmail());
							enviarRespuestaRegistro(respuesta);
						}
						if (o instanceof UserExistRequest) {
							UserExistRequest rr = (UserExistRequest) o;
							boolean respuesta = ConnectionSQLUsuarios.existeUsuario(ConstantesServer.nombreTabla,
									rr.getUsername());
							System.out.println("Respuesta: " + respuesta);
							enviarRespuestaUser(respuesta);
						}
						if (o instanceof EmailExistRequest) {
							EmailExistRequest er = (EmailExistRequest) o;
							boolean respuesta = ConnectionSQLUsuarios.emailInscrito(ConstantesServer.nombreTabla,
									er.getEmail());

							System.out.println("Respuesta: " + respuesta);
							enviarRespuestaEmail(respuesta);
						}
						if (o instanceof SendEmailRequest) {
							SendEmailRequest ser = (SendEmailRequest) o;
							String code = PasswordGenerator.createPassword(6);
							boolean status = Verification.sendEmail(ser.getEmail(), code, ser.getUsername());
							System.out.println("Respuesta: " + status);
							enviarRespuestaVerificationCode(code, status);

						}
						if (o instanceof LoginRequest) {
							LoginRequest lr = (LoginRequest) o;
							boolean alreadyOnline = false;
							System.out.println("Sockets: " + Main.clientes.size());
							for (int i = 0; i < Main.clientes.size(); i++) {
								if (Main.clientes.get(i).online) {
									if (Main.clientes.get(i).sp.username.equals(lr.getUsername())) {
										alreadyOnline = true;
										System.out.println(lr.getUsername());
									}
								}
							}

							boolean resultado = ConnectionSQLUsuarios.consultarUsuario(ConstantesServer.nombreTabla,
									lr.getUsername(), lr.getPwd());
							if(ConstantesServer.requiredVersion.equals(lr.getVersion())){
							if(ConstantesServer.serverOperational){
							if (!alreadyOnline) {
								if (resultado) {
									enviarRespuestaLogin(resultado, lr.getUsername(),0);
									online = true;
									p = sp.login(SQLConnection.getConnection(), lr.getUsername(), lr.getPwd());
								}else{
									enviarRespuestaLogin(resultado, lr.getUsername(),0);
								}
								System.out.println("Respuesta: " + resultado);
							}else{
								resultado=false;
								enviarRespuestaLogin(resultado, lr.getUsername(),1);
							}
							}else{
								resultado=false;
								enviarRespuestaLogin(resultado, lr.getUsername(),2);
							}
							}else{
								resultado=false;
								enviarRespuestaLogin(resultado, lr.getUsername(),3);
								enviarDialogResponse(ErrorMessagesUtils.errors[3], "Outdated client");
							}

						}
						if (o instanceof CreateProductRequest) {
							CreateProductRequest cpr = (CreateProductRequest) o;

							boolean resultado = p.insertarProducto(SQLConnection.getConnection(), cpr.getName(),
									cpr.getPrice(), cpr.getInfo(), cpr.getTipo(), cpr.getCategoria(),
									cpr.isNegociable(), cpr.getImage());
							if (resultado) {
								ArrayList<Producto> productos = sp.getProducts(p, SQLConnection.getConnection(),
										sp.username);
								if (productos != null) {
									enviarProductos(productos);
								} else {
									MessageUtils.logn("Null arraylist productos from the user " + sp.idUsuario + " : "
											+ sp.username);
								}
							}
							System.out.println("Respuesta: " + resultado);

						}
						if (o instanceof ProductId) {
							ProductId pi = (ProductId) o;
							Producto producto = sp.getProduct(p, SQLConnection.getConnection(), pi.getId());
							if (producto != null) {
								enviarProducto(producto);
							} else {
								MessageUtils.logn("We couldnt find the product with the id " + pi.getId()
										+ " from the user " + sp.idUsuario + " : " + sp.username);
							}

						}
						if (o instanceof EditarProducto) {
							EditarProducto ep = (EditarProducto) o;
							boolean resultado = sp.editarProducto(p, SQLConnection.getConnection(), ep.getId(),
									ep.getProducto());

							if (resultado) {
								MessageUtils.logn("Product from user " + sp.idUsuario + " : " + sp.username
										+ " edited succesfully");
								ArrayList<Producto> productos = sp.getProducts(p, SQLConnection.getConnection(),
										sp.username);
								if (productos != null) {
									enviarProductos(productos);
								} else {
									MessageUtils.logn("Null arraylist productos from the user " + sp.idUsuario + " : "
											+ sp.username);
								}
							} else {
								MessageUtils.logn("An error while editing the product with the id " + ep.getId()
										+ " from the user " + sp.idUsuario + " : " + sp.username);
							}

						}
						if (o instanceof EliminarProducto) {
							EliminarProducto ep = (EliminarProducto) o;
							boolean resultado = sp.eliminarProducto(p, SQLConnection.getConnection(), ep.getId());

							if (resultado) {
								MessageUtils.logn("Product from user " + sp.idUsuario + " : " + sp.username
										+ " removed succesfully");
								ArrayList<Producto> productos = sp.getProducts(p, SQLConnection.getConnection(),
										sp.username);
								if (productos != null) {
									enviarProductos(productos);
								} else {
									MessageUtils.logn("Null arraylist productos from the user " + sp.idUsuario + " : "
											+ sp.username);
								}
							} else {
								MessageUtils.logn("An error while removing the product with the id " + ep.getId()
										+ " from the user " + sp.idUsuario + " : " + sp.username);
							}

						}
						if (o instanceof ProductsRequest) {
							ProductsRequest pr = (ProductsRequest) o;
							ArrayList<Producto> productos = sp.getProducts(p, SQLConnection.getConnection(),
									pr.getUsername());
							if (productos != null) {
								enviarProductos(productos);
							} else {
								MessageUtils.logn(
										"Null arraylist productos from the user " + sp.idUsuario + " : " + sp.username);
							}
						}

						if (o instanceof SearchInfo) {
							SearchInfo si = (SearchInfo) o;
							SearchHandler.getCoincidenciasProductos(Cliente.this, si.getSearch());
						}
						if (o instanceof AddViews) {
							AddViews av = (AddViews) o;
							boolean resultado = sp.addView(av.getUsername(), SQLConnection.getConnection(), av.getId());
							if (resultado) {

							} else {
								MessageUtils.logn("Error adding new view of " + av.getUsername()
										+ "from his product id " + av.getId());
							}
						}
						if (o instanceof GetStatusViewsProductById) {
							GetStatusViewsProductById si = (GetStatusViewsProductById) o;
							int status = sp.getStatusById(si.getId(), p);
							int views = sp.getViewsById(si.getId(), p);
							if (status == -1 || views == -1) {
								System.out.println("Ha habido un error getStatusViewsId");

							} else {
								enviarStatusViews(new SendStatusViews(status, views));
							}
						}
						if (o instanceof UpdateStatus) {
							UpdateStatus us = (UpdateStatus) o;
							boolean resultado = sp.updateStatus(us.getIdProducto(), us.getStatus(), p,
									SQLConnection.getConnection());
							if (resultado) {
								int status = sp.getStatusById(us.getIdProducto(), p);
								int views = sp.getViewsById(us.getIdProducto(), p);
								if (status == -1 || views == -1) {
									System.out.println("Ha habido un error getStatusViewsId");

								} else {
									enviarStatusViews(new SendStatusViews(status, views));
								}
							} else {
								MessageUtils.logn("Error trying to update the status of productID " + us.getIdProducto()
										+ " to status " + us.getStatus() + "from the user " + sp.username);
							}
						}

					} catch (ClassNotFoundException | IOException e) {
						MessageUtils.logn("Cliente desconectado " + sp.idUsuario + " : " + sp.username);
						connected = false;
						Main.clientes.remove(Cliente.this);
						MessageUtils.logn("Clientes conectados: " + Main.clientes.size());
						online = false;
					}
				}

			}
		});
		obtenerMensajes.start();

	}

	public void enviarRespuestaRegistro(boolean respuesta) {
		try {
			oos.writeObject(new RegisterCommand(respuesta));
			System.out.println("Respuesta enviada");
		} catch (IOException e) {
			System.out.println("Error 3");
			e.printStackTrace();
		}
	}

	public void enviarRespuestaUser(boolean respuesta) {
		try {
			oos.writeObject(new UserResponse(respuesta));
		} catch (IOException e) {
			System.out.println("Error 3");
			e.printStackTrace();
		}
	}

	public void enviarRespuestaEmail(boolean respuesta) {
		try {
			oos.writeObject(new EmailResponse(respuesta));
		} catch (IOException e) {
			System.out.println("Error 3");
			e.printStackTrace();
		}
	}

	public void enviarRespuestaVerificationCode(String code, boolean status) {
		try {
			oos.writeObject(new EmailCodeVerification(code, status));
		} catch (IOException e) {
			System.out.println("Error 3");
			e.printStackTrace();
		}
	}

	public void enviarRespuestaLogin(boolean respuesta, String username,int error) {
		try {
			oos.writeObject(new LoginResponse(respuesta, ErrorMessagesUtils.errors[error], username));
		} catch (IOException e) {
			System.out.println("Error 3");
			e.printStackTrace();
		}
	}

	public void enviarProductos(ArrayList<Producto> productos) {
		try {
			oos.writeObject(new EnviarProductos(productos));
		} catch (IOException e) {
			System.out.println("Error 3");
			e.printStackTrace();
		}
	}

	public void enviarProducto(Producto producto) {
		try {
			oos.writeObject(new InformacionProducto(producto));
		} catch (IOException e) {
			System.out.println("Error 3");
			e.printStackTrace();
		}
	}

	public void enviarCoincidenciarSearch(ProductoSearch productoSearch) {
		try {
			oos.writeObject(productoSearch);
		} catch (IOException e) {
			System.out.println("Error 3");
			e.printStackTrace();
		}
	}

	public void enviarStatusViews(SendStatusViews s) {
		try {
			oos.writeObject(s);
		} catch (IOException e) {
			System.out.println("Error 3");
			e.printStackTrace();
		}
	}
	public void enviarDialogResponse(String mensaje,String titulo) {
		try {
			oos.writeObject(new DialogResponse(mensaje, titulo));
		} catch (IOException e) {
			System.out.println("Error 3");
			e.printStackTrace();
		}
	}

	public ConnectionSQLProducts getSQLProductsTable() {
		return p;
	}

	public ControlProfile getSetupProfileFunctions() {
		return sp;
	}

}
