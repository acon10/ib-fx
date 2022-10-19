package com.example.ib.repository;

import com.example.ib.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Price findFirstByInstrumentNameOrderByAskDesc(String instrumentName);

    Price findFirstByInstrumentNameOrderByBidAsc(String instrumentName);
}
