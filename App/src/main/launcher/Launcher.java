package main.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Launcher extends JFrame {
	Font font = new Font("Microsoft Sans Sherif",Font.BOLD,25);
	JPanelBackground mainPanel = new JPanelBackground();
	JTextField tf1 = new JTextField(10);
	JTextField tf2 = new JTextField(10);
	JLabel l = new JLabel("launcher");
	JButton b = new JButton("Check");
	JPanel arriba = new JPanel(new FlowLayout());
	JPanel abajo = new JPanel(new FlowLayout());
	JPanel centro = new JPanel();
	JPanel p1 = new JPanel(new FlowLayout());
	JPanel p2 = new JPanel(new FlowLayout());
	
  
	public Launcher(){
		
		
	mainPanel.setLayout(new BorderLayout());
	mainPanel.setBackground("fondo.jpg");
	this.setContentPane(mainPanel);
	this.setSize(800, 500);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLocationRelativeTo(null);
	this.setResizable(false);
	this.setUndecorated(false);
	
	mainPanel.setOpaque(false);
	arriba.setOpaque(false);
	abajo.setOpaque(false);
	centro.setOpaque(false);
	
	aplicarDecoracion(tf1);
	aplicarDecoracion(tf2);
	centro.add(tf1);
	//addBorder(centro);
	
	font = font.deriveFont(Font.PLAIN,25);
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	ge.registerFont(font);
	tf1.setFont(font);
	font = font.deriveFont(Font.PLAIN, 18);
	tf1.setBorder(BorderFactory.createTitledBorder(null, "Username/email", TitledBorder.CENTER, TitledBorder.TOP, font,new Color(0,0,153)));
	font = font.deriveFont(Font.PLAIN,25);
	tf2.setFont(font);
	font = font.deriveFont(Font.PLAIN, 18);
	tf2.setBorder(BorderFactory.createTitledBorder(null, "Password", TitledBorder.CENTER, TitledBorder.TOP, font,new Color(0,0,153)));
	p1.add(tf1);
	p2.add(tf2);
	centro.add(p1);
	p1.setOpaque(false);
	p2.setOpaque(false);
	
	centro.add(p2);
	centro.setBorder(new LineBorder(Color.WHITE));
	centro.revalidate();
	centro.repaint();
	l.setFont(new Font("Blade Runner Movie Font", Font.PLAIN, 25));
	l.setForeground(Color.white);
	arriba.add(l);
	mainPanel.add(arriba,BorderLayout.NORTH);
	
	abajo.add(b);
	mainPanel.add(abajo,BorderLayout.SOUTH);
	
	mainPanel.add(centro,BorderLayout.CENTER);


	}
	public void aplicarDecoracion(JTextField tf){
		tf.setForeground(new Color(0,0,0));
		tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,new Color(0,76,153)));
		tf.setOpaque(false);
		tf.setHorizontalAlignment(JTextField.CENTER);
		
	}
	public void addBorder(JComponent c){
		
		Font font = new Font("Serif", Font.ITALIC, 12);
		c.setBorder( BorderFactory.createTitledBorder("Username"));

	}
}
