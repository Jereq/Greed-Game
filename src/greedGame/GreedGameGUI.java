package greedGame;

import greedGame.model.Dice;
import greedGame.model.DiceState;
import greedGame.model.GreedGameModel;
import greedGame.model.GreedGameModel.ModelState;
import greedGame.model.player.Player;

import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;

public class GreedGameGUI implements Observer {

	private GreedGameModel model;

	private JFrame frmGreedGame;
	private JPanel surroundingGamePanel;
	private JPanel surroundingAddPlayerPanel;

	private JButton btnAddPlayer;
	private JButton btnRemoveCurrentPlayer;
	private JButton btnRoll;
	private JButton btnBank;
	private JTextPane playerList;
	private JTextPane historyPane;
	
	private JButton btnCreate;
	private JButton btnReturn;
	private JTextPane playerAddList;
	private JComboBox playerTypeComboBox;

	private JLabel[] diceLabels;
	private JCheckBox[] diceCheckBoxes;

	/*
	 * Default application launcher provided by WindowBuilder. We use our own,
	 * but leave this here as a reference.
	 * 
	 * 
	 * //** Launch the application.
	 *//*
		 * public static void main(String[] args) { EventQueue.invokeLater(new
		 * Runnable() {
		 * 
		 * @Override public void run() { try { GreedGameGUIExperiment window =
		 * new GreedGameGUIExperiment(); window.frame.setVisible(true); } catch
		 * (Exception e) { e.printStackTrace(); } } }); }
		 */

	/**
	 * Create the application.
	 */
	public GreedGameGUI(GreedGameModel model) {

		this.model = model;

		initialize();
		
		//** Uncomment one panel and comment the other in initialize when designing
		//
		// displayGamePanel();
		// displayAddPlayerPanel();
	}

	public void setVisible(boolean visible) {
		frmGreedGame.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmGreedGame = new JFrame();
		frmGreedGame.setTitle("Greed Game");
		frmGreedGame.setBounds(100, 100, 740, 460);
		frmGreedGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initializeGamePanel();
		initializeAddPlayerPanel();

		update(model, null);
	}

	private void initializeGamePanel() {

		surroundingGamePanel = new JPanel();
		surroundingGamePanel.setLayout(null);

		JPanel dicePanel = new JPanel();
		dicePanel.setBounds(10, 11, 85, 290);
		surroundingGamePanel.add(dicePanel);
		dicePanel.setLayout(new MigLayout("", "[][][]",
				"[][][][][][][][][][][]"));

		diceLabels = new JLabel[6];
		diceCheckBoxes = new JCheckBox[6];

		JLabel diceLabel1 = new JLabel("A");
		dicePanel.add(diceLabel1, "cell 0 0");
		diceLabels[0] = diceLabel1;

		JCheckBox diceCheckbox1 = new JCheckBox("");
		dicePanel.add(diceCheckbox1, "cell 2 0");
		diceCheckBoxes[0] = diceCheckbox1;

		Component verticalStrut = Box.createVerticalStrut(20);
		dicePanel.add(verticalStrut, "cell 2 1");

		JLabel diceLabel2 = new JLabel("B");
		dicePanel.add(diceLabel2, "cell 0 2");
		diceLabels[1] = diceLabel2;

		JCheckBox diceCheckbox2 = new JCheckBox("");
		dicePanel.add(diceCheckbox2, "cell 2 2");
		diceCheckBoxes[1] = diceCheckbox2;

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		dicePanel.add(verticalStrut_1, "cell 2 3");

		JLabel diceLabel3 = new JLabel("C");
		dicePanel.add(diceLabel3, "cell 0 4");
		diceLabels[2] = diceLabel3;

		JCheckBox diceCheckbox3 = new JCheckBox("");
		dicePanel.add(diceCheckbox3, "cell 2 4");
		diceCheckBoxes[2] = diceCheckbox3;

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		dicePanel.add(verticalStrut_2, "cell 2 5");

		JLabel diceLabel4 = new JLabel("D");
		dicePanel.add(diceLabel4, "cell 0 6");
		diceLabels[3] = diceLabel4;

		JCheckBox diceCheckbox4 = new JCheckBox("");
		dicePanel.add(diceCheckbox4, "cell 2 6");
		diceCheckBoxes[3] = diceCheckbox4;

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		dicePanel.add(verticalStrut_3, "cell 2 7");

		JLabel diceLabel5 = new JLabel("E");
		dicePanel.add(diceLabel5, "cell 0 8");
		diceLabels[4] = diceLabel5;

		JCheckBox diceCheckbox5 = new JCheckBox("");
		dicePanel.add(diceCheckbox5, "cell 2 8");
		diceCheckBoxes[4] = diceCheckbox5;

		Component verticalStrut_4 = Box.createVerticalStrut(20);
		dicePanel.add(verticalStrut_4, "cell 2 9");

		JLabel diceLabel6 = new JLabel("F");
		dicePanel.add(diceLabel6, "cell 0 10");
		diceLabels[5] = diceLabel6;

		JCheckBox diceCheckbox6 = new JCheckBox("");
		dicePanel.add(diceCheckbox6, "cell 2 10");
		diceCheckBoxes[5] = diceCheckbox6;

		btnRoll = new JButton("Roll");
		btnRoll.setBounds(212, 278, 292, 23);
		surroundingGamePanel.add(btnRoll);

		btnBank = new JButton("Bank");
		btnBank.setBounds(212, 244, 292, 23);
		surroundingGamePanel.add(btnBank);

		btnRemoveCurrentPlayer = new JButton("Remove Current Player");
		btnRemoveCurrentPlayer.setBounds(534, 278, 180, 23);
		surroundingGamePanel.add(btnRemoveCurrentPlayer);

		btnAddPlayer = new JButton("Add New Player");
		btnAddPlayer.setBounds(534, 244, 180, 23);
		surroundingGamePanel.add(btnAddPlayer);

		JScrollPane playerListScrollPane = new JScrollPane();
		playerListScrollPane.setBounds(534, 35, 180, 198);
		playerListScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		surroundingGamePanel.add(playerListScrollPane);
		
				playerList = new JTextPane();
				playerListScrollPane.setViewportView(playerList);
				playerList.setEditable(false);
				playerList.setText("mimimimi");

		JLabel lblHistory = new JLabel("History");
		lblHistory.setBounds(212, 11, 46, 14);
		surroundingGamePanel.add(lblHistory);

		JLabel lblPlayers = new JLabel("Players");
		lblPlayers.setBounds(534, 10, 46, 14);
		surroundingGamePanel.add(lblPlayers);

		JScrollPane historyScrollPane = new JScrollPane();
		historyScrollPane.setBounds(212, 35, 292, 198);
		surroundingGamePanel.add(historyScrollPane);

		historyPane = new JTextPane();
		historyPane.setEditable(false);
		historyScrollPane.setViewportView(historyPane);
		historyPane.setText("muu");
	}

	private void initializeAddPlayerPanel() {

		surroundingAddPlayerPanel = new JPanel();
		surroundingAddPlayerPanel.setLayout(null);

		playerTypeComboBox = new JComboBox();
		playerTypeComboBox.setBounds(10, 164, 245, 23);
		surroundingAddPlayerPanel.add(playerTypeComboBox);

		btnCreate = new JButton("Create Player");
		btnCreate.setBounds(10, 39, 245, 72);
		surroundingAddPlayerPanel.add(btnCreate);

		btnReturn = new JButton("Return");
		btnReturn.setBounds(422, 388, 292, 23);
		surroundingAddPlayerPanel.add(btnReturn);

		JScrollPane playerAddListScrollPane = new JScrollPane();
		playerAddListScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		playerAddListScrollPane.setBounds(422, 39, 292, 338);
		surroundingAddPlayerPanel.add(playerAddListScrollPane);

		playerAddList = new JTextPane();
		playerAddList.setEditable(false);
		playerAddList.setText("mimimimi");
		playerAddListScrollPane.setViewportView(playerAddList);

		JLabel lblPlayers = new JLabel("Players");
		lblPlayers.setBounds(422, 11, 46, 14);
		surroundingAddPlayerPanel.add(lblPlayers);
		
		JLabel labelPlayerType = new JLabel("Player type");
		labelPlayerType.setBounds(10, 139, 66, 14);
		surroundingAddPlayerPanel.add(labelPlayerType);
	}

	public void addAddPlayerActionListener(ActionListener actionListener) {
		btnAddPlayer.addActionListener(actionListener);
	}

	public void addRemoveCurrentPlayerActionListener(
			ActionListener actionListener) {
		btnRemoveCurrentPlayer.addActionListener(actionListener);
	}

	public void addRollActionListener(ActionListener actionListener) {
		btnRoll.addActionListener(actionListener);
	}

	public void addBankActionListener(ActionListener actionListener) {
		btnBank.addActionListener(actionListener);
	}

	public void addAllDiceCheckBoxActionListener(ActionListener actionListener) {
		for (JCheckBox checkBox : diceCheckBoxes)
			checkBox.addActionListener(actionListener);
	}
	
	public void addCreatePlayerActionListener(ActionListener actionListener) {
		btnCreate.addActionListener(actionListener);
	}
	
	public void addReturnActionListener(ActionListener actionListener) {
		btnReturn.addActionListener(actionListener);
	}
	
	public void setPlayerTypes(Iterable<String> playerTypes) {
		playerTypeComboBox.removeAllItems();
		for (String playerType : playerTypes)
			playerTypeComboBox.addItem(playerType);
	}
	
	public String getSelectedPlayerType() {
		return (String)playerTypeComboBox.getSelectedItem();
	}

	@Override
	public void update(Observable o, Object arg) {

		if (o != model)
			return;

		switch (model.getState()) {
		case WAITING_FOR_FIRST_ROLL:
		case WAITING_FOR_PLAYER_DECISION:
			updateGamePanel();
			break;

		case ADD_PLAYER:
			displayAddPlayerPanel();
			
			playerAddList.setText(buildPlayerList());

			break;
		}
	}
	
	private void updateGamePanel() {
		
		displayGamePanel();
		
		boolean localPlayer = model.isCurrentPlayerLocalGUI();
		boolean canPlayerDecide = model.canDecide() && localPlayer;
		btnBank.setEnabled(canPlayerDecide);
		
		if (model.getState() == ModelState.WAITING_FOR_FIRST_ROLL) {
			btnAddPlayer.setEnabled(true);
			btnRemoveCurrentPlayer.setEnabled(true);
			btnRoll.setEnabled(localPlayer);
		} else {
			btnAddPlayer.setEnabled(false);
			btnRemoveCurrentPlayer.setEnabled(false);
			btnRoll.setEnabled(canPlayerDecide);
		}

		List<Dice> dice = model.getDice();

		int i = 0;
		for (Dice d : dice) {
			diceLabels[i].setText(Integer.toString(d.getValue()));

			DiceState dState = d.getState();
			diceCheckBoxes[i].setSelected(dState == DiceState.SELECTED);
			diceCheckBoxes[i].setEnabled(dState != DiceState.RESERVED && model.isCurrentPlayerLocalGUI());

			i++;
		}

		playerList.setText(buildPlayerList());
		historyPane.setText(buildHistory());
	}

	private String buildPlayerList() {

		StringBuilder playerStats = new StringBuilder();

		for (Player p : model.getPlayers())
			playerStats.append(p.getName() + "\t" + p.getScore() + "\n");

		if (playerStats.length() > 0)
			playerStats.deleteCharAt(playerStats.length() - 1);

		return playerStats.toString();
	}
	
	private String buildHistory() {
		
		StringBuilder history = new StringBuilder();
		
		for (String s : model.getLog())
			history.append(s + "\n");
		
		if (history.length() > 0)
			history.deleteCharAt(history.length() - 1);
		
		return history.toString();
	}

	public List<JCheckBox> getDiceCheckBoxes() {
		return Arrays.asList(diceCheckBoxes);
	}

	public void displayGamePanel() {
		frmGreedGame.setContentPane(surroundingGamePanel);
	}

	public void displayAddPlayerPanel() {
		frmGreedGame.setContentPane(surroundingAddPlayerPanel);
	}
	
	public void dispose() {
		frmGreedGame.dispose();
	}
}
