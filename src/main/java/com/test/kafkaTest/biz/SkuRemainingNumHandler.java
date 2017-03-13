package com.test.kafkaTest.biz;

import com.test.kafkaTest.util.SkuUtil;


public class SkuRemainingNumHandler {



	private static Object lock = new Object();

	/**
	 * Compete for sku.
	 *
	 * @param userId
	 * @return
	 */
	public static boolean competeSku(String userId){
		synchronized (lock) {
			int currSkuIndex = SkuUtil.SKU_NUM.getAndDecrement();
			if(currSkuIndex>0){
				SkuUtil.luckUserIdSkuId.put(userId, String.valueOf(currSkuIndex));
				return true;
			}
			return false;
		}
	}
}
