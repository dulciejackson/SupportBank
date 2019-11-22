package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.UUID;

public class Bank {
    private static final Logger LOGGER = LogManager.getLogger(Bank.class);
    private String name;
    private HashMap<String,Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.accounts = new HashMap<String, Account>();
    }

    public String getName() {
        return name;
    }

    public Boolean accountExists(String name) {
        return accounts.containsKey(name);
    }

    public void addAccount(Account newAccount) {
        accounts.put(newAccount.getOwner(), newAccount);
    }

    public Account getAccount(String owner) {
        return accounts.get(owner);
    }

    public void listAccountBalances() {
        accounts.forEach((key, value) -> System.out.println(value.toString()));
    }

    public Account checkAccounts(Bank currentBank, String accountID) {
        if(!currentBank.accountExists(accountID)) {
            Account newAcc = new Account(accountID);
            currentBank.addAccount(newAcc);
            LOGGER.info("Account created with accountID " + accountID);
            return newAcc;
        } else {
            return currentBank.getAccount(accountID);
        }
    }

}
