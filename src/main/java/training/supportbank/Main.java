package training.supportbank;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.GsonBuilder;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        final Bank supportBank = new Bank("SupportBank");
        LOGGER.info("Bank created");

        System.out.println("Welcome to " + supportBank.getName());

        readCSV(supportBank, "Transactions2014.csv");
        readCSV(supportBank, "DodgyTransactions2015.csv");

        commandsFromUser(supportBank);
    }

    private static void readCSV(Bank currentBank, String fileName) {
        final String COMMA_DELIMITER = ",";
        BufferedReader br = null;
        int errorCount = 0;

        System.out.println("Attempting to read transactions from csv file...");

        try {
            br = new BufferedReader(new FileReader(fileName));

            String line = "";
            br.readLine();

            while((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(COMMA_DELIMITER);

                if(transactionDetails.length > 0) {
                    // Save transaction details by creating new accounts and transactions
                    errorCount = addTransaction(currentBank, transactionDetails, errorCount);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Failed with exception - unable to read transactions from " + fileName);
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                LOGGER.error("Unable to close reader for " + fileName);
            }
        }

        LOGGER.info("Transactions from " + fileName + " imported successfully. " + errorCount + " lines omitted due to incorrect formatting.");
        System.out.println("Transactions read");
    }

    private static void readJSON(Bank currentBank, String fileName) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (jsonElement, type, jsonDeserializationContext) ->
                LocalDate.parse(jsonElement.getAsString()));
        Gson gson = gsonBuilder.create();
    }

    private static int addTransaction(Bank currentBank, String[] transactionDetails, int invalidCount) {
        Account sender;
        Account receiver;

        sender = checkAccounts(currentBank, transactionDetails[1]);
        receiver = checkAccounts(currentBank, transactionDetails[2]);

        try {
            Transaction newTransaction;
            LocalDate tDate = LocalDate.parse(transactionDetails[0]);
            String tFrom = transactionDetails[1];
            String tTo = transactionDetails[2];
            String reason = transactionDetails[3];
            BigDecimal amount = new BigDecimal(transactionDetails[4]);

            newTransaction = new Transaction(tDate, tFrom, tTo, reason, amount);
            sender.pay(newTransaction);
            receiver.earn(newTransaction);

        } catch (DateTimeParseException e) {
            System.out.println("Unable to add transaction between " + transactionDetails[1] + " and " +
                    transactionDetails[2] + " due to invalid value type: column 1 of the .csv file must contain valid dates");
            LOGGER.error("Unable to create transaction for details: " + Arrays.toString(transactionDetails));
            invalidCount++;
        } catch (NumberFormatException e) {
            System.out.println("Unable to add transaction between " + transactionDetails[1] + " and " +
                    transactionDetails[2] + " due to invalid value type: column 5 of the .csv file must contain decimal values");
            invalidCount++;
        }

        return invalidCount;

    }

    private static Account checkAccounts(Bank currentBank, String accountID) {
        if(!currentBank.accountExists(accountID)) {
            Account newAcc = new Account(accountID);
            currentBank.addAccount(newAcc);
            LOGGER.info("Account created with accountID " + accountID);
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
        LOGGER.info("Line from terminal read in successfully. Line reads: " + command);
        String[] commandParts = command.split(" ", 2);

        while(!command.equals("quit")) {

            if(command.equals("help")) {
                LOGGER.info("User input matched with 'help'");
                System.out.println("Available commands:");
                System.out.println("List All - this will output the names of every person, and the total amount they owe/are owed");
                System.out.println("List [Account] - this will print a list of every transaction for the account with the name requested");
            } else if(command.equals("List All")) {
                LOGGER.info("User input matched with 'List All'");
                supportBank.listAccountBalances();
            } else if(commandParts[0].equals("List") && commandParts.length == 2) {
                LOGGER.info("User input matched with List [Account]. Attempting to find account...");
                System.out.println(commandParts[1]);
                try {
                    Account searched = supportBank.getAccount(commandParts[1]);
                    searched.getTransactions();
                    LOGGER.info("Account found");
                } catch (Exception e) {
                    System.out.println("Account not found");
                    LOGGER.error("Account not found");
                }
            }

            System.out.println();
            System.out.println("Enter a command to search the bank records or 'quit' to exit");
            command = scanner.nextLine();
            commandParts = command.split(" ", 2);

        }
    }
}
