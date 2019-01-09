package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidParameterException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;
import data.Database;
import model.BankAccount;

@SuppressWarnings("serial")
public class DepositView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JButton powerButton;
	private JButton backButton;
	private JButton depositButton;
	private JLabel errorMessageLabel;
	
	private JTextField depositField;
	
	public static final String NL = System.getProperty("line.separator");  
	// creates new line variable cited from https://stackoverflow.com/questions/20706206/insert-line-break-in-java
	
	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 */
	
	public DepositView(ViewManager manager) {
		super();
		
		this.manager = manager;
		this.errorMessageLabel = new JLabel("", SwingConstants.CENTER);

		initialize();
	}
	public void updateErrorMessage(String errorMessage) {
		errorMessageLabel.setText(errorMessage);
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the HomeView components.
	 */
	
	
	private void initialize() {
		this.setLayout(null);
		
		this.add(new javax.swing.JLabel("HomeView", javax.swing.SwingConstants.CENTER));
		
		initPowerButton();
		initBackButton();
		initDepositButton();
		initDepositField();
		initErrorMessageLabel();
	}
	
	private void initDepositField() {
		JLabel label = new JLabel("Amount to Deposit");
		label.setBounds(50, 70, 200, 35);
		label.setLabelFor(depositField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		depositField = new JTextField(20);
		depositField.setBounds(260, 70, 150, 40);
		
		this.add(label);
		this.add(depositField);
	}
	
	private void initPowerButton() {
		powerButton = new JButton();
		powerButton.setBounds(5, 5, 50, 50);
		powerButton.addActionListener(this);
		
		try {
			Image image = ImageIO.read(new File("images/power-off.png"));
			powerButton.setIcon(new ImageIcon(image));
		} catch (Exception e) {
			powerButton.setText("OFF");
		}
		
		this.add(powerButton);
	}
	
	private void initBackButton() {
		backButton = new JButton("Back");
		backButton.setBounds(150, 280, 200, 40);
		backButton.addActionListener(this);
		
		this.add(backButton);
	}
	
	private void initDepositButton() {
		depositButton = new JButton("Deposit");
		depositButton.setBounds(150, 200, 200, 60);
		depositButton.addActionListener(this);
		this.add(depositButton);
	}
	private void initErrorMessageLabel() {
		errorMessageLabel.setBounds(0, 420, 500, 35);
		errorMessageLabel.setFont(new Font("DialogInput", Font.ITALIC, 14));
		errorMessageLabel.setForeground(Color.RED);
		
		this.add(errorMessageLabel);
	}
	
	private void setValues() {
		double amount = Double.valueOf(depositField.getText());
		
		if (String.valueOf(amount).length() == 0) {
			throw new InvalidParameterException("Please enter a valid amount");
		}
		
		manager.deposit(amount);
	}
	/*
	 * HomeView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The HomeView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the HomeView.
	 * 
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source.equals(depositButton)) {
			try {
				setValues();
				manager.switchTo(ATM.HOME_VIEW);
				manager.showLabels();
				this.removeAll();
				initialize();
				updateErrorMessage("");
			}
			catch (NumberFormatException e1) {
				updateErrorMessage("Please complete all fields correctly");
			}
			catch (InvalidParameterException e2) {
				updateErrorMessage(e2.getMessage());
			}
			catch (NullPointerException e3) {
				updateErrorMessage("Null pointer");
			}
			
		}
		else if (source.equals(backButton)) {
			manager.switchTo(ATM.HOME_VIEW);
			this.removeAll();
			this.initialize();
			updateErrorMessage("");
		}
		else if (source.equals(powerButton)) {
			manager.shutdown();
		}
		else {
			System.err.println("ERROR: Action command not found (" + e.getActionCommand() + ")");
		}
	}
}