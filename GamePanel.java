// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: Class that represents the panel on the right of the GUI that displays
//              the map and events that occur in the game. The panel is updated every 
//              iteration of the game.

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

	private Tile hoveredTile;
	private Turret selectedTurret;
	public static final int WIDTH = GUI.SCREEN_WIDTH - ControlsPanel.WIDTH;

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, GUI.SCREEN_HEIGHT));
		setBackground(Color.BLACK);

		addMouseListener(new GameMouseListener());
		addMouseMotionListener(new GameMouseListener());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Game.paint(g);
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
			if (!Game.isDone()) {
				int tileX = e.getX() / Game.TILE_SIZE;
				int tileY = e.getY() / Game.TILE_SIZE;

				if (hoveredTile != null) {
					hoveredTile.setHovered(false);
				}

				hoveredTile = Game.map[tileX][tileY];
				hoveredTile.setHovered(true);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!Game.isDone()) {
				if (hoveredTile != null && hoveredTile.isFillable()
						&& hoveredTile.getChild() == null
						&& Game.getMoney() >= Turret.COST) {
					Game.setMoney(Game.getMoney() - Turret.COST);

					Turret turret = new Turret(hoveredTile.getX(),
							hoveredTile.getY());
					hoveredTile.setChild(turret);
					Game.getTurrets().add(turret);

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
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		repaint();
		Game.update();
	}
}
