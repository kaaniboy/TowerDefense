// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: Class that represents the base that must be defended in the tower
//              defense game. The base has a specific health quantity.


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Base extends Entity {
	
	//Instance fields for the base's maximum health and its current health.
	private int maxHealth;
	private int health;

	public Base(int x, int y) {
		super(x, y);
		health = maxHealth = 5;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		//Draw the base itself.
		g2.setColor(Color.MAGENTA);
		g2.fillRect(x, y, Game.TILE_SIZE, Game.TILE_SIZE);
		
		
		//Draw the health bar underneath the base.
		g2.setColor(Color.RED);
		g2.fillRect(x - 4, y + Game.TILE_SIZE + 10, Game.TILE_SIZE + 8, 12);
		g2.setColor(Color.GREEN);
		g2.fillRect(x - 4, y + Game.TILE_SIZE + 10,
				(int) (((health * 1.0) / maxHealth) * (Game.TILE_SIZE + 8)), 12);
		
		//Draw a border around the base's health bar.
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1.4F));
		g2.drawRect(x - 4, y + Game.TILE_SIZE + 10, Game.TILE_SIZE + 8, 12);
	}

	//Getter for health.
	public int getHealth() {
		return health;
	}
	
	//Setter for health.
	public void setHealth(int health) {
		this.health = health;
	}

}
