package greedGame;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class SetupGUI {

	private JFrame frmGreedGame;
	private JTextField pointsToWinText;
	private JTextField bustLimitText;
	private JButton btnReturn;

	/**
	 * Create the application.
	 */
	public SetupGUI() {
		initialize();
	}
	
	public void setVisible(boolean visible) {
		frmGreedGame.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGreedGame = new JFrame();
		frmGreedGame.setTitle("Setup Game");
		frmGreedGame.setBounds(100, 100, 245, 128);
		frmGreedGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGreedGame.getContentPane().setLayout(null);
		
		pointsToWinText = new JTextField();
		pointsToWinText.setText("10000");
		pointsToWinText.setBounds(10, 25, 100, 20);
		frmGreedGame.getContentPane().add(pointsToWinText);
		pointsToWinText.setColumns(10);
		
		JLabel lblPointsToWin = new JLabel("Points to win");
		lblPointsToWin.setBounds(10, 11, 100, 14);
		frmGreedGame.getContentPane().add(lblPointsToWin);
		
		JLabel lblBustLimit = new JLabel("Bust limit");
		lblBustLimit.setBounds(120, 11, 99, 14);
		frmGreedGame.getContentPane().add(lblBustLimit);
		
		bustLimitText = new JTextField();
		bustLimitText.setText("300");
		bustLimitText.setBounds(120, 25, 99, 20);
		frmGreedGame.getContentPane().add(bustLimitText);
		bustLimitText.setColumns(10);
		
		btnReturn = new JButton("Start Game");
		btnReturn.setBounds(10, 56, 209, 23);
		frmGreedGame.getContentPane().add(btnReturn);
	}
	
	public void addStartGameActionListener(ActionListener actionListener) {
		btnReturn.addActionListener(actionListener);
	}
	
	public int getPointsToWin() {
		return Integer.parseInt(pointsToWinText.getText());
	}
	
	public int getBustLimit() {
		return Integer.parseInt(bustLimitText.getText());
	}
}
