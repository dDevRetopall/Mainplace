package products.myProducts.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

import cliente.net.connection.MainCliente;
import gui.jcomponents.Button;
import gui.jcomponents.JPanelBackground;
import gui.jcomponents.LabelButton;
import gui.jcomponents.TableDemo;
import gui.popup.popupFrame;
import main.mainApplication.Constantes;
import main.mainApplication.MainApplication;
import productos.Producto;
import products.myProducts.preview.PreviewWindow;
import utils.imageUtils.ImageUtilsUpdater;

public class MyProductWindow extends JFrame {
	int WIDTH, HEIGHT;
	JPanelBackground mainPanel = new JPanelBackground();
	JPanel pa = new JPanel();
	JPanel pb = new JPanel();
	JPanel pc = new JPanel();
	JPanel p1 = new JPanel(new FlowLayout());
	JPanel p2 = new JPanel(new FlowLayout());
	JPanel p3 = new JPanel(new FlowLayout());
	JPanel p4 = new JPanel(new FlowLayout());
	JPanel p5 = new JPanel(new FlowLayout());
	JPanel p6 = new JPanel(new FlowLayout());
	JPanel p7 = new JPanel(new FlowLayout());

	public Button b = new Button(new Color(0, 132, 0), "new.jpg", "New");
	public Button b1 = new Button(new Color(0, 0, 153), "edit.jpg", "Edit");
	public Button b2 = new Button(new Color(204, 0, 0), "delete.jpg", "Delete");
	public Button b3 = new Button(new Color(225, 128, 0), "preview.jpg", "Preview");
	public Button b4 = new Button(new Color(204, 0, 204), "update.jpg", "Status");
	public LabelButton b5 = new LabelButton(Color.BLACK, 55, 180, "");
	public LabelButton b6 = new LabelButton(Color.BLACK, 55, 180, "");
	Font font = new Font("Microsoft Sans Sherif", Font.BOLD, 18);
	public TableDemo tm = new TableDemo();

	public MyProductWindow() {
		pc.setVisible(false);

		b1.setHabilitado(false);
		b2.setHabilitado(false);
		b3.setHabilitado(false);
		b4.setHabilitado(false);

		pa.setLayout(new BoxLayout(pa, BoxLayout.Y_AXIS));
		pb.setLayout(new BoxLayout(pb, BoxLayout.Y_AXIS));
		pc.setLayout(new BoxLayout(pc, BoxLayout.Y_AXIS));

		GridBagConstraints gbc = new GridBagConstraints();

		mainPanel.setLayout(null);
		mainPanel.setBackground("fondo.jpg");

		this.setContentPane(mainPanel);
		this.setTitle("MainPlace - Products");
		this.setSize(1180, 820);

		WIDTH = this.getSize().width;
		HEIGHT = this.getSize().width;

		this.setLocationRelativeTo(null);
		this.setUndecorated(false);

		pa.setBorder(BorderFactory.createTitledBorder(new MatteBorder(3, 3, 3, 3, new Color(0, 0, 153)),
				" Edit Actions", TitledBorder.CENTER, TitledBorder.TOP, font, new Color(0, 0, 0)));
		pb.setBorder(BorderFactory.createTitledBorder(new MatteBorder(3, 3, 3, 3, new Color(0, 0, 153)),
				"Update Actions", TitledBorder.CENTER, TitledBorder.TOP, font, new Color(0, 0, 0)));
		pc.setBorder(BorderFactory.createTitledBorder(new MatteBorder(3, 3, 3, 3, new Color(0, 0, 153)), "Status",
				TitledBorder.CENTER, TitledBorder.TOP, font, new Color(0, 0, 0)));

		pa.setOpaque(false);
		pb.setOpaque(false);
		pc.setOpaque(false);

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		p5.setOpaque(false);
		p6.setOpaque(false);
		p7.setOpaque(false);

		p1.add(b);
		p2.add(b1);
		p3.add(b2);
		p4.add(b3);
		p5.add(b4);
		p6.add(b5);
		p7.add(b6);

		pa.add(p1, gbc);
		pa.add(p2, gbc);
		pa.add(p3, gbc);
		pb.add(p4, gbc);
		pb.add(p5, gbc);
		pc.add(p6, gbc);
		pc.add(p7, gbc);

		pa.setBounds(WIDTH - 300, 20, 230, HEIGHT - 900);
		pb.setBounds(WIDTH - 300, 300, 230, HEIGHT - 1000);
		pc.setBounds(WIDTH - 300, 490, 230, HEIGHT - 1000);

		mainPanel.add(pa);
		mainPanel.add(pb);
		mainPanel.add(pc);

		tm.setBounds(100, 100, 700, 600);
		tm.setOpaque(false);

		mainPanel.add(tm);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				closeTopFrames();

			}

		});
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainApplication.openNewProductWindow();
			}
		});

		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int selectedRow = tm.getTable().getSelectedRow();
				MainCliente.enviarProductRequestById((int) tm.ids.get(selectedRow));
			}

		});
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				popupFrame ppf = new popupFrame(
						"<html><body>Are you sure you want to delete <br>  the product selected? </body></html>") {

					@Override
					protected void function() {
						int selectedRow = tm.getTable().getSelectedRow();
						removeProduct((int) tm.ids.get(selectedRow));

					}
				};
				ppf.setVisible(true);
			}

		});
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tm.getTable().getSelectedRow();
				MainApplication.openPreview(((int) tm.ids.get(selectedRow)));

			}

		});
		b4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tm.getTable().getSelectedRow();
				MainApplication.updateStatusWindow(((int) tm.ids.get(selectedRow)));

			}

		});

	}

	public void createNewProduct(String productName, double price, String informacion, boolean negociable,
			int categoria, int tipo, Image i) {
		MainCliente.createProduct(productName, price, informacion, categoria, tipo, negociable, i);
	}

	public void editProduct(String productName, String informacion, double price, boolean negociable, int categoria,
			int tipo) {

	}

	public void removeProduct(int id) {
		MainCliente.removeProduct(id);
	}

	public void setProductInformation(Producto p) {
		byte[] imagenBytes = p.getImageBytes();
		Image image;
		image = ImageUtilsUpdater.ByteArrayToImage(imagenBytes);
		Image foto = ImageUtilsUpdater.scale(image, Constantes.ImgProfileX, Constantes.ImgProfileY);
		Image general = ImageUtilsUpdater.scale(image, Constantes.WIDTHRequiredImg, Constantes.HEIGHTRequiredImg);
		MainApplication.openEditProductWindow(p.getId(), p.getName(), p.getPrice(), p.getInformacion(),
				p.isNegociable(), p.getTipo(), p.getCategoria(), general, foto);

	}

	public void updateStatus(int status, int views) {
		if (MainApplication.usw != null) {

			MainApplication.usw.slider.setValue(status);
			MainApplication.usw.updateLabel();
		}
		String statusString = (ConstantesCategorias.fases[status]);
		if (status == 0) {
			this.b5.updateColorTexto(new Color(0, 153, 0), statusString);
		} else if (status == 1) {
			this.b5.updateColorTexto(new Color(255, 128, 0), statusString);
		} else if (status == 2) {
			this.b5.updateColorTexto(new Color(64, 64, 64), statusString);
		}
		if (views < 50) {
			this.b6.updateColorTexto(new Color(0, 153, 0), "Views: " + Integer.toString(views));
		}
		if (views < 250 && views >= 50) {
			this.b6.updateColorTexto(new Color(0, 0, 153), "Views: " + Integer.toString(views));
		}
		if (views > 250) {
			this.b6.updateColorTexto(new Color(204, 0, 204), "Views: " + Integer.toString(views));
		}

	}

	public void closeTopFrames() {
		if (MainApplication.pew != null) {
			MainApplication.pew.dispose();
		}
		if (MainApplication.pww != null) {
			MainApplication.pww.dispose();
		}
		if (MainApplication.usw != null) {
			MainApplication.usw.dispose();
		}
		if (MainApplication.pview != null) {
			MainApplication.pview.dispose();
		}
	}

	public void setInfoPanelVisible(boolean visible) {
		if (visible) {
			this.pc.setVisible(true);
		} else {
			this.pc.setVisible(false);
		}
	}

}
