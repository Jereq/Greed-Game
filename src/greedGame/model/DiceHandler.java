package greedGame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceHandler {

	private final int numberOfDice = 6;

	private List<Dice> dice;
	private ScoringRules rules;

	//
	public DiceHandler() {
		dice = new ArrayList<Dice>(numberOfDice);
		rules = new BasicScoringRules();

		Random rnd = new Random();

		for (int i = 0; i < numberOfDice; i++) {
			dice.add(new Dice(rnd));
		}
	}

	//
	public void rollDice() {

		boolean isAllReserved = true;

		for (Dice d : dice) {
			if (d.getState() != DiceState.RESERVED) {
				isAllReserved = false;
				break;
			}
		}

		for (Dice d : dice) {
			if (isAllReserved || d.getState() == DiceState.FREE)
				d.roll();
		}
	}

	//Returns the maximum amount of points the current dice can give. 
	public int getMaxPoints() {
		return rules.getMaxPoints(getUnreservedDice());
	}

	//Returns the scoring combinations of the current dice.
	public List<ScoringCombination> getScoringCombinations() {
		return rules.getScoringCombinations(getSelectedDice());
	}

	//Sets the state of the selected dice to reserved.
	public void reserveSelectedDice() {
		for (Dice d : getSelectedDice()) {
			d.setState(DiceState.RESERVED);
		}
	}

	//
	public void selectDice(Dice selDice) {
		selDice.setState(DiceState.SELECTED);
	}

	//
	public void unselectDice(Dice selDice) {
		selDice.setState(DiceState.FREE);
	}

	//Returns the selected dice
	public List<Dice> getSelectedDice() {

		ArrayList<Dice> selectedDice = new ArrayList<Dice>(numberOfDice);
		for (Dice d : dice) {
			if (d.getState() == DiceState.SELECTED)
				selectedDice.add(d);
		}

		return selectedDice;
	}

	//Returns the dice that weren't selected before the new roll.
	public List<Dice> getUnreservedDice() {

		ArrayList<Dice> unreservedDice = new ArrayList<Dice>(numberOfDice);
		for (Dice d : dice) {
			if (d.getState() != DiceState.RESERVED)
				unreservedDice.add(d);
		}

		return unreservedDice;
	}

	//Returns the dice that are neither reserved nor selected.
	public List<Dice> getFreeDice() {

		ArrayList<Dice> freeDice = new ArrayList<Dice>(numberOfDice);
		for (Dice d : dice) {
			if (d.getState() == DiceState.FREE)
				freeDice.add(d);
		}

		return freeDice;
	}

	//
	public List<Dice> getDice() {
		return dice;
	}

	//Returns the scoring rules.
	public ScoringRules getScoringRules() {
		return rules;
	}

	//Reserves all dice.
	public void reserveAllDice() {
		for (Dice d : dice)
			d.setState(DiceState.RESERVED);
	}
}
