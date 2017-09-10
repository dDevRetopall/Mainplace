package cliente.net.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import cliente.net.connection.MainCliente;
import constantes.ServerStatusConstants;
import gui.jcomponents.ImageLabel;
import gui.jcomponents.JPanelBackground;
import main.mainApplication.Constantes;
import main.mainApplication.MainApplication;

public class Launcher extends JFrame {
	Font font = new Font("Microsoft Sans Sherif", Font.BOLD, 25);
	JPanelBackground mainPanel = new JPanelBackground();
	JTextField tf1 = new JTextField();
	JPasswordField tf2 = new JPasswordField();
	JLabel l = new JLabel("launcher");
	JButton b = new JButton("Check");
	JPanel arriba = new JPanel(new FlowLayout());
	JPanel abajo = new JPanel(new FlowLayout());
	JPanel centro = new JPanel();
	JPanel p1 = new JPanel(new FlowLayout());
	JPanel p2 = new JPanel(new FlowLayout());
	JPanel p5 = new JPanel();
	JLabel createAccount = new JLabel("I don't have an account");
	public JLabel lMError = new JLabel ();
	CustomButton enter;
	int HEIGHT, WIDTH;
	ImageLabel il = new ImageLabel(20);
	JPanel north = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	private String mensajeError="Default error";
	private boolean enviarmensajeError=false;

	public Launcher(boolean conectado) {

		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

		GridBagConstraints gbc = new GridBagConstraints();

		tf1.setPreferredSize(new Dimension(120, 70));
		tf2.setPreferredSize(new Dimension(120, 70));

		mainPanel.setBackground("fondo.jpg");

		this.setContentPane(mainPanel);
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setUndecorated(false);
		this.setTitle("Launcher");
		this.setLayout(null);

		mainPanel.setOpaque(false);
		
		north.setOpaque(false);
		north.add(il);
		north.setBounds(0,0,750,30);
		il.setForeground(new Color(0,153,0));
		
		tf1.setPreferredSize(new Dimension(240,70));
		tf2.setPreferredSize(new Dimension(240,70));
		

		arriba.setOpaque(false);
		abajo.setOpaque(false);
		centro.setOpaque(false);

		aplicarDecoracion(tf1);
		aplicarDecoracion(tf2);

		HEIGHT = this.getHeight();
		WIDTH = this.getWidth();

		arriba.setBounds(WIDTH / 2 - 80, 15, 160, 60);
		mainPanel.add(arriba);
		mainPanel.add(north);

		font = font.deriveFont(Font.PLAIN, 21);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		tf1.setFont(font);
		font = font.deriveFont(Font.PLAIN, 18);
		tf1.setBorder(BorderFactory.createTitledBorder(new MatteBorder(2, 2, 2, 2, new Color(0, 0, 153)), "Username",
				TitledBorder.CENTER, TitledBorder.TOP, font, new Color(0, 0, 0)));
		font = font.deriveFont(Font.PLAIN, 15);
		tf2.setFont(font.deriveFont(Font.PLAIN,12));
		font = font.deriveFont(Font.PLAIN, 18);
		tf2.setBorder(BorderFactory.createTitledBorder(new MatteBorder(2, 2, 2, 2, new Color(0, 0, 153)), "Password",
				TitledBorder.CENTER, TitledBorder.TOP, font, new Color(0, 0, 0)));
		p1.add(tf1);
		p2.add(tf2);
		centro.add(p1, gbc);
		centro.add(p2, gbc);
		centro.setBounds(40, 40, 100, 100);
		p5.setBorder(new MatteBorder(2, 2, 2, 2, new Color(96, 96, 96)));
		p5.setBounds(WIDTH / 2 - (WIDTH / 3) / 2, 15 + 80, (int) (WIDTH / 3), (int) (HEIGHT / 2));
		p5.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);

		l.setFont(new Font("Blade Runner Movie Font", Font.PLAIN, 25));
		l.setForeground(Color.white);

		font = font.deriveFont(Font.PLAIN, 18);
		createAccount.setFont(font);

		createAccount.setForeground(Color.white);
		createAccount.setBounds(WIDTH / 2 - 95, HEIGHT - 195, 200, 50);
		createAccount.setForeground(new Color(0, 0, 153));
		createAccount.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Launcher.this.dispose();
				MainApplication.createRegisterActivity();
				super.mouseClicked(e);
			}

		});

		arriba.add(l);
		abajo.add(b);

		centro.setBounds(WIDTH / 2 - (WIDTH / 3) / 2, 15 + 80, (int) (WIDTH / 3), (int) (HEIGHT / 3));

		enter = new CustomButton();
		enter.setText("Log in");
		enter.setFont(font);
		enter.setSize(150, 50);
		enter.setBounds(WIDTH / 2 - 75, HEIGHT - 235, 150, 50);

		mainPanel.add(enter);
		mainPanel.add(createAccount);
		mainPanel.add(p5);
		mainPanel.add(centro);
		

		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(conectado) {
				String password = "";
				char[] pwd = tf2.getPassword();
				for (char c : pwd) {
					password = password + c;
				}
				if(!tf1.getText().equals("")&&!password.equals("")){
				
				MainCliente.sendLoginRequest(tf1.getText(), password);
				tf2.setText("");

				}else{
				enviarMensajeDeError("The fields must not be empty");	
				}
				}else {
					enviarMensajeDeError("Server is not working now");	
				}
			}
		});
		if(conectado) {
		MainCliente.requestStatusServer();
		}else {
		updateStatusServer(3);
		}
	}

	public void aplicarDecoracion(JTextField tf) {
		tf.setForeground(new Color(0, 0, 0));
		tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 76, 153)));
		tf.setOpaque(false);
		tf.setHorizontalAlignment(JTextField.CENTER);

	}

	public void abrirPerfil(String username) {
		Launcher.this.dispose();
		MainApplication.openMainApplicationWindow(username);
	}


	public void enviarMensajeDeError(String mensajeError) {
	
		
		
		lMError.setText(mensajeError);
		lMError.setBounds(new Rectangle(WIDTH / 2 - 450 / 2, HEIGHT - 150, 450, 70));
		lMError.setHorizontalAlignment(JLabel.CENTER);
		lMError.setFont(font);
		lMError.setForeground(new Color(204,0,0));
		mainPanel.add(lMError);
		mainPanel.updateUI();
	

	}
	public void updateStatusServer(int idStatusServer) {
		
		String status = ServerStatusConstants.status[idStatusServer];

		if(idStatusServer==0) {
			il.changeIcon("working.jpg", status);
			il.setForeground(new Color(0,153,0));
		}
		if(idStatusServer==1) {
			il.changeIcon("maintenance.jpg", status);
			il.setForeground(new Color(255,128,0));
		}
		if(idStatusServer==2) {
			il.changeIcon("maintenance.jpg", status);
			il.setForeground(new Color(255,128,0));
		}
		if(idStatusServer==3) {
			il.changeIcon("notworking.jpg", status);
			il.setForeground(new Color(200,0,0));
		}
		if(idStatusServer==4) {
			il.changeIcon("notworking.jpg", status);
			il.setForeground(new Color(200,0,0));
		}
		System.out.println(status);
		
	}

}
