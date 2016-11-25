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
import java.io.IOException;
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

	private static List<Turret> turrets = new ArrayList<Turret>();
	private static List<Enemy> enemies = new ArrayList<Enemy>();
	private static List<Bullet> bullets = new ArrayList<Bullet>();
	private static List<Point> enemyPath = new ArrayList<Point>();
	private static Base base;

	public static void init() {// ControlsPanel cPanel, GamePanel gPanel) {
		// controlsPanel = cPanel;
		// gamePanel = gPanel;

		map = new Tile[FIELD_SIZE][FIELD_SIZE];

		File file = null;
		Scanner sc = null;

		try {
			file = new File(MAP_FILE);
			sc = new Scanner(file);

			for (int y = 0; y < Game.FIELD_SIZE; y++) {
				for (int x = 0; x < Game.FIELD_SIZE; x++) {
					int curr = sc.nextInt();

					if (curr == 0) {
						map[x][y] = new Tile(x * TILE_SIZE, y * TILE_SIZE, true);
					} else if (curr == 1) {
						map[x][y] = new Tile(x * TILE_SIZE, y * TILE_SIZE, false);
					}
				}
			}

			enemyStartX = sc.nextInt();
			enemyStartY = sc.nextInt();

			enemyPath.add(new Point(enemyStartX, enemyStartY));
			buildEnemyPath(enemyStartX, enemyStartY);
			Point basePoint = enemyPath.get(enemyPath.size() - 1);
			basePoint.x += 1;
			
			map[basePoint.x][basePoint.y].setFillable(false);
			
			base = new Base(basePoint.x * TILE_SIZE, basePoint.y * TILE_SIZE);

			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("The map file was not found.");
			System.exit(0);
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

	private static void buildEnemyPath(int startX, int startY) {
		List<Point> visited = new ArrayList<Point>();
		int currX = startX;
		int currY = startY;
		boolean done = false;

		while (!done) {
			visited.add(new Point(currX, currY));
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

	public static void update() {
		updateGameStatus();
		
		cleanEnemies();
		cleanBullets();
		
		spawnEnemies();
		
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
	
	public static void paint(Graphics g) {
		if (!Game.isDone()) {

			for (int y = 0; y < Game.FIELD_SIZE; y++) {
				for (int x = 0; x < Game.FIELD_SIZE; x++) {
					Game.map[x][y].paint(g);
				}
			}

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(0.7F));
			g2.setColor(Color.BLACK);
			for (int i = 0; i < Game.FIELD_SIZE; i++) {
				g.drawLine(0, i * Game.TILE_SIZE, 600, i * Game.TILE_SIZE);
				g.drawLine(i * Game.TILE_SIZE, 0, i * Game.TILE_SIZE, 600);
			}

			for (Enemy e : getEnemies()) {
				e.paint(g2);
			}
			for (Bullet b : getBullets()) {
				b.paint(g2);
			}

			getBase().paint(g2);
		} else {
			Font font = new Font("Arial", Font.BOLD, 20);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("Game Over!", 10, 50);

			int seconds = (Game.getTotalTicks() * GUI.MILLISECONDS_PER_TICK) / 1000;
			g.drawString("You lasted " + (seconds / 60) + "m and "
					+ (seconds % 60) + " s.", 10, 100);
		}
	}
	
	private static void updateGameStatus() {
		if(base.getHealth() <= 0) {
			isDone = true;
		} else {
			totalTicks++;
		}
	}
	private static void spawnEnemies() {
		if(currentTick == ticksBetweenSpawns) {
			currentTick = 0;
			enemies.add(new Enemy(enemyStartX * TILE_SIZE, enemyStartY * TILE_SIZE));
		} else {
			currentTick++;
		}
	}
	
	private static void cleanEnemies() {
		Iterator<Enemy> iter = enemies.iterator();
		while(iter.hasNext()) {
			Enemy e = iter.next();
			if(e.isDead()) {
				money += MONEY_PER_KILL;
				iter.remove();
			} else if(base.getDistanceTo(e) < 5) {
				base.setHealth(base.getHealth() - e.getDamage());
				iter.remove();
			}
		}
	}
	
	private static void cleanBullets() {
		Iterator<Bullet> iter = bullets.iterator();
		Bullet b = null;
		while(iter.hasNext()) {
			b = iter.next();
			if(b.getX() < 0 || b.getX() > GamePanel.WIDTH || b.getY() < 0 || b.getY() > GUI.SCREEN_HEIGHT) {
				iter.remove();
			} else if(b.hasCollided()) {
				iter.remove();
			}
		}
	}

	public static int toCartesianY(int y) {
		return GUI.SCREEN_HEIGHT - y;
	}
	
	public static boolean isDone() {
		return isDone;
	}
	
	public static int getTotalTicks() {
		return totalTicks;
	}
	
	public static List<Turret> getTurrets() {
		return turrets;
	}

	public static List<Bullet> getBullets() {
		return bullets;
	}
	
	public static List<Enemy> getEnemies() {
		return enemies;
	}
	
	public static List<Point> getEnemyPath() {
		return enemyPath;
	}
	
	public static Base getBase() {
		return base;
	}
	
	public static int getMoney() {
		return money;
	}
	
	public static void setMoney(int money) {
		Game.money = money;
	}
}
