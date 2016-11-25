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
	public static final int COST = 20;
	public static final int DAMAGE_UPGRADE_INCREMENT = 10;
	public static final int SPEED_UPGRADE_INCREMENT = 5;

	private int rotation;
	private int armLength;

	private int ticksBetweenShots;
	private int currentTick;
	private int bulletDamage;

	private int priceToUpgradeSpeed;
	private int priceToUpgradeDamage;

	private boolean selected = false;

	public Turret(int x, int y) {
		super(x, y);

		color = Color.RED;
		rotation = (int) (Game.random.nextDouble() * 360);
		armLength = 12;
		bulletDamage = 10;
		ticksBetweenShots = 50;
		priceToUpgradeSpeed = priceToUpgradeDamage = 20;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		if (isSelected()) {
			g2.setColor(Color.BLUE);
		} else {
			g2.setColor(color);
		}

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
			g2.drawLine(centerX, centerY, centerX + (int) (armLength * Math.cos(Math.toRadians(rotation))),
					centerY - (int) (armLength * Math.sin(Math.toRadians(rotation))));
		}

	}

	@Override
	public void update() {
		Enemy target = getClosestEnemy();
		if (target != null) {
			rotation = (int) Math
					.toDegrees(Math.atan2(Game.toCartesianY(target.getY()) - Game.toCartesianY(y), target.getX() - x));

			if (rotation < 0) {
				rotation = 360 - Math.abs(rotation);
			}

			double xVelocity = Math.cos(Math.toRadians(rotation));
			double yVelocity = -1 * Math.sin(Math.toRadians(rotation));

			if (currentTick == ticksBetweenShots) {
				Bullet bullet = new Bullet(x + Game.TILE_SIZE / 2, y + Game.TILE_SIZE / 2, xVelocity, yVelocity);
				bullet.setDamage(bulletDamage);
				Game.getBullets().add(bullet);

				currentTick = 0;
			} else {
				currentTick++;
			}
		}
	}
	
	private Enemy getClosestEnemy() {
		if(Game.getEnemies().isEmpty())
			return null;
		
		Enemy closest = null;
		double closestDist = Double.MAX_VALUE;
		for(Enemy e: Game.getEnemies()) {
			double dist = getDistanceTo(e);
			if(dist < closestDist) {
				closest = e;
				closestDist = dist;
			}
		}
		
		return closest;
	}

	public void upgradeDamage() {
		bulletDamage += DAMAGE_UPGRADE_INCREMENT;
		priceToUpgradeDamage += 30;
	}

	public void upgradeSpeed() {
		ticksBetweenShots -= SPEED_UPGRADE_INCREMENT;
		priceToUpgradeSpeed += 30;
	}

	public int getPriceToUpgradeSpeed() {
		return priceToUpgradeSpeed;
	}

	public int getPriceToUpgradeDamage() {
		return priceToUpgradeDamage;
	}

	public int getDamage() {
		return bulletDamage;
	}
	
	public int getSpeed() {
		return ticksBetweenShots;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
