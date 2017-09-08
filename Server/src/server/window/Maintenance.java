package server.window;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constantesLocalesServidor.ConstantesServer;

import server.main.Main;
import tools.datautils.MessageUtils;
import tools.mysqlutils.SQLConnection;

public class Maintenance extends JFrame {
	ImageIcon pathCorrect = new ImageIcon(getClass().getResource("working.jpg"));
	ImageIcon pathWrong = new ImageIcon(getClass().getResource("notworking.jpg"));
	JPanel p;
	JPanel pp = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel pp1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel pp2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel pp3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel pp4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel pp5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel pp6 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel pp7 = new JPanel(new FlowLayout(FlowLayout.CENTER));

	JButton b = new JButton("Apply");
	JButton b1 = new JButton("Accept");
	JButton b2 = new JButton("Cancel");

	JLabel l = new JLabel("Available (no maintenance)");
	JLabel l1 = new JLabel("Accepting connections");
	JLabel l2 = new JLabel("MySQL");
	JLabel l3 = new JLabel("Client handler");
	JLabel l4 = new JLabel("Server working");
	JLabel l5 = new JLabel("People connected: ");

	JLabel s = new JLabel("");
	JLabel s1 = new JLabel("");
	JLabel s2 = new JLabel("");
	JLabel s3 = new JLabel("");
	JLabel s4 = new JLabel("");

	JCheckBox c = new JCheckBox("");

	Font f = new Font("Arial", Font.PLAIN, 22);

	public Maintenance(MainWindow mw) {

		b1.setEnabled(false);
		p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		this.setSize(400, 300);
		this.setTitle("Maintenance");
		this.setLocationRelativeTo(mw);
		this.setContentPane(p);

		this.setResizable(false);
		GridBagConstraints gbc = new GridBagConstraints();
		pp.add(l);
		pp.add(s);
		pp1.add(l1);
		pp1.add(s1);
		pp2.add(l2);
		pp2.add(s2);
		pp3.add(l3);
		pp3.add(s3);
		pp4.add(l4);
		pp4.add(s4);
		pp5.add(l5);

		l.setFont(f);
		l1.setFont(f);
		l2.setFont(f);
		l3.setFont(f);
		l4.setFont(f);
		l5.setFont(f);
		l5.setForeground(new Color(0, 0, 153));

		Image image = pathCorrect.getImage(); // transform it
		pathCorrect = new ImageIcon(image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		Image image2 = pathWrong.getImage(); // transform it
		pathWrong = new ImageIcon(image2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));

		if (ConstantesServer.serverOperational) {
			s.setIcon(pathCorrect);
		} else {
			s.setIcon(pathWrong);
		}
		if (Main.aceptarConexiones) {
			s1.setIcon(pathCorrect);
		} else {
			s1.setIcon(pathWrong);
		}
		if (Main.ss != null) {
			if (!Main.ss.isClosed()) {
				s4.setIcon(pathCorrect);
			} else {
				s4.setIcon(pathWrong);
			}
		} else {
			s4.setIcon(pathWrong);
		}
		if (SQLConnection.getConnection() != null) {
			s2.setIcon(pathCorrect);
		} else {
			s2.setIcon(pathWrong);
		}

		s3.setIcon(pathCorrect);

		int total = Main.getPersonasConectadas();
		l5.setText("People connected: " + total);

		p.add(pp4, gbc);
		p.add(pp, gbc);
		p.add(pp1, gbc);
		p.add(pp2, gbc);
		p.add(pp3, gbc);

		p.add(pp5, gbc);

	}
}
