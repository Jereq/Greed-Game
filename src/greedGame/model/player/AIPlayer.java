package greedGame.model.player;

import java.util.List;

import greedGame.model.Dice;
import greedGame.model.GreedGameModel;
import greedGame.model.ScoringCombination;
import greedGame.model.ScoringRules;

public abstract class AIPlayer implements Player {

	protected enum AIDecision {
		KEEP_ROLLING,
		BANK
	}
	
	private String name;
	private int score;
	private GreedGameModel gameModel;
	private AIDecision decision;
	
	public AIPlayer(String name, GreedGameModel gameModel) {
		this.name = name;
		this.gameModel = gameModel;
		score = 0;
	}
	
	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void addScore(int points) {
		score += points;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void beginTurn() {
		gameModel.tryRollDice();
	}
	
	@Override
	public boolean isLocalGUIPlayer() {
		return false;
	}
	
	@Override
	public void beginDecide() {
		// TODO: wait
		decide();
		// TODO: wait
		act();
	}
	
	protected abstract void decide();
	
	protected void selectAllCombinations() {
		List<Dice> diceList = getUnreservedDice(); //gets the unreserved dice and puts it in a local list.
		ScoringRules rules = getScoringRules(); //gets the scoring rules.
		List<ScoringCombination> combinations = rules.getScoringCombinations(diceList); //gets the scoring combinations of the rules.
		
		// loop for deciding which dice to reserve.
		for(ScoringCombination forCombinations : combinations) //checks point-giving combinations available.
		{
			if(forCombinations.getScore() > 0) //if the available combinations give points.
			{
				for(Dice forDice : forCombinations.getDice()) //sorts out which dice to input.
				{
					selectDice(forDice); //reserves the input dice.
				}
			}
		}
	}
	
	private void act() {
		if (decision == AIDecision.KEEP_ROLLING)
			gameModel.tryRollDice();
		else
			gameModel.bank();
	}
	
	protected void setDecision(AIDecision decision) {
		this.decision = decision;
	}
	
	protected void selectDice(Dice dice) {
		gameModel.selectDice(dice);
	}
	
	protected List<Dice> getUnreservedDice() {
		return gameModel.getUnreservedDice();
	}
	
	protected List<Player> getPlayers() {
		return gameModel.getPlayers();
	}
	
	protected ScoringRules getScoringRules() {
		return gameModel.getScoringRules();
	}
}
