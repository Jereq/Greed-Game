package greedGame.model.player;

import java.util.List;
import java.util.Random;

import greedGame.model.Dice;
import greedGame.model.GreedGameModel;
import greedGame.model.ScoringCombination;
import greedGame.model.ScoringRules;
import greedGame.model.player.AIPlayer.AIDecision;

public class RandomAIPlayer extends AIPlayer {

	Random random;
	public RandomAIPlayer(String name, GreedGameModel model) {
		super(name, model);
		random = new Random();
	}
	
	@Override
	protected void decide() {
		List<Dice> diceList = getUnreservedDice();
		ScoringRules rules = getScoringRules();
		List<ScoringCombination> combinations = rules.getScoringCombinations(diceList);
		
		for(ScoringCombination forCombo : combinations)
		{
			if(forCombo.getScore() > 0)
			{
				for(Dice forDice : diceList)
				{
					selectDice(forDice);
				}
			}
		}
		
		if (random.nextBoolean())
		{
			setDecision(AIDecision.KEEP_ROLLING);
		}
		else
		{
			setDecision(AIDecision.BANK);
		}
		

	}

}
