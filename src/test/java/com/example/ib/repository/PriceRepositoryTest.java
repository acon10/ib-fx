package com.example.ib.repository;

import com.example.ib.entity.Price;
import com.example.ib.service.price.PriceService;
import com.example.ib.service.price.impl.PriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PriceRepositoryTest {
    @Autowired
    private PriceRepository priceRepository;

    private PriceService priceService;

    @BeforeEach
    void setUp() throws Exception {
        priceService = new PriceServiceImpl(priceRepository, null);
        priceService.savePriceData("106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001");
        priceService.savePriceData("107,EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002");
        priceService.savePriceData("108,GBP/USD,1.2500,1.2560,01-06-2020 12:01:02:002");
        priceService.savePriceData("109,GBP/USD,1.2499,1.2561,01-06-2020 12:01:02:100");
        priceService.savePriceData("110,EUR/JPY,119.61,119.91,01-06-2020 12:01:02:110");
    }

    @Test
    void findFirstByInstrumentNameOrderByAskDesc() {
        //given
        String instrument = "EUR/JPY";

        //when
        Price price = priceRepository.findFirstByInstrumentNameOrderByAskDesc(instrument);

        //then
        assertEquals(price.getAsk(), BigDecimal.valueOf(131.9));
    }

    @Test
    void findFirstByInstrumentNameOrderByBidAsc() {
        //given
        String instrument = "EUR/JPY";

        //when
        Price price = priceRepository.findFirstByInstrumentNameOrderByBidAsc(instrument);

        //then
        assertEquals(price.getBid(), BigDecimal.valueOf(107.64));
    }
}