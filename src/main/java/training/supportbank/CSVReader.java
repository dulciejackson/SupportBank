package training.supportbank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class CSVReader implements Reader {
    private Bank currentBank;

    public CSVReader(Bank currentBank) {
        this.currentBank = currentBank;
    }

    @Override
    public void readFile(String fileName) {
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
                    errorCount = addTransaction(transactionDetails, errorCount);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Failed with exception - unable to read transactions from " + fileName);
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                LOGGER.error("Unable to close reader for " + fileName);
            }
        }

        LOGGER.info("Transactions from " + fileName + " imported successfully. " + errorCount + " lines omitted due to incorrect formatting.");
        System.out.println("Transactions read");
    }

    private int addTransaction(String[] transactionDetails, int invalidCount) {
        Account sender;
        Account receiver;

        sender = currentBank.checkAccounts(currentBank, transactionDetails[1]);
        receiver = currentBank.checkAccounts(currentBank, transactionDetails[2]);

        try {
            Transaction newTransaction;

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate tDate = LocalDate.parse(transactionDetails[0], format);
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
}
