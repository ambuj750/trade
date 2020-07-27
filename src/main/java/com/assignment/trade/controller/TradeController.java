package com.assignment.trade.controller;

import com.assignment.trade.exception.LowerVersionTradeException;
import com.assignment.trade.exception.MaturityException;
import com.assignment.trade.service.TradeService;
import com.assignment.trade.service.TradeVersionMappingService;
import com.assignment.trade.model.Trade;
import com.assignment.trade.model.TradeVersionMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TradeController {
    @Autowired
    TradeService tradeService;
    @Autowired
    TradeVersionMappingService tradeVersionMappingService;

    //Endpoint to get details of all trades
    @GetMapping("trades")
    public ResponseEntity<List<Trade>> getAllTrades() {

        final List<Trade> trades = tradeService.getTrades();
        return new ResponseEntity<>(trades, HttpStatus.OK);
    }

    //Endpoint to get details of all tradeId and their latest version
    @GetMapping("tradeVersionMapping")
    public ResponseEntity<List<TradeVersionMapping>> getAllTradesVersionMap() {

        final List<TradeVersionMapping> tradeVersionMappings = tradeVersionMappingService.getTradeVersionMappings();
        return new ResponseEntity<>(tradeVersionMappings, HttpStatus.OK);
    }

    //Endpoint to save trade into store
    @PostMapping("trade")
    public ResponseEntity<Trade> createNewTrade(@RequestBody final Trade trade) {

        final LocalDateTime todayDate = LocalDateTime.now();

        if (trade.getMaturityDate().compareTo(todayDate) < 0) {
            throw new MaturityException("Less maturity date than today date not allowed");
        }

        final Optional<TradeVersionMapping> tradeVersionMappingOptional = tradeVersionMappingService.getMappingById(
            trade.getTradeId());

        //If trade is not available into store
        if (tradeVersionMappingOptional.equals(Optional.empty())) {
            tradeVersionMappingService.createNewTradeVersionMapping(new TradeVersionMapping(
                trade.getTradeId(),
                trade.getVersion()
            ));
            return new ResponseEntity<>(tradeService.createNewTrade(trade), HttpStatus.OK);

            //If Current version of trade is lower than existing one.
        } else if (trade.getVersion() < tradeVersionMappingOptional.get().getLatestVersion()) {
            throw new LowerVersionTradeException("Lower version of trade not allowed");

            //Current version of trade is greater than or equals to existing version
        } else {
            updateTradeVersionMapping(trade);
            final Trade updatedTrade = updateTrade(trade);
            return new ResponseEntity<>(updatedTrade, HttpStatus.OK);
        }
    }

    private Trade updateTrade(Trade trade) {
        Trade existingTrade = tradeService.getTradeById(trade.getTradeId()).get();
        existingTrade.setVersion(trade.getVersion());
        existingTrade.setBookId(trade.getBookId());
        existingTrade.setCounterPartyId(trade.getCounterPartyId());
        existingTrade.setCreatedDate(trade.getCreatedDate());
        existingTrade.setExpired(trade.getExpired());
        existingTrade.setMaturityDate(trade.getMaturityDate());
        return tradeService.createNewTrade(existingTrade);
    }

    private void updateTradeVersionMapping(Trade trade) {
        TradeVersionMapping existingTVId = tradeVersionMappingService.getMappingById(trade.getTradeId()).get();
        existingTVId.setLatestVersion(trade.getVersion());
        tradeVersionMappingService.createNewTradeVersionMapping(existingTVId);
    }
}
