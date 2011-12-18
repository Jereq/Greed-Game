package greedGame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BasicScoringRules implements ScoringRules {

	//returns the most available points possible with the current dice.
	@Override
	public int getMaxPoints(List<Dice> dice) {

		List<ScoringCombination> combinations = getScoringCombinations(dice);
		
		int result = 0;
		for (ScoringCombination score : combinations) {
			result += score.getScore();
		}
		
		return result;
	}

	//
	@Override
	public List<ScoringCombination> getScoringCombinations(List<Dice> dice) {
		
		List<Dice> tmpDice = new ArrayList<Dice>(dice);
		Collections.sort(tmpDice);
		
		LinkedList<ScoringCombination> result = new LinkedList<ScoringCombination>();
		
		ScoringCombination score;
		
		score = getThreeOfAKind(tmpDice);
		while (score != null) {
			result.add(score);
			tmpDice.removeAll(score.getDice());
			
			score = getThreeOfAKind(tmpDice);
		}
		
		score = getStreet(tmpDice);
		while (score != null) {
			result.add(score);
			tmpDice.removeAll(score.getDice());
			
			score = getStreet(tmpDice);
		}
		
		score = getSingleOne(tmpDice);
		while (score != null) {
			result.add(score);
			tmpDice.removeAll(score.getDice());
			
			score = getSingleOne(tmpDice);
		}
		
		score = getSingleFive(tmpDice);
		while (score != null) {
			result.add(score);
			tmpDice.removeAll(score.getDice());
			
			score = getSingleFive(tmpDice);
		}
		
		if (!tmpDice.isEmpty())
			result.add(new ScoringCombination("Nothing", 0, tmpDice));
		
		return result;
	}

	//Checks if the current dice can be composed into a street.
	private ScoringCombination getStreet(List<Dice> dice) {
		if (dice.size() < 6)
			return null;
		
		ArrayList<Dice> resDice = new ArrayList<Dice>(6);
		
		int targetValue = 1;
		for (Dice d : dice) {
			
			if (d.getValue() > targetValue)
				return null;
			
			if (d.getValue() == targetValue) {
				resDice.add(d);
				targetValue++;
			}
		}
		
		if (resDice.size() != 6)
			return null;
		
		return new ScoringCombination("Street", 1000, resDice);
	}
	
	//Checks if there are three of a kind among the remaining dice.
	private ScoringCombination getThreeOfAKind(List<Dice> dice) {
		if (dice.size() < 3)
			return null;
		
		Iterator<Dice> it = dice.iterator();
		Dice d = it.next();
		int value = d.getValue();
		
		ArrayList<Dice> resDice = new ArrayList<Dice>(3);
		resDice.add(d);
		
		while (it.hasNext() && resDice.size() < 3)
		{
			d = it.next();
			
			if (d.getValue() == value)
				resDice.add(d);
			else
			{
				resDice = new ArrayList<Dice>(3);
				resDice.add(d);
				value = d.getValue();
			}
		}
		
		if (resDice.size() < 3)
			return null;
		
		if (value == 1)
			value = 10;
		
		return new ScoringCombination("Three of a Kind", 100 * value, resDice);
	}
	
	//Checks if there is a single one amongst the current dice
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
	
	//Checks if there is a single 5 amongst the current dice
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
