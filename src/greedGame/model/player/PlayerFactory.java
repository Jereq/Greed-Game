package greedGame.model.player;

import greedGame.model.GreedGameModel;

public class PlayerFactory {

	private int humanPlayerCount = 0;
	private int cowardAIPlayerCount = 0;
	private GreedGameModel model;
	
	public PlayerFactory(GreedGameModel model) {
		this.model = model;
	}
	
	public HumanPlayer createHumanPlayer() {
		humanPlayerCount++;
		return new HumanPlayer("Player " + humanPlayerCount);
	}
	
	public CowardAIPlayer createCowardAIPlayer() {
		return new CowardAIPlayer("Coward AI " + cowardAIPlayerCount, model);
	}
}
