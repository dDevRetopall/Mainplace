package findProducts.search;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CellPanel {
	int width=0;
	int height=0;
	int x=0;
	int y=0;
	JPanel p;
	private SafeProducto s;
	public CellPanel(JPanel p,SafeProducto s){
		this.p=p;
		height=p.getSize().height;
		width=p.getSize().width;
		x=p.getLocation().x;
		y=p.getLocation().y;
		this.s = s;
	}

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public JPanel getPanel() {
		return p;
	}
	public SafeProducto getProductoSafe() {
		return s;
	}
	
}

