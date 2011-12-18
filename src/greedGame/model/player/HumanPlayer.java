package greedGame.model.player;

public class HumanPlayer implements Player {
	
	private String name;
	private int score;
	
	//
	public HumanPlayer(String name) {
		this.name = name;
		score = 0;
	}

	//Returns the players score.
	@Override
	public int getScore() {
		return score;
	}

	//Adds the current subscore to the players total.
	@Override
	public void addScore(int points) {
		score += points;
	}

	//Returns the players name.
	@Override
	public String getName() {
		return name;
	}

	
	@Override
	public void beginTurn() {
		// Calling human... Turn begun!
	}

	//
	@Override
	public boolean isLocalGUIPlayer() {
		return true;
	}

	
	@Override
	public void beginDecide() {
		// Waiting for response from gui...
	}

}
