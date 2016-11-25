// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: Class that the individual tiles in the tower defense game's map.
//              The tile may contain an entity of its own (a turret) given that
//              it is fillable.

import java.awt.Color;
import java.awt.Graphics;

public class Tile extends Entity {
	//Instance fields for the tile's child, whether it is fillable, and whether it is hovered.
	private Entity child;
	private boolean fillable;
	private boolean hovered;

	public Tile(int x, int y, boolean fillable) {
		super(x, y);
		this.fillable = fillable;
		
		//Fillable tiles should be green, otherwise brown.
		if(fillable) {
			color = Game.GREEN;
		} else {
			color = Game.BROWN;
		}
	}

	//Check whether the tile is filled.
	//If the tile has no child, then it is not filled.
	public boolean isFilled() {
		return child != null;
	}
	
	//Draw the tile.
	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, Game.TILE_SIZE, Game.TILE_SIZE);
		
		//If the tile has a child, draw it as well.
		if(isFilled()) {
			child.paint(g);
		}
		
		//If the tile is hovered, draw a semi-transparent rectangle over it.
		if(isHovered()) {
			g.setColor(new Color(255, 0, 0, 100));
			g.fillRect(x, y, Game.TILE_SIZE, Game.TILE_SIZE);
		}
	}
	
	//Empty implementation of the Entity class's abstract update method.
	@Override
	public void update() {
		
	}
	
	//Getter for child.
	public Entity getChild() {
		return child;
	}
	
	//Setter for child.
	public void setChild(Entity child) {
		this.child = child;
	}
	
	//Setter for hovered.
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	//Check whether the tile is hovered.
	public boolean isHovered() {
		return hovered;
	}
	
	//Setter for fillable.
	public void setFillable(boolean fillable) {
		this.fillable = fillable;
	}
	
	//Check whether the tile is fillable.
	public boolean isFillable() {
		return fillable;
	}
}
