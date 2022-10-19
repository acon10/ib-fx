package com.example.ib.service.price.impl;

import com.example.ib.api.AskPriceResponse;
import com.example.ib.api.BidPriceResponse;
import com.example.ib.entity.Price;
import com.example.ib.exception.PriceCsvValidationException;
import com.example.ib.repository.PriceRepository;
import com.example.ib.service.price.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class PriceServiceImplTest {

    private PriceRepository priceRepository;

    @BeforeEach
    void setUp(){
        priceRepository = Mockito.mock(PriceRepository.class);

    }

    @Test
    void givenPriceValueShouldBeSaved() throws Exception {
        //given
        PriceService priceService = new PriceServiceImpl(priceRepository, null);
        ReflectionTestUtils.setField(priceService,"commissionRate","0.1");
        String data = "106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001";

        //when
        priceService.savePriceData(data);

        //then
        ArgumentCaptor<Price> priceArgumentCaptor = ArgumentCaptor.forClass(Price.class);
        verify(priceRepository).save(priceArgumentCaptor.capture());
        Price capturedPrice = priceArgumentCaptor.getValue();
        assertEquals(capturedPrice.getId(), 106);
    }

    @Test
    void givenPriceValueShouldBeThrowPriceCsvValidationException() throws Exception {
        //given
        PriceService priceService = new PriceServiceImpl(priceRepository, null);
        ReflectionTestUtils.setField(priceService,"commissionRate","0.1");
        String data = "106EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001";

        //when then
        PriceCsvValidationException exception = assertThrows(PriceCsvValidationException.class, () -> {
            priceService.savePriceData(data);
        });

        String exceptionMessage = "price csv value is wrong";
        assertTrue(exception.getMessage().contains(exceptionMessage));
    }

    @Test
    void givenPriceValueShouldBeThrowPriceCsvValidationExceptionWithWrongQuoting() throws Exception {
        //given
        PriceService priceService = new PriceServiceImpl(priceRepository, null);
        ReflectionTestUtils.setField(priceService,"commissionRate","0.1");
        String data = "'106',EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001";

        //when then
        PriceCsvValidationException exception = assertThrows(PriceCsvValidationException.class, () -> {
            priceService.savePriceData(data);
        });

        String exceptionMessage = "price csv value is wrong";
        assertTrue(exception.getMessage().contains(exceptionMessage));
    }

    @Test
    void givenInstrumentNameShouldReturnMaxPrice() {
        //given
        PriceService priceService = new PriceServiceImpl(priceRepository, new ModelMapper());
        String instrumentName = "EUR/USD";
        Price price = Price.builder().ask(BigDecimal.valueOf(106)).build();
        when(priceRepository.findFirstByInstrumentNameOrderByAskDesc(instrumentName)).thenReturn(price);

        //when
        AskPriceResponse returnResponse = priceService.getMaxPriceByInstrumentName(instrumentName);

        //then
        assertEquals(returnResponse.getAsk(), BigDecimal.valueOf(106));
    }

    @Test
    void givenInstrumentNameShouldReturnMinPrice() {
        //given
        PriceService priceService = new PriceServiceImpl(priceRepository, new ModelMapper());
        String instrumentName = "EUR/USD";
        Price price = Price.builder().bid(BigDecimal.valueOf(106)).build();
        when(priceRepository.findFirstByInstrumentNameOrderByBidAsc(instrumentName)).thenReturn(price);

        //when
        BidPriceResponse returnResponse = priceService.getMinPriceByInstrumentName(instrumentName);

        //then
        assertEquals(returnResponse.getBid(), BigDecimal.valueOf(106));
    }
}