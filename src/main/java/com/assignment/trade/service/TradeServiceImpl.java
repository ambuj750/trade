package com.assignment.trade.service;

import com.assignment.trade.repository.TradeRepository;
import com.assignment.trade.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public Trade createNewTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Override
    public List<Trade> getTrades() {
        return tradeRepository.findAll();
    }

    @Override
    public Optional<Trade> getTradeById(String tradeId) {
        return tradeRepository.findById(tradeId);
    }

    @Override
    public List<Trade> getAllTradeByExpiryDate(Date todayDate) {
        return tradeRepository.findAllByExpiryDate(todayDate);
    }

}
