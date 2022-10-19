package com.example.ib.service.rabbit.impl;

import com.example.ib.service.rabbit.RabbitService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitServiceImpl implements RabbitService {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public String createPriceData(String data){
        rabbitTemplate.convertAndSend(queue.getName(), data);
        return "Price is fed";
    }
}
