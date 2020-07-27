package com.assignment.trade.service;

import com.assignment.trade.repository.TradeVersionMappingRepo;
import com.assignment.trade.model.TradeVersionMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeVersionMappingServiceImpl implements TradeVersionMappingService {
    @Autowired
    private TradeVersionMappingRepo tradeVersionMappingRepo;

    @Override
    public TradeVersionMapping createNewTradeVersionMapping(TradeVersionMapping tradeVersionMapping) {
        return tradeVersionMappingRepo.save(tradeVersionMapping);
    }

    @Override
    public List<TradeVersionMapping> getTradeVersionMappings() {
        return tradeVersionMappingRepo.findAll();
    }

    @Override
    public Optional<TradeVersionMapping> getMappingById(String tradeId) {
        return tradeVersionMappingRepo.findById(tradeId);
    }
}
