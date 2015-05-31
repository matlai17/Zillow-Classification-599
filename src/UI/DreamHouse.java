package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DreamHouse {

	private JFrame frame;

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
		JLabel lbl = new JLabel("Enter Requirement :");
		JLabel lblPredictedPrice = new JLabel("Estimated House Price :");
		JTextField textBed = new JTextField("#Bed");
		textBed.setToolTipText("Enter # of bedroom");
		JTextField textBath = new JTextField("#Bath");
		textBath.setToolTipText("Enter # of Bathroom");
		JTextField textArea = new JTextField("#Area");
		textArea.setToolTipText("Enter Area");
		JTextField textYBuilt = new JTextField("#YearBuilt");
		textYBuilt.setToolTipText("Enter House Year built");
		JTextField textPricesqrt = new JTextField("#PricePerSQFT");
		JButton btnPredict = new JButton("Predict");
		btnPredict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblPredictedPrice.setText("Estimated House Price :" + "$0.00");
			}
		});
                
                textBed.addFocusListener(new FocusAdapter(){
                    @Override
                    public void focusGained(java.awt.event.FocusEvent fe)
                    {
                        textBed.setText("");
                    }
                    @Override
                    public void focusLost(java.awt.event.FocusEvent fe)
                    {
                        if(textBed.getText().equals(""))
                            textBed.setText("#Bed");
                    }
                });
                textBath.addFocusListener(new FocusAdapter(){
                    @Override
                    public void focusGained(java.awt.event.FocusEvent fe)
                    {
                        textBath.setText("");
                    }
                    @Override
                    public void focusLost(java.awt.event.FocusEvent fe)
                    {
                        if(textBath.getText().equals(""))
                            textBath.setText("#Bath");
                    }
                });
                textArea.addFocusListener(new FocusAdapter(){
                    @Override
                    public void focusGained(java.awt.event.FocusEvent fe)
                    {
                        textArea.setText("");
                    }
                    @Override
                    public void focusLost(java.awt.event.FocusEvent fe)
                    {
                        if(textArea.getText().equals(""))
                            textArea.setText("#Area");
                    }
                });
                textYBuilt.addFocusListener(new FocusAdapter(){
                    @Override
                    public void focusGained(java.awt.event.FocusEvent fe)
                    {
                        textYBuilt.setText("");
                    }
                    @Override
                    public void focusLost(java.awt.event.FocusEvent fe)
                    {
                        if(textYBuilt.getText().equals(""))
                            textYBuilt.setText("#YearBuilt");
                    }
                });
                textPricesqrt.addFocusListener(new FocusAdapter(){
                    @Override
                    public void focusGained(java.awt.event.FocusEvent fe)
                    {
                        textPricesqrt.setText("");
                    }
                    @Override
                    public void focusLost(java.awt.event.FocusEvent fe)
                    {
                        if(textPricesqrt.getText().equals(""))
                            textPricesqrt.setText("#PricePerSQFT");
                    }
                });
                
		frame.setBounds(100, 100, 800, 400);
		lbl.setBounds(4, 0, 108, 20);
		textBed.setBounds(110, 0, 60, 20);
		textBath.setBounds(180, 0, 60, 20);
		textArea.setBounds(250, 0, 60, 20);
		textYBuilt.setBounds(320, 0, 60, 20);
		textPricesqrt.setBounds(390, 0, 80, 20);
		btnPredict.setBounds(480, 0, 80, 20);
		lblPredictedPrice.setBounds(100, 40, 300, 20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(lbl, BorderLayout.BEFORE_FIRST_LINE);
		frame.getContentPane().add(textBed, BorderLayout.NORTH);
		frame.getContentPane().add(textBath, BorderLayout.NORTH);
		frame.getContentPane().add(textArea, BorderLayout.NORTH);
		frame.getContentPane().add(textYBuilt, BorderLayout.NORTH);
		frame.getContentPane().add(textPricesqrt, BorderLayout.NORTH);
		frame.getContentPane().add(btnPredict, BorderLayout.NORTH);
		frame.getContentPane().add(lblPredictedPrice, BorderLayout.NORTH);
		frame.setVisible(true);
	}

}
