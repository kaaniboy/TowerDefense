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
	
	//Instance fields for the currently selected turret and the highlighted tile.
	private Tile hoveredTile;
	private Turret selectedTurret;
	
	//Calcuate the width for the GamePanel.
	public static final int WIDTH = GUI.SCREEN_WIDTH - ControlsPanel.WIDTH;

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, GUI.SCREEN_HEIGHT));
		setBackground(Color.BLACK);
		
		
		//Add the GameMouseListener to listen for mouse clicks and moves.
		addMouseListener(new GameMouseListener());
		addMouseMotionListener(new GameMouseListener());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Call the paint method in the Game class that handles all drawing.
		Game.paint(g);
	}

	//Getter for selectedTurret.
	public Turret getSelectedTurret() {
		return selectedTurret;
	}

	//Private inner class that is used to handle mouse presses and mouse movements in the GamePanel.
	private class GameMouseListener extends MouseAdapter {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			//Only do the following if the user has not lost the game yet.
			if (!Game.isDone()) {
				//Find which tile the mouse is currently hovering over.
				int tileX = e.getX() / Game.TILE_SIZE;
				int tileY = e.getY() / Game.TILE_SIZE;
				
				//Remove the previously hovered tile.
				if (hoveredTile != null) {
					hoveredTile.setHovered(false);
				}
				
				//Set the newly hovered tile.
				hoveredTile = Game.map[tileX][tileY];
				hoveredTile.setHovered(true);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			//Only do the following if the user has not lost the game yet.
			if (!Game.isDone()) {
				
				//Check if a turret can be placed on the currently hovered tile.
				if (hoveredTile != null && hoveredTile.isFillable()
						&& hoveredTile.getChild() == null
						&& Game.getMoney() >= Turret.COST) {
					Game.setMoney(Game.getMoney() - Turret.COST);
					
					//Create a new turret and set is as a child of the hovered tile.
					Turret turret = new Turret(hoveredTile.getX(),
							hoveredTile.getY());
					hoveredTile.setChild(turret);
					Game.getTurrets().add(turret);

					if (selectedTurret != null) {
						selectedTurret.setSelected(false);
					}
					
					//Set the newly-bought turret as the selected turret.
					selectedTurret = turret;
					selectedTurret.setSelected(true);
				}
				
				//Even if a turret cannot be placed, update the currently selected turret.
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

	//Called every time the game timer ticks.
	@Override
	public void actionPerformed(ActionEvent event) {
		//Repaint the panel and update the game state.
		repaint();
		Game.update();
	}
}
