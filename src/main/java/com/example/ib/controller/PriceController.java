package com.example.ib.controller;

import com.example.ib.api.AskPriceResponse;
import com.example.ib.api.BidPriceResponse;
import com.example.ib.api.PriceFeedRequest;
import com.example.ib.service.price.PriceService;
import com.example.ib.service.rabbit.RabbitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/price")
@RequiredArgsConstructor
public class PriceController {
    private final RabbitService rabbitService;
    private final PriceService priceService;

    @PostMapping ("/feed")
    public ResponseEntity<String> priceFeed(@RequestBody PriceFeedRequest request){
        return ResponseEntity.ok(rabbitService.createPriceData(request.getPriceFeed()));
    }

    @GetMapping("/ask")
    public ResponseEntity<AskPriceResponse> getAskPrice(@RequestParam String instrument){
        return ResponseEntity.ok(priceService.getMaxPriceByInstrumentName(instrument));
    }

    @GetMapping("/bid")
    public ResponseEntity<BidPriceResponse> getBidPrice(@RequestParam String instrument){
        return ResponseEntity.ok(priceService.getMinPriceByInstrumentName(instrument));
    }

}
