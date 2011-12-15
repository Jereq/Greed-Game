package greedGame.model;

import greedGame.model.player.Player;
import greedGame.model.player.PlayerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class GreedGameModel extends Observable {

	private List<Player> players;
	private Iterator<Player> currentPlayerIterator;
	private Player currentPlayer;
	private DiceHandler diceHandler;
	private PlayerFactory playerFactory;

	public void rollDice() {
		diceHandler.rollDice();
		modelChanged();
	}

	public void bank() {
		currentPlayer.addScore(0); // TODO: Calculate score
		nextPlayer();
		modelChanged();
	}
	
	private void modelChanged() {
		setChanged();
		notifyObservers();
	}

	private void nextPlayer() {
		if (!currentPlayerIterator.hasNext())
			currentPlayerIterator = players.iterator();

		currentPlayer = currentPlayerIterator.next();
		currentPlayer.beginTurn();
	}

	private void addPlayer(Player player) {
		players.add(player);
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
		nextPlayer();
	}
}
