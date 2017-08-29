package utils.image.imageEditor;

public class Vector {
	private int x;
	private int y;
	private int xOrigen;
	private int yOrigen;

	public Vector(int xOrigen,int yOrigen){
		this.xOrigen = xOrigen;
		this.yOrigen = yOrigen;
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getxOrigen() {
		return xOrigen;
	}

	public int getyOrigen() {
		return yOrigen;
	}

	public void setxOrigen(int xOrigen) {
		this.xOrigen = xOrigen;
	}

	public void setyOrigen(int yOrigen) {
		this.yOrigen = yOrigen;
	}
	
	
}
