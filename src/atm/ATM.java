package atm;

import balance_inq.BalanceInquiry;
import bankdata.BankDatabase;
import cashdispence.CashDispenser;
import deposit.Deposit;
import depositslot.DepositSlot;
import keypad.Keypad;
import screen.Screen;
import transaction.Transaction;
import withdrawal.Withdrawal;

public class ATM {
    private boolean userAuthenticated;
    private int currentAccountNumber;
    private Screen screen;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private DepositSlot depositSlot;
    private BankDatabase bankDatabase;

    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int DEPOSIT = 3;
    private static final int EXIT = 4;


    //Add constructor
    public ATM() {
        userAuthenticated = false;
        currentAccountNumber = 0;
        screen = new Screen();
        keypad = new Keypad();
        cashDispenser = new CashDispenser();
        depositSlot = new DepositSlot();
        bankDatabase = new BankDatabase();
    }

    public void run() {
        while (true) {
            while (!userAuthenticated) {
                screen.displayMessageLine("\nWelcome!");
                authenticateUser();
            }

            performTransactions();
            userAuthenticated = false;
            currentAccountNumber = 0;
            screen.displayMessageLine("\nThank you! Goodbye!");
        }
    }

    //Authenticating user
    private void authenticateUser() {
        screen.displayMessage("\nPlease enter your account number: ");
        int accountNumber = keypad.getInput();
        screen.displayMessage("\nEnter your PIN: ");
        int pin = keypad.getInput();

        userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);

        if (userAuthenticated) {
            currentAccountNumber = accountNumber;
        } else {
            screen.displayMessageLine("Invalid account number or PIN. Please try again.");
        }
    }


    //A method to perform transaction
    private void performTransactions() {
        boolean userExited = false;

        while (!userExited) {
            int mainMenuSelection = displayMainMenu();

            //Switching between mainMenuSelection
            switch (mainMenuSelection) {
                case BALANCE_INQUIRY:
                case WITHDRAWAL:
                case DEPOSIT:
                    Transaction currentTransaction = createTransaction(mainMenuSelection);
                    currentTransaction.execute();
                    break;
                case EXIT:
                    screen.displayMessageLine("\nExiting the system...");
                    userExited = true;
                    break;
                default:
                    screen.displayMessageLine("\nInvalid selection. Please try again.");
                    break;
            }
        }
    }

    //A private method to displayMainMenu
    private int displayMainMenu() {
        screen.displayMessageLine("\nMain Menu:");
        screen.displayMessageLine("1 - Balance Inquiry");
        screen.displayMessageLine("2 - Withdrawal");
        screen.displayMessageLine("3 - Deposit");
        screen.displayMessageLine("4 - Exit");
        screen.displayMessage("Enter a choice: ");
        return keypad.getInput();
    }

    private Transaction createTransaction(int type) {
        Transaction temp = null;

        switch (type) {
            case BALANCE_INQUIRY:
                temp = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
                break;
            case WITHDRAWAL:
                temp = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
                break;
            case DEPOSIT:
                temp = new Deposit(currentAccountNumber, screen, bankDatabase, keypad, depositSlot);
                break;
        }

        return temp;
    }

}
