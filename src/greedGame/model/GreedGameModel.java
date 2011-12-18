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

	public void tryRollDice() {

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
	}

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

	private void askPlayerForDecision() {

		log.add("Waiting for " + currentPlayer.getName()
				+ " to make a decision");
		currentPlayer.beginDecide();
	}

	private void addScore(int score) {
		currentPlayer.addScore(score);
		log.add(currentPlayer.getName() + " banked " + score + " points");
	}

	private void endGame() {
		state = ModelState.GAME_OVER;
		log.add("Game ended");
	}

	private void rollDice() {

		diceHandler.reserveSelectedDice();
		diceHandler.rollDice();

	}

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

	private void modelChanged() {
		setChanged();
		notifyObservers();
	}

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

	public void addPlayer(Player player) {

		if (state == ModelState.ADD_PLAYER) {

			currentPlayerIterator.add(player);

			log.add(player.getName() + " joined the game");

			modelChanged();
		} else
			log.add("addPlayer(Player) must not be called while in state "
					+ state.toString());
	}

	public void removeCurrentPlayer() {
		currentPlayerIterator.remove();
		log.add(currentPlayer.getName() + " left the game");

		if (players.isEmpty())
			endGame();
		else
			nextPlayer();

		modelChanged();
	}

	public void selectDice(Dice dice) {
		diceHandler.selectDice(dice);
		modelChanged();
	}

	public void unselectDice(Dice dice) {
		diceHandler.unselectDice(dice);
		modelChanged();
	}

	public List<Dice> getUnreservedDice() {
		return diceHandler.getUnreservedDice();
	}

	public List<Dice> getSelectedDice() {
		return diceHandler.getSelectedDice();
	}

	public List<Dice> getFreeDice() {
		return diceHandler.getFreeDice();
	}

	public List<Dice> getDice() {
		return diceHandler.getDice();
	}

	public ModelState getState() {
		return state;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public ScoringRules getScoringRules() {
		return diceHandler.getScoringRules();
	}

	public void startAddPlayer() {
		if (state != ModelState.WAITING_FOR_FIRST_ROLL) {
			log.add("ERROR: startAddPLayer() must not be called while in state "
					+ state.toString() + ", only in WAITING_FOR_FIRST_ROLL");
			return;
		}

		state = ModelState.ADD_PLAYER;
		modelChanged();
	}

	public void stopAddPlayer() {
		if (state != ModelState.ADD_PLAYER) {
			log.add("ERROR: stopAddPlayer() must not be called while in state "
					+ state.toString() + ", only in ADD_PLAYER");
			return;
		}
		
		state = ModelState.WAITING_FOR_FIRST_ROLL;

		if (players.size() == 0)
			endGame();
		else {
			if (currentPlayer == null)
				nextPlayer();
			else
				askPlayerToBegin();
		}

		modelChanged();
	}

	public List<String> getLog() {
		return log;
	}

	public int getSubScore() {
		return currentSubScore;
	}

	public boolean canDecide() {
		return (state == ModelState.WAITING_FOR_PLAYER_DECISION && currentSubScore
				+ getNewSubScore() >= firstRollScoreLimit);
	}

	public boolean isCurrentPlayerLocalGUI() {
		return currentPlayer.isLocalGUIPlayer();
	}

	public Player getWinningPlayer() {
		if (state == ModelState.GAME_OVER
				&& currentPlayer.getScore() >= winScoreLimit)
			return currentPlayer;
		else
			return null;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
}
