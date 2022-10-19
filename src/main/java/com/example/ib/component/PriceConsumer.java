package com.example.ib.component;

import com.example.ib.service.price.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PriceConsumer implements DataConsumer{
    private final PriceService priceService;

    @RabbitListener(queues = {"${queue.name}"})
    public void onMessage(String data) throws Exception {
        priceService.savePriceData(data);
        log.info("price data is consumed");
    }
}
