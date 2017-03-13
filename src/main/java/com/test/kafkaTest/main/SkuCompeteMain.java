package com.test.kafkaTest.main;

import java.util.Map.Entry;

import com.test.kafkaTest.service.consumer.OrderConsumerService;
import com.test.kafkaTest.service.consumer.OrderConsumerServiceImpl;
import com.test.kafkaTest.service.producer.OrderProducerService;
import com.test.kafkaTest.service.producer.OrderProducerServiceImpl;
import com.test.kafkaTest.util.ExecutorUtil;
import com.test.kafkaTest.util.SkuUtil;

public class SkuCompeteMain {


	OrderConsumerService consumerService = new OrderConsumerServiceImpl();
	OrderProducerService producerService = new OrderProducerServiceImpl();

	public static void main(String[] args) {

		SkuCompeteMain skuCompeteMain = new SkuCompeteMain();
		skuCompeteMain.startConsumerService();
		skuCompeteMain.startProducerService();
		skuCompeteMain.waitAllProducerFinished();
		skuCompeteMain.outputResult();
		skuCompeteMain.close();
	}
	private void close() {
		producerService.close();
		consumerService.close();
	}
	private  void outputResult() {
		for(Entry<String, String> luckUserIdSkuId: SkuUtil.luckUserIdSkuId.entrySet()){
			String userId = luckUserIdSkuId.getKey();
			String skuId = luckUserIdSkuId.getValue();
			System.out.println(userId+" : "+skuId);
		}
	}
	private void waitAllProducerFinished() {
		try {
			ExecutorUtil.END_COUNT_DOWN_LATCH.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private  void startConsumerService() {
		consumerService.consume();
	}
	private  void startProducerService() {

		for(int i =0;i<SkuUtil.USER_COUNT;i++){
			Thread thread = new Thread(new Client("user-"+i,producerService));
			thread.start();
		}
		ExecutorUtil.START_COUNT_DOWN_LATCH.countDown();
	}

}
