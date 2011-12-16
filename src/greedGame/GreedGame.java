package greedGame;

import greedGame.model.GreedGameModel;

public class GreedGame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		GreedGameModel model;
		GreedGameGUIExperiment view;
		GreedGameController controller;
		
		try {
			model = new GreedGameModel(300, 10000);
			view = new GreedGameGUIExperiment(model);
			model.addObserver(view);
			
			controller = new GreedGameController(model, view);
			
			view.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
