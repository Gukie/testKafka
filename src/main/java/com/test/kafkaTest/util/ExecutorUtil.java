package com.test.kafkaTest.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtil {


	private static ExecutorService executorService;

	public final static int RECOMMONED_THREAD_COUNT = Runtime.getRuntime().availableProcessors();

	public static CountDownLatch START_COUNT_DOWN_LATCH = new CountDownLatch(1);

	public static CountDownLatch END_COUNT_DOWN_LATCH = new CountDownLatch(SkuUtil.USER_COUNT);

	public static ExecutorService getExecutorService() {
		if(executorService == null){
			executorService = Executors.newFixedThreadPool(RECOMMONED_THREAD_COUNT);
		}

		return executorService;
	}


	public static void sleep(long milliseconds) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			//TODO add log
		}
	}

}
