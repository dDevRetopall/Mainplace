package products.findProducts.search;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import cliente.net.connection.MainCliente;
import gui.jcomponents.Button;
import gui.jcomponents.ButtonWithSize;
import gui.jcomponents.JPanelBackground;
import main.mainApplication.MainApplication;

public class SearchWindow extends JFrame{
	JTextField search = new JTextField(30);
	Font f = new Font("Arial", Font.PLAIN, 22);
	JPanel pSearch = new JPanel(new FlowLayout());
	public JPanelBackground mainPanel = new JPanelBackground();
	int width =(Toolkit.getDefaultToolkit().getScreenSize().width)*2/4;
	int height =(Toolkit.getDefaultToolkit().getScreenSize().height*3)/4;
	ButtonWithSize b = new ButtonWithSize(150,50,new Color(0,0,153),"search.jpg","Search");

	public Table t;
	public SearchWindow(){
		this.setTitle("Main Place - Search Engine");
		this.setSize(width,height);
		this.setLocationRelativeTo(null);
		mainPanel.setLayout(null);
		mainPanel.setBackground("fondo.jpg");
		pSearch.setOpaque(false);
		search.setHorizontalAlignment(JTextField.CENTER);
		this.setContentPane(mainPanel);
		aplicarDecoracionEspecial(search);
		pSearch.add(search);
		pSearch.add(b);
		pSearch.setBounds(0, 100, width, 100);


	
		mainPanel.add(pSearch);

		t= new Table();
		t.setBounds(100, 200, 600, 400);
		mainPanel.add(t);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				closeTopFrames();

			}

		});
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				t.cleanTable();
				MainCliente.requestSearch(search.getText());
				
				
			}
		});
	}
	public void closeTopFrames() {
		if (MainApplication.pview != null) {
			MainApplication.pview.dispose();
		}
	}
	public void aplicarDecoracionEspecial(JTextField tf) {
		UIManager.put("TextField.selectionBackground", new Color(210, 210, 210));
		UIManager.put("TextField.selectionForeground", new ColorUIResource(Color.BLACK));
		tf.updateUI();
	
		tf.setForeground(new Color(0, 0, 0));
		tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 0, 153)));
		tf.setOpaque(false);
		tf.setFont(f);
		tf.setPreferredSize(new Dimension(150,50));
	}
}
