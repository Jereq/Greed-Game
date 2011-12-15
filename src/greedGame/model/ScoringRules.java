package greedGame.model;

import java.util.List;

public interface ScoringRules {

	int getMaxPoints(List<Dice> dice);
	List<ScoringCombination> getScoringCombinations(List<Dice> dice);
}
