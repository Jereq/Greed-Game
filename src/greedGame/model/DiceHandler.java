package greedGame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceHandler {

	private final int numberOfDice = 6;

	private List<Dice> dice;
	private ScoringRules rules;

	public DiceHandler() {
		dice = new ArrayList<Dice>(numberOfDice);
		rules = new BasicScoringRules();

		Random rnd = new Random();

		for (int i = 0; i < numberOfDice; i++) {
			dice.add(new Dice(rnd));
		}
	}

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

	public int getMaxPoints() {
		return rules.getMaxPoints(getUnreservedDice());
	}

	public List<ScoringCombination> getScoringCombinations() {
		return rules.getScoringCombinations(getSelectedDice());
	}

	public void reserveSelectedDice() {
		for (Dice d : getSelectedDice()) {
			d.setState(DiceState.RESERVED);
		}
	}

	public void selectDice(Dice selDice) {
		selDice.setState(DiceState.SELECTED);
	}

	public void unselectDice(Dice selDice) {
		selDice.setState(DiceState.FREE);
	}

	public List<Dice> getSelectedDice() {

		ArrayList<Dice> selectedDice = new ArrayList<Dice>(numberOfDice);
		for (Dice d : dice) {
			if (d.getState() == DiceState.SELECTED)
				selectedDice.add(d);
		}

		return selectedDice;
	}

	public List<Dice> getUnreservedDice() {

		ArrayList<Dice> unreservedDice = new ArrayList<Dice>(numberOfDice);
		for (Dice d : dice) {
			if (d.getState() != DiceState.RESERVED)
				unreservedDice.add(d);
		}

		return unreservedDice;
	}

	public List<Dice> getFreeDice() {

		ArrayList<Dice> freeDice = new ArrayList<Dice>(numberOfDice);
		for (Dice d : dice) {
			if (d.getState() == DiceState.FREE)
				freeDice.add(d);
		}

		return freeDice;
	}

	public List<Dice> getDice() {
		return dice;
	}

	public ScoringRules getScoringRules() {
		return rules;
	}

	public void reserveAllDice() {
		for (Dice d : dice)
			d.setState(DiceState.RESERVED);
	}
}
