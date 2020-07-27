package com.assignment.trade.scheduler;

import com.assignment.trade.model.Trade;
import com.assignment.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduledConfiguration {

    @Autowired
    TradeService tradeService;

    //Runs at 12 PM every day
    @Scheduled(cron = "0 0 12 * * ?")
    public void updateTradeExpiry() {
        final List<Trade> tradeList = tradeService.getAllTradeByExpiryDate(new Date());

        tradeList.stream().forEach(
            trade ->
            {
                trade.setExpired("Y");
                tradeService.createNewTrade(trade);
            }
        );
        System.out.println("Running Scheduler to update expiry date of trade");
    }
}