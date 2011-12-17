package greedGame.model.player;

import java.awt.EventQueue;
import java.util.List;

import greedGame.model.Dice;
import greedGame.model.GreedGameModel;
import greedGame.model.ScoringCombination;
import greedGame.model.ScoringRules;

public abstract class AIPlayer implements Player {

	protected enum AIDecision {
		KEEP_ROLLING, BANK
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

	// Returns the score.
	@Override
	public int getScore() {
		return score;
	}

	// Adds the current points to the players total.
	@Override
	public void addScore(int points) {
		score += points;
	}

	// Returns the name of the player.
	@Override
	public String getName() {
		return name;
	}

	//
	@Override
	public void beginTurn() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (isPlayersTurn())
					gameModel.tryRollDice();
			}
		});
	}

	//
	@Override
	public boolean isLocalGUIPlayer() {
		return false;
	}

	//
	@Override
	public void beginDecide() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (isPlayersTurn()) {
					decide();
					// TODO: wait
					act();
				}
			}
		});
	}

	protected boolean isPlayersTurn() {
		return this == gameModel.getCurrentPlayer();
	}

	protected abstract void decide();

	protected void selectAllCombinations() {
		List<Dice> diceList = getUnreservedDice(); // gets the unreserved dice
													// and puts it in a local
													// list.
		ScoringRules rules = getScoringRules(); // gets the scoring rules.
		List<ScoringCombination> combinations = rules
				.getScoringCombinations(diceList); // gets the scoring
													// combinations of the
													// rules.

		// loop for deciding which dice to reserve.
		for (ScoringCombination forCombinations : combinations) // checks
																// point-giving
																// combinations
																// available.
		{
			if (forCombinations.getScore() > 0) // if the available combinations
												// give points.
			{
				for (Dice forDice : forCombinations.getDice()) // sorts out
																// which dice to
																// input.
				{
					selectDice(forDice); // reserves the input dice.
				}
			}
		}
	}

	//
	private void act() {
		if (decision == AIDecision.KEEP_ROLLING)
			gameModel.tryRollDice();
		else
			gameModel.bank();
	}

	//
	protected void setDecision(AIDecision decision) {
		this.decision = decision;
	}

	// Selects the dice for keeping or banking.
	protected void selectDice(Dice dice) {
		gameModel.selectDice(dice);
	}

	// returns the dice that weren't selected last turn.
	protected List<Dice> getUnreservedDice() {
		return gameModel.getUnreservedDice();
	}

	// Returns the dice that are currently selected.
	protected List<Dice> getSelectedDice() {
		return gameModel.getSelectedDice();
	}

	// Returns the dice that weren't selected.
	protected List<Dice> getFreeDice() {
		return gameModel.getFreeDice();
	}

	// Returns the active players.
	protected List<Player> getPlayers() {
		return gameModel.getPlayers();
	}

	// gets the scoring rules.
	protected ScoringRules getScoringRules() {
		return gameModel.getScoringRules();
	}

	// returns the score amassed during the current turn and not yet banked.
	protected int getSubScore() {
		return gameModel.getSubScore();
	}

	// returns the score of the currently selected dice.
	protected int getSelectedDiceScore() {
		return getScoringRules().getMaxPoints(getSelectedDice());
	}
}
