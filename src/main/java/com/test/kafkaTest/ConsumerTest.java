package com.test.kafkaTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.test.kafkaTest.util.KafkaUtil;

public class ConsumerTest {

    public static void main(String[] args) {
        //        Properties props = new Properties();
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("bootstrap.servers", "192.168.158.204:9092");
        props.put("group.id", KafkaUtil.CONSUMER_GROUP_ID);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(KafkaUtil.TOPIC));
        System.out.println("Subscribed to topic " + KafkaUtil.TOPIC);

        while (true) {
            try {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {

                    System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(),
                        record.key(), record.value());
                }
            } catch (Exception e) {
                if (consumer != null) {
                    consumer.close();
                }
                break;
            }
        }

    }
}
