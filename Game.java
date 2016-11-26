// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: Class that handles the bulk of the game's logic. For example,
//              it is used to load in the map from the specified text file and build the enemy path. 
//              Furthermore, this class updates and paints every entity in the game (ie, turrets/bullets).

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Game {
	public static final int FIELD_SIZE = 20;
	public static final int TILE_SIZE = 600 / FIELD_SIZE;
	public static final int MONEY_PER_KILL = 10;
	public static final String MAP_FILE = "map.txt";
	public static final Color BROWN = new Color(165, 126, 74);
	public static final Color GREEN = new Color(0, 230, 0);
	public static final Random random = new Random();
	
	private static boolean isDone = false;
	private static int enemyStartX;
	private static int enemyStartY;
	private static int money = 100;
	private static int ticksBetweenSpawns = 150;
	private static int currentTick = 0;
	private static int totalTicks = 0;
	public static Tile[][] map;

	//The following fields contain all the entities that currently exist in the game.
	private static List<Turret> turrets = new ArrayList<Turret>();
	private static List<Enemy> enemies = new ArrayList<Enemy>();
	private static List<Bullet> bullets = new ArrayList<Bullet>();
	private static List<Point> enemyPath = new ArrayList<Point>();
	private static Base base;

	//Method that initializes the game by loading in the map and generating the enemy path.
	public static void init() {
		map = new Tile[FIELD_SIZE][FIELD_SIZE];

		File file = null;
		Scanner sc = null;
		
		//Attempt to read the provided file to load a map into the game.
		try {
			file = new File(MAP_FILE);
			sc = new Scanner(file);
			
			//The loop will iterate once for every tile spot in the map.
			for (int y = 0; y < Game.FIELD_SIZE; y++) {
				for (int x = 0; x < Game.FIELD_SIZE; x++) {
					int curr = sc.nextInt();
					
					//The number 0 represents a fillable tile, whereas 1 represents an unfillable tile.
					if (curr == 0) {
						map[x][y] = new Tile(x * TILE_SIZE, y * TILE_SIZE, true);
					} else if (curr == 1) {
						map[x][y] = new Tile(x * TILE_SIZE, y * TILE_SIZE, false);
					}
				}
			}
			
			//The last two numbers in the text file represent the starting point of the enemy path.
			enemyStartX = sc.nextInt();
			enemyStartY = sc.nextInt();

			enemyPath.add(new Point(enemyStartX, enemyStartY));
			buildEnemyPath(enemyStartX, enemyStartY);
			
			//The location of the base should be right at the end of the enemy path.
			Point basePoint = enemyPath.get(enemyPath.size() - 1);
			basePoint.x += 1;
			
			//User should not be able to place a turret on the base.
			map[basePoint.x][basePoint.y].setFillable(false);
			
			base = new Base(basePoint.x * TILE_SIZE, basePoint.y * TILE_SIZE);

			sc.close();
		} catch (FileNotFoundException e) {
			//Occurs only if the specified file could not be found.
			System.out.println("The map file was not found.");
			System.exit(0);
		} finally {
			//Make sure to close the scanner even if an exception was thrown earlier.
			if (sc != null) {
				sc.close();
			}
		}
	}

	//Automatically generate the enemy path given the the start point.
	private static void buildEnemyPath(int startX, int startY) {
		//Keep track of every point that has already been visited.
		List<Point> visited = new ArrayList<Point>();
		int currX = startX;
		int currY = startY;
		boolean done = false;

		//Check all adjacent tiles (North, East, South, and West).
		//If an adjacent tile has not been visited yet and is a part of the enemy path, move to that tile.
		while (!done) {
			//Add the current tile to the list of visited tiles.
			visited.add(new Point(currX, currY));
			
			//Have to make sure that the adjacent tiles are within the bounds of the map.
			
			if (currY - 1 >= 0 && !visited.contains(new Point(currX, currY - 1))
					&& !map[currX][currY - 1].isFillable()) {
				currY = currY - 1;
			} else if (currY + 1 < FIELD_SIZE && !visited.contains(new Point(currX, currY + 1))
					&& !map[currX][currY + 1].isFillable()) {
				currY = currY + 1;
			} else if (currX - 1 >= 0 && !visited.contains(new Point(currX - 1, currY))
					&& !map[currX - 1][currY].isFillable()) {
				currX = currX - 1;
			} else if (currX + 1 < FIELD_SIZE && !visited.contains(new Point(currX + 1, currY))
					&& !map[currX + 1][currY].isFillable()) {
				currX = currX + 1;
			} else {
				done = true;
			}
		}

		enemyPath = visited;
	}

	//Method that updates every entity in the game, and also removes entity that are no longer needed.
	public static void update() {
		updateGameStatus();
		
		cleanEnemies();
		cleanBullets();
		
		spawnEnemies();
		
		//Iterate through all turrets, enemies, and bullets to update them.
		for (Turret t : turrets) {
			t.update();
		}
		for (Enemy e : enemies) {
			e.update();
		}
		for (Bullet b : bullets) {
			b.update();
		}
		base.update();
	}
	
	//Draw the map as well as all other entities currently in the game.
	public static void paint(Graphics g) {
		//Do the following only if the game has not ended yet.
		if (!Game.isDone()) {
			
			//Draw every tile in the map.
			for (int y = 0; y < Game.FIELD_SIZE; y++) {
				for (int x = 0; x < Game.FIELD_SIZE; x++) {
					Game.map[x][y].paint(g);
				}
			}

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(0.7F));
			g2.setColor(Color.BLACK);
			
			//Draw a grid around every tile in the map.
			for (int i = 0; i < Game.FIELD_SIZE; i++) {
				g.drawLine(0, i * Game.TILE_SIZE, 600, i * Game.TILE_SIZE);
				g.drawLine(i * Game.TILE_SIZE, 0, i * Game.TILE_SIZE, 600);
			}

			//Draw every enemy and bullet.
			for (Enemy e : getEnemies()) {
				e.paint(g2);
			}
			for (Bullet b : getBullets()) {
				b.paint(g2);
			}
			
			//Draw the base.
			getBase().paint(g2);
		} else {
			//Only runs if the game has already ended.
			Font font = new Font("Arial", Font.BOLD, 20);
			g.setColor(Color.WHITE);
			g.setFont(font);
			
			//Display a game-over message and the time that the user was able to survive the enemies.
			g.drawString("Game Over!", 10, 50);

			int seconds = (Game.getTotalTicks() * GUI.MILLISECONDS_PER_TICK) / 1000;
			g.drawString("You lasted " + (seconds / 60) + "m and "
					+ (seconds % 60) + " s.", 10, 100);
		}
	}
	
	//Method that checks whether the base is dead yet.
	//If the base is dead, the game should end.
	private static void updateGameStatus() {
		if(base.getHealth() <= 0) {
			isDone = true;
		} else {
			totalTicks++;
			
			if(totalTicks % 1000 == 0 && ticksBetweenSpawns >= 25) {
				ticksBetweenSpawns -= 10;
			}
		}
	}
	
	//Method that spawns enemies every couple of ticks.
	private static void spawnEnemies() {
		//Only spawn an enemy once the current number of ticks equals the ticks between spawns.
		if(currentTick == ticksBetweenSpawns) {
			//Once an enemy is spawned, reset the tick counter.
			currentTick = 0;
			enemies.add(new Enemy(enemyStartX * TILE_SIZE, enemyStartY * TILE_SIZE));
		} else {
			currentTick++;
		}
	}
	
	//Method that deletes all enemies that are dead or have collided with the base.
	private static void cleanEnemies() {
		//Iterator needs to be used to delete items in the list while looping.
		Iterator<Enemy> iter = enemies.iterator();
		while(iter.hasNext()) {
			Enemy e = iter.next();
			if(e.isDead()) {
				//The user receives money every time they kill an enemy.
				money += MONEY_PER_KILL;
				iter.remove();
			} else if(base.getDistanceTo(e) < 5) {
				//When an enemy hits the base, the base should be damaged.
				base.setHealth(base.getHealth() - e.getDamage());
				iter.remove();
			}
		}
	}
	
	//Method that deletes all bullets outside of the screen and that have already collided with an enemy.
	private static void cleanBullets() {
		//Iterator needs to be used to delete items in the list while looping.
		Iterator<Bullet> iter = bullets.iterator();
		Bullet b = null;
		while(iter.hasNext()) {
			b = iter.next();
			//Check if the bullet is outside of the screen.
			if(b.getX() < 0 || b.getX() > GamePanel.WIDTH || b.getY() < 0 || b.getY() > GUI.SCREEN_HEIGHT) {
				iter.remove();
			} else if(b.hasCollided()) {
				//Check if the bullet has already hit an enemy.
				iter.remove();
			}
		}
	}

	//Convert Swing's y coordinate to the usual Cartesean y coordinate.
	public static int toCartesianY(int y) {
		return GUI.SCREEN_HEIGHT - y;
	}
	
	//Getter for isDone.
	public static boolean isDone() {
		return isDone;
	}
	
	//Getter for totalTicks, which represents how many ticks the user survived before losing.
	public static int getTotalTicks() {
		return totalTicks;
	}
	
	//Getter for turrets.
	public static List<Turret> getTurrets() {
		return turrets;
	}

	//Getter for bullets.
	public static List<Bullet> getBullets() {
		return bullets;
	}
	
	//Getter for enemies.
	public static List<Enemy> getEnemies() {
		return enemies;
	}
	
	//Getter for enemyPath.
	public static List<Point> getEnemyPath() {
		return enemyPath;
	}
	
	//Getter for base.
	public static Base getBase() {
		return base;
	}
	
	//Getter for money.
	public static int getMoney() {
		return money;
	}
	
	//Setter for money.
	public static void setMoney(int money) {
		Game.money = money;
	}
}
