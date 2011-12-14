package greedGame.model;

import greedGame.model.player.Player;
import greedGame.model.player.PlayerFactory;

import java.util.Iterator;
import java.util.List;

public class GreedGameModel {

	private List<Player> players;
	private Iterator<Player> currentPlayerIterator;
	private Player currentPlayer;
	private DiceHandler diceHandler;
	private PlayerFactory playerFactory;

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
		// TODO : Signal player
	}

	private void addPlayer(Player player) {
		players.add(player);
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
