package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
public class TransferView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JButton powerButton;
	private JButton backButton;
	private JButton transferButton;
	
	private JTextField transferField;
	
	public static final String NL = System.getProperty("line.separator");  
	// creates new line variable cited from https://stackoverflow.com/questions/20706206/insert-line-break-in-java
	
	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 */
	
	public TransferView(ViewManager manager) {
		super();
		
		this.manager = manager;
		initialize();
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
		initTransferButton();
		initTransferField();
	}
	
	private void initTransferField() {
		JLabel label = new JLabel("Amount to Transfer");
		label.setBounds(50, 70, 100, 35);
		label.setLabelFor(transferField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		transferField = new JTextField(20);
		transferField.setBounds(160, 70, 240, 35);
		
		this.add(label);
		this.add(transferField);
		
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
		backButton.setBounds(100, 300, 300, 20);
		backButton.addActionListener(this);
		
		this.add(backButton);
	}
	
	private void initTransferButton() {
		transferButton = new JButton("Transfer");
		this.add(transferButton);
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
		if (source.equals(backButton)) {
			manager.switchTo(ATM.HOME_VIEW);
		}
		else if (source.equals(powerButton)) {
			manager.shutdown();
		}
		else {
			System.err.println("ERROR: Action command not found (" + e.getActionCommand() + ")");
		}
	}
}