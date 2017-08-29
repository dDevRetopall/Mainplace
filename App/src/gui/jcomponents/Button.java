package gui.jcomponents;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import main.mainApplication.Constantes;

public class Button extends JButton {
	
	public int press = 0;
	JLabel l = new JLabel();
	JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private Color colorPredeterminado;

	public Button(Color colorPredeterminado, String pathImg,String text) {
		super();
		this.colorPredeterminado = colorPredeterminado;
		super.setContentAreaFilled(false);
		l.setForeground(colorPredeterminado);
		l.setFont(this.getFont().deriveFont(Font.BOLD, 18));
		l.setIconTextGap(20);
		l.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.setPreferredSize(new Dimension(180,55));
		this.setForeground(colorPredeterminado);
		this.setBorder(new MatteBorder(3, 3, 3, 3, colorPredeterminado));

		setFocusPainted(false);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(Constantes.nameFolderImg+"/"+pathImg)); // load

		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH); // scale

		imageIcon = new ImageIcon(newimg);
		l.setText(text);
		l.setIcon(imageIcon);

		p.add(l);
		p.setOpaque(false);
		this.add(p);
		super.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				press = 1;

				super.mouseClicked(e);
			}

		});
		super.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {

				press = 0;

				super.mouseClicked(e);
			}

		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isRollover()) {
			g.setColor(getBgHoverColor());

		} else {
			g.setColor(new Color(224, 224, 224));

		}
		if (press >= 1) {
		
			g.setColor(getBgPressedColor());
			press++;

		}

		g.fillRect(0, 0, getWidth(), getHeight());

		super.paintComponent(g);
	}

	private Color getBgPressedColor() {
		// TODO Auto-generated method stub
		return new Color(192, 192, 192);
	}

	private Color getBgHoverColor() {
		// TODO Auto-generated method stub
		return new Color(210, 210, 210);
	}
	
	
	public void setHabilitado(boolean b) {
		if(!b){
		this.setEnabled(false);
		this.setBorder(new MatteBorder(3, 3, 3, 3, getBgPressedColor()));
		l.setForeground( getBgPressedColor());
	
		
		}else{
		this.setEnabled(true);
		this.setBorder(new MatteBorder(3, 3, 3, 3, colorPredeterminado));
		l.setForeground(colorPredeterminado);

		}
		
	}
	public void clearImage(){
		l.setIcon(null);
	}

}
