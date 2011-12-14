package greedGame.model;

import greedGame.player.Player;

import java.util.Iterator;
import java.util.List;

public class GreedGameModel {

	private List<Player> players;
	private Iterator<Player> currentPlayerIterator;
	private Player currentPlayer;
	private DiceHandler diceHandler;

	public void rollDice() {
		diceHandler.rollDice();
	}

	public void bank() {
		currentPlayer.addScore(0); // TODO: Calculate score
		nextPlayer();
	}
	
	private void nextPlayer() {
		if (!currentPlayerIterator.hasNext())
			currentPlayerIterator = players.iterator();

		currentPlayer = currentPlayerIterator.next();
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removeCurrentPlayer() {
		currentPlayerIterator.remove();
		nextPlayer();
	}
}
