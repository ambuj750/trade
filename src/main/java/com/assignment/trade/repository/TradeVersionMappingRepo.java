package com.assignment.trade.repository;

import com.assignment.trade.model.TradeVersionMapping;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface TradeVersionMappingRepo extends CassandraRepository<TradeVersionMapping, String> {
    @Override
    Optional<TradeVersionMapping> findById(String s);
}
