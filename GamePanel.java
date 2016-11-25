import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

	private Tile hoveredTile;
	private Turret selectedTurret;
	public static final int WIDTH = GUI.SCREEN_WIDTH - ControlsPanel.WIDTH;

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, GUI.SCREEN_HEIGHT));
		setBackground(Color.RED);

		addMouseListener(new GameMouseListener());
		addMouseMotionListener(new GameMouseListener());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (!Game.isDone()) {
			setBackground(Color.RED);

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

			for (Enemy e : Game.enemies) {
				e.paint(g2);
			}
			for (Bullet b : Game.bullets) {
				b.paint(g2);
			}

			Game.base.paint(g2);
		} else {
			setBackground(Color.BLACK);
			Font font = new Font("Arial", Font.BOLD, 20);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("Game Over!", 10, 50);
			
			int seconds = (Game.getTotalTicks() * GUI.MILLISECONDS_PER_TICK) / 1000;
			g.drawString("You lasted " + (seconds / 60) + "m and " + (seconds % 60) + " s.", 10, 100);
		}
	}

	public Turret getSelectedTurret() {
		return selectedTurret;
	}

	private class GameMouseListener extends MouseAdapter {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			int tileX = e.getX() / Game.TILE_SIZE;
			int tileY = e.getY() / Game.TILE_SIZE;

			if (hoveredTile != null) {
				hoveredTile.setHovered(false);
			}

			hoveredTile = Game.map[tileX][tileY];
			hoveredTile.setHovered(true);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (hoveredTile != null && hoveredTile.isFillable()
					&& hoveredTile.getChild() == null
					&& Game.money >= Turret.COST) {
				Game.money -= Turret.COST;

				Turret turret = new Turret(hoveredTile.getX(),
						hoveredTile.getY());
				hoveredTile.setChild(turret);
				Game.addTurret(turret);

				if (selectedTurret != null) {
					selectedTurret.setSelected(false);
				}

				selectedTurret = turret;
				selectedTurret.setSelected(true);
			}

			if (hoveredTile.getChild() != null) {
				if (selectedTurret != null) {
					selectedTurret.setSelected(false);
				}
				selectedTurret = (Turret) hoveredTile.getChild();
				selectedTurret.setSelected(true);
			} else if (selectedTurret != null) {
				selectedTurret.setSelected(false);
				selectedTurret = null;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		repaint();
		Game.update();
	}
}
