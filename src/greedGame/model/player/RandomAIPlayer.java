package greedGame.model.player;

import java.util.List;
import java.util.Random;

import greedGame.model.Dice;
import greedGame.model.GreedGameModel;
import greedGame.model.ScoringCombination;
import greedGame.model.ScoringRules;
import greedGame.model.player.AIPlayer.AIDecision;

public class RandomAIPlayer extends AIPlayer {

	Random random;// creates a "Random object".
	
	//the class constructor.
	public RandomAIPlayer(String name, GreedGameModel model) {
		super(name, model); 
		random = new Random();
	}
	
	
	@Override
	protected void decide() {
		List<Dice> diceList = getUnreservedDice(); //gets the unreserved dice and puts it in a local list.
		ScoringRules rules = getScoringRules(); //gets the scoring rules.
		List<ScoringCombination> combinations = rules.getScoringCombinations(diceList); //gets the scoring combinations of the rules.
		
		// loop for deciding which dice to reserve.
		for(ScoringCombination forCombinations : combinations) //checks point-giving combinations available.
		{
			if(forCombinations.getScore() > 0) //if the available combinations give points.
			{
				for(Dice forDice : diceList) //sorts out which dice to input.
				{
					selectDice(forDice); //reserves the input dice.
				}
			}
		}
		
		// random boolean if statement to make the random AI either keep rolling or bank his score.
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
