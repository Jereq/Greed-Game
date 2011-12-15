package greedGame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;

public class GreedGameGUIExperiment {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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

	/**
	 * Create the application.
	 */
	public GreedGameGUIExperiment() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 530, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 85, 290);
		frame.getContentPane().add(panel);
		panel.setLayout(new MigLayout("", "[][][]", "[][][][][][][][][][][]"));
		
		JLabel diceLabel1 = new JLabel("A");
		panel.add(diceLabel1, "cell 0 0");
		
		JCheckBox diceCheckbox1 = new JCheckBox("Dice 1");
		panel.add(diceCheckbox1, "cell 2 0");
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut, "cell 2 1");
		
		JLabel diceLabel2 = new JLabel("B");
		panel.add(diceLabel2, "cell 0 2");
		
		JCheckBox diceCheckbox2 = new JCheckBox("Dice 2");
		panel.add(diceCheckbox2, "cell 2 2");
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_1, "cell 2 3");
		
		JLabel diceLabel3 = new JLabel("C");
		panel.add(diceLabel3, "cell 0 4");
		
		JCheckBox diceCheckbox3 = new JCheckBox("Dice 3");
		panel.add(diceCheckbox3, "cell 2 4");
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_2, "cell 2 5");
		
		JLabel diceLabel4 = new JLabel("D");
		panel.add(diceLabel4, "cell 0 6");
		
		JCheckBox diceCheckbox4 = new JCheckBox("Dice 4");
		panel.add(diceCheckbox4, "cell 2 6");
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_3, "cell 2 7");
		
		JLabel diceLabel5 = new JLabel("E");
		panel.add(diceLabel5, "cell 0 8");
		
		JCheckBox diceCheckbox5 = new JCheckBox("Dice 5");
		panel.add(diceCheckbox5, "cell 2 8");
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_4, "cell 2 9");
		
		JLabel diceLabel6 = new JLabel("F");
		panel.add(diceLabel6, "cell 0 10");
		
		JCheckBox diceCheckbox6 = new JCheckBox("Dice 6");
		panel.add(diceCheckbox6, "cell 2 10");
		
		JButton btnRoll = new JButton("Roll");
		btnRoll.setBounds(98, 228, 89, 23);
		frame.getContentPane().add(btnRoll);
		
		JButton btnBank = new JButton("Bank");
		btnBank.setBounds(98, 194, 89, 23);
		frame.getContentPane().add(btnBank);
		
		JButton btnRemoveCurrentPlayer = new JButton("Remove Current Player");
		btnRemoveCurrentPlayer.setBounds(359, 228, 145, 23);
		frame.getContentPane().add(btnRemoveCurrentPlayer);
		
		JButton btnAddPlayer = new JButton("Add New Player");
		btnAddPlayer.setBounds(359, 194, 145, 23);
		btnAddPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		frame.getContentPane().add(btnAddPlayer);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(359, 35, 145, 148);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(scrollPane);
		
		JTextPane playerList = new JTextPane();
		playerList.setText("mimimimi");
		scrollPane.setViewportView(playerList);
		
		JLabel lblHistorik = new JLabel("Historik");
		lblHistorik.setBounds(264, 10, 46, 14);
		frame.getContentPane().add(lblHistorik);
		
		JLabel lblPlayers = new JLabel("Players");
		lblPlayers.setBounds(359, 11, 46, 14);
		frame.getContentPane().add(lblPlayers);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(264, 35, 85, 148);
		frame.getContentPane().add(scrollPane_1);
		
		JTextPane txtpnMuu = new JTextPane();
		txtpnMuu.setText("muu");
		scrollPane_1.setViewportView(txtpnMuu);
	}
}
