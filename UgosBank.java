import java.util.Scanner;

public class UgosBank{
	// ENCAPSULATION OF BALANCE SO EXTERNAL CODE CANNOT TAMPER WITH IT DIRECTLY
	private String accountHolder;
	private double balance;
	private int securityPin;
	private int failedAttempts;
	private boolean isLocked;
	
	//CONSTRUCTOR THAT INITIALIZES THE BANK ACCOUNT WITH A NAME AND A STARTING BALANCE
	public UgosBank(String name, double initialBalance, int pin){
		this.accountHolder = name;
		this.failedAttempts = 0;
		this.isLocked = false;

		//I MADE SURE THE STARTING BALANCE CANNOT BE NEGATIVE
		if (initialBalance > 0){
			this.balance = initialBalance;
		} else {
			this.balance = 0.0;
		}

		//Structural validation to ensure PIN is exactly 4 digits
		if (pin >= 1000 && pin <= 9999){
			this.securityPin = pin;
		} else {
			this.securityPin = 1234;  //System default fallback
			System.out.println("Warning: Invalid PIN format. Default set to 1234.");
		}
	}
	
	
	//THE SECURITY GATE (AUTHENTICATION STAGE)
	//CHECKS ENTERED PIN AGAINST HIDDEN VARIABLE AND TRACKS SECURITY STATE
	public boolean verifyPin(int enteredPin){
		if (isLocked){
			System.out.println("Account Blocked: Please contact system Administrator.");
			return false;
		}

		if (enteredPin == this.securityPin){
			failedAttempts = 0; //Reset counter on successful loginy
			return true;
		} else {
			failedAttempts++;
			System.out.println("Access Denied: Incorrect PIN.");
			System.out.println("Attempt remaining: " + (3 - failedAttempts));

			if(failedAttempts >= 3){
				isLocked = true;
				System.out.println("SECURITY ALERT: Account locked due to too many failed attempts.");
			}
			return false;
		}
	}

	//SECURE DEPOSIT THAT CHECKS IF INPUT DATA IS VALID BEFORE ADDING MONEY
	public void deposit(double amount){
		if(amount > 0){
			balance += amount;
			System.out.println("Successfully deposited ₦" + amount);
		} else {
			System.out.println("Error: Deposit amount must be greater than zero.");
		}
	}


	//SECURE WITHDRAWAL THAT VALIDATES DATA AND PREVENTS OVERDRAWING FUNDS
	public void withdraw(double amount){
		if(amount <= 0){
			System.out.println("Error: Withdrawal amount must be greater than zero.");
		} else if(amount > balance) {
			System.out.println("Transaction Denied: Insufficient funds available.");
		} else {
			balance -=  amount;
			System.out.println("Successfully withdrew ₦" + amount);
		}
	}
	
	//GETTER METHOD: SAFE CHANNEL TO CHECK THE HIDDEN BALANCE VALUE
	public double getBalance(){
		return balance;
	}
	
	public String getAccountHolder(){
		return accountHolder;
	}

	public boolean checkLockStatus(){
		return isLocked;
	}

	//THE MAIN INTERACTIVE INTERFACE LOOP CODE
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);

		System.out.println("=== Welcome to Ugochukwu's Banking Simulation ===");
		System.out.print("Create Account Name: ");
		String name = scanner.nextLine();

		System.out.print("Set 4-Digit Security PIN: ");
		int userPin = scanner.nextInt();

		//I INITIALIZED ACCOUNT WITH A STARTING DEPOSIT VALUE.
		UgosBank myAccount = new UgosBank(name, 10000.00, userPin);
		System.out.println("\nAccount created for " + myAccount.getAccountHolder() + " with ₦10,000.00 base balance.");
	
		boolean running = true;
		while (running) {
			//INSTANT TERMINATION CHECK IF BRUTE-FORCE ATTACK IS DETECTED
			if (myAccount.checkLockStatus()){
				System.out.println("\n System forced logout. Account terminal terminated.");
				break;
			}

			System.out.println("\n--- MAIN MENU ---");
			System.out.println("1. Check Balance");
			System.out.println("2. Deposit Funds");
			System.out.println("3. Withdraw Funds");
			System.out.println("4. Exit Terminal");
			System.out.print("Select an option (1-4): ");

			int choice = scanner.nextInt();

			switch (choice){
				case 1:
					System.out.print("Verify Security PIN: ");
					int pinCheck1 = scanner.nextInt();
					if (myAccount.verifyPin(pinCheck1)){
						System.out.print("Current Balance: ₦" + myAccount.getBalance());
					}
					break;
	
				case 2:
					System.out.print("Verify Security PIN: ");
					int pinCheck2 = scanner.nextInt();
					if (myAccount.verifyPin(pinCheck2)){
						System.out.print("Enter cash deposit value: ₦");
						double depAmount = scanner.nextDouble();
						myAccount.deposit(depAmount);
					}
					break;


				case 3:
					System.out.print("Verify Security PIN: ");
					int pinCheck3 = scanner.nextInt();
					if (myAccount.verifyPin(pinCheck3)){
						System.out.print("Enter withdrawal value: ₦");
						double withAmount = scanner.nextDouble();
						myAccount.withdraw(withAmount);
					}
					break;


				case 4:
					System.out.println("Closing secure gateway interface. Session destroyed clean.");
					running = false;
					break;
	
				default:
					System.out.println("Processing Exception: Invalid cluster choice.");

			}
		}
		scanner.close();
	}
}