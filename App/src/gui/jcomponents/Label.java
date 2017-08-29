package gui.jcomponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.mainApplication.Constantes;

public class Label extends JLabel {
	public Label() {
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(Constantes.nameFolderImg+"/"+"accept.jpg")); 
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH); 
		
		imageIcon = new ImageIcon(newimg);
		this.setFont(new Font("Microsoft Sans Sherif", Font.BOLD, 15));
		this.setIcon(imageIcon);
		this.setForeground(new Color(153,0,0));
	}
	public void changeIcon(String pathIcon,String text){
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(Constantes.nameFolderImg+"/"+pathIcon)); 
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH); 
		
		imageIcon = new ImageIcon(newimg);
		this.setIcon(imageIcon);
		this.setText(text);
	}
}
