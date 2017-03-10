package com.test.kafkaTest.factory;

import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;

import com.test.kafkaTest.util.KafkaUtil;

public class ConsumerFactory {

    public static ConsumerConnector createConsumer(String groupId) {

        ConsumerConfig consumerConfig = createConsumerConfig(KafkaUtil.ZK_SERVER_PORT, groupId);
        return (ConsumerConnector) Consumer.createJavaConsumerConnector(consumerConfig);
    }

    private static ConsumerConfig createConsumerConfig(String zookeeper, String groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeper);
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");

        return new ConsumerConfig(props);
    }
}
