package com.example.ib.service.price;

import com.example.ib.api.AskPriceResponse;
import com.example.ib.api.BidPriceResponse;

public interface PriceService {
    void savePriceData(String data) throws Exception;
    AskPriceResponse getMaxPriceByInstrumentName(String instrumentName);

    BidPriceResponse getMinPriceByInstrumentName(String instrument);
}
