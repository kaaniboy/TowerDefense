import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.Timer;

public class Game {
	public static final int FIELD_SIZE = 20;
	public static final int TILE_SIZE = 600 / FIELD_SIZE;
	public static final String MAP_FILE = "map.txt";
	public static final Color BROWN = new Color(165, 126, 74);
	public static final Color GREEN = new Color(0, 230, 0);
	public static final Random random = new Random();

	private static ControlsPanel controlsPanel;
	private static GamePanel gamePanel;

	private static int enemyStartX;
	private static int enemyStartY;
	public static int round = 0;
	public static int money = 100;
	public static int ticksBetweenSpawns = 60;
	private static int currentTick = 0;
	public static Tile[][] map;

	public static List<Turret> turrets = new ArrayList<Turret>();
	public static List<Enemy> enemies = new ArrayList<Enemy>();
	public static List<Bullet> bullets = new ArrayList<Bullet>();
	public static List<Point> enemyPath = new ArrayList<Point>();
	public static Base base;

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
		/*
		 * for(Point p: enemyPath) { System.out.println(p.x + ", " + p.y); }
		 */
	}

	public static void update() {
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
	}
	
	public static void spawnEnemies() {
		if(currentTick == ticksBetweenSpawns) {
			currentTick = 0;
			enemies.add(new Enemy(enemyStartX * TILE_SIZE, enemyStartY * TILE_SIZE));
		} else {
			currentTick++;
		}
		
	}
	
	public static void cleanEnemies() {
		Iterator<Enemy> iter = enemies.iterator();
		while(iter.hasNext()) {
			if(iter.next().isDead()) {
				iter.remove();
			}
		}
	}
	
	public static void cleanBullets() {
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

	public static void addTurret(Turret t) {
		turrets.add(t);
	}

	public List<Turret> getTurrets() {
		return turrets;
	}

	public static void addTurret(Bullet b) {
		bullets.add(b);
	}

	public List<Bullet> getBullets() {
		return bullets;
	}

}
