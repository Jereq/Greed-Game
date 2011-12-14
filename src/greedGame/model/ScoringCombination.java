package greedGame.model;

import java.util.List;

public class ScoringCombination {

	private String name;
	private int score;
	private List<Dice> dice;
	
	public ScoringCombination(String name, int score, List<Dice> dice) {
		this.name = name;
		this.score = score;
		this.dice = dice;
	}
	
	public String toString() {
		return name + " : " + score;
	}
	
	public int getScore() {
		return score;
	}
	
	public List<Dice> getDice() {
		return dice;
	}
	
	public String getScoreName() {
		return name;
	}
}
