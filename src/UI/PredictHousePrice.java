package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controller.Controller;

public class PredictHousePrice {

	private JFrame frame;
	private JTable table;
	ArrayList<String[]> test = new ArrayList<String[]>();

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

	public void showtable(ArrayList<String[]> data, String city) {
		String[] columnNames = { "Address", "City", "Price sold", "Zestimate",
				"ANN Prediction", "NB Prediction" };

		ArrayList<String[]> filteredData = new ArrayList<String[]>();

		for (int i = 0; i < data.size(); i++)
			if (data.get(i)[1].equals(city)) {
				filteredData.add(data.get(i));
			}

		String[][] tblData = new String[filteredData.size()][filteredData
				.get(0).length];
		for (int i = 0; i < filteredData.size(); i++)
			if (filteredData.get(i)[1].equals(city)) {
				for (int j = 0; j < filteredData.get(0).length; j++) {
					tblData[i][j] = filteredData.get(i)[j];
				}
			}
		table.setBounds(0, 0, 700, 700);
		JScrollPane scrollPane = new JScrollPane(table);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		table = new JTable();
		frame.setBounds(0, 0, 800, 800);
		frame.getContentPane().setLayout(new BorderLayout());
		// frame.getContentPane().add(new JButton("Left"), BorderLayout.WEST);
		// frame.getContentPane().add(new JButton("Center"),
		// BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Controller.main(null);
		test = Controller.res;
		ArrayList<String> city = new ArrayList<String>();
		String prev = "";
		for (String string[] : test) {
			if (!string[1].equals(prev)) {
				city.add(string[1]);
				prev = string[1];
			}
		}
		JComboBox comboBox = new JComboBox(city.toArray());
		JLabel lbl = new JLabel("Select City");
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showtable(test, comboBox.getSelectedItem().toString());
			}
		});
		lbl.setBounds(5, 0, 98, 20);
		comboBox.setBounds(104, 0, 298, 20);
		frame.getContentPane().add(lbl, BorderLayout.BEFORE_FIRST_LINE);
		frame.getContentPane().add(comboBox, BorderLayout.NORTH);

	}
}
