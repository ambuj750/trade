package com.assignment.trade.service;


import com.assignment.trade.model.TradeVersionMapping;

import java.util.List;
import java.util.Optional;

public interface TradeVersionMappingService {

    TradeVersionMapping createNewTradeVersionMapping(TradeVersionMapping tradeVersionMapping);

    List<TradeVersionMapping> getTradeVersionMappings();

    Optional<TradeVersionMapping> getMappingById(String tradeId);

}
