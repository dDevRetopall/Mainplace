package utils.image.imageEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import main.mainApplication.Constantes;
import main.mainApplication.MainApplication;
import utils.imageUtils.ImageUtilsUpdater;

public class ImageEditor extends JFrame {
	private Image img;
	JPanel p = new JPanel(new BorderLayout());
	JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JButton b = new JButton("Save image");
	JButton b2 = new JButton("Change image");
	Graphics2D buf;
	boolean bordeIzquierdaColision = false;
	boolean bordeDerechaColision = false;
	boolean bordeArribaColision = false;
	boolean bordeAbajoColision = false;
	boolean resize = false;
	int WIDTH;
	int HEIGHT;
	int WIDTHWindow = 900;
	int HEIGHTWindow = 600;
	boolean editedSizeImg = false;
	boolean sizeCustom = false;
	private File f;
	int origenX = 0;
	int origenY = 0;
	int longitudPxWidth = Constantes.WIDTHRequiredImg;
	int longitudPxHeight = Constantes.HEIGHTRequiredImg;
	int previousXOrigen = 0;
	int previousYOrigen = 0;
	BufferedImage bf;
	Vector v;
	boolean pressed = false;
	private boolean create;

	ImageEditor(Image img, File f,boolean create) {

		this.img = img;
		this.f = f;
		this.create = create;
		if (!(img.getWidth(null) < Constantes.WIDTHRequiredImg)
				|| !(img.getHeight(null) < Constantes.HEIGHTRequiredImg)) {
			setup();
		} else {

			this.setSize(400, 400);

			this.dispose();
			JOptionPane.showMessageDialog(null,
					"The image is less than " + Constantes.WIDTHRequiredImg + "X" + Constantes.HEIGHTRequiredImg);

			new SearchImage(create);
			

		}
		BufferedImage buffered;
		try {
			buffered = ImageIO.read(f);
			buffered.getGraphics().drawImage(img, 0, 0, null);
			buffered.getGraphics().dispose();
			if (buffered.getColorModel().hasAlpha()) {
				JOptionPane.showMessageDialog(null,"This image has alpha. Alpha would be converted in Black color","Warning",JOptionPane.WARNING_MESSAGE
						);
			}
		} catch (IOException e) {
			System.out.println("Error trying to see if the image is alpha");			
		}
			
	
	}

	public void setup() {

		p.setOpaque(false);
		this.setContentPane(p);
		p.add(p2, BorderLayout.SOUTH);
		p2.add(b);
		p2.add(b2);
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// ORIGENX = E.GETX();
				// ORIGENY = E.GETY();

			}
		});
		this.setTitle("Image Editor");
		//
		WIDTH = img.getWidth(null);
		HEIGHT = img.getHeight(null);
		if (WIDTH > Toolkit.getDefaultToolkit().getScreenSize().width - 500) {
			int s = (int) (((Toolkit.getDefaultToolkit().getScreenSize().width - 500) * HEIGHT) / (float) WIDTH);
			this.img = img.getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width - 500, s,
					Image.SCALE_SMOOTH); // scale
			WIDTH = img.getWidth(null);
			HEIGHT = img.getHeight(null);
			editedSizeImg = true;
		}
		if (HEIGHT > Toolkit.getDefaultToolkit().getScreenSize().height - 300) {
			int s = (int) (((Toolkit.getDefaultToolkit().getScreenSize().height - 300) * WIDTH) / (float) HEIGHT);
			this.img = img.getScaledInstance(s, Toolkit.getDefaultToolkit().getScreenSize().height - 300,
					Image.SCALE_SMOOTH); // scale
			WIDTH = img.getWidth(null);
			HEIGHT = img.getHeight(null);
			editedSizeImg = true;
		}
		if (WIDTH < WIDTHWindow && HEIGHT < HEIGHTWindow) {
			//1000X700
			this.setSize(WIDTHWindow+100,HEIGHTWindow+100);
			WIDTHWindow=WIDTHWindow+100;
			HEIGHTWindow=HEIGHTWindow+100;
			sizeCustom = false;
		} else {
			this.setSize(WIDTH + 100, HEIGHT + 100 + 30);
			WIDTHWindow = WIDTH + 100;
			HEIGHTWindow = HEIGHT + 100 + 30;
			sizeCustom = true;
		}
		if (sizeCustom) {

			this.origenX = 50;
			this.origenY = 80;
		} else {

			this.origenX = WIDTHWindow / 2 - WIDTH / 2;
			this.origenY = 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2);

		}

		this.setLocationRelativeTo(null);
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				BufferedImage buffered=ImageUtilsUpdater.imageToBufferedImage(img);
				int x, y = 0;
				if (sizeCustom) {

					x = origenX - 50;
					y = origenY - 80;
				} else {

					x = origenX - (WIDTHWindow / 2 - WIDTH / 2);
					y = origenY - (30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2));

				}
				
				Image i2 = buffered.getSubimage(x, y, longitudPxWidth, longitudPxHeight);
				Image i3 = ImageUtilsUpdater.scale(i2, Constantes.WIDTHRequiredImg, Constantes.HEIGHTRequiredImg);
				Image i =	ImageUtilsUpdater.scale(i2, Constantes.ImgProfileX, Constantes.ImgProfileY);
				ImageEditor.this.dispose();
				if(create){
				products.myProducts.createProduct.PanelEditorImagen pei = new products.myProducts.createProduct.PanelEditorImagen(i3);
				pei.prepareForCreation(i3, i);
				}else{
				products.myProducts.editProduct.PanelEditorImagen pei = new products.myProducts.editProduct.PanelEditorImagen(i3);
				pei.prepareForCreation(i3, i);
				}
				

			}
		});
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SearchImage(create);
				ImageEditor.this.dispose();

			}
		});
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				pressed = false;
				resize = false;
				v.setX(0);
				v.setY(0);
				v.setxOrigen(e.getX());
				v.setxOrigen(e.getY());
				p.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				super.mouseReleased(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				ImageEditor.this.previousXOrigen = ImageEditor.this.origenX;
				ImageEditor.this.previousYOrigen = ImageEditor.this.origenY;
				v = new Vector(e.getX(), e.getY());
				pressed = true;
				if (estaDentroDeResizer(e.getX(), e.getY())) {
					p.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
					resize = true;
				}
				super.mousePressed(e);

			}

		});
		this.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {

				if (pressed == true) {
					if (resize == false) {
						if (estaDentroDeSeleccionador(e.getX(), e.getY())) {
							int imagenXOrigen;
							int imagenYOrigen;
							if (sizeCustom) {
								imagenXOrigen = 50;
								imagenYOrigen = 80;

							} else {
								imagenXOrigen = WIDTHWindow / 2 - WIDTH / 2;
								imagenYOrigen = 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2);
							}
							v.setX(e.getX() - v.getxOrigen());
							v.setY(e.getY() - v.getyOrigen());

							colision();
							if (v.getX() < 0 && bordeIzquierdaColision) {
								ImageEditor.this.origenX = imagenXOrigen;
							} else if (v.getX() > 0 && bordeDerechaColision) {
								ImageEditor.this.origenX = imagenXOrigen + WIDTH - longitudPxWidth;
							} else {
								ImageEditor.this.origenX = ImageEditor.this.previousXOrigen + v.getX();
							}
							if (v.getY() < 0 && bordeArribaColision) {
								ImageEditor.this.origenY = imagenYOrigen;
							} else if (v.getY() > 0 && bordeAbajoColision) {
								System.out.println(v.getY());
								ImageEditor.this.origenY = imagenYOrigen + HEIGHT - longitudPxHeight;

							} else {
								ImageEditor.this.origenY = ImageEditor.this.previousYOrigen + v.getY();
							}
							ImageEditor.this.previousXOrigen = ImageEditor.this.origenX;
							ImageEditor.this.previousYOrigen = ImageEditor.this.origenY;
							v = new Vector(e.getX(), e.getY());

							repaint();
						}

					} else {
						int imagenXOrigen = WIDTHWindow / 2 - WIDTH / 2;
						int imagenYOrigen = 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2);
						if ((ImageEditor.this.origenY + ImageEditor.this.longitudPxHeight < imagenYOrigen + HEIGHT
								&& ImageEditor.this.origenX + ImageEditor.this.longitudPxWidth < imagenXOrigen
										+ WIDTH)||(e.getX() - (ImageEditor.this.origenX + ImageEditor.this.longitudPxWidth)<0)) {
							if (ImageEditor.this.longitudPxWidth + (e.getX() - (ImageEditor.this.origenX
									+ ImageEditor.this.longitudPxWidth)) > Constantes.WIDTHRequiredImg) {
								ImageEditor.this.longitudPxWidth = ImageEditor.this.longitudPxWidth
										+ (e.getX() - (ImageEditor.this.origenX + ImageEditor.this.longitudPxWidth));
								ImageEditor.this.longitudPxHeight = ImageEditor.this.longitudPxHeight
										+ (e.getX() - (ImageEditor.this.origenX + ImageEditor.this.longitudPxHeight));

							
						}
						}else{
							
								if(ImageEditor.this.origenY + ImageEditor.this.longitudPxHeight >= imagenYOrigen + HEIGHT){
									ImageEditor.this.origenY=imagenYOrigen + HEIGHT - longitudPxHeight;
								}else if(ImageEditor.this.origenX + ImageEditor.this.longitudPxWidth <= imagenXOrigen
											+ WIDTH){
									ImageEditor.this.origenX=imagenXOrigen + WIDTH - longitudPxWidth;
									
								}
							
						}
					}
					if (!(e.getX() - ImageEditor.this.origenX
							+ ImageEditor.this.longitudPxWidth >= Constantes.WIDTHRequiredImg)) {

						ImageEditor.this.longitudPxWidth = Constantes.WIDTHRequiredImg;
						ImageEditor.this.longitudPxHeight = Constantes.HEIGHTRequiredImg;

						System.out.println("Resized:" + longitudPxHeight + "OrigenX" + origenX);
					}
					repaint();
				}

				super.mouseDragged(e);
			}

			public void mouseMoved(MouseEvent e) {

				if (estaDentroDeSeleccionador(e.getX(), e.getY())) {
					p.setCursor(new Cursor(Cursor.MOVE_CURSOR));

				} else {
					p.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				if (estaDentroDeResizer(e.getX(), e.getY())) {
					p.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));

				} else {

				}

				repaint();
				super.mouseMoved(e);

			}
		});
		this.setVisible(true);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {

		bf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		try {
			dibujarResto(bf.getGraphics());
			dibujarRegleta(bf.getGraphics());

			g.drawImage(bf, 0, 0, null);

		} catch (Exception ex) {

		}

	}

	public void dibujarRegleta(Graphics g) {
		g.setColor(Color.black);
		g.drawLine(this.origenX, this.origenY, this.origenX, this.origenY + this.longitudPxHeight);
		g.drawLine(this.origenX, this.origenY, this.origenX + this.longitudPxWidth, this.origenY);
		g.drawLine(this.origenX, this.origenY + this.longitudPxHeight, this.origenX + this.longitudPxWidth,
				this.origenY + this.longitudPxHeight);
		g.drawLine(this.origenX + this.longitudPxWidth, this.origenY, this.origenX + this.longitudPxWidth,
				this.origenY + this.longitudPxHeight);
		g.fillRect(this.origenX + this.longitudPxWidth - 5, this.origenY + longitudPxHeight - 5, 10, 10);
	}

	public boolean estaDentroDeSeleccionador(int x, int y) {
		if (x <= this.origenX + this.longitudPxWidth && x >= this.origenX && y <= this.origenY + this.longitudPxHeight
				&& y >= this.origenY) {
			return true;
		}
		return false;

	}

	public boolean estaDentroDeResizer(int x, int y) {

		if (x <= this.origenX + this.longitudPxWidth + 5 && x >= this.origenX + longitudPxWidth - 5
				&& y <= this.origenY + this.longitudPxHeight + 5 && y >= this.longitudPxHeight + this.origenY - 5) {
			return true;
		}
		return false;

	}

	public void colision() {
		bordeIzquierdaColision = false;
		bordeDerechaColision = false;
		bordeArribaColision = false;
		bordeAbajoColision = false;
		int imagenXOrigen;
		int imagenYOrigen;
		if (sizeCustom) {
			imagenXOrigen = 50;
			imagenYOrigen = 80;

		} else {
			imagenXOrigen = WIDTHWindow / 2 - WIDTH / 2;
			imagenYOrigen = 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2);
		}

		if (this.origenX <= imagenXOrigen) {
			bordeIzquierdaColision = true;
		}
		if (this.origenX + this.longitudPxWidth >= imagenXOrigen + this.WIDTH) {
			bordeDerechaColision = true;
		}
		if (this.origenY <= imagenYOrigen) {
			bordeArribaColision = true;
		}
		if (this.origenY + this.longitudPxHeight >= imagenYOrigen + this.HEIGHT) {
			bordeAbajoColision = true;
		}
		

	}

	public boolean estaEnBordeDeSeleccionador(int x, int y) {
		if (x <= this.origenX + this.longitudPxWidth && x >= this.origenX && y <= this.origenY + this.longitudPxHeight
				&& y >= this.origenY) {
			return true;
		}
		return false;

	}

	public void dibujarResto(Graphics g) {
		g.setColor(Color.black);
		super.paint(g);
		if (sizeCustom) {
			g.drawImage(img, 50, 80, null);

		} else {
			g.drawImage(img, WIDTHWindow / 2 - WIDTH / 2, 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2), null);

		}
		g.setColor(Color.black);
		g.drawLine(WIDTHWindow / 2 - WIDTH / 2, 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2),
				WIDTHWindow / 2 - WIDTH / 2, 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2) + HEIGHT);
		g.drawLine(WIDTHWindow / 2 - WIDTH / 2, 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2),
				WIDTHWindow / 2 - WIDTH / 2 + WIDTH, 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2));
		g.drawLine(WIDTHWindow / 2 - WIDTH / 2, 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2) + HEIGHT,
				WIDTHWindow / 2 - WIDTH / 2 + WIDTH, 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2) + HEIGHT);
		g.drawLine(WIDTHWindow / 2 - WIDTH / 2 + WIDTH, 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2),
				WIDTHWindow / 2 - WIDTH / 2 + WIDTH, 30 + ((HEIGHTWindow - 30) / 2 - HEIGHT / 2) + HEIGHT);
		if (editedSizeImg) {
			g.drawString("Path: " + f.getPath() + " Archive name: " + f.getName() + " Pixels (resized): "
					+ img.getWidth(null) + "X" + img.getHeight(null), 100, 70);
		} else {
			g.drawString("Path: " + f.getPath() + " Archive name: " + f.getName() + " Pixels: " + img.getWidth(null)
					+ "X" + img.getHeight(null), 100, 70);
		}
	}

}
