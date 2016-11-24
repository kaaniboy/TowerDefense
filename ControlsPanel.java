import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class ControlsPanel extends JPanel implements ActionListener {
	JLabel moneyLabel;
	public ControlsPanel() {
		setPreferredSize(new Dimension(200, GUI.SCREEN_HEIGHT));
		
		moneyLabel = new JLabel("Money: 100");
		add(moneyLabel);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
	}
}
