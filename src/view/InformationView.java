package view;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidParameterException;
import java.sql.SQLException;

import controller.ViewManager;
import data.Database;
import model.BankAccount;
import model.User;

@SuppressWarnings("serial")
public class InformationView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JTextField accountNumberField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JComboBox<Integer> monthField;
	private JComboBox<Integer> dayField;
	private JComboBox<Integer> yearField;
	private JTextField phoneNumber1Field;
	private JTextField phoneNumber2Field;
	private JTextField phoneNumber3Field;
	private JTextField streetAddressField;
	private JTextField cityField;
	private JComboBox<String> stateField;
	private JTextField postalField;
	private JPasswordField pinField;
	private JButton editButton;
	private JButton powerButton;
	private JButton cancelButton;
	private JLabel errorMessageLabel;
	
	private BankAccount account;
	
	/**
	 * Constructs an instance (or object) of the CreateView class.
	 * 
	 * @param manager
	 */
	
	public InformationView(ViewManager manager) {
		super();
		
		this.manager = manager;
		this.errorMessageLabel = new JLabel("", SwingConstants.CENTER);
		initialize();
	}
	
	public void initAccount(BankAccount account) {
		this.account = account;
	}
	
	public void updateErrorMessage(String errorMessage) {
		errorMessageLabel.setText(errorMessage);
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the CreateView components.
	 */
	
	private void initialize() {
		this.setLayout(null);
		initPowerButton();
		initEditButton();
		initCancelButton();
		initErrorMessageLabel();
	}
	
	public void initInformation() {
		initAccountNumberField();
		initFirstNameField();
		initLastNameField();
		initBirthDateField();
		initPhoneNumberInput();
		initAddressField();
		initPinField();
		initTextFields();
	}
	
	private void initTextFields() {
		accountNumberField.setText(Long.toString(account.getAccountNumber()));
		firstNameField.setText(account.getUser().getFirstName());
		lastNameField.setText(account.getUser().getLastName());
		monthField.setSelectedItem(Integer.toString(account.getUser().getDob()).substring(0,2));
		dayField.setSelectedItem(Integer.toString(account.getUser().getDob()).substring(2,4));
		yearField.setSelectedItem(Integer.toString(account.getUser().getDob()).substring(4));
		phoneNumber1Field.setText(Long.toString(account.getUser().getPhone()).substring(0,3));
		phoneNumber2Field.setText(Long.toString(account.getUser().getPhone()).substring(3,6));
		phoneNumber3Field.setText(Long.toString(account.getUser().getPhone()).substring(6));
		streetAddressField.setText(account.getUser().getStreetAddress());
		cityField.setText(account.getUser().getCity());
		stateField.setSelectedItem(account.getUser().getState());
		postalField.setText(account.getUser().getZip());
		pinField.setText(Integer.toString(account.getUser().getPin()));
		pinField.setEchoChar('\u2022');
	}
	
	private void initAccountNumberField() {
		JLabel label = new JLabel("Account Number");
		label.setLayout(null);
		label.setBounds(50, 25, 150, 35);
		label.setLabelFor(accountNumberField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		accountNumberField = new JTextField(20);
		accountNumberField.setBounds(200, 25, 200, 35);
		
		accountNumberField.setEditable(false);
		
		this.add(label);
		this.add(accountNumberField);
	}
	
	private void initFirstNameField() {
		
		JLabel label = new JLabel("First Name");
		label.setLayout(null);
		label.setBounds(50, 70, 100, 35);
		label.setLabelFor(firstNameField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		firstNameField = new JTextField(20);
		firstNameField.setBounds(160, 70, 240, 35);
		
		
		firstNameField.setEditable(false);
		
		this.add(label);
		this.add(firstNameField);
		
	}
	
	private void initLastNameField() {
		JLabel label = new JLabel("Last Name");
		label.setBounds(50, 110, 100, 35);
		label.setLabelFor(lastNameField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		lastNameField = new JTextField(20);
		lastNameField.setBounds(160, 110, 240, 35);
		
		
		lastNameField.setEditable(false);
		
		this.add(label);
		this.add(lastNameField);
	}
	
	private void initBirthDateField() {
		JLabel label = new JLabel("DOB (MM/DD/YYYY)");
		label.setBounds(50, 150, 200, 35);
		
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		/*String[] month = {"January", "February", "March", "April", "May", 
				"June", "July", "August", "September", "October", "November", "December"};*/
		Integer[] month = new Integer[12];
		for (int i = 0; i < 12; i++) {
			month[i] = i + 1;
		}
		
		monthField = new JComboBox<Integer>(month);
		monthField.setBounds(200, 150, 50, 35);
		
		monthField.setEnabled(false);
		this.add(label);
		this.add(monthField);
		
		Integer[] day = new Integer[31];
		for (int i = 0; i < 31; i++) {
			day[i] = i + 1;
		}
		
		dayField = new JComboBox<Integer>(day);
		dayField.setBounds(270, 150, 50, 35);
		
		dayField.setEnabled(false);
		this.add(dayField);
		
		Integer[] year = new Integer[120];
		
		for (int i = 0; i < 120; i++) {
			year[i] = 1900 + i;
		}
		
		yearField = new JComboBox<Integer>(year);
		yearField.setBounds(330, 150, 70, 35);
		
		yearField.setEnabled(false);
		this.add(yearField);
		
	}
	
	private void initPhoneNumberInput() {
		JLabel label = new JLabel("Phone Number");
		label.setBounds(50, 195, 100, 35);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		phoneNumber1Field = new JTextField(3);

		phoneNumber1Field.setBounds(160, 195, 80, 35);
		
		phoneNumber1Field.setEditable(false);
		limitToIntegers(phoneNumber1Field);
		limitSize(phoneNumber1Field, 3);
		
		this.add(label);
		this.add(phoneNumber1Field);
		
		phoneNumber2Field = new JTextField(3);
		phoneNumber2Field.setEditable(false);

		phoneNumber2Field.setBounds(240, 195, 80, 35);
		

		
		limitToIntegers(phoneNumber2Field);
		limitSize(phoneNumber2Field, 3);
		
		this.add(phoneNumber2Field);
		
		
		phoneNumber3Field = new JTextField(4);

		phoneNumber3Field.setBounds(320, 195, 80, 35);
		
		phoneNumber3Field.setEditable(false);
		limitToIntegers(phoneNumber3Field);
		limitSize(phoneNumber3Field, 4);
		
		this.add(label);
		this.add(phoneNumber3Field);
	
	}
	
	private void initAddressField() {
		JLabel streetLabel = new JLabel("Address");
		streetLabel.setBounds(50, 240, 100, 35);
		streetLabel.setLabelFor(streetAddressField);
		streetLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		streetAddressField = new JTextField(20);
		streetAddressField.setBounds(160, 240, 240, 35);
		
		streetAddressField.setEditable(false);
		limitSize(streetAddressField, 30);
		
		this.add(streetLabel);
		this.add(streetAddressField);
		
		JLabel cityLabel = new JLabel("City");
		cityLabel.setBounds(50, 290, 100, 35);
		cityLabel.setLabelFor(cityField);
		cityLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		cityField = new JTextField(20);
		cityField.setBounds(100, 290, 150, 35);
		cityField.setEditable(false);
		
		
		limitSize(cityField, 30);
		
		this.add(cityLabel);
		this.add(cityField);
		
		JLabel stateLabel = new JLabel("State");
		stateLabel.setBounds(270, 290, 100, 35);
		
		
		stateLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		String[] states = 
			{
				"AL", 
				"AK",
				"AZ", 
				"AR", 
				"CA",
				"CO", 
				"CT", 
				"DC", 
				"DE",
				"FL", 
				"GA", 
				"HI", 
				"ID", 
				"IL",
				"IN",
				"IA", 
				"KS", 
				"KY", 
				"LA", 
				"ME",
				"MD",
				"MA", 
				"MI", 
				"MN", 
				"MS",
				"MO",
				"MT", 
				"NE", 
				"NV",
				"NH",
				"NJ", 
				"NM",
				"NY",
				"NC",
				"ND",
				"OH",
				"OK",
				"OR",
				"PA",
				"PR",
				"RI", 
				"SC",
				"SD",
				"TN",
				"TX",
				"UT",
				"VT",
				"VA",
				"WA", 
				"WV", 
				"WI", 
				"WY"};
		stateField = new JComboBox<>(states);
		stateField.setBounds(320, 290, 60, 35);
		
		stateField.setEnabled(false);
		this.add(stateLabel);
		this.add(stateField);
		
		JLabel postalLabel = new JLabel("Postal Code");
		postalLabel.setBounds(50, 340, 100, 35);
		postalLabel.setLabelFor(postalField);
		postalLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		postalField = new JTextField(20);
		postalField.setBounds(160, 340, 100, 35);
		
		postalField.setEditable(false);
		limitSize(postalField, 5);
		//limitToIntegers(postalField);
		
		this.add(postalLabel);
		this.add(postalField);
	}
	
	private void initPinField(){
		JLabel label = new JLabel("PIN");
		label.setBounds(280, 340, 95, 35);
		label.setLabelFor(pinField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		pinField = new JPasswordField(20);
		pinField.setBounds(320, 340, 100, 35);
		
		
		limitToIntegers(pinField);
		limitSize(pinField, 4);
		
		this.add(label);
		this.add(pinField);
		
	}
	
	/*
	 * CreateView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The CreateView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the CreateView.
	 * 
	 * @param e
	 */
	
	private void initErrorMessageLabel() {
		errorMessageLabel.setBounds(0, 420, 500, 35);
		errorMessageLabel.setFont(new Font("DialogInput", Font.ITALIC, 14));
		errorMessageLabel.setForeground(Color.RED);
		
		this.add(errorMessageLabel);
	}
	
	private void initCancelButton() {
		cancelButton = new JButton("Go Back to Home");
		cancelButton.setBounds(50, 390, 160, 40);
		
		cancelButton.addActionListener(this);
		this.add(cancelButton);
	}
	
	private void initEditButton() {
		
		editButton = new JButton("Edit");
		editButton.setBounds(230, 390, 160, 40);
		editButton.addActionListener(this);
		
		this.add(editButton);
	}
	
	private void initPowerButton() {
		powerButton = new JButton();
		powerButton.setBounds(420, 5, 50, 50);
		powerButton.addActionListener(this);
		
		try {
			Image image = ImageIO.read(new File("images/power-off.png"));
			powerButton.setIcon(new ImageIcon(image));
		} catch (Exception e1) {
			powerButton.setText("OFF");
		}
		
		this.add(powerButton);
	}
	
	public void limitToIntegers(JTextField keyText) {
		keyText.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE))) {
		        getToolkit().beep();
		        e.consume();
		      }
		    }
		  });
	}
	public void limitSize(JTextField keyText, int n) {
		keyText.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (keyText.getText().length() >= n ) // limit textfield to n characters
		            e.consume(); 
		    }  
		});
	}
	
	private void edit(boolean edit) {
		streetAddressField.setEditable(edit);
		cityField.setEditable(edit);
		stateField.setEnabled(edit);
		postalField.setEditable(edit);
		phoneNumber1Field.setEditable(edit);
		phoneNumber2Field.setEditable(edit);
		phoneNumber3Field.setEditable(edit);
		pinField.setEditable(edit);
		pinField.setEchoChar('\u2022');
	}
	
	
	@SuppressWarnings("deprecation")
	private void setValues() {
		
		int pin = Integer.valueOf(pinField.getText());
		if (String.valueOf(pin).length() != 4) {
			throw new InvalidParameterException("Please enter a 4 digit PIN");
		}
		
		String phoneNumber1 = phoneNumber1Field.getText();
		String phoneNumber2 = phoneNumber2Field.getText();
		String phoneNumber3 = phoneNumber3Field.getText();
		long phone = Long.valueOf(phoneNumber1 + phoneNumber2 + phoneNumber3);
		
		if (String.valueOf(phone).length() != 10) {
			throw new InvalidParameterException("Please enter a valid Phone Number");
		}
		
		String streetAddress = streetAddressField.getText();
		String city = cityField.getText();
		String state = stateField.getSelectedItem() + "";
		String postalCode = postalField.getText();
		
		if (postalCode.length() != 5) {
			throw new InvalidParameterException("Please enter a valid Postal Code");
		}
		
		if (streetAddress.length() == 0 || city.length() == 0) {
			throw new InvalidParameterException("Please complete all fields");
		}
		account.getUser().setPin(account.getUser().getPin(), pin);
		account.getUser().setPhone(phone);
		account.getUser().setStreetAddress(streetAddress);
		account.getUser().setCity(city);
		account.getUser().setState(state);
		account.getUser().setZip(postalCode);
		
		manager.getDatabase().updateAccount(account);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.equals(editButton)) {
			try {
				if (editButton.getText().equals("Edit")) {
					editButton.setText("Save");
					cancelButton.setText("Cancel");
					edit(true);
					pinField.setEchoChar((char) 0);
					updateErrorMessage("");
				}
				else if (editButton.getText().equals("Save")) {
					setValues();
					editButton.setText("Edit");
					cancelButton.setText("Go Back to Home");
					edit(false);
					updateErrorMessage("");
				}
				else {
					throw new InvalidParameterException("Something went wrong. Please try again.");
				}
			}
			catch (NumberFormatException e2){
				updateErrorMessage("Please complete all fields");
			}
			catch(InvalidParameterException e3) {
				updateErrorMessage(e3.getMessage());
			}
			catch(NullPointerException e4) {
				updateErrorMessage("Null Pointer");
			}
		} else if (source.equals(cancelButton)) {
			if (cancelButton.getText().equals("Cancel")) {
				cancelButton.setText("Go Back to Home");
				editButton.setText("Edit");
				initTextFields();
				edit(false);
				updateErrorMessage("");
			}
			else if (cancelButton.getText().equals("Go Back to Home")) {
				manager.switchTo(ATM.HOME_VIEW);
				this.removeAll();
				this.initialize();
				edit(true);
				initTextFields();
				updateErrorMessage("");
			}
			else {
				updateErrorMessage("Something went wrong.");
			}
			
			
		} else if (source.equals(powerButton)) {
			manager.shutdown();
		} else {
			System.err.println("ERROR: Action command not found (" + e.getActionCommand() + ")");
		}
		
	}
	
}