package greedGame.model;

public interface ScoringRules {

	int getMaxPoints(Dice dice);
	
	ScoringCombination getScoringCombination(Dice dice);
}
