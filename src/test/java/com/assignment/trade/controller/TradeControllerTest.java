package com.assignment.trade.controller;


import com.assignment.trade.exception.LowerVersionTradeException;
import com.assignment.trade.exception.MaturityException;
import com.assignment.trade.model.Trade;
import com.assignment.trade.model.TradeVersionMapping;
import com.assignment.trade.service.TradeService;
import com.assignment.trade.service.TradeVersionMappingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TradeControllerTest {
    @Mock
    TradeService tradeService;

    @Mock
    TradeVersionMappingService tradeVersionMappingService;

    @InjectMocks
    private TradeController tradeController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldStoreTradeSuccessfullyIfNotStored() {

        final LocalDateTime maturityDate = LocalDateTime.of(LocalDate.of(2021, 05, 01), LocalTime.MIN);
        final LocalDateTime createdDate = LocalDateTime.of(LocalDate.of(2020, 07, 26), LocalTime.MIN);

        final Trade trade = new Trade(
            "T1",
            1,
            "CP-1",
            "B1",
            maturityDate,
            createdDate,
            "N"
        );

        when(tradeVersionMappingService.getMappingById(any())).thenReturn(Optional.empty());

        final ResponseEntity<Trade> response = tradeController.createNewTrade(trade);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test(expected = MaturityException.class)
    public void shouldThrowExceptionOnLessMaturityDate() {
        final LocalDateTime maturityDate = LocalDateTime.of(LocalDate.of(2020, 05, 01), LocalTime.MIN);
        final LocalDateTime createdDate = LocalDateTime.of(LocalDate.of(2020, 04, 01), LocalTime.MIN);

        final Trade trade = new Trade(
            "T1",
            1,
            "CP-1",
            "B1",
            maturityDate,
            createdDate,
            "N"
        );

        tradeController.createNewTrade(trade);
    }

    @Test(expected = LowerVersionTradeException.class)
    public void shouldThrowExceptionOnReceivingLowerTradeVersion() {

        final LocalDateTime maturityDate = LocalDateTime.of(LocalDate.of(2021, 05, 01), LocalTime.MIN);
        final LocalDateTime createdDate = LocalDateTime.of(LocalDate.of(2020, 04, 01), LocalTime.MIN);


        final Trade trade = new Trade(
            "T2",
            2,
            "CP-1",
            "B1",
            maturityDate,
            createdDate,
            "N"
        );

        final TradeVersionMapping tradeVersionMapping = new TradeVersionMapping("T2", 3);

        when(tradeVersionMappingService.getMappingById(any())).thenReturn(Optional.of(tradeVersionMapping));

        tradeController.createNewTrade(trade);
    }

    @Test
    public void shouldOverrideTradeForSameVersion() {

        final LocalDateTime maturityDate = LocalDateTime.of(LocalDate.of(2021, 05, 01), LocalTime.MIN);
        final LocalDateTime createdDate = LocalDateTime.of(LocalDate.of(2020, 07, 26), LocalTime.MIN);


        final Trade trade = new Trade(
            "T2",
            3,
            "CP-6",
            "B1",
            maturityDate,
            createdDate,
            "N"
        );

        final Trade existingTrade = new Trade(
            "T2",
            3,
            "CP-1",
            "B0",
            maturityDate,
            createdDate,
            "N"
        );

        final TradeVersionMapping tradeVersionMapping = new TradeVersionMapping("T2", 3);

        when(tradeVersionMappingService.getMappingById(any())).thenReturn(Optional.of(tradeVersionMapping));
        when(tradeService.getTradeById(any())).thenReturn(Optional.of(existingTrade));

        final ResponseEntity<Trade> response = tradeController.createNewTrade(trade);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
