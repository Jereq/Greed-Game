package greedGame.model.player;

public class PlayerFactory {

	private int humanPlayerCount = 0;
	private int cowardAIPlayerCount = 0;
	
	public HumanPlayer createHumanPlayer() {
		humanPlayerCount++;
		return new HumanPlayer("Player " + humanPlayerCount);
	}
	
	public CowardAIPlayer createCowardAIPlayer() {
		return new CowardAIPlayer("Coward AI " + cowardAIPlayerCount);
	}
}
