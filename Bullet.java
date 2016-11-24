import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Entity {
	
	private double xVelocity;
	private double yVelocity;
	private int speedMultiplier;

	public Bullet(int x, int y, double xVelocity, double yVelocity) {
		super(x, y);
		speedMultiplier = 10;
		this.xVelocity = speedMultiplier * xVelocity;
		this.yVelocity = speedMultiplier * yVelocity;
	}

	@Override
	public void update() {
		x += xVelocity;
		y += yVelocity;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(x, y, 4, 4);
	}
}
