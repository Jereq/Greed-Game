package greedGame.model;

import java.util.Random;

public class Dice implements Comparable<Dice> {
	
	private Random rnd;
	private int value = 1;
	private DiceState state;
	
	//
	public Dice(Random rnd) {
		this.rnd = rnd;
		state = DiceState.RESERVED;
	}
	
	//returns the rolled value
	public int getValue() {
		return value;
	}
	
	//Rolls the dice changing the value and makes the state free.
	public void roll() {
		state = DiceState.FREE;
		value = rnd.nextInt(6) + 1;
	}
	
	//Returns the state of the dice
	public DiceState getState() {
		return state;
	}
	
	//Changes the state of the dice to the input state
	public void setState(DiceState state) {
		this.state = state;
	}
	
	//
	@Override
	public int compareTo(Dice o) {
		return value - o.getValue();
	}
}
