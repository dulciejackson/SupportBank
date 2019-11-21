package training.supportbank;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private Date transactionDate;
    private String tFrom;
    private String tTo;
    private String narrative;
    private BigDecimal amount;

    public Transaction(Date tDate, String sender, String receiver, String reason, BigDecimal amount) {
        this.transactionDate = tDate;
        this.tFrom = sender;
        this.tTo = receiver;
        this.narrative = reason;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionDate=" + transactionDate +
                ", narrative='" + narrative + '\'' +
                ", amount=" + amount +
                '}';
    }
}
