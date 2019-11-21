package training.supportbank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class Account {
    private UUID id;
    private String owner;
    private BigDecimal balance;

    public Account(String name) {
        this.id = UUID.randomUUID();
        this.owner = name;
        this.balance = new BigDecimal("0.00");
    }

    public void addTransaction(Transaction newT) {
    }
}
