package greedGame.model;

import java.util.List;

/**
 * Interface for implementing different scoring rules. A scoring rules class is
 * responsible for checking which combinations can be made with a set of dice
 * and the maximum score for a set of dice.
 */
public interface ScoringRules {

	/**
	 * Calculates the maximum amount of points that can be scored with the given
	 * set of dice.
	 * 
	 * @param dice
	 *            A <code>List</code> of <code>Dice</code> to evaluate. The list
	 *            will not be altered.
	 * @return The maximum amount of points that can be scored under these rules
	 *         with the given dice
	 */
	int getMaxPoints(List<Dice> dice);

	/**
	 * Gets the list of scoring combinations that would result in the highest
	 * score. The sum of all combinations' scores must be equal to the result of
	 * <code>getMaxPoints</code>. Any dice not used in any combination is
	 * returned in a scoring combination set to give 0 points appended to the
	 * end of the list. Each dice must be returned in exactly
	 * <strong>one</strong> combination.
	 * 
	 * @param dice
	 *            A <code>List</code> of <code>Dice</code> to evaluate. The list
	 *            will not be altered.
	 * @return A <code>List</code> of the best <code>ScoringCombination</code>s
	 *         these rules can find
	 */
	List<ScoringCombination> getScoringCombinations(List<Dice> dice);
}
