package com.kafka.springboot.myproject;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class KafkaProducerUtil {

    private static final String testEvnServers = "10.120.52.38:9092,10.120.52.39:9092,10.120.52.40:9092";
    private static final String prodEvnServers = "10.120.52.2:9092,10.120.52.3:9092,10.120.52.4:9092";

    public static KafkaProducer<String, String> kafkaProducerTest(String evn) {
        Properties props = new Properties();
        //设置Kafka服务器地址
        if (evn.equals("test")) {
            props.put("bootstrap.servers", testEvnServers);
        } else if (evn.equals("prod")) {
            props.put("bootstrap.servers", prodEvnServers);
        }
        //设置数据key的序列化处理类
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //设置数据value的序列化处理类
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"admin\" password=\"admin\";");

        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "PLAIN");
        return new KafkaProducer<>(props);
    }

}
