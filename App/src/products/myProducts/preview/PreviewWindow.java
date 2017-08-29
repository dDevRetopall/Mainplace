package products.myProducts.preview;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import cliente.net.connection.MainCliente;
import gui.jcomponents.Button;
import gui.jcomponents.ImageLabel;
import gui.jcomponents.JPanelBackground;
import main.mainApplication.Constantes;
import productos.Producto;
import products.myProducts.main.ConstantesCategorias;
import utils.dataUtils.FontLoader;
import utils.imageUtils.ImageUtilsUpdater;

public class PreviewWindow extends JFrame {
	JPanelBackground mainPanel = new JPanelBackground();
	private int idProducto;
	JPanel mainInformation = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel infoPrincipal = new JLabel();
	JTextArea desc = new JTextArea();
	JLabel title = new JLabel();
	JLabel user = new JLabel();
	JLabel status = new JLabel();
	JPanel pstatus = new JPanel();
	Font font = FontLoader.createFont(Constantes.nameFolderFonts+"/AGENCYB.TTF", 18, "Bold");
	JPanel pButtons = new JPanel(new FlowLayout());
	Button b = new Button(new Color(0,0,153),"chats.jpg","Start chat");
	Button b2 = new Button(new Color(0,0,153),"username.jpg","View user");
	ImageLabel il = new ImageLabel(Constantes.ImgProfileX);

	public PreviewWindow(int idProducto) {
		this.idProducto = idProducto;
		this.setSize(600, 400);
	
		this.setLocationRelativeTo(null);
		this.setContentPane(mainPanel);
		this.setTitle("Product");
		this.setResizable(false);
		mainPanel.setLayout(null);

		mainPanel.setBackground("bgprofile.jpg");
		mainInformation.setBounds(267, 50, 250, 150);
		
		mainInformation.setOpaque(false);
		mainPanel.add(mainInformation);
		infoPrincipal.setHorizontalAlignment(JLabel.CENTER);
		mainInformation.add(infoPrincipal);
	
		il.setBounds(15, 50, Constantes.ImgProfileX, Constantes.ImgProfileY);
		infoPrincipal.setFont(font);
		mainPanel.add(il);
		il.setBorder(new MatteBorder(2, 2, 2, 2, Color.white));
		infoPrincipal.setForeground(Color.WHITE);
		desc.setBounds(270, 150, 250, 160);

		desc.setFont(font);
		desc.setHighlighter(null);
		desc.setForeground(Color.WHITE);
		desc.setEditable(false);
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		desc.setOpaque(false);
		mainPanel.add(desc);
		title.setBounds(15, 0, 585, 50);
		title.setHorizontalAlignment(JLabel.CENTER);

		title.setForeground(Color.white);
		font = font.deriveFont(Font.BOLD, 25);
		title.setFont(font);
		mainPanel.add(title);
		
		pstatus.setBounds(17, 52, Constantes.ImgProfileX-4, 35);
		pstatus.setOpaque(true);
		pstatus.add(status);
		status.setFont(font);
		status.setForeground(Color.WHITE);
		mainPanel.add(pstatus,2,0);
		getProducto();

	}

	public void getProducto() {

		Constantes.productRequestForPreview = true;
		MainCliente.enviarProductRequestById(idProducto);

	}

	public void update(Producto p) {
		this.setTitle(p.getName());
		Image i = ImageUtilsUpdater.ByteArrayToImage(p.getImageBytes());
		Image profileImage = ImageUtilsUpdater.scale(i, Constantes.ImgProfileX, Constantes.ImgProfileY);
		String categoria = ConstantesCategorias.categorias[p.getCategoria()];
		String tipo = ConstantesCategorias.tipos[p.getTipo()];
		String negociable;
		if (p.isNegociable()) {
			negociable = "Precio negociable";
		} else {
			negociable = "Precio no negociable";
		}
		il.setIcon(new ImageIcon(profileImage));
		il.setBorder(new MatteBorder(2,2,2,2,new Color(0,0,153)));
		String status= ConstantesCategorias.fases[p.getStatus()];
		this.status.setText(status);
		if(p.getStatus()==0){
			this.pstatus.setBackground(new Color(0,153,0));
		}else if (p.getStatus()==1){
			this.pstatus.setBackground(new Color(255,128,0));
		}else if(p.getStatus()==2){
			this.pstatus.setBackground(new Color(64,64,64));
		}
		infoPrincipal.setText(convertToMultiline(
				"Precio: " + p.getPrice() + " €\nCategoria: " + categoria + "\nTipo: " + tipo + "\n" + negociable));
		
		desc.setText(p.getInformacion());
		title.setText(p.getName());
		this.setVisible(true);

	}
	public void update(Producto p,String username) {
		this.setTitle(p.getName());
		Image i = ImageUtilsUpdater.ByteArrayToImage(p.getImageBytes());
		Image profileImage = ImageUtilsUpdater.scale(i, Constantes.ImgProfileX, Constantes.ImgProfileY);
		String categoria = ConstantesCategorias.categorias[p.getCategoria()];
		String tipo = ConstantesCategorias.tipos[p.getTipo()];
		String negociable;
		if (p.isNegociable()) {
			negociable = "Precio negociable";
		} else {
			negociable = "Precio no negociable";
		}
		il.setIcon(new ImageIcon(profileImage));
		il.setBorder(new MatteBorder(2,2,2,2,new Color(0,0,153)));
		String status= ConstantesCategorias.fases[p.getStatus()];
		this.status.setText(status);
		if(p.getStatus()==0){
			this.pstatus.setBackground(new Color(0,153,0));
		}else if (p.getStatus()==1){
			this.pstatus.setBackground(new Color(255,128,0));
		}else if(p.getStatus()==2){
			this.pstatus.setBackground(new Color(64,64,64));
		}
		infoPrincipal.setText(convertToMultiline(
				"Precio: " + p.getPrice() + " €\nCategoria: " + categoria + "\nTipo: " + tipo + "\n" + negociable));
		desc.setText(p.getInformacion());
		title.setText(p.getName());

		pButtons.setBounds(0, 300, 600, 100);
		pButtons.setOpaque(false);
		pButtons.add(b);
		pButtons.add(b2);

		mainPanel.add(pButtons);
		user.setBounds(30, 10, 150, 30);
		user.setText("Created by "+username);
		user.setForeground(Color.WHITE);
		user.setFont(font.deriveFont(Font.BOLD, 18));
		mainPanel.add(user);
		mainPanel.updateUI();
	
		
		
		
		this.setVisible(true);

	}


	public static String convertToMultiline(String orig) {
		return "<html>" + orig.replaceAll("\n", "<br>");
	}
}
