package greedGame.model.player;

import greedGame.model.GreedGameModel;

public class CowardAIPlayer extends AIPlayer {
	
	//the class constructor.
	public CowardAIPlayer(String name, GreedGameModel model) {
		super(name, model);
	}
	
	@Override
	public void decide()
	{
		selectAllCombinations(); //selects the dice to keep or bank.
		setDecision(AIDecision.BANK); // the coward AI will bank points as soon as possible
	}
}
