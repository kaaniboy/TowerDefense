import java.awt.Color;
import java.awt.Graphics;

public class Base extends Entity {
	private int health;

	public Base(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillOval(x, y, Game.TILE_SIZE, Game.TILE_SIZE);

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
