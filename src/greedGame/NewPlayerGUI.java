package greedGame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewPlayerGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewPlayerGUI window = new NewPlayerGUI();
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
	public NewPlayerGUI() {
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(32, 44, 113, 20);
		frame.getContentPane().add(comboBox);
		
		JButton btnCreate = new JButton("Create Player");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCreate.setBounds(155, 43, 100, 23);
		frame.getContentPane().add(btnCreate);
		
		JButton btnCancel = new JButton("Cancel/Continue");
		btnCancel.setBounds(265, 228, 159, 23);
		frame.getContentPane().add(btnCancel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(265, 44, 159, 176);
		frame.getContentPane().add(scrollPane);
		
		JTextPane txtpnMimimimi = new JTextPane();
		txtpnMimimimi.setText("mimimimi");
		scrollPane.setViewportView(txtpnMimimimi);
		
		JLabel lblPlayers = new JLabel("Players");
		lblPlayers.setBounds(265, 19, 46, 14);
		frame.getContentPane().add(lblPlayers);
	}
}
