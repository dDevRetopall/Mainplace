package main.mainApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

import cliente.net.connection.MainCliente;
import gui.jcomponents.Button;
import gui.jcomponents.ButtonWithSize;
import gui.jcomponents.JPanelBackground;
import gui.jcomponents.TableDemo;
import gui.popup.popupFrame;
import productos.Producto;

public class MainApplicationWindow extends JFrame {
	int WIDTH, HEIGHT;
	JPanelBackground mainPanel = new JPanelBackground();
	JPanel p = new JPanel();
	JPanel p1 = new JPanel(new FlowLayout());
	JPanel p2 = new JPanel(new FlowLayout());
	JPanel p3 = new JPanel(new FlowLayout());
	public ButtonWithSize b = new ButtonWithSize(220, 70, new Color(0, 132, 0), "myproducts.jpg", "My products");
	public ButtonWithSize b1 = new ButtonWithSize(220, 70, new Color(0, 0, 153), "search.jpg", "Find products");
	public ButtonWithSize b2 = new ButtonWithSize(220, 70, new Color(255, 128, 0), "chats.jpg", "Chats");
	JPanel pTitle = new JPanel(new FlowLayout());
	JLabel l = new JLabel();

	JPanel pUsername = new JPanel(new FlowLayout());
	JLabel l2 = new JLabel();

	Font font = new Font("Microsoft Sans Sherif", Font.BOLD, 18);

	public MainApplicationWindow(String username) {
		Constantes.usuario = username;
		l2.setText("Hi, " + username);
		l2.setFont(font.deriveFont(Font.BOLD, 30));

		l.setText("main place");
		l.setFont(new Font("Blade Runner Movie Font", Font.PLAIN, 80));
		p.setLayout(new FlowLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		mainPanel.setLayout(null);
		mainPanel.setBackground("fondo.jpg");
		this.setContentPane(mainPanel);
		this.setTitle("MainPlace");
		this.setSize(1180, 820);
		WIDTH = this.getSize().width;
		HEIGHT = this.getSize().width;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(false);

		pTitle.add(l);
		pTitle.setBounds(0, 20, WIDTH, 200);
		pTitle.setOpaque(false);

		pUsername.add(l2);
		pUsername.setBounds(0, 100, WIDTH, 50);
		pUsername.setOpaque(false);
		this.add(pTitle);
		this.add(pUsername);
		p.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p1.add(b);
		p2.add(b1);
		p3.add(b2);
		p.add(p1, gbc);
		p.add(p2, gbc);
		p.add(p3, gbc);
		p.setBounds(0, HEIGHT / 2, WIDTH, 100);

		mainPanel.add(p);

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainApplication.openProductWindow(false);
				MainCliente.getProducts(Constantes.usuario);
				// Visible when te products come

			}
		});
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainApplication.openSearchWindow();
				

			}
		});

	}

}
