package utils.image.imageEditor;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.mainApplication.MainApplication;

public class SearchImage {
	JFileChooser fc = new JFileChooser("user.home");
	FileFilter png = new FileNameExtensionFilter(".png", "Image");
	FileFilter ico = new FileNameExtensionFilter(".ico", "Image");
	FileFilter jpg = new FileNameExtensionFilter(".jpg", "Image");

	public SearchImage(boolean create) {

		fc.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "All supported image formats(*.png,*.jpeg,*.jpg,*.gif)";
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					return f.getName().toLowerCase().endsWith(".png") || f.getName().toLowerCase().endsWith(".jpeg")
							|| f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".gif");
				}
			}

		});

		fc.setDialogTitle("Add an image");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fc.showOpenDialog(MainApplication.pww);
		if (result == JFileChooser.APPROVE_OPTION) {

			File selectedFile = fc.getSelectedFile();
			Image img = null;
			try {
				img = ImageIO.read(selectedFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ImageEditor ie = new ImageEditor(img,selectedFile,create);
			
			
		}
	}
}
