package gui.jcomponents;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.mainApplication.Constantes;

public class JPanelBackground extends JPanel {
	private boolean iconoPuesto=false;
	// Atributo que guardara la imagen de Background que le pasemos.
	private Image background;
	public JPanelBackground(){
		
	}
	// Metodo que es llamado automaticamente por la maquina virtual Java cada vez que repinta
	public void paintComponent(Graphics g) {
		
		/* Obtenemos el tamaño del panel para hacer que se ajuste a este
		cada vez que redimensionemos la ventana y se lo pasamos al drawImage */
		int width = this.getSize().width;
		int height = this.getSize().height;
 
		// Mandamos que pinte la imagen en el panel
		if (this.background != null) {
			g.drawImage(this.background, 0, 0, width, height, null);
		}
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		if(topFrame!=null&&!iconoPuesto){
		topFrame.setIconImage(new ImageIcon(getClass().getResource(Constantes.nameFolderImg+"/"+"Icon.jpg")).getImage());
		iconoPuesto=true;
		}
		super.paintComponent(g);
	}
 
	// Metodo donde le pasaremos la dirección de la imagen a cargar.
	public void setBackground(String imagePath) {
		
		// Construimos la imagen y se la asignamos al atributo background.
		this.setOpaque(false);
		this.background = new ImageIcon(getClass().getResource(Constantes.nameFolderImg+"/"+imagePath)).getImage();
		repaint();
	}
 
}