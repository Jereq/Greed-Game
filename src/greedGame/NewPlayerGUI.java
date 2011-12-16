package greedGame;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewPlayerGUI {

	private JFrame frame;
	
	private JPanel surroundingAddPlayerPanel;

	/**
	 * Create the application.
	 */
	public NewPlayerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initializeAddPlayerPanel();
	}
	
	private void initializeAddPlayerPanel() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		surroundingAddPlayerPanel = new JPanel();
		frame.getContentPane().add(surroundingAddPlayerPanel);
		surroundingAddPlayerPanel.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(32, 44, 113, 20);
		surroundingAddPlayerPanel.add(comboBox);
		
		JButton btnCreate = new JButton("Create Player");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCreate.setBounds(155, 43, 100, 23);
		surroundingAddPlayerPanel.add(btnCreate);
		
		JButton btnCancel = new JButton("Cancel/Continue");
		btnCancel.setBounds(265, 228, 159, 23);
		surroundingAddPlayerPanel.add(btnCancel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(265, 44, 159, 176);
		surroundingAddPlayerPanel.add(scrollPane);
		
		JTextPane txtpnMimimimi = new JTextPane();
		txtpnMimimimi.setText("mimimimi");
		scrollPane.setViewportView(txtpnMimimimi);
		
		JLabel lblPlayers = new JLabel("Players");
		lblPlayers.setBounds(265, 19, 46, 14);
		surroundingAddPlayerPanel.add(lblPlayers);
	}
}
