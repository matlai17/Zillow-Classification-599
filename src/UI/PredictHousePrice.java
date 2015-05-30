package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import Controller.Controller;

public class PredictHousePrice {

	private JFrame frame;
	ArrayList<String[]> test = new ArrayList<String[]>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
		frame = new JFrame();
		initialize();
	}
	
	
	public void showtable(ArrayList<String[]> test1, String city){
		String[] columnNames = { "Address", "City", "Price sold", "Zestimate",
				"ANN", "NB" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		String[][] tblData = new String[test1.size()][test1.get(0).length];
		
		JTable table = new JTable(tableModel);
		table.setBounds(25, 25, 700, 700);
		JScrollPane scrollPane = new JScrollPane(table);
		for (String string[] : test1) {
			tableModel.addRow(string);
		}
		frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame.setBounds(0, 0, 800, 800);
		  frame.getContentPane().setLayout(new BorderLayout());
	      //  frame.getContentPane().add(new JButton("Left"), BorderLayout.WEST);
	      //  frame.getContentPane().add(new JButton("Center"), BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Controller.main(null);
	     test = Controller.res;
				ArrayList<String> city = new ArrayList<String>();
				String prev = "";
				for (String string[] : test) {
					if(!string[1].equals(prev))
					{
						city.add(string[1]);
						prev = string[1];
					}
				}
				JComboBox comboBox = new JComboBox(city.toArray());
				comboBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						showtable(test,comboBox.getSelectedItem().toString());
					}
				});
				comboBox.setBounds(0, 0, 298, 23);
				frame.getContentPane().add(comboBox,BorderLayout.NORTH);
				
			
	}
	

}
