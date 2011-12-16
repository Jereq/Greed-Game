package greedGame;

import greedGame.model.Dice;
import greedGame.model.DiceState;
import greedGame.model.GreedGameModel;

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
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;
import java.awt.BorderLayout;

public class GreedGameGUIExperiment implements Observer {

	private GreedGameModel model;
	
	private JFrame frame;
	private JButton btnAddPlayer;
	private JButton btnRemoveCurrentPlayer;
	private JButton btnRoll;
	private JButton btnBank;
	private JLabel[] diceLabels;
	private JCheckBox[] diceCheckBoxes;

	/*
	 * Default application launcher provided by WindowBuilder.
	 * We use our own.
	 * 
	
	//**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					GreedGameGUIExperiment window = new GreedGameGUIExperiment();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the application.
	 */
	public GreedGameGUIExperiment(GreedGameModel model) {
		
		this.model = model;
		
		initialize();
	}
	
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		frame = new JFrame();
		frame.setBounds(100, 100, 571, 361);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel surroundingPanel = new JPanel();
		frame.getContentPane().add(surroundingPanel);
		surroundingPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 85, 290);
		surroundingPanel.add(panel);
		panel.setLayout(new MigLayout("", "[][][]", "[][][][][][][][][][][]"));
		
		diceLabels = new JLabel[6];
		diceCheckBoxes = new JCheckBox[6];
		
		JLabel diceLabel1 = new JLabel("A");
		panel.add(diceLabel1, "cell 0 0");
		diceLabels[0] = diceLabel1;
		
		JCheckBox diceCheckbox1 = new JCheckBox("Dice 1");
		panel.add(diceCheckbox1, "cell 2 0");
		diceCheckBoxes[0] = diceCheckbox1;
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut, "cell 2 1");
		
		JLabel diceLabel2 = new JLabel("B");
		panel.add(diceLabel2, "cell 0 2");
		diceLabels[1] = diceLabel2;
		
		JCheckBox diceCheckbox2 = new JCheckBox("Dice 2");
		panel.add(diceCheckbox2, "cell 2 2");
		diceCheckBoxes[1] = diceCheckbox2;
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_1, "cell 2 3");
		
		JLabel diceLabel3 = new JLabel("C");
		panel.add(diceLabel3, "cell 0 4");
		diceLabels[2] = diceLabel3;
		
		JCheckBox diceCheckbox3 = new JCheckBox("Dice 3");
		panel.add(diceCheckbox3, "cell 2 4");
		diceCheckBoxes[2] = diceCheckbox3;
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_2, "cell 2 5");
		
		JLabel diceLabel4 = new JLabel("D");
		panel.add(diceLabel4, "cell 0 6");
		diceLabels[3] = diceLabel4;
		
		JCheckBox diceCheckbox4 = new JCheckBox("Dice 4");
		panel.add(diceCheckbox4, "cell 2 6");
		diceCheckBoxes[3] = diceCheckbox4;
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_3, "cell 2 7");
		
		JLabel diceLabel5 = new JLabel("E");
		panel.add(diceLabel5, "cell 0 8");
		diceLabels[4] = diceLabel5;
		
		JCheckBox diceCheckbox5 = new JCheckBox("Dice 5");
		panel.add(diceCheckbox5, "cell 2 8");
		diceCheckBoxes[4] = diceCheckbox5;
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_4, "cell 2 9");
		
		JLabel diceLabel6 = new JLabel("F");
		panel.add(diceLabel6, "cell 0 10");
		diceLabels[5] = diceLabel6;
		
		JCheckBox diceCheckbox6 = new JCheckBox("Dice 6");
		panel.add(diceCheckbox6, "cell 2 10");
		diceCheckBoxes[5] = diceCheckbox6;
		
		btnRoll = new JButton("Roll");
		btnRoll.setBounds(98, 228, 89, 23);
		surroundingPanel.add(btnRoll);
		
		btnBank = new JButton("Bank");
		btnBank.setBounds(98, 194, 89, 23);
		surroundingPanel.add(btnBank);
		
		btnRemoveCurrentPlayer = new JButton("Remove Current Player");
		btnRemoveCurrentPlayer.setBounds(324, 228, 180, 23);
		surroundingPanel.add(btnRemoveCurrentPlayer);
		
		btnAddPlayer = new JButton("Add New Player");
		btnAddPlayer.setBounds(324, 194, 180, 23);
		surroundingPanel.add(btnAddPlayer);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(324, 35, 180, 148);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		surroundingPanel.add(scrollPane);
		
		JTextPane playerList = new JTextPane();
		playerList.setText("mimimimi");
		scrollPane.setViewportView(playerList);
		
		JLabel lblHistory = new JLabel("History");
		lblHistory.setBounds(230, 10, 46, 14);
		surroundingPanel.add(lblHistory);
		
		JLabel lblPlayers = new JLabel("Players");
		lblPlayers.setBounds(324, 11, 46, 14);
		surroundingPanel.add(lblPlayers);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(230, 35, 85, 148);
		surroundingPanel.add(scrollPane_1);
		
		JTextPane txtpnMuu = new JTextPane();
		txtpnMuu.setText("muu");
		scrollPane_1.setViewportView(txtpnMuu);
	}
	
	public void addAddPlayerActionListener(ActionListener actionListener) {
		btnAddPlayer.addActionListener(actionListener);
	}
	
	public void addRemoveCurrentPlayerActionListener(ActionListener actionListener) {
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

	@Override
	public void update(Observable o, Object arg) {

		if (o != model)
			return;
		
		List<Dice> dice = model.getDice();
		
		int i = 0;
		for (Dice d : dice) {
			diceLabels[i].setText(Integer.toString(d.getValue()));
			
			DiceState dState = d.getState();
			diceCheckBoxes[i].setSelected(dState == DiceState.SELECTED);
			diceCheckBoxes[i].setEnabled(dState != DiceState.RESERVED);
			
			i++;
		}
	}
	
	public List<JCheckBox> getDiceCheckBoxes() {		
		return Arrays.asList(diceCheckBoxes);
	}
}
