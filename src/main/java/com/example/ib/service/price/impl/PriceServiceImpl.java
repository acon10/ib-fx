package com.example.ib.service.price.impl;

import com.example.ib.api.AskPriceResponse;
import com.example.ib.api.BidPriceResponse;
import com.example.ib.entity.Price;
import com.example.ib.exception.PriceCsvValidationException;
import com.example.ib.exception.PriceFormatException;
import com.example.ib.repository.PriceRepository;
import com.example.ib.service.price.PriceService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final ModelMapper modelMapper;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");


    @Value("${price.commission.rate}")
    private String commissionRate;


    @Override
    public void savePriceData(String data) throws Exception{
        try (CSVReader reader = new CSVReader(new StringReader(data))) {
            String[] priceDataStr = reader.readNext();
            BigDecimal bid = new BigDecimal(priceDataStr[2]);
            bid = bid.subtract(bid.multiply(new BigDecimal(commissionRate)));
            BigDecimal ask = new BigDecimal(priceDataStr[3]);
            ask = ask.add(ask.multiply(new BigDecimal(commissionRate)));
            Price price = Price.builder()
                    .id(Long.parseLong(priceDataStr[0]))
                    .instrumentName(priceDataStr[1])
                    .bid(bid)
                    .ask(ask)
                    .timestamp(LocalDateTime.parse(priceDataStr[4], formatter))
                    .build();
            priceRepository.save(price);
            log.info("Price is saved to DB");
        } catch (IOException e) {
            throw new PriceFormatException(e.getMessage());
        } catch (CsvValidationException e) {
            throw new PriceCsvValidationException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new PriceCsvValidationException("price csv value is wrong");
        }
    }

    @Override
    public AskPriceResponse getMaxPriceByInstrumentName(String instrumentName) {
        Price price = priceRepository.findFirstByInstrumentNameOrderByAskDesc(instrumentName);
        return modelMapper.map(price, AskPriceResponse.class);
    }

    @Override
    public BidPriceResponse getMinPriceByInstrumentName(String instrumentName) {
        Price price = priceRepository.findFirstByInstrumentNameOrderByBidAsc(instrumentName);
        return modelMapper.map(price, BidPriceResponse.class);
    }
}
