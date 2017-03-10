package com.test.kafkaTest.service.consumer;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import com.test.kafkaTest.biz.SkuRemainingNumHandler;

public class OrderConsumerHandler implements Runnable {

    
    private KafkaStream<byte[], byte[]> stream;
    
    public OrderConsumerHandler( KafkaStream<byte[], byte[]> stream) {
        setStream(stream);
    }
    

    public KafkaStream<byte[], byte[]> getStream() {
        return stream;
    }

    public void setStream(KafkaStream<byte[], byte[]> stream) {
        this.stream = stream;
    }

    
    @Override
    public void run() {
        
        if(stream == null){
            return;
        }
        
        ConsumerIterator<byte[], byte[]> ite =  stream.iterator();
        while(ite.hasNext()){
            MessageAndMetadata<byte[], byte[]> msgAndMetadata = ite.next();
            byte[] keyBytes = msgAndMetadata.key();
            SkuRemainingNumHandler.competeSku(String.valueOf(keyBytes));
        }
    }
    
}
