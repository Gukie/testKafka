package com.test.kafkaTest.main;

import com.test.kafkaTest.service.producer.OrderProducerService;
import com.test.kafkaTest.util.ExecutorUtil;

public class Client implements Runnable {


	private String userName;

	private OrderProducerService orderProducerService;


	public Client(String userName,OrderProducerService orderProducerService) {
		setUserName(userName);
		setOrderProducerService(orderProducerService);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public OrderProducerService getOrderProducerService() {
		return orderProducerService;
	}

	public void setOrderProducerService(OrderProducerService orderProducerService) {
		this.orderProducerService = orderProducerService;
	}

	@Override
	public void run() {

		try {
			ExecutorUtil.START_COUNT_DOWN_LATCH.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		orderProducerService.receiveOrder(userName);

	}

}
