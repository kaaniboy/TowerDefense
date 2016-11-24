import java.awt.Color;
import java.awt.Graphics;

public abstract class Entity {
	protected int x;
	protected int y;
	protected Color color;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public abstract void update();
	public abstract void paint(Graphics g);
}
