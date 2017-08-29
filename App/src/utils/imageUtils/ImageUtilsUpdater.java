package utils.imageUtils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtilsUpdater {
	public static byte[] ImageToByteArray(BufferedImage bi){
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", baos);
			byte[]imageInByte;
			imageInByte = baos.toByteArray();
			return imageInByte;
		} catch (IOException e) {
			System.out.println("Error writing the image from a byte[]image");
			e.printStackTrace();
			return null;
		}
		
	}
	public static Image ByteArrayToImage(byte[]imageBytes){
		Image image;
		try {
			image = ImageIO.read(new ByteArrayInputStream(imageBytes));
			return image;
		} catch (IOException e) {
			System.out.println("Error reading byte[] image");
			e.printStackTrace();
			return null;
		}
		
	}
	public static Image scale(Image i,int scaleX,int scaleY){
		Image newimg = i.getScaledInstance(scaleX, scaleY, java.awt.Image.SCALE_SMOOTH);
		return newimg;
	}
	public static BufferedImage imageToBufferedImage(Image img){
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		return bimage;
		
	}
}
