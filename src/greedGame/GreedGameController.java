package greedGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JCheckBox;

import greedGame.model.Dice;
import greedGame.model.GreedGameModel;
import greedGame.model.player.Player;
import greedGame.model.player.PlayerFactory;

/**
 * A controller class for GreedGameGUI and GreedGameModel. When created, it ties
 * the view and the model together by setting what action to pass to the model
 * for every action generated by the GUI.
 */
public class GreedGameController {

	/**
	 * Pairing of a check box to a dice. Used so that the controller knows what
	 * dice to select for each time a check box is selected.
	 */
	private class CheckBoxDicePair {
		public JCheckBox checkBox;
		public Dice dice;

		/**
		 * Constructor.
		 * 
		 * @param checkBox
		 *            The check box in the pair
		 * @param dice
		 *            The dice in the pair
		 */
		public CheckBoxDicePair(JCheckBox checkBox, Dice dice) {
			this.checkBox = checkBox;
			this.dice = dice;
		}
	}

	// Store the model and view for the sake of the action events
	private GreedGameModel model;
	private GreedGameGUI view;

	// Mapping between check boxes and dice
	private List<CheckBoxDicePair> checkBoxDiceMap;

	// Creates different players to use
	private PlayerFactory playerFactory;

	/**
	 * Constructor.
	 * 
	 * @param greedGameModel
	 *            The model to bind together with a view. Must not be null.
	 * @param greedGameView
	 *            The view to bind together with a model. Must not be null.
	 */
	public GreedGameController(GreedGameModel greedGameModel,
			GreedGameGUI greedGameView) {

		model = greedGameModel;
		view = greedGameView;
		checkBoxDiceMap = new ArrayList<CheckBoxDicePair>(6);
		playerFactory = new PlayerFactory(model);

		// Iterate through all check boxes and dice and create as many pairs as
		// possible
		Iterator<JCheckBox> boxIt = view.getDiceCheckBoxes().iterator();
		Iterator<Dice> diceIt = model.getDice().iterator();
		while (boxIt.hasNext() && diceIt.hasNext()) {
			checkBoxDiceMap.add(new CheckBoxDicePair(boxIt.next(), diceIt
					.next()));
		}

		// Add listener to the check boxes
		view.addAllDiceCheckBoxActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Iterate through the pairs, and if a matching check box is
				// found, select or unselect the corresponding dice
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

		// Add listener for remove player button press
		view.addRemoveCurrentPlayerActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Forward the request to the model
				model.removeCurrentPlayer();
			}
		});

		// Add listener for bank button press
		view.addBankActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Forward the request to the model
				model.bank();
			}
		});

		// Add listener for roll button press
		view.addRollActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Forward request to the model
				model.tryRollDice();
			}
		});

		// Add listener for add player button press
		view.addAddPlayerActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Forward request to the model
				model.startAddPlayer();
			}
		});

		// Add listener for create player button press
		view.addCreatePlayerActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get the selected player type from the view
				String playerType = view.getSelectedPlayerType();

				// Pass the player type to the player factory and see if
				// recognizes it
				Player newPlayer = playerFactory.createPlayer(playerType);

				// See if a player was created, and if so, ask the model to add
				// the player to the game
				if (newPlayer != null)
					model.addPlayer(newPlayer);
			}
		});

		// Add listener for return from add player button press
		view.addReturnActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Forward request to the model
				model.stopAddPlayer();
			}
		});

		// Get the player types supported by the player factory and pass them to
		// the view
		view.setPlayerTypes(playerFactory.getPlayerTypes());
	}
}
