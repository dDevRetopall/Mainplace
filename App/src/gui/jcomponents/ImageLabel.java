package gui.jcomponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.mainApplication.Constantes;

public class ImageLabel extends JLabel{
	int scale=50;
	public ImageLabel(int scale) {
		this.scale=scale;
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(Constantes.nameFolderImg+"/"+"add.jpg"));
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH);

		imageIcon = new ImageIcon(newimg);
		this.setFont(new Font("Microsoft Sans Sherif", Font.BOLD, 15));
		this.setIcon(imageIcon);
		this.setForeground(new Color(153, 0, 0));
	}

	public void changeIcon(String pathIcon, String text) {
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(Constantes.nameFolderImg+"/"+pathIcon));
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH);

		imageIcon = new ImageIcon(newimg);
		this.setIcon(imageIcon);
		this.setText(text);
	}
	public int getScale(){
		return scale;
	}

	
}
