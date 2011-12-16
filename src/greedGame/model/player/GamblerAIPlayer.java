package greedGame.model.player;

import greedGame.model.Dice;
import greedGame.model.ScoringCombination;
import greedGame.model.ScoringRules;
import greedGame.model.GreedGameModel;

import java.util.List;



public class GamblerAIPlayer extends AIPlayer {
	
	public GamblerAIPlayer(String name, GreedGameModel model) {
		super(name, model);
	}
	@Override
	
	public void decide() {
		rollDice();
		List<Dice> diceList = getUnreservedDice();
		ScoringRules rules = getScoringRules();
		List<ScoringCombination> combinations = rules.getScoringCombinations(diceList);
		
		while (diceList.size() > 0)
		{
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
			//end of for loop
		}
		//end of while loop

	}

}
