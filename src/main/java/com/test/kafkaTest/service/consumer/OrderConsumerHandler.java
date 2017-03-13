package com.test.kafkaTest.service.consumer;

import java.io.UnsupportedEncodingException;

import com.test.kafkaTest.biz.SkuRemainingNumHandler;
import com.test.kafkaTest.util.ExecutorUtil;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

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
			try {
				SkuRemainingNumHandler.competeSku(new String(keyBytes, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				ExecutorUtil.END_COUNT_DOWN_LATCH.countDown();
			}
		}
	}

}
