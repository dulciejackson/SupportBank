package training.supportbank;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private Date transactionDate;
    private UUID tFrom;
    private UUID tTo;
    private String narrative;
    private BigDecimal amount;

    public Transaction(Date tDate, UUID sender, UUID receiver, String reason, BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.transactionDate = tDate;
        this.tFrom = sender;
        this.tTo = receiver;
        this.narrative = reason;
        this.amount = amount;
    }
}
