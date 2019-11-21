package training.supportbank;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private LocalDate date;
    private String fromAccount;
    private String toAccount;
    private String narrative;
    private BigDecimal amount;

    public Transaction(LocalDate tDate, String sender, String receiver, String reason, BigDecimal amount) {
        this.date = tDate;
        this.fromAccount = sender;
        this.toAccount = receiver;
        this.narrative = reason;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionDate=" + date +
                ", narrative='" + narrative + '\'' +
                ", amount=" + amount +
                '}';
    }
}
