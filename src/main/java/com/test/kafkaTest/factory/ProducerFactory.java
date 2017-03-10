package com.test.kafkaTest.factory;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import com.test.kafkaTest.util.KafkaUtil;

public class ProducerFactory {

    public static <K, V> Producer<K, V> getProducer() {

        Properties prop = getConfigProp();
        Producer<K, V> producer = new KafkaProducer<K, V>(prop);
        return producer;
    }

    private static Properties getConfigProp() {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaUtil.KAFKA_SERVER_PORT);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }

}
