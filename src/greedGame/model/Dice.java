package greedGame.model;

import java.util.Random;

/**
 * A simple class that models a six-sided dice that can be either selected, free
 * or reserved. As it implements <code>Comparable&ltDice&gt</code>, it can be
 * sorted.
 */
public class Dice implements Comparable<Dice> {

	private Random rnd;
	private int value;
	private DiceState state;

	/**
	 * Constructor.
	 * 
	 * @param rnd
	 *            The <code>Random</code> object to be used to generate new
	 *            values. If <code>null</code>, a new random will be created.
	 */
	public Dice(Random rnd) {
		if (rnd == null)
			rnd = new Random();
		else
			this.rnd = rnd;

		value = 1;
		state = DiceState.RESERVED;
	}

	/**
	 * Gets the value of the dice
	 * 
	 * @return the last value rolled, or 1 if not rolled
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Rolls the dice changing the value and makes the state <code>FREE</code>.
	 */
	public void roll() {
		state = DiceState.FREE;
		value = rnd.nextInt(6) + 1;
	}

	/**
	 * Returns the state of the dice
	 * 
	 * @return the state of the dice
	 */
	public DiceState getState() {
		return state;
	}

	/**
	 * Changes the state of the dice
	 * 
	 * @param state
	 *            the state to set
	 */
	public void setState(DiceState state) {
		this.state = state;
	}

	@Override
	public int compareTo(Dice o) {
		// throws NullPointerException if o is null
		
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		if (this == o)
			return EQUAL;

		if (this.value < o.value)
			return BEFORE;
		if (this.value > o.value)
			return AFTER;

		return EQUAL;
	}
}
