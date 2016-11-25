// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: Class that represents the bullet that are shot out of the turrets.
//              Each bullet has its own damage amount as well as a velocity, and can
//              only collide with an enemy once.

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
		speedMultiplier = 15;
		hasCollided = false;
		this.xVelocity = speedMultiplier * xVelocity;
		this.yVelocity = speedMultiplier * yVelocity;
	}

	@Override
	public void update() {
		//Update the position of the bullet after each iteration of the game.
		x += xVelocity;
		y += yVelocity;

		//Check to see if the bullet has collided with an enemy.
		//If the bullet has collided with an enemy, reduce the enemy's health.
		if (!hasCollided) {
			for (Enemy e : Game.getEnemies()) {
				if (Math.sqrt(Math.pow(y - e.getCenterY(), 2) + Math.pow(x - e.getCenterX(), 2)) < Game.TILE_SIZE - 4) {
					e.setHealth(e.getHealth() - damage);
					hasCollided = true;
					break;
				}
			}
		}
	}
	
	//Getter for hasCollided.
	public boolean hasCollided() {
		return hasCollided;
	}
	//Setter for damage.
	public void setDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(x, y, 4, 4);
	}
}
