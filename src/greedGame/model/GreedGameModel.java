package greedGame.model;

import greedGame.model.player.Player;
import greedGame.model.player.PlayerFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class GreedGameModel extends Observable {

	private enum ModelState {
		NO_PLAYERS,
		FIRST_ROLL,
		PLAYER_DECISION,
		GAME_OVER
	}

	private List<Player> players;
	private Iterator<Player> currentPlayerIterator;
	private Player currentPlayer;
	private PlayerFactory playerFactory;
	
	private DiceHandler diceHandler;
	
	private ModelState state;	
	private int currentSubScore;
	
	private int firstRollScoreLimit;
	private int winScoreLimit;
	
	public GreedGameModel(int firstRollScoreLimit, int winScoreLimit) {
		
		players = new LinkedList<Player>();
		
		playerFactory = new PlayerFactory(this);
		state = ModelState.NO_PLAYERS;
		
		diceHandler = new DiceHandler();
		
		this.firstRollScoreLimit = firstRollScoreLimit;
		this.winScoreLimit = winScoreLimit;
	}

	public void rollDice() {
		if (state == ModelState.FIRST_ROLL) {
			diceHandler.rollDice();
			if (diceHandler.getMaxPoints() < firstRollScoreLimit)
				nextPlayer();
		} else if (state == ModelState.PLAYER_DECISION) {
			updateSubScore();
			diceHandler.rollDice();
		} else {
			throw new RuntimeException();
		}
		
		modelChanged();
	}

	public void bank() {
		updateSubScore();
		currentPlayer.addScore(currentSubScore);
		
		if (currentPlayer.getScore() >= winScoreLimit)
			endGame();
		
		nextPlayer();
		modelChanged();
	}
	
	private void endGame() {
		state = ModelState.GAME_OVER;
		modelChanged();
		// TODO: End the game
	}
	
	private void updateSubScore() {
		
		List<ScoringCombination> combinations = diceHandler.getScoringCombinations();
		
		if (combinations.isEmpty())
			return;
		
		if (combinations.get(combinations.size() - 1).getScore() == 0)
			return;
		
		for (ScoringCombination c : combinations)
			currentSubScore += c.getScore();
	}
	
	private void modelChanged() {
		setChanged();
		notifyObservers();
	}

	private void nextPlayer() {
		if (state == ModelState.GAME_OVER || state == ModelState.NO_PLAYERS)
			throw new RuntimeException();
		
		if (currentPlayerIterator == null || !currentPlayerIterator.hasNext())
			currentPlayerIterator = players.iterator();

		currentPlayer = currentPlayerIterator.next();
		currentSubScore = 0;
		
		currentPlayer.beginTurn();
	}

	private void addPlayer(Player player) {

		players.add(player);
		
		if (state == ModelState.NO_PLAYERS) {
			state = ModelState.FIRST_ROLL;
			nextPlayer();
		}
		
		modelChanged();
	}
	
	public void addHumanPlayer() {
		addPlayer(playerFactory.createHumanPlayer());
	}
	
	public void addCowardAIPlayer() {
		addPlayer(playerFactory.createCowardAIPlayer());
	}

	public void removeCurrentPlayer() {
		currentPlayerIterator.remove();
		
		if (players.isEmpty())
			endGame();
		else
			nextPlayer();
	}
	
	public void selectDice(int index) {
		diceHandler.selectDice(index);
	}
	
	public List<Dice> getUnreservedDice() {
		return diceHandler.getUnreservedDice();
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
}
