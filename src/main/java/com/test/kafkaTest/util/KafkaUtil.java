package com.test.kafkaTest.util;

public class KafkaUtil {

	public static final String TOPIC = "miaosha-topic";
	public static final String CONSUMER_GROUP_ID = "miaosha-consumer-group";

	//    public static final String SERVER_IP = "192.168.158.204";
	public static final String SERVER_IP = "127.0.0.1";
	public static final String SEMI = ":";

	/**
	 * zookeeper connection info
	 */
	public static final String ZK_PORT = "2181";
	public static final String ZK_SERVER_PORT = SERVER_IP+SEMI+ZK_PORT;

	/**
	 * kafka connection info
	 */
	public static final String KAFKA_PORT = "9092";
	public static final String KAFKA_SERVER_PORT = SERVER_IP+SEMI+KAFKA_PORT;

}
