package com.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;


/**
 * @description: kafka消息的同步发送
 * @author: shangqj
 * @date: 2023/12/18
 * @version: 1.0
 */
public class SimpleProducerSync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //kafka配置
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "119.23.70.8:9092");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Serializable.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Serializable.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //普通发送消息
        producer.send(new ProducerRecord<>("test", "hello"));

        //发送消息并获取应答的信息  同步发送与异步发送的区别在于 同步发送多了一个get方法
        producer.send(new ProducerRecord<>("test", "hello callback"), (recordMetadata, e) -> {
            if (e == null) {
                System.out.println(recordMetadata.topic() + "\t" + recordMetadata.offset() + "\t" + recordMetadata.partition());
            }
        }).get();

        producer.close();

    }
}
