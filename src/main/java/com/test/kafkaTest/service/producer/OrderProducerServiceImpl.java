package com.test.kafkaTest.service.producer;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.test.kafkaTest.factory.ProducerFactory;
import com.test.kafkaTest.util.KafkaUtil;

public class OrderProducerServiceImpl implements OrderProducerService {

	private Producer<String, String> producer = ProducerFactory.getProducer();

	@Override
	public void receiveOrder(String userId) {

		ProducerRecord<String, String> record = new ProducerRecord<String, String>(KafkaUtil.TOPIC, userId, userId);
		producer.send(record);
	}

	@Override
	public void close() {
		if (producer != null) {
			producer.close();
		}
	}

}
