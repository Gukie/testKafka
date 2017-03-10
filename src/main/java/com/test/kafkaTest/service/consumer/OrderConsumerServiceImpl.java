package com.test.kafkaTest.service.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import com.test.kafkaTest.factory.ConsumerFactory;
import com.test.kafkaTest.util.CollectionUtil;
import com.test.kafkaTest.util.ExecutorUtil;
import com.test.kafkaTest.util.KafkaUtil;

public class OrderConsumerServiceImpl implements OrderConsumerService {

    private ConsumerConnector consumerConn = ConsumerFactory
                                               .createConsumer(KafkaUtil.CONSUMER_GROUP_ID);

    //TODO maybe it is better to make this a daemon thread.
    @Override
    public void consume() {

        Map<String, Integer> topicThreadCountMap = new HashMap<String, Integer>();
        topicThreadCountMap.put(KafkaUtil.TOPIC, ExecutorUtil.RECOMMONED_THREAD_COUNT);
        while (true) {

            Map<String, List<KafkaStream<byte[], byte[]>>> topicStreamMap = consumerConn
                .createMessageStreams(topicThreadCountMap);

            if (CollectionUtil.isEmpty(topicStreamMap)) {
                ExecutorUtil.sleep(3000);
                continue;
            }

            List<KafkaStream<byte[], byte[]>> streamList = topicStreamMap.get(KafkaUtil.TOPIC);
            if (CollectionUtil.isEmpty(streamList)) {
                ExecutorUtil.sleep(3000);
                continue;
            }

            ExecutorService executorService = ExecutorUtil.getExecutorService();
            for (KafkaStream<byte[], byte[]> item : streamList) {
                executorService.submit(new OrderConsumerHandler(item));
            }

        }
    }

}
