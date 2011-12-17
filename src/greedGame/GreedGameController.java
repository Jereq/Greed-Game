package greedGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JCheckBox;

import greedGame.model.Dice;
import greedGame.model.GreedGameModel;

public class GreedGameController {
	
	private class CheckBoxDicePair {
		public JCheckBox checkBox;
		public Dice dice;
		
		public CheckBoxDicePair(JCheckBox checkBox, Dice dice) {
			this.checkBox = checkBox;
			this.dice = dice;
		}
	}

	GreedGameModel model;
	GreedGameGUI view;
	List<CheckBoxDicePair> checkBoxDiceMap;
	
	public GreedGameController(GreedGameModel greedGameModel, GreedGameGUI greedGameView) {
		
		model = greedGameModel;
		view = greedGameView;
		checkBoxDiceMap = new ArrayList<CheckBoxDicePair>(6);
		
		Iterator<JCheckBox> boxIt = view.getDiceCheckBoxes().iterator();
		Iterator<Dice> diceIt = model.getDice().iterator();
		while (boxIt.hasNext() && diceIt.hasNext()) {
			checkBoxDiceMap.add(new CheckBoxDicePair(boxIt.next(), diceIt.next()));
		}
		
		view.addAllDiceCheckBoxActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (CheckBoxDicePair pair : checkBoxDiceMap) {
					if (pair.checkBox == e.getSource()) {
						if (pair.checkBox.isSelected())
							model.selectDice(pair.dice);
						else
							model.unselectDice(pair.dice);
					}
				}
			}
		});
		
		view.addRemoveCurrentPlayerActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeCurrentPlayer();
			}
		});
		
		view.addBankActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.bank();
			}
		});
		
		view.addRollActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.rollDice();
			}
		});
		
		view.addAddPlayerActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.startAddPlayer();
			}
		});
		
		view.addCreatePlayerActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addHumanPlayer();
			}
		});
		
		view.addCancelAddPlayerActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.stopAddPlayer();
			}
		});
	}
}
