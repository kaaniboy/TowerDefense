import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GUI extends JFrame {
	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;
	
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tower Defense");
		setResizable(false);
		
		JPanel controlsPanel = new ControlsPanel();
		JPanel gamePanel = new GamePanel();
		
		Container cont = getContentPane();
		cont.setLayout(new BorderLayout());
		
		cont.add(controlsPanel, BorderLayout.WEST);
		cont.add(gamePanel, BorderLayout.CENTER);
	}
}
