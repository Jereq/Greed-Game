package greedGame.model;

import java.util.List;

public class ScoringCombination {

	private String name;
	private int score;
	private List<Dice> dice;
	
	//
	public ScoringCombination(String name, int score, List<Dice> dice) {
		this.name = name;
		this.score = score;
		this.dice = dice;
	}
	
	//Returns the name and score in a string.
	public String toString() {
		return name + " : " + score;
	}
	
	//Returns the score.
	public int getScore() {
		return score;
	}
	
	//Returns the all the dice.
	public List<Dice> getDice() {
		return dice;
	}
	
	//
	public String getScoreName() {
		return name;
	}
}
