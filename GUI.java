// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: Class that represents the GUI used to play the game.
//              The GUI has a panel on the left for upgrading turrets,
//              and a panel on the right to display the game.
//              It also controls the timer used to update the game every 
//              several millseconds.

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.Timer;


public class GUI extends JFrame {
	public static int SCREEN_WIDTH = 820;
	public static int SCREEN_HEIGHT = 600;
	public static int MILLISECONDS_PER_TICK = 50;
	private static Timer timer;
	
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tower Defense");
		setResizable(false);
		
		ControlsPanel controlsPanel = new ControlsPanel();
		GamePanel gamePanel = new GamePanel();
		controlsPanel.setGamePanel(gamePanel);
		
		Container cont = getContentPane();
		cont.setLayout(new BorderLayout());
		
		cont.add(controlsPanel, BorderLayout.WEST);
		cont.add(gamePanel, BorderLayout.CENTER);
		
		timer = new Timer(MILLISECONDS_PER_TICK, null);
		timer.addActionListener(controlsPanel);
		timer.addActionListener(gamePanel);
		timer.start();
	}
}
