package gui.popup;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import gui.jcomponents.Button;
import gui.jcomponents.JPanelBackground;
import main.mainApplication.MainApplication;

public abstract class popupFrame extends JFrame{
	JPanel p2 = new JPanel(new FlowLayout());
	JPanelBackground principal = new JPanelBackground();
	JLabel l = new JLabel();
	JPanel p = new JPanel();
	Button b = new Button(new Color(0,204,0),"accept.jpg","Accept");
	Button b1 = new Button(new Color(204,0,0),"error.jpg","Cancel");
	public popupFrame(String text){
		principal.setBackground("popup.jpg");
		this.setSize(400,200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setTitle("Popup menu");
		this.setAlwaysOnTop(true);
		principal.setBorder(new MatteBorder(2,2,2,2,new Color(192,192,192)));
		principal.setLayout(null);
		principal.setBackground(Color.BLACK);
		p.add(b);
		p.add(b1);
		p2.add(l);
		principal.add(p2);
		principal.add(p);
		p.setOpaque(false);
		p2.setOpaque(false);
		l.setFont(new Font("Arial",Font.BOLD,18));
		l.setText(text);
		l.setForeground(Color.WHITE);
		p2.setBounds(0, 30,this.getSize().width,50);
		p.setBounds(0, 100, this.getSize().width,80 );
		this.setContentPane(principal);
	
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				function();
				popupFrame.this.dispose();
				
				
			}
		});
		b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				popupFrame.this.dispose();
			}
		});
		
		
	}
	 protected abstract void function();
}
