package training.supportbank;

import java.util.HashMap;
import java.util.UUID;

public class Bank {
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

    public String addAccount(Account newAccount) {
        accounts.put(newAccount.getOwner(), newAccount);
        return newAccount.getOwner();
    }

    public Account getAccount(String owner) {
        return accounts.get(owner);
    }
}
