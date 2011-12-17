package greedGame.model.player;

import java.util.Random;

import greedGame.model.GreedGameModel;

public class RandomAIPlayer extends AIPlayer {

	Random random;// creates a "Random object".
	
	//the class constructor.
	public RandomAIPlayer(String name, GreedGameModel model) {
		super(name, model); 
		random = new Random();
	}
	
	@Override
	protected void decide() {
		selectAllCombinations();
		
		// random boolean if statement to make the random AI either keep rolling or bank his score.
		if (random.nextBoolean()) 
		{
			setDecision(AIDecision.KEEP_ROLLING);
		}
		else
		{
			setDecision(AIDecision.BANK);
		}
		

	}

}
