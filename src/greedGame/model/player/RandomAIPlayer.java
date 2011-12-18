package greedGame.model.player;

import java.util.Random;

import greedGame.model.GreedGameModel;

/**
 * A randomly deciding implementation of <code>AIPlayer</code>. Banks and keeps
 * rolling at a 50/50 ration.
 */
public class RandomAIPlayer extends AIPlayer {

	Random random;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The name given to the player
	 * @param model
	 *            The model the AI is going to act upon
	 */
	public RandomAIPlayer(String name, GreedGameModel model) {
		super(name, model);
		random = new Random();
	}

	@Override
	protected void decide() {
		selectAllCombinations(); // selects all dice to keep or bank.

		// random boolean if statement to make the random AI either keep rolling
		// or bank his score.
		if (random.nextBoolean()) {
			setDecision(AIDecision.KEEP_ROLLING);
		} else {
			setDecision(AIDecision.BANK);
		}
	}
}
