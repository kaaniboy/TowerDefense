import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Entity {

	private int damage;
	private double xVelocity;
	private double yVelocity;
	private int speedMultiplier;
	private boolean hasCollided;

	public Bullet(int x, int y, double xVelocity, double yVelocity) {
		super(x, y);
		damage = 10;
		speedMultiplier = 10;
		hasCollided = false;

		this.xVelocity = speedMultiplier * xVelocity;
		this.yVelocity = speedMultiplier * yVelocity;
	}

	@Override
	public void update() {
		x += xVelocity;
		y += yVelocity;

		if (!hasCollided) {
			for (Enemy e : Game.enemies) {
				if (Math.sqrt(Math.pow(y - e.getCenterY(), 2) + Math.pow(x - e.getCenterX(), 2)) < Game.TILE_SIZE - 4) {
					e.setHealth(e.getHealth() - damage);
					hasCollided = true;
					break;
				}
			}
		}
	}

	public boolean hasCollided() {
		return hasCollided;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(x, y, 4, 4);
	}
}
