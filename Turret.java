import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Turret extends Entity {
	public static final int COST = 20;
	
	private int rotation;
	private int armLength;
	
	public Turret(int x, int y) {
		super(x, y);
		color = Color.RED;
		rotation = (int) (Game.random.nextDouble() * 360);
		armLength = 12;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(color);
		g2.fillOval(x + 2, y + 2, Game.TILE_SIZE - 4, Game.TILE_SIZE - 4);
		
		int centerX = x + Game.TILE_SIZE / 2;
		int centerY = y + Game.TILE_SIZE / 2;
		
		g2.setColor(Color.YELLOW);
		g2.setStroke(new BasicStroke(2F));
		
		if (rotation == 0) {
			g2.drawLine(centerX, centerY, centerX + armLength, centerY);
		} else if (rotation == 90) {
			g2.drawLine(centerX, centerY, centerX, centerY - armLength);
		} else if (rotation == 180) {
			g2.drawLine(centerX, centerY, centerX - armLength, centerY);
		} else if (rotation == 270) {
			g2.drawLine(centerX, centerY, centerX, centerY + armLength);
		} else {
			g2.drawLine(
					centerX,
					centerY,
					centerX + (int) (armLength * Math.cos(Math.toRadians(rotation))),
					centerY - (int) (armLength * Math.sin(Math.toRadians(rotation))));
		}
	}
	
	@Override
	public void update() {
		Enemy firstEnemy = Game.enemies.get(0);
		rotation = (int) Math.toDegrees(Math.atan2(Game.toCartesianY(firstEnemy.y) - Game.toCartesianY(y), firstEnemy.x - x));
		if(rotation < 0) {
			rotation = 360 - Math.abs(rotation);
		}
	}
}
