package training.supportbank;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {
    private LocalDate date;
    @XmlElement(name="From")
    private String fromAccount;
    @XmlElement(name="To")
    private String toAccount;
    @XmlElement(name="Description")
    private String narrative;
    @XmlElement(name="Value")
    private BigDecimal amount;

    public Transaction() {}

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

    public String getFromAccount() { return fromAccount; }

    public String getToAccount() { return toAccount; }

    public void setNarrative(String desc) {
        narrative = desc;
    }

    public void setAmount(BigDecimal val) {
        amount = val;
    }

    public void setFromAccount(String from) {
        fromAccount = from;
    }

    public void setToAccount(String to) {
        toAccount = to;
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
