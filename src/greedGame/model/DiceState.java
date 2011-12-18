package greedGame.model;

/**
 * An enumeration defining the possible states a dice can be in.
 */
public enum DiceState {
	/**
	 * The dice is free to roll or select for scoring.
	 */
	FREE,

	/**
	 * The dice is selected for scoring
	 */
	SELECTED,
	
	/**
	 * The dice is reserved, and can not normally be rolled or used for scoring.
	 */
	RESERVED
}
