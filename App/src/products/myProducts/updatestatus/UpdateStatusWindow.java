package products.myProducts.updatestatus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cliente.net.connection.MainCliente;
import gui.jcomponents.Button;
import gui.jcomponents.ImageLabel;
import gui.jcomponents.JPanelBackground;
import gui.jcomponents.LabelButton;
import gui.jcomponents.MySliderUI;
import main.mainApplication.Constantes;
import products.myProducts.main.ConstantesCategorias;

public class UpdateStatusWindow extends JFrame {
	JPanelBackground mainPanel = new JPanelBackground();
	private int idProducto;
	JPanel mainInformation = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel l = new JLabel(ConstantesCategorias.fases[0]);
	JLabel l1 = new JLabel(ConstantesCategorias.fases[1]);
	JLabel l2 = new JLabel(ConstantesCategorias.fases[2]);
	boolean fistUpdate = false;
	Font font = new Font("Agency FB", Font.BOLD, 24);
	JPanel pButtons = new JPanel(new FlowLayout());
	LabelButton bl = new LabelButton(Color.BLACK, 55, 180, "");
	Button b = new Button(new Color(0, 0, 153), "update.jpg", "Update");

	public JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 2, 0);
	private int id;

	public UpdateStatusWindow(int id) {
		b.setHabilitado(false);
		l.setFont(font);
		l.setForeground(new Color(0, 153, 0));
		l1.setFont(font);
		l1.setForeground(new Color(255, 128, 0));
		l2.setFont(font);
		l2.setForeground(new Color(64, 64, 64));
		UIManager.put("Slider.focus", UIManager.get("Slider.background"));
		this.id = id;
		this.idProducto = idProducto;
		this.setSize(600, 400);
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setContentPane(mainPanel);
		this.setTitle("Update status");
		this.setResizable(false);
		mainPanel.setLayout(null);

		mainPanel.setBackground("bgprofile.jpg");

		l.setBounds(100 - 45, 180, 100, 50);
		l1.setBounds(225 + 45, 180, 100, 50);
		l2.setBounds(450 + 45, 180, 100, 50);

		bl.setSize(180, 55);
		bl.setBounds(this.getWidth() / 2 - bl.getWidth() / 2, 50, 180, 55);

		b.setSize(180, 55);
		b.setBounds(this.getWidth() / 2 - bl.getWidth() / 2, 250, 180, 55);

		slider.setBounds(75, 150, 450, 50);

		slider.setUI(new MySliderUI(slider));
		slider.setOpaque(false);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setMajorTickSpacing(1);
		slider.setFocusable(false);
		slider.setPreferredSize(new Dimension(400, 30));

		mainPanel.add(bl);
		mainPanel.add(b);
		mainPanel.add(slider);
		mainPanel.add(l);
		mainPanel.add(l1);
		mainPanel.add(l2);

		
		this.setVisible(true);
		MainCliente.requestStatusViewsProductById(id);
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				b.setHabilitado(false);
				MainCliente.updateStatus(slider.getValue(), id);

			}
		});
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
			

				if (fistUpdate) {
					b.setHabilitado(true);
				} else {
					fistUpdate = true;
				}
			}
		});

	}

	public void updateLabel() {
		if (slider.getValue() == 0) {
			bl.updateColorTexto(new Color(0, 153, 0), ConstantesCategorias.fases[0]);
		} else if (slider.getValue() == 1) {
			bl.updateColorTexto(new Color(255, 128, 0), ConstantesCategorias.fases[1]);
		} else if (slider.getValue() == 2) {
			bl.updateColorTexto(new Color(64, 64, 64), ConstantesCategorias.fases[2]);
		}
	}
}
