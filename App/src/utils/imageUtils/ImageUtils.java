package utils.imageUtils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.mainApplication.Constantes;

public class ImageUtils {
	public ImageUtils(){
		
	}
	public BufferedImage getBufferedImage(String path){
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(path));
		Image image = imageIcon.getImage();
		
		BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(image, 0, 0, null);
		bGr.dispose();
		return bimage;
		
	}
}
