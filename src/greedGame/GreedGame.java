package greedGame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import greedGame.model.GreedGameModel;
import greedGame.model.player.Player;

public class GreedGame {

	static SetupGUI setup;

	static GreedGameModel model;
	static GreedGameGUI view;
	static GreedGameController controller;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					setup = new SetupGUI();
					setup.addStartGameActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							setup.setVisible(false);

							if (view != null)
								view.dispose();

							model = new GreedGameModel(setup.getBustLimit(),
									setup.getPointsToWin());
							view = new GreedGameGUI(model);
							view.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosing(WindowEvent arg0) {
									Player winningPlayer = model.getWinningPlayer();
									if (winningPlayer != null)
										JOptionPane.showMessageDialog(null, winningPlayer.getName() + " won the game with " + winningPlayer.getScore() + " points.");
									
									setup.setVisible(true);
									view.setVisible(false);
								}
							});
							model.addObserver(view);

							controller = new GreedGameController(model, view);

							view.setVisible(true);
						}
					});
					setup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
