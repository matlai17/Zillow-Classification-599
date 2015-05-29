package UI;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;

import Controller.Controller;

public class PredictHousePrice {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PredictHousePrice window = new PredictHousePrice();
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
	public PredictHousePrice() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		Controller.main(null);
		String[][] test = Controller.result;
		// table = new JTable();
		// table.setBounds(54, 423, 504, -327);
		// frame.getContentPane().add(table);
		String[] columnNames = { "Address", "City", "Price sold", "Zestimate",
				"ANN", "NB" };
		// JScrollPane scrollPane = new JScrollPane();
		JTable table = new JTable(test, columnNames);
		table.setBounds(29, 86, 743, 134);
		// scrollPane.setViewportView(table);
		frame.getContentPane().add(table);

		JButton btnNewButton = new JButton("Predict");
		btnNewButton.setBounds(450, 28, 108, 23);
		frame.getContentPane().add(btnNewButton);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(142, 28, 298, 23);
		frame.getContentPane().add(comboBox);

		JLabel lblNewLabel = new JLabel("Select City");
		lblNewLabel.setBounds(57, 30, 56, 19);
		frame.getContentPane().add(lblNewLabel);
	}
}
