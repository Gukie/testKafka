package com.test.kafkaTest.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtil {

    public static <K> boolean isEmpty(Collection<K> map) {
        if(map == null || map.isEmpty()){
            return true;
        }
        return false;
    }
    
    
    public static <K,V> boolean isEmpty(Map<K, V> map) {
        if(map == null || map.isEmpty()){
            return true;
        }
        return false;
    }

}
