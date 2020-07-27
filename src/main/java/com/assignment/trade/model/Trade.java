package com.assignment.trade.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("trade")
public class Trade {

    @PrimaryKey("trade_id")
    private String tradeId;

    @Column("version")
    private int version;

    @Column("counter_party_id")
    private String counterPartyId;

    @Column("book_id")
    private String bookId;

    @Column("maturity_date")
    private LocalDateTime maturityDate;

    @Column("created_date")
    private LocalDateTime createdDate;

    @Column("expired")
    private String expired;

    public Trade(
        String tradeId,
        int version,
        String counterPartyId,
        String bookId,
        LocalDateTime maturityDate,
        LocalDateTime createdDate,
        String expired
    ) {
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.expired = expired;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public LocalDateTime getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDateTime maturityDate) {
        this.maturityDate = maturityDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "Trade{" +
            "trade_Id='" + tradeId + '\'' +
            ", version='" + version + '\'' +
            ", counterPartyId='" + counterPartyId + '\'' +
            ", bookId='" + bookId + '\'' +
            ", maturityDate=" + maturityDate +
            ", createdDate=" + createdDate +
            ", expired='" + expired + '\'' +
            '}';
    }
}
