package greedGame.model.player;


/**
 * This is the interface for creating new player types.
 *
 */
public interface Player {

	void addScore(int points);
	int getScore();
	String getName();
	void beginTurn();
	void beginDecide();
	boolean isLocalGUIPlayer();
}
