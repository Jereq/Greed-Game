package greedGame.model.player;

/**
 * This is an interface for creating new player classes. AI classes may prefer
 * <code>AIPlayer</code>, which implements <code>Player</code>.
 * 
 * @see AIPlayer
 */
public interface Player {

	/**
	 * Adds a score to the players banked total.
	 * 
	 * @param points
	 *            Banked score to add, may be negative
	 */
	void addScore(int points);

	/**
	 * Gets the players total banked score.
	 * 
	 * @return Banked score for the player, may be negative
	 */
	int getScore();

	/**
	 * Returns the players name.
	 * 
	 * @return Returns a name for the player
	 */
	String getName();

	/**
	 * Called to signal that the player may begin his/her turn by rolling the
	 * dice, as soon as the currently executing methods have finished (the
	 * thread queue is empty).
	 */
	void beginTurn();

	/**
	 * Called to signal that the player may begin to select dice and roll/bank
	 * as soon as the currently executing methods have finished (the thread
	 * queue is empty).
	 */
	void beginDecide();

	/**
	 * Determines whether the player makes use of the GUI or not. Usually a
	 * static value.
	 * 
	 * @return <code>true</code> if the player needs to use the GUI, otherwise
	 *         <code>false</code>
	 */
	boolean isLocalGUIPlayer();
}
