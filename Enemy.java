import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Enemy extends Entity {
	private int maxHealth;
	private int health;
	int targetIndex;

	public Enemy(int x, int y) {
		super(x, y);
		color = Color.YELLOW;
		targetIndex = 1;
		health = maxHealth = 100;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(color);
		g2.fillOval(x + 4, y + 4, Game.TILE_SIZE - 8, Game.TILE_SIZE - 8);

		int centerX = x + Game.TILE_SIZE / 2;
		int centerY = y + Game.TILE_SIZE / 2;
		
		g2.setColor(Color.RED);
		g2.fillRect(x, y - 10, Game.TILE_SIZE, 7);
		g2.setColor(Color.GREEN);
		g2.fillRect(x, y - 10, (int)((health * 1.0)/maxHealth * Game.TILE_SIZE), 7);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1.1F));
		g2.drawRect(x, y - 10, Game.TILE_SIZE, 7);
	}

	@Override
	public void update() {
		int targetX = Game.enemyPath.get(targetIndex).x * Game.TILE_SIZE;
		int targetY = Game.enemyPath.get(targetIndex).y * Game.TILE_SIZE;

		if (targetX != x) {
			if(targetX > x) {
				x++;
			} else {
				x--;
			}
		} else if (targetY != y) {
			if(targetY > y) {
				y++;
			} else {
				y--;
			}
		} else if (targetIndex - 1 < Game.enemyPath.size()) {
			targetIndex++;
		} else {
			System.out.println("Reached end!");
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public boolean isDead() {
		return health <= 0;
	}
	
	public int getCenterX() {
		return 4 + getX() + (Game.TILE_SIZE - 8) / 2;
	}
	
	public int getCenterY() {
		return 4 + getY() + (Game.TILE_SIZE - 8) / 2;
	}
}
