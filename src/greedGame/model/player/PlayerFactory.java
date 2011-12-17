package greedGame.model.player;

import java.util.Arrays;
import java.util.List;

import greedGame.model.GreedGameModel;

public class PlayerFactory {

	private int humanPlayerCount = 1;
	private int cowardAIPlayerCount = 1;
	private GreedGameModel model;
	
	private String[] playerTypes = {
			"Human player",
			"Coward AI player"
	};
	
	public PlayerFactory(GreedGameModel model) {
		this.model = model;
	}
	
	private HumanPlayer createHumanPlayer() {
		return new HumanPlayer("Player #" + humanPlayerCount++);
	}
	
	private CowardAIPlayer createCowardAIPlayer() {
		return new CowardAIPlayer("Coward AI #" + cowardAIPlayerCount++, model);
	}
	
	public Player createPlayer(String playerType) {
		
		if (playerTypes[0].equals(playerType))
			return createHumanPlayer();
		else if (playerTypes[1].equals(playerType))
			return createCowardAIPlayer();
		else
			return null;
	}
	
	public List<String> getPlayerTypes() {
		return Arrays.asList(playerTypes);
	}
}
