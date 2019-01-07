package controller;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import data.Database;
import model.BankAccount;
import view.ATM;
import view.HomeView;
import view.LoginView;

public class ViewManager {
	
	private Container views;				// the collection of all views in the application
	private Database database;					// a reference to the database

	private BankAccount account;			// the user's bank account
	private BankAccount destination;		// an account to which the user can transfer funds
	private JLabel welcomeLabel;
	
	public static final String NL = System.getProperty("line.separator");  
	// creates new line variable cited from https://stackoverflow.com/questions/20706206/insert-line-break-in-java
	/**
	 * Constructs an instance (or object) of the ViewManager class.
	 * 
	 * @param layout
	 * @param container
	 */
	
	public ViewManager(Container views) {
		this.views = views;
		this.database = new Database();
	}
	
	///////////////////// INSTANCE METHODS ////////////////////////////////////////////
	
	/**
	 * Routes a login request from the LoginView to the Database.
	 * 
	 * @param accountNumber
	 * @param pin
	 */
	public void login(String accountNumber, char[] pin) {
		LoginView lv = (LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX];
		HomeView hv = (HomeView) views.getComponents()[ATM.HOME_VIEW_INDEX];
		
		try {
			account = database.getAccount(Long.valueOf(accountNumber), Integer.valueOf(new String(pin)));
			
			if (account == null) {
				lv.updateErrorMessage("Invalid account number and/or PIN.");
			} else {
				switchTo(ATM.HOME_VIEW);
				//System.out.println(account.getAccountNumber());
				//hv.initWelcomeLabel();
			}
		} catch (NumberFormatException e) {
			lv.updateErrorMessage("Account numbers and PINs don't have letters.");
		}
	}
	
	/*public void login(String accountNumber, char[] pin) {
		String userPin = new String(pin);
		
		if (accountNumber != null && userPin != null && accountNumber.length() > 0 && userPin.length() > 0) {
			setAccount(db.getAccount(Long.valueOf(accountNumber), Integer.valueOf(new String(pin))));
			
			if (getAccount() == null) {
				LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
				lv.updateErrorMessage("Invalid account number and/or PIN.");
			} else {
				HomeView hv = ((HomeView) views.getComponents()[ATM.HOME_VIEW_INDEX]);
				switchTo(ATM.HOME_VIEW);
				hv.initWelcomeLabel();
				LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
				lv.updateErrorMessage("");
			}
		}
	}*/
	
	/*public void createAccount(){
		try {			
			JOptionPane.showConfirmDialog(
				views,
				"Successfully Created Account! Your account number is be " + (db.getMaxAccountNumber() + 1) + "."
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Switches the active (or visible) view upon request.
	 * 
	 * @param view
	 */
	
	public void insertAccount() {
		database.insertAccount(account);
	}
	
	public void switchTo(String view) {
		((CardLayout) views.getLayout()).show(views, view);
	}
	
	/**
	 * Routes a shutdown request to the database before exiting the application. This
	 * allows the database to clean up any open resources it used.
	 */
	
	public void logout() {
		try {			
			int choice = JOptionPane.showConfirmDialog(
				views,
				"Are you sure?",
				"Log Out of ATM",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			
			if (choice == 0) {
				this.setAccount(null);
				this.setDestination(null);
				switchTo(ATM.LOGIN_VIEW);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		try {			
			int choice = JOptionPane.showConfirmDialog(
				views,
				"Are you sure?",
				"Shutdown ATM",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			
			if (choice == 0) {
				database.shutdown();
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//public String welcome() {
		/*String welcome =  "Welcome, " + " " + account.getAccountNumber() + 
				NL + "Account Number: " + NL + "Balance: ";
		return welcome;*/
		//return null;
	//}
	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}

	public BankAccount getDestination() {
		return destination;
	}

	public void setDestination(BankAccount destination) {
		this.destination = destination;
	}
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}
}