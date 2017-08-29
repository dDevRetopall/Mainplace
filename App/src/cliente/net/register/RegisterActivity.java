package cliente.net.register;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.MaskFormatter;

import cliente.net.connection.MainCliente;
import main.mainApplication.Constantes;
import main.mainApplication.MainApplication;
import utils.dataUtils.MessageUtils;
import utils.dataUtils.PasswordGenerator;
import utils.emailUtils.Verification;
import utils.threadUtils.RunnableDemo;

public class RegisterActivity extends JFrame {

	private int valuePb = 0;
	private String pathError = "error.jpg";
	private String pathAccept = "accept.jpg";
	JPanelBackground mainPanel = new JPanelBackground(new BorderLayout());
	int verde = 0;
	int rojo = 255;
	int v = 0;
	int velocidad = 10;
	public boolean resent = false;

	boolean suspended = false;
	public String info = "We have sent you a verification email";
	public RunnableDemo t2;

	boolean aumentando = true;
	final BoundedRangeModel model = new DefaultBoundedRangeModel();

	JPanel p11 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel p22 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	public JLabel codeL = new JLabel("Enter code: ");

	public JPanel p33 = new JPanel(new FlowLayout(FlowLayout.CENTER));

	Font f = new Font("Arial", Font.PLAIN, 22);

	JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l1 = new JLabel("                             Username: ");
	public JTextField tf1 = new JTextField(10);

	JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l2 = new JLabel("                            Telephone: ");
	JFormattedTextField tf2;

	JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l3 = new JLabel("                     E-mail address: ");
	public JTextField tf3 = new JTextField(15);

	JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l4 = new JLabel("                             Password: ");
	JPasswordField tf4 = new JPasswordField(10);

	JPanel p5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l5 = new JLabel("                 Repeat password: ");
	JPasswordField tf5 = new JPasswordField(10);

	public JPanel pBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JButton b = new JButton("Create account");

	JPanel p6 = new JPanel(new GridLayout(8, 1));

	JProgressBar pb = new JProgressBar(model);

	JLabel l = new JLabel("Create an account");
	JPanel pInicio = new JPanel(new FlowLayout(FlowLayout.CENTER));

	public JFormattedTextField verification;

	JLabel label = new JLabel("");
	JLabel label1 = new JLabel("");
	JLabel label2 = new JLabel("");
	JLabel label3 = new JLabel("");
	JLabel label4 = new JLabel("");
	public JLabel labelCode = new JLabel("");

	public JLabel background = new JLabel("Creating account");
	public JLabel background2 = new JLabel(info);
	private String code;

	public RegisterActivity() {
		t2 = new RunnableDemo("t2", this);
		initComponents();

	}

	private void initComponents() {
		mainPanel.setBackground("fondo.jpg");
		this.setContentPane(mainPanel);
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setUndecorated(false);

		ImageIcon imageIcon = new ImageIcon(getClass().getResource(pathAccept)); // load
																					// the
																					// image
																					// to
																					// a
																					// imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH); // scale
																						// it
																						// the
																						// smooth

		background.setFont(f);
		background2.setFont(f);
		background2.setForeground(Color.DARK_GRAY);

		// way
		imageIcon = new ImageIcon(newimg);
		label.setIcon(imageIcon);
		label1.setIcon(imageIcon);
		label2.setIcon(imageIcon);
		label3.setIcon(imageIcon);
		label4.setIcon(imageIcon);
		labelCode.setIcon(imageIcon);
		label.setFont(new Font("Microsoft Sans Sherif", Font.BOLD, 15));
		label1.setFont(new Font("Microsoft Sans Sherif", Font.BOLD, 15));
		label2.setFont(new Font("Microsoft Sans Sherif", Font.BOLD, 15));
		label3.setFont(new Font("Microsoft Sans Sherif", Font.BOLD, 15));
		label4.setFont(new Font("Microsoft Sans Sherif", Font.BOLD, 15));
		labelCode.setFont(new Font("Microsoft Sans Sherif", Font.BOLD, 15));
		labelCode.setForeground(new Color(0, 153, 0));

		MaskFormatter mf1 = null;
		try {
			mf1 = new MaskFormatter(" ### ### ### ");
			// mf1.setPlaceholderCharacter('_');

			// mf1.setPlaceholderCharacter('');
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pInicio.add(l);
		l.setFont(new Font("Blade Runner Movie Font", Font.PLAIN, 25));
		l.setForeground(Color.WHITE);
		p6.add(pInicio);

		MaskFormatter mf2 = null;
		try {
			mf2 = new MaskFormatter("AAAAAA");

			// mf1.setPlaceholderCharacter('');
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		verification = new JFormattedTextField(mf2);
		verification.setColumns(8);
		aplicarDecoracion(verification);

		tf2 = new JFormattedTextField(mf1);
		tf2.setColumns(8);

		p1.add(l1);
		p1.add(tf1);
		p6.add(p1);
		aplicarDecoracion(tf1, l1);

		p4.add(l4);
		p4.add(tf4);
		p6.add(p4);
		aplicarDecoracion(tf4, l4);

		p5.add(l5);
		l5.setFont(f);
		l5.setForeground(Color.white);
		p5.add(tf5);
		tf5.setFont(f);
		p6.add(p5);
		aplicarDecoracion(tf5, l5);

		p2.add(l2);
		p2.add(tf2);
		p6.add(p2);
		aplicarDecoracionEspecial(tf2, l2);

		p3.add(l3);
		p3.add(tf3);
		p6.add(p3);
		aplicarDecoracionEspecial(tf3, l3);

		b.setBackground(new Color(0, 76, 153));
		b.setOpaque(false);

		pBoton.add(b);

		pb.setValue(0);

		this.add(pBoton, BorderLayout.SOUTH);

		pb.setStringPainted(true);
		pb.setString("");
		pb.setValue(0);
		pb.setBackground(new Color(0, 0, 0, 0));
		pb.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 76, 153)));
		pb.setForeground(Color.GREEN);

		p6.setOpaque(false);
		pInicio.setOpaque(false);
		pBoton.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		p5.setOpaque(false);

		this.add(p6, BorderLayout.CENTER);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				int value = 0;
				while (true) {
					System.out.print("");
					if (valuePb != pb.getValue()) {

						if (valuePb - pb.getValue() > 0) {
							int v = pb.getValue();
							for (int i = 0; i < valuePb - pb.getValue(); i++) {
								value++;
								v++;
								int porcentaje = (int) ((value * 255) / 50d);
								if (rojo == 255 && verde == 255) {
									aumentando = false;
									value = 1;
									porcentaje = (int) ((value * 255) / 50d);

								}
								if (aumentando) {
									verde = porcentaje;
								} else {
									rojo = 255 - porcentaje;
								}

								pb.setValue(v);
								pb.setForeground(new Color(rojo, verde, 0));

								try {
									Thread.sleep(velocidad);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						} else if ((valuePb - pb.getValue()) < 0) {

							pb.setValue(0);
							rojo = 255;
							verde = 0;
							aumentando = true;
							v = 0;

							value = 0;
							pb.setForeground(Color.GREEN);
						}
					}
				}

			}
		});

		t.start();
		tf2.addKeyListener(new KeyListener() {
			boolean presionado = false;
			int contador = 0;

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (presionado) {
					if (tf2.getText().trim().replace(" ", "").length() == 9) {
						cambiarIcono(pathAccept, label3);
						p2.add(label3);
						Constantes.controlTelefono = true;
					} else {
						Constantes.controlTelefono = false;
						p2.remove(label3);
					}
					p2.updateUI();

				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				presionado = true;

			}
		});
		tf3.addKeyListener(new KeyListener() {
			boolean presionado = false;
			int contador = 0;

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (presionado) {
					if (Verification.isValidEmailAddress(tf3.getText())) {
						MainCliente.sendEmailRequest(tf3.getText());

					} else {
						p3.remove(label4);
						Constantes.controlEmail = false;
					}
					p3.updateUI();
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				presionado = true;

			}
		});
		tf4.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				p4.remove(pb);
				p4.updateUI();
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});
		tf4.addKeyListener(new KeyListener() {
			boolean presionado = false;

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (presionado) {

					String password = "";
					char[] pwd = tf4.getPassword();
					for (char c : pwd) {
						password = password + c;
					}
					if (password != null) {
						checkPwd(password);

					}
					presionado = false;
					if (tf4.getPassword().length > 0) {
						p4.add(pb);

					} else {
						p4.remove(pb);

					}
					p4.updateUI();

					String password2 = "";
					char[] pwd2 = tf5.getPassword();
					for (char c2 : pwd2) {
						password2 = password2 + c2;
					}
					if (password2.equals(password) && !password.equals("")) {
						cambiarIcono(pathAccept, label1);
						p5.add(label1);
						Constantes.controlPasswordR = true;
					} else {
						p5.remove(label1);
						Constantes.controlPasswordR = false;
					}
					p5.updateUI();
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				presionado = true;

			}
		});
		tf5.addKeyListener(new KeyListener() {
			boolean presionado = false;

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (presionado) {
					String password = "";
					char[] pwd = tf5.getPassword();
					for (char c : pwd) {
						password = password + c;
					}
					String password2 = "";
					char[] pwd2 = tf4.getPassword();
					for (char c2 : pwd2) {
						password2 = password2 + c2;
					}
					if (password2.equals(password) && !password.equals("")) {
						cambiarIcono(pathAccept, label1);
						p5.add(label1);
						Constantes.controlPasswordR = true;
					} else {
						p5.remove(label1);
						Constantes.controlPasswordR = false;
					}
					p5.updateUI();
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				presionado = true;

			}
		});
		tf1.addKeyListener(new KeyListener() {
			boolean presionado = false;

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (presionado) {
					if (tf1.getText().length() >= 6 && tf1.getText().length() <= 14) {
						MainCliente.sendUserRequest(tf1.getText());

					} else {
						Constantes.controlUsuario = false;
						p1.remove(label2);
						p1.updateUI();
					}
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				presionado = true;

			}
		});
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Constantes.controlPassword && Constantes.controlPasswordR && Constantes.controlUsuario
						&& Constantes.controlTelefono && Constantes.controlEmail) {
					MainCliente.sendEmailSendRequest(tf3.getText(), tf1.getText());
					p33.add(codeL);
					p33.add(verification);
					waiting();
					verification.addKeyListener(new KeyListener() {
						boolean presionado = false;

						@Override
						public void keyTyped(KeyEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void keyReleased(KeyEvent e) {
							if (presionado) {
								if (verification.getText().trim().equals(code)) {
									String password = "";
									char[] pwd = tf5.getPassword();
									for (char c : pwd) {
										password = password + c;
									}
									MainCliente.createRegisterRequest(tf1.getText(), password, tf2.getText(),
											tf3.getText());
									presionado = false;
									labelCode.setText("Creating account");
									p33.add(labelCode);
									p33.updateUI();

								} else if (verification.getText().trim().length() == 6) {
									cambiarIcono(pathError, labelCode);
									p33.add(labelCode);
									p33.updateUI();
									// error
								} else {
									p33.remove(labelCode);
									p33.updateUI();
								}
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
							presionado = true;

						}
					});

				} else {
					if (!Constantes.controlUsuario) {
						cambiarIcono(pathError, label2);
						p1.add(label2);
						p1.updateUI();
					} else {
						cambiarIcono(pathAccept, label2);
						label2.setText("");
						p1.add(label2);
						p1.updateUI();
					}
					if (!Constantes.controlPassword) {
						cambiarIcono(pathError, label);

						p4.remove(pb);

						p4.add(label);

						p4.updateUI();
					} else {
						cambiarIcono(pathAccept, label);
						label.setText("");
						p4.add(label);
						p4.updateUI();
					}
					if (!Constantes.controlPasswordR) {
						cambiarIcono(pathError, label1);
						p5.add(label1);
						p5.updateUI();
					} else {
						cambiarIcono(pathAccept, label1);
						label1.setText("");
						p5.add(label1);
						p5.updateUI();
					}
					if (!Constantes.controlEmail) {
						cambiarIcono(pathError, label4);
						p3.add(label4);
						p3.updateUI();
					} else {
						cambiarIcono(pathAccept, label4);
						label4.setText("");
						p3.add(label4);
						p3.updateUI();
					}
					if (!Constantes.controlTelefono) {
						cambiarIcono(pathError, label3);
						p2.add(label3);
						p2.updateUI();
					} else {
						cambiarIcono(pathAccept, label3);
						label3.setText("");
						p2.add(label3);
						p2.updateUI();
					}
				}
			}

	

		});
	}
	public void waiting() {
		info = "Trying to send a verification email";
		background.setText("Waiting");
		background2.setText(info);
		pBoton.remove(b);
		pBoton.updateUI();
		p11.add(background);
		p22.add(background2);
		p6.add(p11);
		p6.add(p22);
		p6.updateUI();
		
		tf1.setEnabled(false);
		tf4.setEnabled(false);
		tf5.setEnabled(false);
		tf2.setEnabled(false);
		tf3.setEnabled(false);

	}
	public void cambiarIcono(String icono, JLabel l) {
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(icono)); // load
																			// the
																			// image
																			// to
																			// a
																			// imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH); // scale
																						// it
																						// the
																						// smooth
																						// way
		imageIcon = new ImageIcon(newimg);
		l.setIcon(imageIcon);
	}

	public void aplicarDecoracion(JTextField tf, JLabel l) {
		tf.setForeground(new Color(0, 0, 0));
		tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 76, 153)));
		tf.setOpaque(false);
		tf.setHorizontalAlignment(JTextField.CENTER);
		l.setFont(f);
		l.setForeground(new Color(255, 255, 255));
		tf.setFont(f);
	}

	public void aplicarDecoracion(JTextField tf) {
		tf.setForeground(new Color(0, 0, 0));
		tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 76, 153)));
		tf.setOpaque(false);
		tf.setHorizontalAlignment(JTextField.CENTER);
		tf.setFont(f);
	}

	public void aplicarDecoracionEspecial(JTextField tf, JLabel l) {
		tf.setForeground(new Color(0, 0, 0));
		tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 76, 153)));
		tf.setOpaque(false);
		l.setFont(f);
		l.setForeground(new Color(255, 255, 255));
		tf.setFont(f);
	}

	public void quitarTodo() {
		RegisterActivity.this.p1.remove(tf1);

		RegisterActivity.this.p2.remove(tf2);
		RegisterActivity.this.p3.remove(tf3);
		RegisterActivity.this.p4.remove(tf4);
		RegisterActivity.this.p5.remove(tf5);
		RegisterActivity.this.p1.remove(l1);
		RegisterActivity.this.p2.remove(l2);
		RegisterActivity.this.p3.remove(l3);
		RegisterActivity.this.p4.remove(l4);
		RegisterActivity.this.p5.remove(l5);
		RegisterActivity.this.p1.remove(label2);
		RegisterActivity.this.p2.remove(label3);
		RegisterActivity.this.p3.remove(label4);
		RegisterActivity.this.p4.remove(label);
		RegisterActivity.this.p5.remove(label1);
		RegisterActivity.this.p1.updateUI();
		RegisterActivity.this.p2.updateUI();
		RegisterActivity.this.p3.updateUI();
		RegisterActivity.this.p4.updateUI();
		RegisterActivity.this.p5.updateUI();

	}

	public void registerCreated() {
		cambiarIcono(pathAccept, labelCode);
		labelCode.setText("");
		pBoton.updateUI();
		labelCode.setText("Account created");
		p33.add(labelCode);
		p33.updateUI();
		background.setText("The account was succesfully created");
		background2.setText("Please go to the main place to see our feautures");

		background2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				RegisterActivity.this.dispose();
				MainApplication.createLoginActivity();

			}

		});
		verification.setEnabled(false);
		quitarTodo();
		t2.continuing = false;
	}

	public void registerError() {
		background.setText("We couldn´t create the account. Try it later");
		verification.setEnabled(false);
		p33.remove(labelCode);
		p33.updateUI();
		quitarTodo();
		t2.continuing = false;
	}

	protected void checkPwd(String password) {
		int complejidad = 0;
		if (password.length() >= 1) {
			complejidad += 5;
			if (password.length() >= 4) {
				complejidad += 10;
				if (password.length() >= 8) {
					complejidad += 15;

					if (password.length() >= 12) {
						complejidad += 10;

					}
				}
			}
		}
		char clave;
		byte contNumero = 0, contLetraMay = 0, contLetraMin = 0, contCaracter = 0;
		for (byte i = 0; i < password.length(); i++) {
			clave = password.charAt(i);
			String passValue = String.valueOf(clave);
			if (passValue.matches("[A-Z]")) {
				contLetraMay++;
			} else if (passValue.matches("[a-z]")) {
				contLetraMin++;
			} else if (passValue.matches("[0-9]")) {
				contNumero++;
			} else if (passValue.matches("\\W")) {
				contCaracter++;
			}
		}
		if (contNumero >= 1) {
			complejidad += 15;

		}
		if (contLetraMay >= 1) {
			complejidad += 15;

		}
		if (contLetraMin >= 1) {
			complejidad += 15;

		}
		if (contCaracter >= 1) {
			complejidad += 15;

		}
		if (password.length() < 16) {

			valuePb = complejidad;

			if (valuePb >= 75) {
				cambiarIcono(pathAccept, label);

				p4.add(label);
				p4.updateUI();
				Constantes.controlPassword = true;
			} else {

				p4.remove(label);
				p4.updateUI();
				Constantes.controlPassword = false;

			}
		} else {
			p4.remove(label);
			p4.updateUI();

			MessageUtils.logn("Contraseña demasiado larga");
		}

		// }else
		// if(valuePb>=50&&valuePb<75){
		// pb.setString("Correct security");
		// }else if(valuePb<50){
		// pb.setString("Low security");
		// }
	}

	public void updateUserLabel(boolean c) {
		if (c) {
			cambiarIcono(pathAccept, label2);
			label2.setText("");
			Constantes.controlUsuario = true;
			p1.add(label2);
		} else {
			Constantes.controlUsuario = false;
			label2.setText(" Ya existe el usuario ");

			label2.setForeground(new Color(153, 0, 0));
			cambiarIcono(pathError, label2);
			p1.add(label2);
		}
		p1.updateUI();

	}

	public void updateEmailLabel(boolean c) {
		if (c) {
			cambiarIcono(pathAccept, label4);
			label4.setText("");
			p3.add(label4);
			Constantes.controlEmail = true;
		} else {
			Constantes.controlEmail = false;
			cambiarIcono(pathError, label4);
			label4.setForeground(new Color(153, 0, 0));
			label4.setText("Este email esta usado");
			p3.add(label4);
			p3.updateUI();
		}

	}

	public void updateUIPendingCode(String code, boolean status) {

		if (suspended) {
			suspended = false;

		}

		if (status) {
			this.code = code;
			if (!resent) {

				p11.add(background);
				p22.add(background2);

				codeL.setFont(f);

				p33.add(codeL);
				p33.add(verification);

				p6.add(p11);
				p6.add(p22);

				p6.updateUI();
				p4.remove(pb);
				p4.updateUI();
				
				background.setText("Waiting for code");
				info = "We have sent you a verification email";
				pBoton.remove(b);
				pBoton.add(p33);
				pBoton.setOpaque(true);
				t2.start();

			} else {
				t2.resume();
			}

		} else {
			info = "Check your connection or try it later";
			background.setText("The server couldn't send the verification email");
			background2.setText(info);
			pBoton.remove(b);
			pBoton.updateUI();
			p11.add(background);
			p22.add(background2);
			p6.add(p11);
			p6.add(p22);
			p6.updateUI();
			t2.continuing = false;
			quitarTodo();
		}
	}

}
