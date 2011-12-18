package greedGame.model;

import java.util.List;

/**
 * Contains all the information that defines a possible scoring combination.
 */
public class ScoringCombination {

	private String name;
	private int score;
	private List<Dice> dice;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the name of the combination, like Street, or Three of a Kind
	 * @param score
	 *            the amount of points this combination is worth
	 * @param dice
	 *            the dice used to form this combination
	 */
	public ScoringCombination(String name, int score, List<Dice> dice) {
		this.name = name;
		this.score = score;
		this.dice = dice;
	}

	@Override
	public String toString() {
		// Returns the name and score as a string.
		return name + " : " + score;
	}

	/**
	 * Returns the score.
	 * 
	 * @return the amount of points this combination is worth
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Returns the dice.
	 * 
	 * @return the dice used to form this combination
	 */
	public List<Dice> getDice() {
		return dice;
	}

	/**
	 * Gets the name of the combination.
	 * 
	 * @return the name defining the combination
	 */
	public String getScoreName() {
		return name;
	}
}
