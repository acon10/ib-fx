package com.example.ib.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidPriceResponse {
    private BigDecimal bid;

    @JsonProperty("price")
    public BigDecimal getBid() {
        return bid;
    }
}
