package com.test.kafkaTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.test.kafkaTest.util.KafkaUtil;



public class ProducerDemo {

	public static void main(String[] args) {
        int events=100;

        // 设置配置属性
//        Properties props = new Properties();
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("bootstrap.servers","192.168.158.204:9092");
        props.put("value.serializer", StringSerializer.class);
        props.put("key.serializer", StringSerializer.class);
        // 触发acknowledgement机制，否则是fire and forget，可能会引起数据丢失
        // 值为0,1,-1,可以参考
        // http://kafka.apache.org/08/configuration.html
        props.put("request.required.acks", "1");
        
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        // 产生并发送消息
        long start=System.currentTimeMillis();
        String key = null;
        String value = null;
        for (long i = 0; i < events; i++) {
            long runtime = new Date().getTime();
            key = "producer_" + i;//rnd.nextInt(255);
            value = runtime + ",www.example.com," + key;
            //如果topic不存在，则会自动创建，默认replication-factor为1，partitions为0
//            KeyedMessage<String, String> data = new KeyedMessage<String, String>(
//                    "page_visits", ip, msg);
            ProducerRecord<String, String> data = new ProducerRecord<String, String>(KafkaUtil.TOPIC, key, value);
            producer.send(data);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
        // 关闭producer
        producer.close();

	}

}
