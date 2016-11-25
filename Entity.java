// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: The parent class used to represent objects that can be painted and updated.
//              Each entity also has a location and a color.

import java.awt.Color;
import java.awt.Graphics;

public abstract class Entity {
	//Instance fields for the entity's coordinates and color.
	protected int x;
	protected int y;
	protected Color color;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//Getter for x.
	public int getX() {
		return x;
	}

	//Getter for y.
	public int getY() {
		return y;
	}

	//Setter for x.
	public void setX(int x) {
		this.x = x;
	}

	//Setter for y.
	public void setY(int y) {
		this.y = y;
	}
	
	//Calculates the Euclidean distance between two entities.
	public double getDistanceTo(Entity e) {
		return Math.sqrt(Math.pow(getX() - e.getX(), 2) + Math.pow(getY() - e.getY(), 2));
	}
	
	//Abstract methods for updating and drawing the entity which will be implemented in subclasses.
	public abstract void update();
	public abstract void paint(Graphics g);
}
