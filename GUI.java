import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GUI extends JFrame {
	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;
	private static int MILLISECONDS_PER_TICK = 50;
	public static Timer timer;
	
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
