package greedGame.model.player;

/**
 * A basic human player implementation of <code>Player</code>. It is a player
 * using the GUI, so this class does very little.
 */
public class HumanPlayer implements Player {

	private String name;
	private int score;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The name given to the player
	 */
	public HumanPlayer(String name) {
		this.name = name;
		score = 0;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void addScore(int points) {
		score += points;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void beginTurn() {
		// Calling human... Turn begun!
		// A human player can see his/her turn has begun in the GUI.
	}

	@Override
	public boolean isLocalGUIPlayer() {
		// A human player needs to use the GUI.
		return true;
	}

	@Override
	public void beginDecide() {
		// Waiting for response from GUI...
		// A human player can see his/her possibilities in the GUI.
	}

}
