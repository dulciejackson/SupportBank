package training.supportbank;

import java.util.HashMap;

public class Bank {
    private String name;
    private HashMap<Integer,Account> accounts;

    public Bank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
