package greedGame.model.player;

import java.util.Arrays;
import java.util.List;

import greedGame.model.GreedGameModel;

public class PlayerFactory {

	private int humanPlayerCount = 0;
	private int cowardAIPlayerCount = 0;
	private int gamblerAIPlayerCount = 0;
	private int randomAIPlayerCount = 0;
	
	private GreedGameModel model;
	
	private String[] playerTypes = {
			"Human player",
			"Coward AI player",
			"Gambler AI player",
			"Random AI player"
	};
	
	public PlayerFactory(GreedGameModel model) {
		this.model = model;
	}
	
	private HumanPlayer createHumanPlayer() {
		return new HumanPlayer("Player #" + ++humanPlayerCount);
	}
	
	private CowardAIPlayer createCowardAIPlayer() {
		return new CowardAIPlayer("Coward AI #" + ++cowardAIPlayerCount, model);
	}
	
	private GamblerAIPlayer createGamblerAIPlayer() {
		return new GamblerAIPlayer("Gambler AI #" + ++gamblerAIPlayerCount, model);
	}
	
	private RandomAIPlayer createRandomAIPlayer() {
		return new RandomAIPlayer("Random AI #" + ++randomAIPlayerCount, model);
	}
	
	public Player createPlayer(String playerType) {
		
		if (playerTypes[0].equals(playerType))
			return createHumanPlayer();
		
		else if (playerTypes[1].equals(playerType))
			return createCowardAIPlayer();
		
		else if (playerTypes[2].equals(playerType))
			return createGamblerAIPlayer();
		
		else if (playerTypes[3].equals(playerType))
			return createRandomAIPlayer();
		
		else
			return null;
	}
	
	public List<String> getPlayerTypes() {
		return Arrays.asList(playerTypes);
	}
}
