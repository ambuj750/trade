package com.assignment.trade.service;

import com.assignment.trade.model.Trade;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TradeService {

    Trade createNewTrade(Trade trade);

    List<Trade> getTrades();

    Optional<Trade> getTradeById(String tradeId);

    List<Trade> getAllTradeByExpiryDate(Date todayDate);
}
