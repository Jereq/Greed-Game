package greedGame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceHandler {

	private final int numberOfDice = 6; 
	
	private List<Dice> dice;
	
	public DiceHandler() {
		dice = new ArrayList<Dice>(numberOfDice);
		
		Random rnd = new Random();
		
		for(int i = 0; i < numberOfDice; i++) {
			dice.add(new Dice(rnd));
		}
	}
	
	public void rollDice() {
		for (Dice d : dice) {
			if (d.getState() == DiceState.FREE)
				d.roll();
		}
	}
}
