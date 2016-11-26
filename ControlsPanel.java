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

	// Instance fields for the various labels and buttons used in the
	// ControlsPanel.
	private JLabel moneyLabel;
	private JButton increaseSpeedButton;
	private JButton increaseDamageButton;
	private JLabel speedLabel;
	private JLabel damageLabel;
	private JLabel infoLabel;

	private GamePanel gamePanel;

	// Constant defining how wide the ControlsPanel should be.
	public static final int WIDTH = 220;

	public ControlsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(WIDTH, GUI.SCREEN_HEIGHT));

		// Add padding to the top of the panel.
		add(Box.createRigidArea(new Dimension(0, 20)));

		// Create the label showing money and set it to be centered
		// horizontally.
		moneyLabel = new JLabel("Money: $" + Game.getMoney());
		moneyLabel.setFont(moneyLabel.getFont().deriveFont(20.0F));
		moneyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(moneyLabel);

		// Add padding below the label showing money.
		add(Box.createRigidArea(new Dimension(0, 20)));

		// Panel that contains the components necessary to upgrade turrets.
		JPanel upgradesPanel = new JPanel();
		upgradesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Put a border around the upgrades panel.
		upgradesPanel.setBorder(BorderFactory
				.createTitledBorder("Turret Upgrades"));

		// buttonsPanel uses a GridLayout to display two upgrade buttons
		// side-by-side.
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2));

		// Using the HTML tags allows the creation of multi-line buttons.
		increaseSpeedButton = new JButton("<html>Upgrade<br>Speed</html>");
		increaseDamageButton = new JButton("<html>Upgrade<br>Damage</html>");

		// Listener for when the increaseSpeedButton is clicked.
		increaseSpeedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Turret selected = gamePanel.getSelectedTurret();

				// If there is a selected turret and the user has enough money,
				// upgrade the turret's speed.
				if (selected != null
						&& Game.getMoney() >= selected.getPriceToUpgradeSpeed()
						&& selected.getSpeed() > 0) {
					// Reduce how much money the user has remaining after the
					// purchase.
					Game.setMoney(Game.getMoney()
							- selected.getPriceToUpgradeSpeed());
					selected.upgradeSpeed();
				}
			}
		});

		// Listener for when the increaseDamageButton is clicked.
		increaseDamageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Turret selected = gamePanel.getSelectedTurret();
				// If there is a selected turret and the user has enough money,
				// upgrade the turret's damage.
				if (selected != null
						&& Game.getMoney() >= selected
								.getPriceToUpgradeDamage()) {
					// Reduce how much money the user has remaining after the
					// purchase.
					Game.setMoney(Game.getMoney()
							- selected.getPriceToUpgradeDamage());
					selected.upgradeDamage();
				}
			}
		});

		buttonsPanel.add(increaseSpeedButton);
		buttonsPanel.add(increaseDamageButton);

		upgradesPanel.add(buttonsPanel);

		// Create a label showing instructions on how to use the upgrades panel.
		infoLabel = new JLabel(
				"<html>The blue turret is the<br>currently selected one.<br></html>");
		speedLabel = new JLabel();
		damageLabel = new JLabel();

		// Add all the labels to the upgrades panel.
		upgradesPanel.add(infoLabel);
		upgradesPanel.add(speedLabel);
		upgradesPanel.add(damageLabel);

		add(upgradesPanel);
		add(Box.createVerticalGlue());
	}

	// Setter for gamePanel.
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	// Called every time the game timer ticks.
	@Override
	public void actionPerformed(ActionEvent event) {
		// Do the following if the game has not ended yet.
		if (!Game.isDone()) {
			// Update the money label.
			moneyLabel.setText("Money: $" + Game.getMoney());

			// If the user has selected a turret, make the upgrades panel
			// usable.
			if (gamePanel.getSelectedTurret() != null) {
				increaseSpeedButton.setEnabled(true);
				increaseDamageButton.setEnabled(true);
				
				
				//Update upgrade prices on the buttons.
				increaseSpeedButton.setText("<html>Upgrade<br>Speed<br>($"
						+ gamePanel.getSelectedTurret()
								.getPriceToUpgradeSpeed() + ")</html>");

				increaseDamageButton.setText("<html>Upgrade<br>Damage<br>($"
						+ gamePanel.getSelectedTurret()
								.getPriceToUpgradeDamage() + ")</html>");

				infoLabel.setVisible(true);
				damageLabel.setVisible(true);
				speedLabel.setVisible(true);

				damageLabel.setText("Power: "
						+ gamePanel.getSelectedTurret().getDamage());
				speedLabel.setText("Time Between Shots: "
						+ gamePanel.getSelectedTurret().getSpeed());
			} else {

				// If the user has not selected a turret, make the upgrades
				// panel unusable.
				increaseSpeedButton.setEnabled(false);
				increaseDamageButton.setEnabled(false);

				infoLabel.setVisible(false);
				damageLabel.setVisible(false);
				speedLabel.setVisible(false);
			}
		} else {
			// If the game has already ended, disable all buttons and hide all
			// labels.
			increaseSpeedButton.setEnabled(false);
			increaseDamageButton.setEnabled(false);
			infoLabel.setVisible(false);
			damageLabel.setVisible(false);
			speedLabel.setVisible(false);
		}
	}
}
