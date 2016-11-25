// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: This class contains the main method for the entire program.
//              The main method creates the GUI to start the game.
public class Main {
	public static void main(String[] args) {
		Game.init();
		GUI gui = new GUI();
		gui.setSize(GUI.SCREEN_WIDTH, GUI.SCREEN_HEIGHT);
		gui.setVisible(true);
	}
}
