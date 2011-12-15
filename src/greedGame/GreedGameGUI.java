package greedGame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GreedGameGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GreedGameGUI window = new GreedGameGUI();
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
	public GreedGameGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 513, 312);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow][][][][][][][grow][grow][][][][]", "[][grow][grow][grow][][][]"));
		
		JLabel diceLable1 = new JLabel("A");
		frame.getContentPane().add(diceLable1, "cell 0 1");
		
		JCheckBox diceCheckbox1 = new JCheckBox("Dice 1");
		frame.getContentPane().add(diceCheckbox1, "cell 1 1");
		
		JButton btnNewButton = new JButton("Add new player");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(scrollPane, "cell 7 1,grow");
		
		JTextPane txtpnMuuhgghjghgfjghj = new JTextPane();
		scrollPane.setViewportView(txtpnMuuhgghjghgfjghj);
		txtpnMuuhgghjghgfjghj.setEditable(false);
		txtpnMuuhgghjghgfjghj.setText("muuhgghjghgfjghj");
		frame.getContentPane().add(btnNewButton, "cell 11 1,alignx center");
		
		JLabel diceLable2 = new JLabel("B");
		frame.getContentPane().add(diceLable2, "cell 0 2");
		
		JCheckBox diceCheckbox2 = new JCheckBox("Dice 2");
		frame.getContentPane().add(diceCheckbox2, "cell 1 2");
		
		JButton btnRoll = new JButton("Roll");
		frame.getContentPane().add(btnRoll, "cell 3 2,alignx center");
		
		JLabel diceLable3 = new JLabel("C");
		frame.getContentPane().add(diceLable3, "cell 0 3");
		
		JCheckBox diceCheckbox3 = new JCheckBox("Dice 3");
		frame.getContentPane().add(diceCheckbox3, "cell 1 3");
		
		JTextPane txtpnMuu = new JTextPane();
		txtpnMuu.setText("muu");
		frame.getContentPane().add(txtpnMuu, "cell 7 3,grow");
		
		JButton btnNewButton_1 = new JButton("Remove current player");
		frame.getContentPane().add(btnNewButton_1, "cell 11 3");
		
		JLabel diceLable4 = new JLabel("D");
		frame.getContentPane().add(diceLable4, "cell 0 4");
		
		JCheckBox diceCheckbox4 = new JCheckBox("Dice 4");
		frame.getContentPane().add(diceCheckbox4, "cell 1 4");
		
		JButton btnBank = new JButton("Bank");
		frame.getContentPane().add(btnBank, "cell 3 4");
		
		JLabel diceLable5 = new JLabel("E");
		frame.getContentPane().add(diceLable5, "cell 0 5");
		
		JCheckBox diceCheckbox5 = new JCheckBox("Dice 5");
		frame.getContentPane().add(diceCheckbox5, "cell 1 5");
	}

}
