package training.supportbank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Your code here!
        System.out.println("Test!");

        Bank supportBank = new Bank("SupportBank");
    }

    public void readTransactions(Bank currentBank) {
        final String COMMA_DELIMITER = ",";
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("Transactions2014.csv"));

            String line = "";
            br.readLine();

            while((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(COMMA_DELIMITER);

                if(transactionDetails.length > 0) {
                    // Save transaction details by creating new accounts and transactions

                    Boolean senderExists = currentBank.accountExists(transactionDetails[1]);
                    Boolean receiverExists = currentBank.accountExists(transactionDetails[2]);

                    Transaction newTransaction;

                    if(senderExists && receiverExists) {
                        Date tDate = new SimpleDateFormat("dd/MM/yyyy").parse(transactionDetails[0]);
                        String tFrom = transactionDetails[1];
                        String tTo = transactionDetails[2];
                        String reason = transactionDetails[3];
                        BigDecimal amount = new BigDecimal(transactionDetails[4]);

                        newTransaction = new Transaction(tDate, tFrom, tTo, reason, amount);
                    } else if (senderExists && !receiverExists) {
                        Account receiverAccount = new Account(transactionDetails[2]);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
