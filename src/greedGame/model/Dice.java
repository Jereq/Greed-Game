package greedGame.model;

import java.util.Random;

public class Dice {
	
	private Random rnd;
	private int value = 1;
	private DiceState state;
	
	public Dice(Random rnd) {
		this.rnd = rnd;
		roll();
	}
	
	public int getValue() {
		return value;
	}
	
	public void roll() {
		value = rnd.nextInt(6) + 1;
	}
	
	public DiceState getState() {
		return state;
	}
}