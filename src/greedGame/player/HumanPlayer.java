package greedGame.player;

public class HumanPlayer implements Player {
	
	private String name;
	private int score;
	
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

}
