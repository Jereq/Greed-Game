package greedGame.model.player;

import java.util.List;

import greedGame.model.Dice;
import greedGame.model.GreedGameModel;
import greedGame.model.ScoringRules;

public abstract class AIPlayer implements Player {

	private String name;
	private int score;
	private GreedGameModel gameModel;
	
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
		// TODO : Add waiting and deciding method
	}
	
	protected void bank() {
		gameModel.bank();
	}
	
	protected void rollDice() {
		gameModel.rollDice();
	}
	
	protected void selectDice(int index) {
		gameModel.selectDice(index);
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
