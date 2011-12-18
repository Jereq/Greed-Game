package greedGame.model;

import greedGame.model.player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

public class GreedGameModel extends Observable {

	public enum ModelState {
		ADD_PLAYER, WAITING_FOR_FIRST_ROLL, WAITING_FOR_PLAYER_DECISION, GAME_OVER
	}

	private class InvalidScoringCombinationsException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}

	private List<Player> players;
	private ListIterator<Player> currentPlayerIterator;
	private Player currentPlayer;

	private DiceHandler diceHandler;

	private ModelState state;
	private int currentSubScore;

	private int firstRollScoreLimit;
	private int winScoreLimit;

	private List<String> log;

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

	//Attempts to roll the dice.
	public void tryRollDice() {

		//Either tells the player he busted or waits for his decision.
		if (state == ModelState.WAITING_FOR_FIRST_ROLL) {

			rollDice();

			if (diceHandler.getMaxPoints() < firstRollScoreLimit) {
				log.add(currentPlayer.getName()
						+ " did not meet the first roll limit, BUST!");
				nextPlayer();
			} else {
				state = ModelState.WAITING_FOR_PLAYER_DECISION;
				askPlayerForDecision();
			}

			//Either tells the player he's busted or waits for his decision.
		} else if (state == ModelState.WAITING_FOR_PLAYER_DECISION)
			try {
				updateSubScore();
				log.add("Current subscore: " + currentSubScore);
				rollDice();

				if (diceHandler.getMaxPoints() == 0) {
					log.add("BUST!");
					nextPlayer();
				} else
					askPlayerForDecision();
					
			} catch (InvalidScoringCombinationsException e) {
			}
		else
			log.add("ERROR: Rolling dice not allowed while in state "
					+ state.toString());

		modelChanged();
	}//End of tryRollDice.

	//Banks the current sub-score and ends current players turn.
	public void bank() {

		if (state == ModelState.WAITING_FOR_PLAYER_DECISION) {

			try {
				updateSubScore();
				addScore(currentSubScore);

				if (currentPlayer.getScore() >= winScoreLimit)
					endGame();
				else
					nextPlayer();
			} catch (InvalidScoringCombinationsException e) {
			}
		} else
			log.add("ERROR: Banking is not allowed in state "
					+ state.toString());

		modelChanged();
	}
	
	//Asks for the players decision.
	private void askPlayerForDecision() {

		log.add("Waiting for " + currentPlayer.getName()
				+ " to make a decision");
		currentPlayer.beginDecide();
	}

	//Adds current score to the players total score
	private void addScore(int score) {
		currentPlayer.addScore(score);
		log.add(currentPlayer.getName() + " banked " + score + " points");
	}

	//Ends the game
	private void endGame() {
		state = ModelState.GAME_OVER;
		log.add("Game ended");
	}

	//Rolls the dice
	private void rollDice() {

		diceHandler.reserveSelectedDice();
		diceHandler.rollDice();

	}

	//Updates the sub-score.
	private void updateSubScore() throws InvalidScoringCombinationsException {

		int newSubScore = getNewSubScore();

		if (newSubScore <= 0) {

			log.add("Invalid combination or no dice selected");
			throw new InvalidScoringCombinationsException();
		}

		if (currentSubScore == 0 && newSubScore < firstRollScoreLimit) {
			log.add("First roll must pass the first roll score limit: "
					+ firstRollScoreLimit);
			throw new InvalidScoringCombinationsException();
		}

		currentSubScore += newSubScore;
	}

	//
	private int getNewSubScore() {

		List<ScoringCombination> combinations = diceHandler
				.getScoringCombinations();

		if (combinations.isEmpty()
				|| combinations.get(combinations.size() - 1).getScore() == 0)
			return 0;

		int result = 0;
		for (ScoringCombination c : combinations)
			result += c.getScore();

		return result;
	}

	//
	private void modelChanged() {
		setChanged();
		notifyObservers();
	}

	//Changes current player.
	private void nextPlayer() {
		if (state == ModelState.GAME_OVER || state == ModelState.ADD_PLAYER) {
			log.add("Switching player not allowed while in state "
					+ state.toString());
			return;
		}

		if (!currentPlayerIterator.hasNext())
			currentPlayerIterator = players.listIterator();

		currentPlayer = currentPlayerIterator.next();
		askPlayerToBegin();
	}

	private void askPlayerToBegin() {

		currentSubScore = 0;
		diceHandler.reserveAllDice();

		state = ModelState.WAITING_FOR_FIRST_ROLL;
		log.add("\n" + "Waiting for " + currentPlayer.getName() + " to roll");
		currentPlayer.beginTurn();
	}

	//Adds player
	public void addPlayer(Player player) {

		if (state == ModelState.ADD_PLAYER) {

			currentPlayerIterator.add(player);

			log.add(player.getName() + " joined the game");
			
			modelChanged();
		} else
			log.add("addPlayer(Player) must not be called while in state "
					+ state.toString());
	}

	//removes the player currently active
	public void removeCurrentPlayer() {
		currentPlayerIterator.remove();
		log.add(currentPlayer.getName() + " left the game");

		if (players.isEmpty())
			endGame();
		else
			nextPlayer();

		modelChanged();
	}

	//selects a dice for either banking or reserving.
	public void selectDice(Dice dice) {
		diceHandler.selectDice(dice);
		modelChanged();
	}

	//Deselects selected dice.
	public void unselectDice(Dice dice) {
		diceHandler.unselectDice(dice);
		modelChanged();
	}
	
	//Returns the dice that are not in the RESERVED state.
	public List<Dice> getUnreservedDice() {
		return diceHandler.getUnreservedDice();
	}
	
	//Returns the dice that are in the SELECTED state.
	public List<Dice> getSelectedDice() {
		return diceHandler.getSelectedDice();
	}
	
	//Returns the dice that are in the FREE state.
	public List<Dice> getFreeDice() {
		return diceHandler.getFreeDice();
	}

	//Returns all dice.
	public List<Dice> getDice() {
		return diceHandler.getDice();
	}

//
	public ModelState getState() {
		return state;
	}

	//Returns all players.
	public List<Player> getPlayers() {
		return players;
	}

	//Returns the ScoringRules.
	public ScoringRules getScoringRules() {
		return diceHandler.getScoringRules();
	}

	//Opens the add player screen.
	public void startAddPlayer() {
		if (state != ModelState.WAITING_FOR_FIRST_ROLL) {
			log.add("ERROR: startAddPLayer() must not be called while in state "
					+ state.toString() + ", only in WAITING_FOR_FIRST_ROLL");
			return;
		}

		state = ModelState.ADD_PLAYER;
		modelChanged();
	}

	//Closes the add player screen.
	public void stopAddPlayer() {
		if (state != ModelState.ADD_PLAYER) {
			log.add("ERROR: stopAddPlayer() must not be called while in state "
					+ state.toString() + ", only in ADD_PLAYER");
			return;
		}

		if (players.size() == 0)
			endGame();
		else {
			state = ModelState.WAITING_FOR_FIRST_ROLL;

			if (currentPlayer == null)
				nextPlayer();
			else
				askPlayerToBegin();
		}

		modelChanged();
	}

	//Returns the log
	public List<String> getLog() {
		return log;
	}

	//Returns the sub-score
	public int getSubScore() {
		return currentSubScore;
	}

//
	public boolean canDecide() {
		return (state == ModelState.WAITING_FOR_PLAYER_DECISION && currentSubScore
				+ getNewSubScore() >= firstRollScoreLimit);
	}

//
	public boolean isCurrentPlayerLocalGUI() {
		return currentPlayer.isLocalGUIPlayer();
	}
	
	//Returns the winning player or null.
	public Player getWinningPlayer() {
		if (state == ModelState.GAME_OVER && currentPlayer != null
				&& currentPlayer.getScore() >= winScoreLimit)
			return currentPlayer;
		else
			return null;
	}
	
	//Returns the current player.
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
}
