package com.test.kafkaTest.service.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.test.kafkaTest.factory.ConsumerFactory;
import com.test.kafkaTest.util.ExecutorUtil;
import com.test.kafkaTest.util.KafkaUtil;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class OrderConsumerServiceImpl implements OrderConsumerService {

	private ConsumerConnector consumerConn = ConsumerFactory.createConsumer(KafkaUtil.CONSUMER_GROUP_ID);

	private ExecutorService executorService = ExecutorUtil.getExecutorService();

	// TODO maybe it is better to make this a daemon thread.
	@Override
	public void consume() {

		Map<String, Integer> topicThreadCountMap = new HashMap<String, Integer>();
		topicThreadCountMap.put(KafkaUtil.TOPIC, ExecutorUtil.RECOMMONED_THREAD_COUNT);
		Map<String, List<KafkaStream<byte[], byte[]>>> topicStreamMap = consumerConn
				.createMessageStreams(topicThreadCountMap);

		List<KafkaStream<byte[], byte[]>> streamList = topicStreamMap.get(KafkaUtil.TOPIC);
		for (KafkaStream<byte[], byte[]> item : streamList) {
			executorService.submit(new OrderConsumerHandler(item));
		}

	}

	@Override
	public void close() {
		if(consumerConn !=null){
			consumerConn.shutdown();
		}
		if(executorService!=null){
			executorService.shutdown();
		}
	}

}
