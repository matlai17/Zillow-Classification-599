package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import Controller.Controller;

public class PredictHousePrice {

	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	ArrayList<String[]> test = new ArrayList<String[]>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// Change how the UI looks like. You can use different UI types if you
		// think something else might look better.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
			Logger.getLogger(PredictHousePrice.class.getName()).log(
					Level.SEVERE, null, ex);
		}

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
		String[] columnNames = { "Address", "City", "Bedroom", "Bathroom",
				"Price/Sqft", "Elementary", "Middle", "High School",
				"Year Built", "Area", "Price sold", "Zestimate",
				"ANN Prediction", "NB Prediction" };

		ArrayList<String[]> filteredData = new ArrayList<String[]>();

		for (int i = 0; i < data.size(); i++)
			if (data.get(i)[1].equals(city) || city.equals("All Cities")) {
				filteredData.add(data.get(i));
			}

		String[][] tblData = new String[filteredData.size()][filteredData
				.get(0).length];
		for (int i = 0; i < filteredData.size(); i++)
			if (filteredData.get(i)[1].equals(city)
					|| city.equals("All Cities")) {
				for (int j = 0; j < filteredData.get(0).length; j++) {
					tblData[i][j] = filteredData.get(i)[j];
				}
			}
		table.setModel(new DefaultTableModel(tblData, columnNames));
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		table = new JTable();
		table.setBounds(0, 0, 700, 700);
		table.setEnabled(false);
		scrollPane = new JScrollPane(table);

		frame = new JFrame();
		frame.setBounds(0, 0, 1200, 700);
		frame.getContentPane().setLayout(new BorderLayout(0, 10));
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		// frame.getContentPane().add(new JButton("Left"), BorderLayout.WEST);
		// frame.getContentPane().add(new JButton("Center"),
		// BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		KeyListener kL = new java.awt.event.KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ESCAPE)
					frame.dispose();
			}
		};

		Controller.main(null);
		test = Controller.res;
		ArrayList<String> city = new ArrayList<String>();
		String prev = "";
		city.add("All Cities");
		for (String string[] : test) {
			if (!string[1].equals(prev)) {
				city.add(string[1]);
				prev = string[1];
			}
		}
		JComboBox comboBox = new JComboBox(city.toArray());
		// JComboBox comboBox1 = new JComboBox(city.toArray());
		JLabel lbl = new JLabel("Select City");
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showtable(test, comboBox.getSelectedItem().toString());
			}
		});
		lbl.setBounds(5, 0, 98, 20);
		comboBox.setBounds(104, 0, 298, 20);
		// comboBox.setBounds(208, 0, 298, 20);
		frame.getContentPane().add(lbl, BorderLayout.BEFORE_FIRST_LINE);
		frame.getContentPane().add(comboBox, BorderLayout.NORTH);
		// frame.getContentPane().add(comboBox1, BorderLayout.AFTER_LAST_LINE);

		showtable(test, comboBox.getSelectedItem().toString());

		frame.addKeyListener(kL);
		scrollPane.addKeyListener(kL);
		table.addKeyListener(kL);
		comboBox.addKeyListener(kL);

		frame.setVisible(true);
	}
}
