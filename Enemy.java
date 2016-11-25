// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: Class that represents an enemy in the tower defense game. Each
//              enemy has its own health amount as well as a damage amount. The enemy
//              will traverse its set path as the game progresses.

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Enemy extends Entity {	
	private int damage;
	private int maxHealth;
	private int health;
	private int targetIndex;

	public Enemy(int x, int y) {
		super(x, y);
		color = Color.YELLOW;
		targetIndex = 1;
		health = maxHealth = 100;
		damage = 5;
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
		g2.fillRect(x, y - 10,
				(int) ((health * 1.0) / maxHealth * Game.TILE_SIZE), 7);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1.1F));
		g2.drawRect(x, y - 10, Game.TILE_SIZE, 7);
	}

	@Override
	public void update() {
		if (targetIndex < Game.getEnemyPath().size()) {
			int targetX = Game.getEnemyPath().get(targetIndex).x * Game.TILE_SIZE;
			int targetY = Game.getEnemyPath().get(targetIndex).y * Game.TILE_SIZE;

			if (targetX != x) {
				if (targetX > x) {
					x++;
				} else {
					x--;
				}
			} else if (targetY != y) {
				if (targetY > y) {
					y++;
				} else {
					y--;
				}
			} else {
				targetIndex++;
			}
		}
	}
	
	public int getDamage() {
		return damage;
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
