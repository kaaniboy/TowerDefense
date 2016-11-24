import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Entity {
	
	private int damage;
	private double xVelocity;
	private double yVelocity;
	private int speedMultiplier;

	public Bullet(int x, int y, double xVelocity, double yVelocity) {
		super(x, y);
		damage = 10;
		speedMultiplier = 10;
		this.xVelocity = speedMultiplier * xVelocity;
		this.yVelocity = speedMultiplier * yVelocity;
	}

	@Override
	public void update() {
		x += xVelocity;
		y += yVelocity;
		
		for(Enemy e: Game.enemies) {
			if(Math.sqrt(Math.pow(y - e.getCenterY(), 2) + Math.pow(x - e.getCenterX(), 2)) < 10) {
				System.out.println("HIT!");
				e.setHealth(e.getHealth() - damage);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(x, y, 4, 4);
	}
}
