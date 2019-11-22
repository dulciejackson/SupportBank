package training.supportbank;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final Bank supportBank = new Bank("SupportBank");

    public static void main(String[] args) {

        LOGGER.info("Bank created");

        System.out.println("Welcome to " + supportBank.getName());

        CSVReader csv = new CSVReader(supportBank);
        JSONReader json = new JSONReader(supportBank);

        csv.readFile("Transactions2014.csv");
        csv.readFile("DodgyTransactions2015.csv");
        json.readFile("Transactions2013.json");

        commandsFromUser();
    }

    private static void commandsFromUser() throws NullPointerException {
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
            } else if(command.matches("Import File (\\w)+.(csv|json|xml)")) {
                String[] fileInfo = command.split(" ");
                final String fileExt = FilenameUtils.getExtension(fileInfo[2]);
                switch (fileExt) {
                    case "csv":
                        CSVReader csvReader = new CSVReader(supportBank);
                        csvReader.readFile(fileInfo[2]);
                        break;
                    case "json":
                        JSONReader jsonReader = new JSONReader(supportBank);
                        jsonReader.readFile(fileInfo[2]);
                    case "xml":
                        XMLReader xmlReader = new XMLReader(supportBank);
                        xmlReader.readFile(fileInfo[2]);
                }
            }

            System.out.println();
            System.out.println("Enter a command to search the bank records or 'quit' to exit");
            command = scanner.nextLine();
            commandParts = command.split(" ", 2);

        }
    }
}
