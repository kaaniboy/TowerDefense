import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlsPanel extends JPanel implements ActionListener {
	private JLabel moneyLabel;
	private JButton increaseSpeedButton;
	private JButton increaseDamageButton;
	private JLabel speedLabel;
	private JLabel damageLabel;
	private JLabel infoLabel;

	private GamePanel gamePanel;

	public static final int WIDTH = 200;

	public ControlsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(WIDTH, GUI.SCREEN_HEIGHT));
		moneyLabel = new JLabel("Money: " + Game.money);
		add(moneyLabel);

		JPanel upgradesPanel = new JPanel();
		upgradesPanel.setBorder(BorderFactory.createTitledBorder("Turret Upgrades"));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2));

		increaseSpeedButton = new JButton("<html>Increase<br>Speed</html>");
		increaseDamageButton = new JButton("<html>Increase<br>Damage</html>");

		increaseSpeedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		increaseDamageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Turret selected = gamePanel.getSelectedTurret();
				if(selected != null) {
					selected.setDamage(selected.getDamage() + 10);
				}
			}
		});
		
		buttonsPanel.add(increaseSpeedButton);
		buttonsPanel.add(increaseDamageButton);

		upgradesPanel.add(buttonsPanel);

		infoLabel = new JLabel("The blue turret is the currently selected one.");
		speedLabel = new JLabel();
		damageLabel = new JLabel();

		upgradesPanel.add(infoLabel);
		upgradesPanel.add(speedLabel);
		upgradesPanel.add(damageLabel);

		add(upgradesPanel);
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		moneyLabel.setText("Money: " + Game.money);

		if (gamePanel.getSelectedTurret() != null) {
			increaseSpeedButton.setEnabled(true);
			increaseDamageButton.setEnabled(true);

			infoLabel.setVisible(true);
			damageLabel.setVisible(true);
			speedLabel.setVisible(true);

			damageLabel.setText("Power: " + gamePanel.getSelectedTurret().getDamage());
		} else {
			increaseSpeedButton.setEnabled(false);
			increaseDamageButton.setEnabled(false);

			infoLabel.setVisible(false);
			damageLabel.setVisible(false);
			speedLabel.setVisible(false);
		}
	}
}
