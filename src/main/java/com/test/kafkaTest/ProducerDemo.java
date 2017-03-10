package com.test.kafkaTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.test.kafkaTest.util.KafkaUtil;



public class ProducerDemo {

	public static void main(String[] args) {
	    
	    InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName("192.168.158.204");
            String hostName = inetAddress.getCanonicalHostName();
            System.out.println(hostName);
        } catch (UnknownHostException e1) {
//            logger.error("", e);
        }
	    

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.158.204:9092");//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//        props.put("bootstrap.servers", "hyz.redlion56.com:9092");//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);
//        props.put("request.timeout.ms", 605000);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for(int i = 0; i < 100; i++){
            Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(KafkaUtil.TOPIC, Integer.toString(i), Integer.toString(i)));
            try {
                RecordMetadata rm = future.get();
                System.out.println("completed: "+rm);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException:"+e.getMessage());
            } catch (ExecutionException e) {
                System.out.println("ExecutionException:"+e.getMessage());
            }
        }

        producer.close();
        System.out.println("finished");

	}

}
