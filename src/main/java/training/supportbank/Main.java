package training.supportbank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final Bank supportBank = new Bank("SupportBank");

        System.out.println("Welcome to " + supportBank.getName());

        readTransactions(supportBank, "Transactions2014.csv");
        readTransactions(supportBank, "DodgyTransactions2015.csv");

        commandsFromUser(supportBank);
    }

    private static void readTransactions(Bank currentBank, String fileName) {
        final String COMMA_DELIMITER = ",";
        BufferedReader br = null;

        System.out.println("Attempting to read transactions from csv file...");

        try {
            br = new BufferedReader(new FileReader(fileName));

            String line = "";
            br.readLine();

            while((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(COMMA_DELIMITER);

                if(transactionDetails.length > 0) {
                    // Save transaction details by creating new accounts and transactions
                    addTransaction(currentBank, transactionDetails);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Transactions read");
    }

    private static void addTransaction(Bank currentBank, String[] transactionDetails) {
        Account sender;
        Account receiver;

        sender = checkAccounts(currentBank, transactionDetails[1]);
        receiver = checkAccounts(currentBank, transactionDetails[2]);

        try {
            Transaction newTransaction;
            Date tDate = new SimpleDateFormat("dd/MM/yyyy").parse(transactionDetails[0]);
            String tFrom = transactionDetails[1];
            String tTo = transactionDetails[2];
            String reason = transactionDetails[3];
            BigDecimal amount = new BigDecimal(transactionDetails[4]);

            newTransaction = new Transaction(tDate, tFrom, tTo, reason, amount);
            sender.pay(newTransaction);
            receiver.earn(newTransaction);

        } catch (ParseException e) {
            System.out.println("Unable to add transaction");
            e.printStackTrace();
        }
    }

    private static Account checkAccounts(Bank currentBank, String accountID) {
        if(!currentBank.accountExists(accountID)) {
            Account newAcc = new Account(accountID);
            currentBank.addAccount(newAcc);
            return newAcc;
        } else {
            return currentBank.getAccount(accountID);
        }
    }

    private static void commandsFromUser(Bank supportBank) {
        Scanner scanner = new Scanner(System.in);
        String command = "";

        System.out.println("Enter a command to search the bank records or 'quit' to exit");
        command = scanner.nextLine();
        String[] commandParts = command.split(" ", 2);

        while(!command.equals("quit")) {

            if(command.equals("help")) {
                System.out.println("Available commands:");
                System.out.println("List All - this will output the names of every person, and the total amount they owe/are owed");
                System.out.println("List [Account] - this will print a list of every transaction for the account with the name requested");
            } else if(command.equals("List All")) {
                supportBank.listAccountBalances();
            } else if(commandParts[0].equals("List") && commandParts.length == 2) {
                System.out.println(commandParts[1]);
                try {
                    Account searched = supportBank.getAccount(commandParts[1]);
                    searched.getTransactions();
                } catch (Exception e) {
                    System.out.println("Account not found");
                }
            }

            System.out.println();
            System.out.println("Enter a command to search the bank records or 'quit' to exit");
            command = scanner.nextLine();
            commandParts = command.split(" ", 2);

        }
    }
}
