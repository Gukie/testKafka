package com.test.kafkaTest.biz;

import com.test.kafkaTest.util.SkuUtil;


public class SkuRemainingNumHandler {

    
    /**
     * Compete for sku.
     * 
     * @param userId
     * @return
     */
    public static boolean competeSku(String userId){
        synchronized (SkuUtil.luckUserIdSkuId) {
            int currSkuIndex = SkuUtil.SKU_NUM.get();
            if(currSkuIndex>0){
                SkuUtil.luckUserIdSkuId.put(userId, String.valueOf(SkuUtil.SKU_NUM.decrementAndGet()));
                return true;
            }
            return false;
        }
    }
}
