package bankdata;

import account.Account;

public class BankDatabase {
    private Account[] accounts; // array of Accounts

    // no-argument BankDatabase constructor initializes accounts
    public BankDatabase() {
        accounts = new Account[4]; // just 2 accounts for testing
        accounts[0] = new Account(10057686, 1976, 1000.0, 1200.0);
        accounts[1] = new Account(10763276, 1995, 200.0, 200.0);
        accounts[2] = new Account(10776796, 1850, 20000.0, 2000.0);
        accounts[3] = new Account(10656327, 1945, 5000.0, 5000.0);
    }

    // retrieve Account object containing specified account number
    private Account getAccount(int accountNumber) {
        // loop through accounts searching for matching account number
        for (Account currentAccount : accounts) {
            // return current account if match found
            if (currentAccount.getAccountNumber() == accountNumber)
                return currentAccount;
        }

        return null; // if no matching account was found, return null
    }

    // determine whether user-specified account number and PIN match
    // those of an account in the database
    public boolean authenticateUser(int userAccountNumber, int userPIN) {
        // attempt to retrieve the account with the account number
        Account userAccount = getAccount(userAccountNumber);

        // if account exists, return the result of Account method validatePIN
        if (userAccount != null)
            return userAccount.validatePIN(userPIN);
        else
            return false; // account number not found, so return false
    }

    // debit an amount from Account with specified account number
    public void debit(int userAccountNumber, double amount) {
        getAccount(userAccountNumber).debit(amount);
    }

    // return available balance of Account with specified account number
    public double getAvailableBalance(int userAccountNumber) {
        return getAccount(userAccountNumber).getAvailableBalance();
    }

    // return total balance of Account with specified account number
    public double getTotalBalance(int userAccountNumber) {
        return getAccount(userAccountNumber).getTotalBalance();
    }

    // credit an amount to Account with specified account number
    public void credit(int userAccountNumber, double amount) {
        getAccount(userAccountNumber).credit(amount);
    }
}
