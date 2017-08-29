package products.myProducts.createProduct;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import gui.jcomponents.Button;
import gui.jcomponents.ImageLabel;
import gui.jcomponents.JPanelBackground;
import gui.jcomponents.Label;
import main.mainApplication.Constantes;
import main.mainApplication.MainApplication;
import utils.image.imageEditor.SearchImage;

public class PanelEditorImagen extends JFrame {
	private Image img;
	JPanelBackground p = new JPanelBackground();
	JLabel l = new JLabel();
	JPanel buttons = new JPanel(new FlowLayout());
	int borde = 20;
	Button b = new Button(new Color(0, 132, 0), "imageicon.jpg", "Add image");
	Button b2 = new Button(new Color(0, 0, 153), "editimage.jpg", "Edit image");
	Button b3 = new Button(new Color(204, 0, 0), "error.jpg", "Remove");
	Button b4 = new Button(new Color(0, 132, 0), "imageicon.jpg", "Create");
	Image general,foto;
	public PanelEditorImagen(Image img) {
		
		this.setContentPane(p);
		
		
		p.setBackground("fondo.jpg");

		if (img == null) {
			l.setText("No image");
			habilitacionBotonesHayImagen(false);
			this.setSize(800, 600);
		} else {
			this.setSize(800, 600);
			habilitacionBotonesHayImagen(true);

			l.setIcon(new ImageIcon(img));
		}
		l.setFont(l.getFont().deriveFont(Font.BOLD, 18));
		l.setBorder(new MatteBorder(2, 2, 2, 2, Color.black));
		l.setBounds(this.getSize().width / 2 - Constantes.WIDTHRequiredImg / 2,
				this.getSize().height / 2 - Constantes.HEIGHTRequiredImg / 2 - 50, Constantes.WIDTHRequiredImg,
				Constantes.HEIGHTRequiredImg);
		this.setLocationRelativeTo(null);
		this.setTitle("Editor image");
		l.setHorizontalAlignment(JLabel.CENTER);
		p.setLayout(null);
	
		buttons.setBounds(0, 480, this.getSize().width, 300);
		buttons.add(b);
		buttons.setOpaque(false);
		buttons.add(b2);
		buttons.add(b3);
		p.add(buttons);
		p.add(l);
		this.img = img;
		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainApplication.pww.ImagenVerdadera = null;
				l.setText("No image");
				l.setIcon(null);
				MainApplication.pww.quitarImagen();
				habilitacionBotonesHayImagen(false);
				PanelEditorImagen.this.dispose();
				

			}
		});
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PanelEditorImagen.this.dispose();
				new SearchImage(true);
				
			}
		});
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PanelEditorImagen.this.dispose();
				new SearchImage(true);
				
			}
		});
		b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PanelEditorImagen.this.dispose();
				updateImage(general, foto);
				
			}
		});
		this.setVisible(true);

	}

	public void habilitacionBotonesHayImagen(boolean habilitar) {
		if (habilitar) {
			b.setHabilitado(false);
			b2.setHabilitado(true);
			b3.setHabilitado(true);
		} else {
			b.setHabilitado(true);
			b2.setHabilitado(false);
			b3.setHabilitado(false);
		}
	}
	public void updateImage(Image general,Image foto){
			
			MainApplication.pww.editarImagenPerfilProducto(general, foto);
			
			
		
	}

	public void prepareForCreation(Image general,Image foto) {
		this.foto=foto;
		this.general=general;
		buttons.remove(b2);
		buttons.remove(b3);
		buttons.remove(b);
		buttons.add(b4);
		
		buttons.updateUI();
		System.out.println(general.getWidth(null));
		
		
	}
	
}
