package com.assignment.trade.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Table("trade_version_mapping")
public class TradeVersionMapping {

    @PrimaryKey("trade_id")
    private String tradeId;

    @Column("latest_version")
    private int latestVersion;

    public TradeVersionMapping(String tradeId, int latestVersion) {
        this.tradeId = tradeId;
        this.latestVersion = latestVersion;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public int getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(int latestVersion) {
        this.latestVersion = latestVersion;
    }
}
