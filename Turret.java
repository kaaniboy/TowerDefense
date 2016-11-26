// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: Class that represents the turrets that users can place on the map
//              in the tower defense game. Turrets can have both their speed and
//              damage upgraded using in-game money. They also shoot bullets at a
//              specified rate.

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Turret extends Entity {
	//Constants for the cost of buying turrets and how quickly turrets can be upgraded.
	public static final int COST = 30;
	public static final int DAMAGE_UPGRADE_INCREMENT = 5;
	public static final int SPEED_UPGRADE_INCREMENT = 5;

	//Instance fields used to draw the barrel of the turret.
	private int rotation;
	private int armLength;

	//Instance fields used to put delays between each shot of the turret.
	private int ticksBetweenShots;
	private int currentTick;
	
	private int bulletDamage;

	//Instance fields for how much it costs to upgrade this turret's speed and damage.
	private int priceToUpgradeSpeed;
	private int priceToUpgradeDamage;

	private boolean selected = false;

	public Turret(int x, int y) {
		super(x, y);

		color = Color.RED;
		
		//Randomly set the turrets initial rotation.
		rotation = (int) (Game.random.nextDouble() * 360);
		
		armLength = 12;
		bulletDamage = 10;
		ticksBetweenShots = 50;
		priceToUpgradeSpeed = priceToUpgradeDamage = 20;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		//If the turret is selected, draw it blue. Otherwise, draw it using the default color.
		if (isSelected()) {
			g2.setColor(Color.BLUE);
		} else {
			g2.setColor(color);
		}
		
		
		//Draw the body of the turret.
		g2.fillOval(x + 2, y + 2, Game.TILE_SIZE - 4, Game.TILE_SIZE - 4);

		int centerX = x + Game.TILE_SIZE / 2;
		int centerY = y + Game.TILE_SIZE / 2;

		g2.setColor(Color.YELLOW);
		g2.setStroke(new BasicStroke(2F));
		
		
		//Draw the barrel/arm of the turret.
		if (rotation == 0) {
			g2.drawLine(centerX, centerY, centerX + armLength, centerY);
		} else if (rotation == 90) {
			g2.drawLine(centerX, centerY, centerX, centerY - armLength);
		} else if (rotation == 180) {
			g2.drawLine(centerX, centerY, centerX - armLength, centerY);
		} else if (rotation == 270) {
			g2.drawLine(centerX, centerY, centerX, centerY + armLength);
		} else {
			
			//If the barrel rotation is not divisible by 90, calculate its direction using sine/cosine.
			g2.drawLine(centerX, centerY, centerX + (int) (armLength * Math.cos(Math.toRadians(rotation))),
					centerY - (int) (armLength * Math.sin(Math.toRadians(rotation))));
		}

	}

	//Update the turret by rotating its barrel and shooting a bullet if necessary.
	@Override
	public void update() {
		Enemy target = getClosestEnemy();
		
		if (target != null) {
			
			//Calculate the angle from the turret to the nearest enemy.
			rotation = (int) Math
					.toDegrees(Math.atan2(Game.toCartesianY(target.getY()) - Game.toCartesianY(y), target.getX() - x));
			
			//Guarantee that the turret's rotation is always positive.
			if (rotation < 0) {
				rotation = 360 - Math.abs(rotation);
			}
			
			//Calculate the directional velocity of the bullet that will be shot.
			double xVelocity = Math.cos(Math.toRadians(rotation));
			double yVelocity = -1 * Math.sin(Math.toRadians(rotation));
			
			//If currentTick is equal to ticksBetweenShots, it means the turret should shoot.
			if (currentTick == ticksBetweenShots) {
				Bullet bullet = new Bullet(x + Game.TILE_SIZE / 2, y + Game.TILE_SIZE / 2, xVelocity, yVelocity);
				bullet.setDamage(bulletDamage);
				Game.getBullets().add(bullet);
				
				//Reset the tick counter for the next shot.
				currentTick = 0;
			} else {
				currentTick++;
			}
		}
	}
	
	//Method that returns the enemy closest to the turret. If there is none, it returns null.
	private Enemy getClosestEnemy() {
		//If there are no enemies in the game, automatically return null.
		if(Game.getEnemies().isEmpty())
			return null;
		
		Enemy closest = null;
		double closestDist = Double.MAX_VALUE;
		//Iterate through every enemy in the game.
		
		for(Enemy e: Game.getEnemies()) {
			double dist = getDistanceTo(e);
			
			//Check if the current enemy is closer than any others previously checked.
			if(dist < closestDist) {
				closest = e;
				closestDist = dist;
			}
		}
		
		return closest;
	}
	
	//Method used to upgrade how much damage the turret's bullets do.
	public void upgradeDamage() {
		bulletDamage += DAMAGE_UPGRADE_INCREMENT;
		//After every upgrade, the price of the next upgrade increases.
		priceToUpgradeDamage += 30;
	}
	
	//Method used to upgrade how quickly the turret can shoot bullets.
	public void upgradeSpeed() {
		ticksBetweenShots -= SPEED_UPGRADE_INCREMENT;
		//After every upgrade, the price of the next upgrade increases.
		priceToUpgradeSpeed += 30;
	}
	
	//Getter for priceToUpgradeSpeed.
	public int getPriceToUpgradeSpeed() {
		return priceToUpgradeSpeed;
	}

	//Getter for priceToUpgradeDamage.
	public int getPriceToUpgradeDamage() {
		return priceToUpgradeDamage;
	}
	
	//Getter for bulletDamage.
	public int getDamage() {
		return bulletDamage;
	}
	
	//Getter for ticksBetweenShots.
	public int getSpeed() {
		return ticksBetweenShots;
	}
	
	//Check whether this turret is selected.
	public boolean isSelected() {
		return selected;
	}
	
	//Setter for selected.
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
