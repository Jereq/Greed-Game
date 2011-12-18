package greedGame.model.player;

import greedGame.model.GreedGameModel;

/**
 * A coward implementation of <code>AIPlayer</code>. Banks whenever possible.
 */
public class CowardAIPlayer extends AIPlayer {

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The name given to the player
	 * @param model
	 *            The model the player will act upon
	 */
	public CowardAIPlayer(String name, GreedGameModel model) {
		super(name, model);
	}

	@Override
	public void decide() {
		selectAllCombinations(); // selects the dice to keep or bank.
		setDecision(AIDecision.BANK); // the coward AI will bank points as soon
										// as possible
	}
}
