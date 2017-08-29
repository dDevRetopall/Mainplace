package findProducts.search;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import cliente.net.connection.MainCliente;
import gui.jcomponents.Label;
import main.mainApplication.Constantes;
import main.mainApplication.MainApplication;
import productos.Producto;
import products.myProducts.main.ConstantesCategorias;
import products.myProducts.preview.PreviewWindow;
import utils.dataUtils.FontLoader;
import utils.imageUtils.ImageUtilsUpdater;

public class Table extends JScrollPane {
	public ArrayList<Integer> ids = new ArrayList<>();
	int borde = 10;
	ArrayList<CellPanel> cells = new ArrayList<>();
	Font font = FontLoader.createFont(Constantes.nameFolderFonts+"/AGENCYB.TTF", 18, "Bold");
	boolean izquierda = true;
	JPanel p = new JPanel() {
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(500, getYLenght());
		}
	};

	public Table() {
		this.setVisible(false);
		p.setLayout(null);

		p.setOpaque(false);
		p.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
		this.setViewportView(p);
		this.setOpaque(false);

		this.getViewport().setOpaque(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		getViewport().setPreferredSize(new Dimension(600, 400));
		getVerticalScrollBar().setUnitIncrement(5);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		

	}

	public int getYLenght() {
		int y = 0;
		for (CellPanel c : cells) {
			if (cells.indexOf(c) % 2 == 0) {
				y = y + borde + c.getHeight();
			}

		}
		return y;
	}

	public void addNewCell(JPanel p, SafeProducto s) {
		this.setVisible(true);
		p.setBackground(new Color(0,0,0,0));
		CellPanel cp = new CellPanel(p, s);

		int y = borde;

		for (CellPanel c : cells) {

			if (cells.indexOf(c) % 2 == 1) {
				y = y + borde + c.getHeight();
			}

		}

		if (izquierda) {
			cp.getPanel().setBounds(borde, y, 200, 120);

			izquierda = false;
		} else {
			cp.getPanel().setBounds(borde + 250, y, 200, 120);

			izquierda = true;
		}
		cells.add(cp);
		p.setOpaque(false);
		
		this.p.add(cp.getPanel());

		this.revalidate();
		this.repaint();

		p.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {

				for (CellPanel c : cells) {
					c.getPanel().setOpaque(false);
					c.getPanel().setBackground(new Color(0,0,0,0));
					c.getPanel().setBorder(null);
				}
				p.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
				p.setOpaque(false);
				p.setBackground(new Color(0,0,153,100));
				p.updateUI();

			}

		});
		p.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				MainApplication.openPreview((cp.getProductoSafe().getIdProducto()));

				SafeProducto ps = cp.getProductoSafe();
				if(!conditionView(ps.getUsuario(), ps.getIdProducto())){
				MainCliente.addView(ps.getIdProducto(), ps.getUsuario());
				Constantes.viewsLocalCache.add(new View(ps.getUsuario(),ps.getIdProducto()));
				}
				
				MainApplication.pview.update(new Producto(ps.getIdProducto(), ps.getNameProducto(), ps.getPrecio(),
						ps.getInformacion(), ps.getTipo(), ps.getCategoria(), ps.isNegociable(), ps.getImgbytes(),ps.getStatus()),
						ps.getUsuario());

			}

		});
		p.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				for (CellPanel c : cells) {
					c.getPanel().setOpaque(false);
					c.getPanel().setBackground(new Color(0,0,0,0));
					c.getPanel().setBorder(null);
				}

			}

		});

	}

	public void cleanTable() {
		for (int i = 0; i < cells.size(); i++) {
			this.p.remove(cells.get(i).getPanel());
			this.p.updateUI();

		}
		for (int i = 0; i < cells.size(); i++) {

			cells.remove(i);
		}
		this.setVisible(true);
		izquierda = true;
		cells.clear();
		System.out.println("Limpiado tabla");
		this.getVerticalScrollBar().setValue(0);
		this.updateUI();
	}

	public void createNewRow(String usuario, int id, String producto, String informacion, boolean negociable,
			int categoria, int tipo, double price, byte[] imageBytes,int views,int status) {
		String palabra = "Si";
		if (!negociable) {
			palabra = "No";
		}
		ids.add(id);
		ImageIcon imageIcon;
		if (imageBytes == null) {
			ImageIcon i = new ImageIcon(getClass().getResource("myproducts.jpg"));
			Image image = i.getImage();
			Image newimg = ImageUtilsUpdater.scale(image, 100, 100);
			imageIcon = new ImageIcon(newimg);

		} else {

			Image i = ImageUtilsUpdater.ByteArrayToImage(imageBytes);
			Image newimg = ImageUtilsUpdater.scale(i, 100, 100);
			imageIcon = new ImageIcon(newimg);
		}

		String s = producto + usuario;
		JPanel p = new JPanel() {

			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}

		};

		p.setLayout(null);
		JLabel precio = new JLabel(Double.toString(price) + "€");
		JLabel titulo = new JLabel(producto);
		titulo.setHorizontalAlignment(JLabel.CENTER);
		titulo.setForeground(new Color(0, 0, 153));
		titulo.setFont(new Font("Arial", Font.BOLD, 16));
		precio.setFont(new Font("Arial", Font.BOLD, 16));
		p.add(titulo);
		p.add(precio);
		titulo.setBounds(105, 15, 90, 15);
		precio.setBounds(105, 40, 90, 15);
		precio.setHorizontalAlignment(JLabel.CENTER);
		
		Label viewsL = new Label();
		viewsL.setBounds(105,85,90,20);
		viewsL.setHorizontalAlignment(JLabel.CENTER);
		viewsL.changeIcon("views.jpg", Integer.toString(views));
		viewsL.setForeground(Color.gray);
		viewsL.setFont(new Font("Arial", Font.BOLD, 18));
		p.add(viewsL);
		
		JLabel l = new JLabel();
		l.setIcon(imageIcon);
		l.setBounds(10, 10, 100, 100);
	
		l.setBorder(new LineBorder(Color.black));
		JPanel p2 = new JPanel(new FlowLayout());
		
		JLabel lStatus= new JLabel();
		lStatus.setHorizontalAlignment(JLabel.CENTER);
		lStatus.setText(ConstantesCategorias.fases[status].toUpperCase());
		lStatus.setFont(font);
		lStatus.setBounds(105, 60, 90, 20);
		if(status==0){
			lStatus.setForeground(new Color(0,153,0));
		}else if (status==1){
			lStatus.setForeground(new Color(255,128,0));
		}else if(status==2){
			lStatus.setForeground(new Color(64,64,64));
		}
		p2.setOpaque(false);

		
		p.add(l);
		p.add(lStatus);
		p.setSize(200, 120);
		
		JLabel texto = new JLabel();
		addNewCell(p, new SafeProducto(usuario, id, producto, informacion, negociable, categoria, tipo, price,
				imageIcon.getImage(), imageBytes,status));

	}
	public boolean conditionView(String username,int idProducto){
		boolean spam=false;
		for(View v:Constantes.viewsLocalCache){
			if((v.getUsername().equals(username))&&(v.getIdProducto()==idProducto)){
				spam=true;
				
			}
			
		}
		return spam;
	}
}
