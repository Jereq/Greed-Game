package greedGame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class SetupGUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SetupGUI window = new SetupGUI();
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
	public SetupGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 25, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPointsToWin = new JLabel("Points to win");
		lblPointsToWin.setBounds(10, 11, 61, 14);
		frame.getContentPane().add(lblPointsToWin);
		
		JLabel lblBustLimit = new JLabel("Bust limit");
		lblBustLimit.setBounds(106, 11, 46, 14);
		frame.getContentPane().add(lblBustLimit);
		
		textField_1 = new JTextField();
		textField_1.setBounds(106, 25, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnReturn = new JButton("Return");
		btnReturn.setBounds(10, 56, 89, 23);
		frame.getContentPane().add(btnReturn);
	}
}
