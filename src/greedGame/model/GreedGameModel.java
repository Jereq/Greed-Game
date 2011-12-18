package greedGame.model;

import greedGame.model.player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

/**
 * GreedGameModel is the core model class for the game. It exposes a lot of
 * methods for getting information about its state or altering it.
 */
public class GreedGameModel extends Observable {

	/**
	 * Enumeration defining the different states the model can be in.
	 */
	public enum ModelState {
		/**
		 * The model accepts new players, but no game-play. Valid even without
		 * players. Can legally transition to
		 * <code>WAITING_FOR_FIRST_ROLL</code> and <code>GAME_OVER</code>.
		 */
		ADD_PLAYER,

		/**
		 * The model awaits the players first roll, or removing the current
		 * player. Can legally transition to <code>ADD_PLAYER</code>,
		 * <code>WAITING_FOR_FIRST_ROLL</code>,
		 * <code>WATING_FOR_PLAYER_DECISION</code> and <code>GAME_OVER</code>.
		 */
		WAITING_FOR_FIRST_ROLL,

		/**
		 * The model awaits a decision from the player whether to bank or keep
		 * rolling. Can legally transition to
		 * <code>WAITING_FOR_FIRST_ROLL</code> and <code>GAME_OVER</code>.
		 */
		WAITING_FOR_PLAYER_DECISION,

		/**
		 * The model has ended, either because a player has won, or because
		 * there are no players in the game. Valid even without players. Can not
		 * legally transition to another state.
		 */
		GAME_OVER
	}

	/**
	 * Exception thrown when a player tries to roll or bank while not selecting
	 * valid scoring combinations.
	 */
	private class InvalidScoringCombinationsException extends Exception {

		/**
		 * Necessary to prevent warning.
		 */
		private static final long serialVersionUID = 1L;
	}

	private List<Player> players;
	/**
	 * Iterator used to edit the player list as well as deciding the next
	 * player. Should never be null.
	 */
	private ListIterator<Player> currentPlayerIterator;
	private Player currentPlayer;

	private DiceHandler diceHandler;

	private ModelState state;
	private int currentSubScore;

	private int firstRollScoreLimit;
	private int winScoreLimit;

	private List<String> log;

	/**
	 * Constructor.
	 * 
	 * @param firstRollScoreLimit
	 *            the amount of points a player must reach with their first roll
	 *            every turn in order to not go bust
	 * @param winScoreLimit
	 *            the amount a player must get in order to win the game
	 */
	public GreedGameModel(int firstRollScoreLimit, int winScoreLimit) {

		players = new LinkedList<Player>();
		currentPlayerIterator = players.listIterator();

		state = ModelState.ADD_PLAYER;

		diceHandler = new DiceHandler();

		this.firstRollScoreLimit = firstRollScoreLimit;
		this.winScoreLimit = winScoreLimit;

		log = new LinkedList<String>();
		log.add("Game started");
	}

	/**
	 * Tries to roll the dice. Depending on the state, the dice may or may not
	 * be rolled. Appropriate actions are taken after the dice is rolled.
	 */
	public void tryRollDice() {

		if (state == ModelState.WAITING_FOR_FIRST_ROLL) {

			// Rolling dice is always valid in this state
			rollFirstRoll();

		} else if (state == ModelState.WAITING_FOR_PLAYER_DECISION)

			// Valid if valid dice combinations are selected
			tryDecideRoll();

		else
			log.add("ERROR: Rolling dice not allowed while in state "
					+ state.toString());

		modelChanged();
	}

	/**
	 * Rolls the dice, checks for a bust and either changes the player or
	 * changes the state to <code>WAITING_FOR_PLAYER_DESICITION</code> and asks
	 * the player to decide.
	 */
	private void rollFirstRoll() {

		rollDice();

		int maxPoints = diceHandler.getMaxPoints();

		if (maxPoints < firstRollScoreLimit || maxPoints <= 0) {
			// The player did not make the bust limit
			log.add(currentPlayer.getName()
					+ " did not meet the first roll limit, BUST!");
			nextPlayer();
		} else {
			// The player made the bust limit, must decide what to do
			state = ModelState.WAITING_FOR_PLAYER_DECISION;
			askPlayerForDecision();
		}
	}

	/**
	 * Called when the player has decided to keep rolling for more points. First
	 * tries to turn the selected dice into points. If succeeded, it rolls the
	 * dice. If failed, the player is buggy (human or not) and nothing happens.
	 */
	private void tryDecideRoll() {

		try {
			// Throws InvalidScoringCombinationsException if the player has
			// not selected valid combinations
			updateSubScore();

			log.add("Current subscore: " + currentSubScore);
			rollDice();

			// If no points can be gotten from the newly rolled dice, the
			// player goes bust
			if (diceHandler.getMaxPoints() == 0) {
				log.add("BUST!");
				nextPlayer();
			} else
				askPlayerForDecision();

		} catch (InvalidScoringCombinationsException e) {
			// Do nothing, human players can try again, AI players is buggy
			// and needs fixing.
		}
	}

	/**
	 * Called when the player has decided to stop rolling and keep his/her
	 * points. First tries to turn the selected dice into points. If succeeded,
	 * it add the accumulated sub-score to the players total and switches
	 * players. If failed, the player is buggy (human or not) and nothing
	 * happens.
	 */
	public void tryBank() {

		if (state == ModelState.WAITING_FOR_PLAYER_DECISION) {

			try {
				// Throws InvalidScoringCombinationsException if the player has
				// not selected valid combinations
				updateSubScore();
				addScore(currentSubScore);

				// Check to see if the player has won
				if (currentPlayer.getScore() >= winScoreLimit)
					endGame();
				else
					nextPlayer();

			} catch (InvalidScoringCombinationsException e) {
				// Do nothing, human players can try again, AI players is buggy
				// and needs fixing.
			}
		} else
			log.add("ERROR: Banking is not allowed in state "
					+ state.toString());

		modelChanged();
	}

	/**
	 * Called when it is time for the player to make a decision. Note: The
	 * player is not supposed to make any decision or take any action directly,
	 * but waiting until the thread queue is empty. Otherwise the program will
	 * become buggy and the GUI will not be able to update.
	 */
	private void askPlayerForDecision() {

		log.add("Waiting for " + currentPlayer.getName()
				+ " to make a decision");
		currentPlayer.beginDecide();
	}

	/**
	 * Adds score to the players total score
	 * 
	 * @param score
	 *            number of points to add to a players total score
	 */
	private void addScore(int score) {
		currentPlayer.addScore(score);
		log.add(currentPlayer.getName() + " banked " + score + " points");
	}

	/**
	 * Ends the game, at least in the view of the model
	 */
	private void endGame() {
		state = ModelState.GAME_OVER;
		log.add("Game ended");
	}

	/**
	 * Reserves any selected dice and then rolls any unreserved dice, or all
	 * dice if all dice is reserved.
	 */
	private void rollDice() {

		diceHandler.reserveSelectedDice();
		diceHandler.rollDice();

	}

	/**
	 * Updates the sub-score by adding the score for the currently selected dice
	 * 
	 * @throws InvalidScoringCombinationsException
	 *             Thrown if the selected dice is not valid combinations
	 *             according to the scoring rules, the first roll bust limit is
	 *             not reached or no points is earned.
	 */
	private void updateSubScore() throws InvalidScoringCombinationsException {

		// Returns 0 if invalid combinations of dice is selected
		int newSubScore = getNewSubScore();

		if (newSubScore <= 0) {

			log.add("Invalid combinations or no dice selected");
			throw new InvalidScoringCombinationsException();
		}

		// If first roll bust limit is not reached on the first roll
		if (currentSubScore == 0 && newSubScore < firstRollScoreLimit) {
			log.add("First roll must pass the first roll score limit: "
					+ firstRollScoreLimit);
			throw new InvalidScoringCombinationsException();
		}

		currentSubScore += newSubScore;
	}

	/**
	 * Calculates a new sub-score from the currently selected dice.
	 * 
	 * @return 0 if invalid combinations are selected or no dice is selected,
	 *         otherwise the sum of all selected combinations
	 */
	private int getNewSubScore() {

		List<ScoringCombination> combinations = diceHandler
				.getScoringCombinations();

		// The last combinations score is 0 if not all selected dice belong to
		// a valid combination
		if (combinations.isEmpty()
				|| combinations.get(combinations.size() - 1).getScore() == 0)
			return 0;

		int result = 0;
		for (ScoringCombination c : combinations)
			result += c.getScore();

		return result;
	}

	/**
	 * Marks the model as changed and notifies any observer
	 */
	private void modelChanged() {
		setChanged();
		notifyObservers();
	}

	/**
	 * If in a valid state, changes the current player to the next one.
	 */
	private void nextPlayer() {
		if (state == ModelState.GAME_OVER || state == ModelState.ADD_PLAYER) {
			log.add("Switching player not allowed while in state "
					+ state.toString());
			return;
		}

		// Wrap around the end of the list
		if (!currentPlayerIterator.hasNext())
			currentPlayerIterator = players.listIterator();

		currentPlayer = currentPlayerIterator.next();
		askPlayerToBegin();
	}

	/**
	 * Notifies the current player that his/her turn has begun. Note: The player
	 * is not supposed to make any decision or take any action directly, but
	 * waiting until the thread queue is empty. Otherwise the program will
	 * become buggy and the GUI will not be able to update.
	 */
	private void askPlayerToBegin() {

		currentSubScore = 0;
		diceHandler.reserveAllDice(); // No dice is free until the first roll

		state = ModelState.WAITING_FOR_FIRST_ROLL;
		log.add("\n" + "Waiting for " + currentPlayer.getName() + " to roll");
		currentPlayer.beginTurn();
	}

	/**
	 * Adds a player to the game. Only valid when the state is
	 * <code>ADD_PLAYER</code>.
	 * 
	 * @param player
	 *            The <code>Player</code> to add. Can be any type (as long as it
	 *            is bug-free).
	 */
	public void addPlayer(Player player) {

		if (state == ModelState.ADD_PLAYER) {

			currentPlayerIterator.add(player);

			log.add(player.getName() + " joined the game");
		} else
			log.add("ERROR: addPlayer(Player) must not be called while in state "
					+ state.toString());

		modelChanged();
	}

	/**
	 * Removes the currently active player. If no other player is left, ends the
	 * game. Otherwise the next player gets his/her turn.
	 */
	public void removeCurrentPlayer() {
		currentPlayerIterator.remove();
		log.add(currentPlayer.getName() + " left the game");

		if (players.isEmpty())
			endGame();
		else
			nextPlayer();

		modelChanged();
	}

	/**
	 * Selects a dice for either banking or reserving. Only valid in state
	 * <code>WAITING_FOR_PLAYER_DECISION</code>.
	 * 
	 * @param dice
	 *            the dice to get points from
	 */
	public void selectDice(Dice dice) {
		if (state != ModelState.WAITING_FOR_PLAYER_DECISION)
			log.add("ERROR: Selecting dice is not valid while in state "
					+ state.toString());
		else
			diceHandler.selectDice(dice);

		modelChanged();
	}

	/**
	 * Unselect a dice. Only valid in state
	 * <code>WAITING_FOR_PLAYER_DECISION</code>.
	 * 
	 * @param dice
	 *            the dice to not get points from
	 */
	public void unselectDice(Dice dice) {
		if (state != ModelState.WAITING_FOR_PLAYER_DECISION)
			log.add("ERROR: Unselecting dice is not valid while in state "
					+ state.toString());
		else
			diceHandler.unselectDice(dice);

		modelChanged();
	}

	/**
	 * Returns the dice that are not in the RESERVED state.
	 * 
	 * @return all dice not reserved
	 */
	public List<Dice> getUnreservedDice() {
		return diceHandler.getUnreservedDice();
	}

	/**
	 * Returns the dice that are in the SELECTED state.
	 * 
	 * @return all selected dice
	 */
	public List<Dice> getSelectedDice() {
		return diceHandler.getSelectedDice();
	}

	/**
	 * Returns the dice that are in the FREE state (not selected and not
	 * reserved).
	 * 
	 * @return all free dice
	 */
	public List<Dice> getFreeDice() {
		return diceHandler.getFreeDice();
	}

	/**
	 * Returns all dice.
	 * 
	 * @return all dice, regardless of state
	 */
	public List<Dice> getDice() {
		return diceHandler.getDice();
	}

	/**
	 * Gets the models state.
	 * 
	 * @return the <code>ModelState</code> of the model
	 * 
	 * @see ModelState
	 */
	public ModelState getState() {
		return state;
	}

	/**
	 * Returns all players.
	 * 
	 * @return a <code>List</code> with all the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Returns the scoring rules used to evaluate dice combinations.
	 * 
	 * @return the scoring rules used for the game
	 */
	public ScoringRules getScoringRules() {
		return diceHandler.getScoringRules();
	}

	/**
	 * Gets the score needed to win
	 * 
	 * @return The score needed to win
	 */
	public int getWinScoreLimit() {
		return winScoreLimit;
	}

	/**
	 * Get the score needed from the first roll to not go bust.
	 * 
	 * @return The bust limit for the first roll
	 */
	public int getFirstRollBustLimit() {
		return firstRollScoreLimit;
	}

	/**
	 * Changes the state of the model to <code>ADD_PLAYER</code>. Only valid in
	 * state <code>WAITING_FOR_FIRST_ROLL</code>. This should cause any GUI to
	 * display controls for adding players.
	 */
	public void startAddPlayer() {
		if (state != ModelState.WAITING_FOR_FIRST_ROLL)
			log.add("ERROR: startAddPLayer() must not be called while in state "
					+ state.toString() + ", only in WAITING_FOR_FIRST_ROLL");
		else
			state = ModelState.ADD_PLAYER;

		modelChanged();
	}

	/**
	 * If in the state <code>ADD_PLAYER</code>, change the state to
	 * <code>WAITING_FOR_FIRST_ROLL</code> and check if any player exist. If so,
	 * make sure there is a current player and notify that player that he/she
	 * can start playing again. This should cause any GUI to disable controls
	 * for adding players.
	 */
	public void stopAddPlayer() {
		if (state != ModelState.ADD_PLAYER) {
			log.add("ERROR: stopAddPlayer() must not be called while in state "
					+ state.toString() + ", only in ADD_PLAYER");
		} else {

			if (players.size() == 0)
				endGame();
			else {
				state = ModelState.WAITING_FOR_FIRST_ROLL;

				if (currentPlayer == null)
					nextPlayer();
				else
					askPlayerToBegin();
			}
		}

		modelChanged();
	}

	/**
	 * Returns the model's log of events during the game.
	 * 
	 * @return a <code>List</code> of log entries
	 */
	public List<String> getLog() {
		return log;
	}

	/**
	 * Returns the sub-score for this turn, that is, all scores from selected
	 * dice that has not been banked.
	 * 
	 * @return
	 */
	public int getSubScore() {
		return currentSubScore;
	}

	/**
	 * Checks whether a player is allowed to make a decision using the currently
	 * selected dice. This is only allowed while the model's state is
	 * <code>WAITING_FOR_PLAYER_DECISION</code>.
	 * 
	 * @return <code>true</code> if the player can decide and the model's state
	 *         allow decisions to be made, otherwise <code>false</code>.
	 */
	public boolean canDecide() {
		return (state == ModelState.WAITING_FOR_PLAYER_DECISION && currentSubScore
				+ getNewSubScore() >= firstRollScoreLimit);
	}

	/**
	 * Gets whether the player uses the local GUI to make decisions.
	 * 
	 * @return <code>true</code> if the player uses a local GUI to play,
	 *         otherwise <code>false</code>
	 */
	public boolean isCurrentPlayerLocalGUI() {
		return currentPlayer.isLocalGUIPlayer();
	}

	/**
	 * Returns the player who won the game, if any. Only allowed in the state
	 * <code>GAME_OVER</code>.
	 * 
	 * @return <code>null</code> if there is no winning player, otherwise the
	 *         player who won
	 */
	public Player getWinningPlayer() {
		if (state == ModelState.GAME_OVER && currentPlayer != null
				&& currentPlayer.getScore() >= winScoreLimit)
			return currentPlayer;
		else
			return null;
	}

	/**
	 * Gets the currently playing player.
	 * 
	 * @return the active player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
}
