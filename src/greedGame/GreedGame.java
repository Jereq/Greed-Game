package greedGame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import greedGame.model.GreedGameModel;
import greedGame.model.player.Player;

/**
 * The startup class of GreedGame. Does little more then create a setup window,
 * a GUI window, a model and a controller and connecting them.
 */
public class GreedGame {

	// Store the main classes for the sake of the action listener
	static SetupGUI setup;
	static GreedGameModel model;
	static GreedGameGUI view;
	static GreedGameController controller;

	/**
	 * main method. Starts the game.
	 * 
	 * @param args
	 *            Not used
	 */
	public static void main(String[] args) {

		// Much taken from the application launcher provided by WindowBuilder in
		// Eclipse.
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					// Create a setup window and set the action for pressing the
					// start game button.
					setup = new SetupGUI();
					setup.addStartGameActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							setup.setVisible(false);

							// If the view is never disposed, it can not be
							// garbage collected
							if (view != null)
								view.dispose();

							// Create the underlying game model with the values
							// set in the setup window
							model = new GreedGameModel(setup.getBustLimit(),
									setup.getPointsToWin());

							// Create a new view and set it to observe the game
							// model
							view = new GreedGameGUI(model);

							// Set an action for when the window is closing,
							// either manually or because the model has ended
							view.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosing(WindowEvent arg0) {

									// Get the winning player, if any
									Player winningPlayer = model
											.getWinningPlayer();

									// If a winning player was found, present
									// the players result to the user with a
									// message box
									if (winningPlayer != null)
										JOptionPane.showMessageDialog(
												null,
												winningPlayer.getName()
														+ " won the game with "
														+ winningPlayer
																.getScore()
														+ " points.");

									// Change visible window to setup
									setup.setVisible(true);
									view.setVisible(false);
								}
							});

							// Create a new controller object, which creates all
							// necessary bindings between the GUI's action
							// listeners and the model's functions
							controller = new GreedGameController(model, view);

							// Display the view
							view.setVisible(true);
						}
					});
					
					// Once setup is setup, display the window
					setup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
