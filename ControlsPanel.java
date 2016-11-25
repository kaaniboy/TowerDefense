// Assignment: Honors Contract
// Arizona State University CSE205
// Name: Kaan Aksoy
// StudentID: 1210619069
// Lecture: T, Th 4:30 PM - 5:45 PM, Dr. Nakamura
// Description: Class that represents the panel on the left of the GUI that the user
//              can use to upgrade their turrets and to see how much money they have.
//              The panel is updated every iteration of the game.

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
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

	public static final int WIDTH = 220;

	public ControlsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(WIDTH, GUI.SCREEN_HEIGHT));

		add(Box.createRigidArea(new Dimension(0, 20)));

		moneyLabel = new JLabel("Money: $" + Game.getMoney());
		moneyLabel.setFont(moneyLabel.getFont().deriveFont(20.0F));
		moneyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(moneyLabel);
		add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel upgradesPanel = new JPanel();
		upgradesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		upgradesPanel.setBorder(BorderFactory
				.createTitledBorder("Turret Upgrades"));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2));

		increaseSpeedButton = new JButton("<html>Upgrade<br>Speed</html>");
		increaseDamageButton = new JButton("<html>Upgrade<br>Damage</html>");

		increaseSpeedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Turret selected = gamePanel.getSelectedTurret();
				if (selected != null
						&& Game.getMoney() >= selected.getPriceToUpgradeSpeed()
						&& selected.getSpeed() > 0) {
					Game.setMoney(Game.getMoney() - selected.getPriceToUpgradeSpeed());
					selected.upgradeSpeed();
				}
			}
		});

		increaseDamageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Turret selected = gamePanel.getSelectedTurret();
				if (selected != null
						&& Game.getMoney() >= selected.getPriceToUpgradeDamage()) {
					Game.setMoney(Game.getMoney() - selected.getPriceToUpgradeDamage());
					selected.upgradeDamage();
				}
			}
		});

		buttonsPanel.add(increaseSpeedButton);
		buttonsPanel.add(increaseDamageButton);

		upgradesPanel.add(buttonsPanel);

		infoLabel = new JLabel(
				"<html>The blue turret is the<br>currently selected one.<br></html>");
		speedLabel = new JLabel();
		damageLabel = new JLabel();

		upgradesPanel.add(infoLabel);
		upgradesPanel.add(speedLabel);
		upgradesPanel.add(damageLabel);

		add(upgradesPanel);
		add(Box.createVerticalGlue());
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
		if (!Game.isDone()) {
			moneyLabel.setText("Money: $" + Game.getMoney());

			if (gamePanel.getSelectedTurret() != null) {
				increaseSpeedButton.setEnabled(true);
				increaseDamageButton.setEnabled(true);

				infoLabel.setVisible(true);
				damageLabel.setVisible(true);
				speedLabel.setVisible(true);

				damageLabel.setText("Power: "
						+ gamePanel.getSelectedTurret().getDamage());
				speedLabel.setText("Time Between Shots: "
						+ gamePanel.getSelectedTurret().getSpeed());
			} else {
				increaseSpeedButton.setEnabled(false);
				increaseDamageButton.setEnabled(false);

				infoLabel.setVisible(false);
				damageLabel.setVisible(false);
				speedLabel.setVisible(false);
			}
		} else {
			increaseSpeedButton.setEnabled(false);
			increaseDamageButton.setEnabled(false);
			infoLabel.setVisible(false);
			damageLabel.setVisible(false);
			speedLabel.setVisible(false);
		}
	}
}
