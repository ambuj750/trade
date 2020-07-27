package com.assignment.trade.repository;


import com.assignment.trade.model.Trade;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TradeRepository extends CassandraRepository<Trade, String> {

    @Override
    Optional<Trade> findById(String s);

    @Query("select * from trade where maturity_date < ?0 ALLOW FILTERING")
    List<Trade> findAllByExpiryDate(Date todayDate);
}
