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
	//Instance fields for the enemy's damage, maximum health, and current health.
	private int damage;
	private int maxHealth;
	private int health;
	
	//Used to make the enemy traverse the specified path.
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
		
		//Draw the enemy itself.
		g2.setColor(color);
		g2.fillOval(x + 4, y + 4, Game.TILE_SIZE - 8, Game.TILE_SIZE - 8);

		//Draw the enemy's health bar.
		g2.setColor(Color.RED);
		g2.fillRect(x, y - 10, Game.TILE_SIZE, 7);
		g2.setColor(Color.GREEN);
		g2.fillRect(x, y - 10,
				(int) ((health * 1.0) / maxHealth * Game.TILE_SIZE), 7);
		
		//Draw a black border around the health bar.
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1.1F));
		g2.drawRect(x, y - 10, Game.TILE_SIZE, 7);
	}

	@Override
	public void update() {
		//Check if the enemy has finished traversing the path yet.
		if (targetIndex < Game.getEnemyPath().size()) {
			
			//Get the next position that the enemy should move to in the path.
			int targetX = Game.getEnemyPath().get(targetIndex).x * Game.TILE_SIZE;
			int targetY = Game.getEnemyPath().get(targetIndex).y * Game.TILE_SIZE;
			
			
			//Either increase or decrease the enemy's coordinates based on the next position in the path.
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
				//Once the enemy has reached a point in the path, start moving to the next point.
				targetIndex++;
			}
		}
	}
	
	//Getter for damage.
	public int getDamage() {
		return damage;
	}
	
	//Getter for health.
	public int getHealth() {
		return health;
	}
	//Setter for health.
	public void setHealth(int health) {
		this.health = health;
	}
	
	//Check whether the enemy is dead.
	public boolean isDead() {
		return health <= 0;
	}
	
	//Getter for the x coordinate of the middle of the enemy.
	public int getCenterX() {
		return 4 + getX() + (Game.TILE_SIZE - 8) / 2;
	}
	
	//Getter for the y coordinate of the middle of the enemy.
	public int getCenterY() {
		return 4 + getY() + (Game.TILE_SIZE - 8) / 2;
	}
}
