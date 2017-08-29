package products.myProducts.createProduct;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;
import gui.jcomponents.JPanelBackground;
import gui.popup.popupFrame;
import gui.jcomponents.*;
import main.mainApplication.Constantes;
import main.mainApplication.MainApplication;
import products.myProducts.main.ConstantesCategorias;

public class CreateProductWindow extends JFrame {
	int WIDTH, HEIGHT;
	Label check1 = new Label();
	Label check2 = new Label();
	Label check3 = new Label();
	public Image ImagenVerdadera = null;
	JPanel pImagen = new JPanel() {
		protected void paintComponent(Graphics g) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			super.paintComponent(g);
		}
	};
	public ImageLabel imagen = new ImageLabel(100);

	JPanel titulo = new JPanel(new FlowLayout());
	JLabel tituloL = new JLabel("Create a new product");
	JPanelBackground mainPanel = new JPanelBackground();
	JPanel informacion = new JPanel(new GridLayout(8, 1));
	JPanel p = new JPanel();
	JPanel p1 = new JPanel(new FlowLayout());
	JPanel p2 = new JPanel(new FlowLayout());
	JPanel p3 = new JPanel(new FlowLayout());

	Button b = new Button(new Color(0, 132, 0), "new.jpg", "Create");
	Button b2 = new Button(new Color(255, 128, 0), "edit.jpg", "Check");
	Button b3 = new Button(new Color(0, 0, 153), "imageicon.jpg", "Edit image");
	Font font = new Font("Microsoft Sans Sherif", Font.BOLD, 18);
	Font f = new Font("Arial", Font.PLAIN, 22);

	JPanel p11 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l1 = new JLabel("Nombre del producto: ");
	JTextField tf1 = new JTextField(15);

	JPanel p22 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l2 = new JLabel("Precio: ");
	JTextField tf2 = new JTextField(5);
	JComboBox<String> combo4;

	JPanel p33 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l3 = new JLabel("Breve informacion");
	JTextArea tf3 = new JTextArea(3, 40);

	JPanel p44 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l4 = new JLabel("Categoria ");
	JComboBox<String> combo;
	JPanel p55 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l5 = new JLabel("Precio negociable ");
	JComboBox<String> combo2;

	JPanel p66 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l6 = new JLabel("Tipo ");
	JComboBox<String> combo3;
	private NumberFormat amountDisplayFormat;
	private NumberFormat amountEditFormat;

	public CreateProductWindow() {

		this.setContentPane(mainPanel);
		this.setTitle("MainPlace - Products - New product");
		this.setSize(1080, 720);
		WIDTH = this.getSize().width;
		HEIGHT = this.getSize().width;
		this.setLocationRelativeTo(null);
		this.setUndecorated(false);
		tituloL.setFont(new Font("Blade Runner Movie Font", Font.PLAIN, 25));
		tituloL.setForeground(Color.white);
		titulo.add(tituloL);
		informacion.add(tituloL);
		b.setHabilitado(false);

		p11.add(l1);
		p11.add(tf1);
		aplicarDecoracionEspecial(tf1, l1);
		p11.setOpaque(false);
		informacion.add(p11);

		p22.add(l2);
		p22.add(tf2);
		combo4 = new JComboBox<String>();
		p22.add(combo4);
		combo4.addItem("euros");
		combo4.addItem("dolares");
		combo4.addItem("libras");
		aplicarDecoracionEspecial(tf2, l2);
		aplicarDecoracionEspecial(combo4, 100, 40);
		p22.setOpaque(false);
		informacion.add(p22);

		

		combo = new JComboBox<String>();
		for (String s : ConstantesCategorias.categorias) {
			combo.addItem(s);
		}
		combo.setOpaque(false);
		p44.add(l4);
		p44.add(combo);
		informacion.add(p44);

		aplicarDecoracionEspecial(combo, l4, 300);
		p44.setOpaque(false);

		combo2 = new JComboBox<String>();
		combo2.addItem("Si");
		combo2.addItem("No");
		combo2.setOpaque(false);
		p55.add(l5);
		p55.add(combo2);
		informacion.add(p55);
		aplicarDecoracionEspecial(combo2, l5, 200);
		p55.setOpaque(false);

		combo3 = new JComboBox<String>();
		for (String s : ConstantesCategorias.tipos) {
			combo3.addItem(s);
		}

		combo3.setOpaque(false);
		p66.add(l6);
		p66.add(combo3);
		informacion.add(p66);
		aplicarDecoracionEspecial(combo3, l6, 320);
		p66.setOpaque(false);
		

	    
		informacion.add(l3);
		aplicarDecoracionEspecial(tf3, l3);
		
		informacion.updateUI();
		
		JScrollPane sc = new JScrollPane(tf3);
		sc.getViewport().setOpaque(false);
		sc.setOpaque(false);
		sc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		sc.setBorder(new MatteBorder(2,2,2,2,new Color(0,0,153)));
		sc.updateUI();
		p33.add(sc);
		tf3.setBorder(null);
		p33.setOpaque(false);
		informacion.add(p33);
		
		informacion.setOpaque(false);
		informacion.setBounds(50, 30, 700, 600);

		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		GridBagConstraints gbc = new GridBagConstraints();
		mainPanel.setLayout(null);
		mainPanel.setBackground("fondo.jpg");

		p.setBorder(BorderFactory.createTitledBorder(new MatteBorder(3, 3, 3, 3, new Color(0, 0, 153)), "Actions",
				TitledBorder.CENTER, TitledBorder.TOP, font, new Color(0, 0, 0)));
		p.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p1.add(b);
		p2.add(b2);
		p3.add(b3);
		p.add(p1, gbc);
		p.add(p2, gbc);
		p.add(p3, gbc);
		p.setBounds(WIDTH - 400, 350, 230, 280);

		pImagen.setLayout(null);
		pImagen.setBorder(new MatteBorder(3, 3, 3, 3, new Color(0, 0, 153)));
		pImagen.setBounds(WIDTH - 410, 100, Constantes.ImgProfileX, Constantes.ImgProfileY);
		imagen.setBounds(125 - imagen.getScale() / 2, 125 - imagen.getScale() / 2, imagen.getScale(),
				imagen.getScale());

		pImagen.add(imagen);
		pImagen.setOpaque(false);
		pImagen.setBackground(new Color(0, 0, 0, 0));
		mainPanel.add(p);
		mainPanel.add(informacion);
		mainPanel.add(pImagen);

		pImagen.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {

				pImagen.setBackground(new Color(0, 0, 200, 55));
				super.mouseEntered(e);
			}

		});
		pImagen.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				pImagen.setOpaque(false);
				pImagen.setBackground(new Color(224, 224, 224, 0));
				super.mouseExited(e);

			}

		});
		pImagen.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				new PanelEditorImagen(ImagenVerdadera);
				super.mouseClicked(e);

			}

		});
		b3.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				new PanelEditorImagen(ImagenVerdadera);
				super.mouseClicked(e);

			}

		});

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				popupFrame popup = new popupFrame("Seguro que quieres crear el producto") {

					@Override
					protected void function() {
						boolean negociable = false;
						if (combo2.getSelectedIndex() == 0) {
							negociable = true;
						}
						MainApplication.pw.createNewProduct(tf1.getText(), Double.parseDouble(tf2.getText()),
								tf3.getText(), negociable, combo.getSelectedIndex(), combo3.getSelectedIndex(),ImagenVerdadera);
						MainApplication.pww.dispose();
					}
				};
				popup.setVisible(true);

			}

		});
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				check();
			}

		});
		tf1.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				b.setHabilitado(false);
				super.keyPressed(e);
			}

		});
		tf2.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				b.setHabilitado(false);
				super.keyPressed(e);
			}

		});
		tf3.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				b.setHabilitado(false);
				super.keyPressed(e);
			}

		});

	}

	public void check() {
		boolean productoCheck = false;
		String nombreProducto = tf1.getText();
		if (!nombreProducto.isEmpty()) {
			if (nombreProducto.length() >= 4) {
				check1.changeIcon("accept.jpg", "");
				productoCheck = true;
				if (nombreProducto.length() <= 20) {
					check1.changeIcon("accept.jpg", "");
					productoCheck = true;
				} else {
					check1.changeIcon("error.jpg", "Maximum characters 20");
					productoCheck = false;
				}
			} else {

				check1.changeIcon("error.jpg", "Minimum characters 4");
				productoCheck = false;
			}
		} else {
			check1.changeIcon("error.jpg", "Empty field");
			productoCheck = false;
		}

		p11.add(check1);
		p11.updateUI();

		boolean precioCheck = false;
		String valor = tf2.getText();
		int contador = 0;

		for (int i = 0; i < valor.length(); i++) {
			char c = valor.charAt(i);
			if (Character.isDigit(c) || c == '.' || c == ',') {
				contador++;
			}

		}
		if (!valor.isEmpty()) {
			if (contador == valor.length()) {
				check2.changeIcon("accept.jpg", "");
				precioCheck = true;
				if (valor.length() > 8) {
					check2.changeIcon("error.jpg", "Maximum digits 8");
					precioCheck = false;
				}
			} else {
				check2.changeIcon("error.jpg", "Invalid characters");
				precioCheck = false;
			}
		} else {
			check2.changeIcon("error.jpg", "Empty field");
			precioCheck = false;
		}
		p22.add(check2);
		p22.updateUI();

		boolean infoCheck = false;
		String info = tf3.getText();
		if (!info.isEmpty()) {
			check3.changeIcon("accept.jpg", "");
			if (info.length() >= 120) {
				check3.changeIcon("error.jpg", "Maximum characters 120");
				infoCheck = false;
			} else {
				check3.changeIcon("accept.jpg", "");
				infoCheck = true;
			}
		} else {
			check3.changeIcon("error.jpg", "Empty field");
		}
		p33.add(check3);
		p33.updateUI();

		if (precioCheck && productoCheck && infoCheck) {
			b.setHabilitado(true);
		}
	}

	public void aplicarDecoracionEspecial(JTextField tf, JLabel l) {
		UIManager.put("TextField.selectionBackground", new Color(210, 210, 210));
		UIManager.put("TextField.selectionForeground", new ColorUIResource(Color.BLACK));
		tf.updateUI();
		tf.setForeground(new Color(0, 0, 0));
		tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 76, 153)));
		tf.setOpaque(false);
		l.setFont(f);
		l.setForeground(new Color(255, 255, 255));
		tf.setFont(f);
	}

	public void aplicarDecoracionEspecial(JTextArea tf, JLabel l) {
		UIManager.put("TextArea.selectionBackground", new Color(210, 210, 210));
		UIManager.put("TextArea.selectionForeground", new ColorUIResource(Color.BLACK));
		tf.updateUI();
		tf.setForeground(new Color(0, 0, 0));

		tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 0, 153)));
		tf.setOpaque(false);

		tf.setFont(f.deriveFont(Font.PLAIN, 18));
		l.setForeground(new Color(255, 255, 255));
		l.setFont(f);
	}

	public void aplicarDecoracionEspecial(JComboBox tf, JLabel l, int width) {

		UIManager.put("ComboBox.background", new ColorUIResource(224, 224, 224));
		UIManager.put("ComboBox.selectionBackground", new Color(210, 210, 210));
		UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.BLACK));
		tf.updateUI();
		tf.setFocusable(false);
		informacion.updateUI();
		tf.setForeground(new Color(0, 0, 0));
		tf.setPreferredSize(new Dimension(width, 50));

		tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 76, 153)));
		tf.setOpaque(false);

		l.setFont(f);
		l.setForeground(new Color(255, 255, 255));
		tf.setFont(f);
	}

	public void aplicarDecoracionEspecial(JComboBox tf, int width, int height) {
		UIManager.put("ComboBox.background", new ColorUIResource(224, 224, 224));
		UIManager.put("ComboBox.selectionBackground", new Color(210, 210, 210));
		UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.BLACK));
		tf.updateUI();
		tf.setFocusable(false);
		informacion.updateUI();
		tf.setForeground(new Color(0, 0, 0));
		tf.setPreferredSize(new Dimension(width, height));

		tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 76, 153)));
		tf.setOpaque(false);

		tf.setFont(f);
	}

	public void editarImagenPerfilProducto(Image general, Image foto) {
		ImagenVerdadera = general;
		imagen.setBounds((125 - Constantes.ImgProfileX / 2) - 5, (125 - Constantes.ImgProfileY / 2) - 5,
				Constantes.ImgProfileX, Constantes.ImgProfileY);
		imagen.setIcon(new ImageIcon(foto));
		imagen.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
	}

	public void quitarImagen() {

		
		imagen.changeIcon("add.jpg", "");
		imagen.setBorder(null);
		imagen.setBounds(125 - imagen.getScale() / 2, 125 - imagen.getScale() / 2, imagen.getScale(),
				imagen.getScale());
		
	}



}
