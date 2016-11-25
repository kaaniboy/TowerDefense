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
	private Entity child;
	private boolean fillable;
	private boolean hovered;

	public Tile(int x, int y, boolean fillable) {
		super(x, y);
		this.fillable = fillable;
		
		if(fillable) {
			color = Game.GREEN;
		} else {
			color = Game.BROWN;
		}
	}

	public boolean isFilled() {
		return child != null;
	}
	
	

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, Game.TILE_SIZE, Game.TILE_SIZE);
		
		if(isFilled()) {
			child.paint(g);
		}
		
		if(isHovered()) {
			g.setColor(new Color(255, 0, 0, 100));
			g.fillRect(x, y, Game.TILE_SIZE, Game.TILE_SIZE);
		}
	}
	
	@Override
	public void update() {
		
	}

	public Entity getChild() {
		return child;
	}

	public void setChild(Entity child) {
		this.child = child;
	}
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	public void setFillable(boolean fillable) {
		this.fillable = fillable;
	}
	
	public boolean isFillable() {
		return fillable;
	}
}
