package greedGame.model.player;

public interface Player {

	void addScore(int points);
	int getScore();
	String getName();
	void beginTurn();
	void beginDecide();
	boolean isLocalGUIPlayer();
}
