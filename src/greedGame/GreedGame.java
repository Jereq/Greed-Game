package greedGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import greedGame.model.GreedGameModel;

public class GreedGame {
		
	static SetupGUI setup;
	
	static GreedGameModel model;
	static GreedGameGUIExperiment view;
	static GreedGameController controller;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			setup = new SetupGUI();
			setup.addStartGameActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setup.setVisible(false);
					
					model = new GreedGameModel(setup.getBustLimit(), setup.getPointsToWin());
					view = new GreedGameGUIExperiment(model);
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

}
