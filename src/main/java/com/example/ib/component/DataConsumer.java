package com.example.ib.component;

public interface DataConsumer {
    void onMessage(String message) throws Exception;
}
