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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class LabelButton extends JButton{
	public int press = 0;
	JLabel l = new JLabel();
	JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private Color colorPredeterminado;

	public LabelButton(Color colorPredeterminado,int height,int width,String text) {
		super();
		this.colorPredeterminado = colorPredeterminado;
		super.setContentAreaFilled(false);
		l.setForeground(colorPredeterminado);
		l.setFont(this.getFont().deriveFont(Font.BOLD, 18));
		l.setHorizontalAlignment(JLabel.CENTER);
		this.setPreferredSize(new Dimension(width,height));
		this.setForeground(colorPredeterminado);
		this.setBorder(new MatteBorder(3, 3, 3, 3, colorPredeterminado));

		setFocusPainted(false);
		
		l.setText(text);
	

		p.add(l);
		p.setOpaque(false);
		this.add(p);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		g.setColor(new Color(224, 224, 224));

	
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
	public void updateColorTexto(Color c,String texto){
		l.setForeground(c);
		l.setText(texto);
		this.colorPredeterminado=c;
		this.setForeground(colorPredeterminado);
		this.setBorder(new MatteBorder(3, 3, 3, 3, colorPredeterminado));
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
	
}
