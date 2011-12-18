package greedGame.model.player;

import greedGame.model.Dice;
import greedGame.model.GreedGameModel;

import java.util.List;

/**
 * A gambling implementation of <code>AIPlayer</code>. It will keep rolling as
 * long as it has two free dice and will not win by banking.
 */
public class GamblerAIPlayer extends AIPlayer {

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The name given to the player
	 * @param model
	 *            The model the player will act upon
	 */
	public GamblerAIPlayer(String name, GreedGameModel model) {
		super(name, model);
	}

	@Override
	public void decide() {
		selectAllCombinations(); // selects the dice to keep or bank.

		List<Dice> diceList = getFreeDice(); // gets the free dice and
												// puts it in a local list.

		// The amount of points the player would have if it banks now
		int totalScore = getScore() + getSubScore() + getSelectedDiceScore();

		// as long as the gambler still has 2 dice or more left, and would not
		// win by banking, he will roll again
		if (diceList.size() >= 2 && totalScore < getWinScoreLimit()) {
			setDecision(AIDecision.KEEP_ROLLING);
		} else {
			setDecision(AIDecision.BANK);
		}
	}
}
