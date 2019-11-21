package training.supportbank;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class Account {
    private String owner;
    private BigDecimal balance;
    private ArrayList<Transaction> accountTransactions;

    public Account(String name) {
        this.owner = name;
        this.balance = new BigDecimal("0.00");
        this.accountTransactions = new ArrayList<Transaction>();
    }

    public String getOwner() {
        return owner;
    }

    public void addTransaction(Transaction newT) {
        accountTransactions.add(newT);
    }
}
