package com.test.kafkaTest.service.producer;

public interface OrderProducerService {

    /**
     * receive the request.
     * 
     * @param userId 
     */
    void receiveOrder(String userId);
    
}
