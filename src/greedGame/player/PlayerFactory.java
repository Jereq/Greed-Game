package greedGame.player;

public class PlayerFactory {

	private int humanPlayerCount = 0;
	
	public HumanPlayer createHumanPlayer() {
		humanPlayerCount++;
		return new HumanPlayer("Player" + humanPlayerCount);
	}
}
