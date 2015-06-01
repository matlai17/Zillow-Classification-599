package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import Classifiers.ANN;
import Controller.Controller;

public class DreamHouse {

	private JFrame frame;
	ANN ANN;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					DreamHouse window = new DreamHouse();
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
	public DreamHouse() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().setBackground(
				UIManager.getColor("InternalFrame.activeTitleGradient"));
		JLabel lbl = new JLabel("Enter Requirement");
		lbl.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		JLabel lblPredictedPrice = new JLabel("Estimated House Price :");
		lblPredictedPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		JTextField zipCode = new JTextField("#Zip");
		zipCode.setToolTipText("Enter ZipCode");
		JTextField textBed = new JTextField("#Bed");
		textBed.setToolTipText("Enter # of bedroom");
		JTextField textBath = new JTextField("#Bath");
		textBath.setToolTipText("Enter # of Bathroom");
		JTextField textArea = new JTextField("#Area");
		textArea.setToolTipText("Enter Area");
		JTextField textYBuilt = new JTextField("#YearBuilt");
		textYBuilt.setToolTipText("Enter House Year built");
		JTextField schoolElem = new JTextField("#schoolElem");
		schoolElem.setToolTipText("Enter expected Elementary school rank");
		JTextField schoolMid = new JTextField("#schoolMid");
		schoolElem.setToolTipText("Enter expected Middle school rank");
		JTextField schoolHigh = new JTextField("#schoolHigh");
		schoolElem.setToolTipText("Enter expected High school rank");
		JButton btnPredict = new JButton("Predict");
		btnPredict.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPredict.setToolTipText("Click to get your dream house Price...");
		btnPredict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.singleHouse = true;
				Controller.dreamHouse[0] = zipCode.getText();
				Controller.dreamHouse[1] = textBed.getText();
				Controller.dreamHouse[2] = textBath.getText();
				Controller.dreamHouse[3] = textArea.getText();
				Controller.dreamHouse[4] = textYBuilt.getText();
				Controller.dreamHouse[5] = schoolElem.getText();
				Controller.dreamHouse[6] = schoolMid.getText();
				Controller.dreamHouse[7] = schoolHigh.getText();
				Controller.dreamHouse[8] = "0.00";
				Controller.main(null);

				lblPredictedPrice.setText("Estimated House Price :$"
						+ Controller.houseValue);
			}
		});
		zipCode.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent fe) {
				zipCode.setText("");
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent fe) {
				if (zipCode.getText().equals(""))
					zipCode.setText("ZipCode");
			}
		});
		textBed.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent fe) {
				textBed.setText("");
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent fe) {
				if (textBed.getText().equals(""))
					textBed.setText("#Bed");
			}
		});
		textBath.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent fe) {
				textBath.setText("");
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent fe) {
				if (textBath.getText().equals(""))
					textBath.setText("#Bath");
			}
		});
		textArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent fe) {
				textArea.setText("");
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent fe) {
				if (textArea.getText().equals(""))
					textArea.setText("#Area");
			}
		});
		textYBuilt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent fe) {
				textYBuilt.setText("");
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent fe) {
				if (textYBuilt.getText().equals(""))
					textYBuilt.setText("#YearBuilt");
			}
		});
		schoolElem.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent fe) {
				schoolElem.setText("");
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent fe) {
				if (schoolElem.getText().equals(""))
					schoolElem.setText("Rank_elem");
			}
		});
		schoolMid.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent fe) {
				schoolMid.setText("");
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent fe) {
				if (schoolMid.getText().equals(""))
					schoolMid.setText("Rank_Mid");
			}
		});
		schoolHigh.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent fe) {
				schoolHigh.setText("");
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent fe) {
				if (schoolHigh.getText().equals(""))
					schoolHigh.setText("Rank_High");
			}
		});

		frame.setBounds(100, 100, 900, 400);
		lbl.setBounds(0, 0, 60, 40);
		zipCode.setBounds(125, 0, 60, 20);
		textBed.setBounds(195, 0, 60, 20);
		textBath.setBounds(265, 0, 60, 20);
		textArea.setBounds(335, 0, 60, 20);
		textYBuilt.setBounds(405, 0, 60, 20);
		schoolElem.setBounds(475, 0, 80, 20);
		schoolMid.setBounds(565, 0, 80, 20);
		schoolHigh.setBounds(655, 0, 80, 20);
		btnPredict.setBounds(745, 0, 80, 20);
		lblPredictedPrice.setBounds(115, 40, 300, 20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(lbl, BorderLayout.BEFORE_FIRST_LINE);
		frame.getContentPane().add(zipCode, BorderLayout.NORTH);
		frame.getContentPane().add(textBed, BorderLayout.NORTH);
		frame.getContentPane().add(textBath, BorderLayout.NORTH);
		frame.getContentPane().add(textArea, BorderLayout.NORTH);
		frame.getContentPane().add(textYBuilt, BorderLayout.NORTH);
		frame.getContentPane().add(schoolElem, BorderLayout.NORTH);
		frame.getContentPane().add(schoolMid, BorderLayout.NORTH);
		frame.getContentPane().add(schoolHigh, BorderLayout.NORTH);
		frame.getContentPane().add(btnPredict, BorderLayout.NORTH);
		frame.getContentPane().add(lblPredictedPrice, BorderLayout.NORTH);
		frame.setVisible(true);
	}
}
