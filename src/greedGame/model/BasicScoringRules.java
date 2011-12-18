package greedGame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * An basic implementation of <code>ScoringRules</code> that counts streets,
 * three of a kind, single ones and single fives. This implementation does not
 * make any assumptions about the number of dice.
 */
public class BasicScoringRules implements ScoringRules {

	@Override
	public int getMaxPoints(List<Dice> dice) {

		// Get the best combinations
		List<ScoringCombination> combinations = getScoringCombinations(dice);

		// And sum them up
		int result = 0;
		for (ScoringCombination score : combinations) {
			result += score.getScore();
		}

		return result;
	}

	@Override
	public List<ScoringCombination> getScoringCombinations(List<Dice> dice) {

		// Get a copy of the dice to work with
		List<Dice> tmpDice = new ArrayList<Dice>(dice);

		// Sort it first to make it easier to work with
		Collections.sort(tmpDice);

		LinkedList<ScoringCombination> result = new LinkedList<ScoringCombination>();

		// Check for and add any possible scoring combination
		result.addAll(getStreets(tmpDice));
		result.addAll(getThreeOfAKinds(tmpDice));
		result.addAll(getSingleOnes(tmpDice));
		result.addAll(getSingleFives(tmpDice));

		// If not every dice was used, append a last scoring combination with no
		// score and the remaining dice
		if (!tmpDice.isEmpty())
			result.add(new ScoringCombination("Nothing", 0, tmpDice));

		return result;
	}

	/**
	 * Finds every street (one dice of each of 1, 2, 3, 4, 5 and 6) and returns
	 * a scoring combinations for each. Each street gives 1000 points. Each dice
	 * used in one of the streets is removed from the supplied list of dice.
	 * 
	 * @param dice
	 *            A <code>List</code> of <code>Dice</code> sorted in ascending
	 *            order to find streets in. If a dice is found to be part of a
	 *            street, it is removed from this list.
	 * @return A <code>List</code> of <code>ScoringCombination</code>s, each a
	 *         street worth 1000 points.
	 */
	protected List<ScoringCombination> getStreets(List<Dice> dice) {

		List<ScoringCombination> result = new LinkedList<ScoringCombination>();

		ScoringCombination score = getStreet(dice);
		while (score != null) {
			result.add(score);
			dice.removeAll(score.getDice());

			score = getStreet(dice);
		}

		return result;
	}

	/**
	 * Tries to find a street (one dice each of 1, 2, 3, 4, 5 and 6) in the
	 * given list of dice. If it finds a street, it returns a
	 * <code>ScoringCombination</code> containing the dice in the street and the
	 * score 1000 point, otherwise it returns <code>null</code>.
	 * 
	 * @param dice
	 *            A <code>List</code> of <code>Dice</code> sorted in ascending
	 *            order in which to find a street. The list is not modified.
	 * @return <code>null</code> if no street could be found, otherwise a
	 *         <code>ScoringCombination</code> containing the dice in the first
	 *         street found and the score 1000 points
	 */
	private ScoringCombination getStreet(List<Dice> dice) {
		// Fail-fast if not enough dice
		if (dice.size() < 6)
			return null;

		ArrayList<Dice> resDice = new ArrayList<Dice>(6);

		// Our streets must start at 1
		int targetValue = 1;
		for (Dice d : dice) {

			// Skipped a value? Not in a street.
			if (d.getValue() > targetValue)
				return null;

			if (d.getValue() == targetValue) {
				resDice.add(d);
				targetValue++;
			}
		}

		// Our streets must contains 6 dice
		if (resDice.size() != 6)
			return null;

		return new ScoringCombination("Street", 1000, resDice);
	}

	/**
	 * Finds every occurrence of three of a kind and returns a scoring
	 * combinations for each. Each three of a kind gives 100 points times the
	 * value of one of its dice, except for three ones, which gives 1000 points.
	 * Each dice used is removed from the supplied list of dice.
	 * 
	 * @param dice
	 *            A <code>List</code> of <code>Dice</code> sorted in ascending
	 *            order to find three of a kind in. If a dice is found to be
	 *            part of a set, it is removed from this list.
	 * @return A <code>List</code> of <code>ScoringCombination</code>s, each a
	 *         three of a kind worth 200 - 1000 points.
	 */
	protected List<ScoringCombination> getThreeOfAKinds(List<Dice> dice) {

		List<ScoringCombination> result = new LinkedList<ScoringCombination>();

		ScoringCombination score = getThreeOfAKind(dice);
		while (score != null) {
			result.add(score);
			dice.removeAll(score.getDice());

			score = getThreeOfAKind(dice);
		}

		return result;
	}

	/**
	 * Tries to find a set with three of a kind (three dice with the same value)
	 * in the given list of dice. If it finds a set, it returns a
	 * <code>ScoringCombination</code> containing the dice in the set and the
	 * score 100 points times the value of one of the dice in the set, except
	 * for three ones, which gives 1000 points. If no three of a kind could be
	 * found it returns <code>null</code>.
	 * 
	 * @param dice
	 *            A <code>List</code> of <code>Dice</code> sorted in ascending
	 *            order in which to find a three of a kind. The list is not
	 *            modified.
	 * @return <code>null</code> if no three of a kind could be found, otherwise
	 *         a <code>ScoringCombination</code> containing the dice in the
	 *         first three of a kind found and the score 200 - 1000 points
	 */
	private ScoringCombination getThreeOfAKind(List<Dice> dice) {
		// Fail-fast if not enough dice
		if (dice.size() < 3)
			return null;

		Iterator<Dice> it = dice.iterator();
		Dice d = it.next();
		int value = d.getValue(); // First possible three of a kind value

		ArrayList<Dice> resDice = new ArrayList<Dice>(3);
		resDice.add(d);

		// Go through all dice while while a three of a kind has not been found
		while (it.hasNext() && resDice.size() < 3) {
			d = it.next();

			// Check if the current dice has the same value as the three of a
			// kind we are currently checking
			if (d.getValue() == value)
				resDice.add(d);
			else {
				// The current set is not valid, test next one
				resDice.clear();
				resDice.add(d);
				value = d.getValue();
			}
		}

		// A three of a kind is three dice big
		if (resDice.size() < 3)
			return null;

		int scoreValue;
		// Three ones is scored higher
		if (value == 1)
			scoreValue = 1000;
		else
			scoreValue = 100 * value;

		return new ScoringCombination("Three of a Kind", scoreValue, resDice);
	}

	/**
	 * Finds all single ones and return scoring combinations for each one of
	 * them. Each single one gives 100 points. Found dice is removed from the
	 * supplied list, but return inside the scoring combinations.
	 * 
	 * @param dice
	 *            A <code>List</code> of <code>Dice</code> sorted in ascending
	 *            order to find single ones in. If a dice is found, it is
	 *            removed from this list.
	 * @return A <code>List</code> of <code>ScoringCombination</code>s, each a
	 *         single one worth 100 points, containing a single one dice.
	 */
	protected List<ScoringCombination> getSingleOnes(List<Dice> dice) {

		List<ScoringCombination> result = new LinkedList<ScoringCombination>();

		ScoringCombination score = getSingleOne(dice);
		while (score != null) {
			result.add(score);
			dice.removeAll(score.getDice());

			score = getSingleOne(dice);
		}

		return result;
	}

	/**
	 * Checks if the first dice is a single one. If it is, it returns a scoring
	 * combination worth 100 points with the dice.
	 * 
	 * @param dice
	 *            A <code>List</code> of <code>Dice</code> sorted in ascending
	 *            order in which to find a single one. The list is not modified.
	 * @return <code>null</code> if no single one, otherwise a
	 *         <code>ScoringCombination</code> containing the first dice and the
	 *         score 100 points
	 */
	private ScoringCombination getSingleOne(List<Dice> dice) {
		if (dice.isEmpty())
			return null;

		Dice d = dice.get(0);
		if (d.getValue() != 1)
			return null;

		ArrayList<Dice> resList = new ArrayList<Dice>(1);
		resList.add(d);

		return new ScoringCombination("Single One", 100, resList);
	}

	/**
	 * Finds all single fives and return scoring combinations for each one of
	 * them. Each single five gives 50 points. Found dice is removed from the
	 * supplied list, but return inside the scoring combinations.
	 * 
	 * @param dice
	 *            A <code>List</code> of <code>Dice</code> sorted in ascending
	 *            order to find single fives in. If a dice is found, it is
	 *            removed from this list.
	 * @return A <code>List</code> of <code>ScoringCombination</code>s, each a
	 *         single five worth 50 points, containing a single five dice.
	 */
	protected List<ScoringCombination> getSingleFives(List<Dice> dice) {

		List<ScoringCombination> result = new LinkedList<ScoringCombination>();

		ScoringCombination score = getSingleFive(dice);
		while (score != null) {
			result.add(score);
			dice.removeAll(score.getDice());

			score = getSingleFive(dice);
		}

		return result;
	}

	/**
	 * Checks if list contains a dice with the value five. If it does, it
	 * returns a <code>ScoringCombination</code> containing the dice and 50
	 * points.
	 * 
	 * @param dice
	 *            A <code>List</code> of <code>Dice</code> sorted in ascending
	 *            order in which to find a single five. The list is not
	 *            modified.
	 * @return <code>null</code> if no single five, otherwise a
	 *         <code>ScoringCombination</code> containing a dice with the value
	 *         five and the score 50 points
	 */
	private ScoringCombination getSingleFive(List<Dice> dice) {
		for (Dice d : dice) {
			if (d.getValue() == 5) {
				ArrayList<Dice> resList = new ArrayList<Dice>(1);
				resList.add(d);

				return new ScoringCombination("Single Five", 50, resList);
			}
		}

		return null;
	}
}
