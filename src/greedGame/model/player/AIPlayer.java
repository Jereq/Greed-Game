package greedGame.model.player;

import java.awt.EventQueue;
import java.util.List;

import greedGame.model.Dice;
import greedGame.model.GreedGameModel;
import greedGame.model.ScoringCombination;
import greedGame.model.ScoringRules;

/**
 * AIPlayer is a abstract base class meant to make creating AI players easier.
 * It provides access to some relevant information from the model while
 * preventing access to methods that would introduce errors.
 */
public abstract class AIPlayer implements Player {

	/**
	 * After rolling (assuming the first roll bust limit is reached), a player
	 * is always faced with the choice between rolling again and banking current
	 * points.
	 */
	protected enum AIDecision {
		KEEP_ROLLING, BANK
	}

	// For implementing player
	private String name;
	private int score;

	private GreedGameModel gameModel;
	private AIDecision decision;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            A name to be assigned to this player
	 * @param gameModel
	 *            The model this AI will belong to and will act upon
	 */
	public AIPlayer(String name, GreedGameModel gameModel) {
		this.name = name;
		this.gameModel = gameModel;
		score = 0;
	}

	@Override
	public int getScore() {
		// Returns the score.
		return score;
	}

	@Override
	public void addScore(int points) {
		// Adds the current points to the players total.
		score += points;
	}

	@Override
	public String getName() {
		// Returns the name of the player.
		return name;
	}

	@Override
	public void beginTurn() {
		// Invoked when a player is supposed to begin his/her turn by rolling
		// the dice. If a AI player calls the roll method of the model
		// immediately, the model will not be completely done and bugs appear.
		// Therefore, invokeLater is used to postpone the AI's action until the
		// thread is empty, which mean that the model is also done and the GUI
		// is updated.
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Cancel if somehow the currently playing player has changed
				if (isPlayersTurn()) {
					try {
						// Ugly but quick way to add a little delay to the AI's
						// actions
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}

					// Tell the model we would like to roll the dice now
					gameModel.tryRollDice();
				}
			}
		});
	}

	@Override
	public boolean isLocalGUIPlayer() {
		return false;
	}

	@Override
	public void beginDecide() {
		// Invoked when a player is supposed to begin deciding whether to keep
		// rolling or bank his/her current subscore. A AI player must first
		// allow the model and GUI to finish their work before it can decide,
		// therefore it's method is delayed until the thread is empty.
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Cancel if a player change somehow happened
				if (isPlayersTurn()) {
					try {
						// Slow down the AI
						Thread.sleep(100);

						// Selects dice and decides whether to continue or not
						decide();

						// Let the GUI update after dice has been selected
						EventQueue.invokeLater(new Runnable() {

							@Override
							public void run() {
								try {
									// Slow down
									Thread.sleep(100);

									// Act out the decision
									act();
								} catch (InterruptedException e) {
								}
							}

						});
					} catch (InterruptedException e) {
					}
				}
			}
		});
	}

	/**
	 * Checks whether this player is the currently playing player.
	 * 
	 * @return <code>true</code> if this player is the currently playing player,
	 *         otherwise <code>false</code>
	 */
	protected boolean isPlayersTurn() {
		return this == gameModel.getCurrentPlayer();
	}

	/**
	 * Implemented by any implementation of AIPlayer in order to let the AI
	 * decide which dice to select and whether to continue rolling or bank.
	 * 
	 * Select dice with selectDice and report decision with setDecision.
	 * 
	 * @see #selectDice(Dice)
	 * @see #setDecision(AIDecision)
	 */
	protected abstract void decide();

	/**
	 * Utility method that selects all dice in the combinations that gives the
	 * highest score.
	 */
	protected void selectAllCombinations() {

		List<Dice> diceList = getUnreservedDice(); // gets the unreserved dice
													// and puts it in a local
													// list.
		ScoringRules rules = getScoringRules(); // gets the scoring rules.
		List<ScoringCombination> combinations = rules
				.getScoringCombinations(diceList); // gets the best scoring
													// combinations of the
													// dice.

		// loop for deciding which dice to reserve.
		for (ScoringCombination combination : combinations) {
			// Consider only valid combinations
			if (combination.getScore() > 0) {
				for (Dice d : combination.getDice()) // Iterate the dice
				{
					selectDice(d);
				}
			}
		}
	}

	/**
	 * Performs the action set previously with setDecision.
	 */
	private void act() {
		if (decision == AIDecision.KEEP_ROLLING)
			gameModel.tryRollDice();
		else
			gameModel.bank();
	}

	/**
	 * Sets the decision the AI has made for this roll. Affects act().
	 * 
	 * @param decision
	 *            The decision that will later be carried out
	 * 
	 * @see #act()
	 */
	protected void setDecision(AIDecision decision) {
		this.decision = decision;
	}

	/**
	 * Selects the dice for keeping or banking.
	 * 
	 * @param dice
	 *            The dice to select for scoring
	 */
	protected void selectDice(Dice dice) {
		gameModel.selectDice(dice);
	}

	/**
	 * Returns the dice that are free to select.
	 * 
	 * @return List of unreserved dice
	 */
	protected List<Dice> getUnreservedDice() {
		return gameModel.getUnreservedDice();
	}

	/**
	 * Returns the dice that are currently selected.
	 * 
	 * @return List of currently selected dice
	 */
	protected List<Dice> getSelectedDice() {
		return gameModel.getSelectedDice();
	}

	/**
	 * Returns the dice that are not selected and not reserved.
	 * 
	 * @return List of free dice
	 */
	protected List<Dice> getFreeDice() {
		return gameModel.getFreeDice();
	}

	/**
	 * Returns the active players. The returned list must not be modified.
	 * 
	 * @return List of players, including this one if it belongs to the model
	 */
	protected List<Player> getPlayers() {
		return gameModel.getPlayers();
	}

	/**
	 * Gets the scoring rules the model uses.
	 * 
	 * @return Scoring rules used to find possible combinations
	 */
	protected ScoringRules getScoringRules() {
		return gameModel.getScoringRules();
	}

	/**
	 * Returns the score amassed during the current turn, but not from the
	 * selected dice, and not yet banked.
	 * 
	 * @return The turns current subscore
	 */
	protected int getSubScore() {
		return gameModel.getSubScore();
	}

	/**
	 * Returns the score of the currently selected dice.
	 * 
	 * @return The score added if the currently selected dice is used
	 */
	protected int getSelectedDiceScore() {
		return getScoringRules().getMaxPoints(getSelectedDice());
	}
}
