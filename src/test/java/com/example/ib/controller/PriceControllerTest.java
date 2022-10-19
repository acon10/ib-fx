package com.example.ib.controller;

import com.example.ib.api.AskPriceResponse;
import com.example.ib.api.BidPriceResponse;
import com.example.ib.component.PriceConsumer;
import com.example.ib.config.RabbitConfig;
import com.example.ib.service.price.PriceService;
import com.example.ib.service.rabbit.RabbitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PriceControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void priceFeed() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/price/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"priceFeed\": \"106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertTrue(contentAsString.equals("Price is fed"));
    }

    @Test
    public void getAskPrice() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/price/ask")
                        .param("instrument","EUR/USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        AskPriceResponse response = objectMapper.readValue(contentAsString, AskPriceResponse.class);
        assertEquals(response.getAsk(), BigDecimal.valueOf(1.32));
    }

    @Test
    public void getBidPrice() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/price/bid")
                        .param("instrument","EUR/USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        BidPriceResponse response = objectMapper.readValue(contentAsString, BidPriceResponse.class);
        assertEquals(response.getBid(), BigDecimal.valueOf(0.99));
    }

}