package com.test.kafkaTest.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SkuUtil {

    public static AtomicInteger SKU_NUM = new AtomicInteger(100);
    
    
    public static Map<String, String> luckUserIdSkuId = new HashMap<String, String>();
}
