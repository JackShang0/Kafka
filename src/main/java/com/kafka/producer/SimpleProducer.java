package com.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Serializable;
import java.util.Properties;


/**
 * @description:  kafka消息的异步发送
 * @author: shangqj
 * @date: 2023/12/18
 * @version: 1.0
 */
public class SimpleProducer {
    public static void main(String[] args) {
        //kafka配置
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "119.23.70.8:9092");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //普通发送消息
        producer.send(new ProducerRecord<>("test", "hello"));

        //发送消息并获取应答的信息
        producer.send(new ProducerRecord<>("test", "hello callback"), (recordMetadata, e) -> {
            if (e == null) {
                System.out.println(recordMetadata.topic() + "\t" + recordMetadata.offset() + "\t" + recordMetadata.partition());
            }
        });

        producer.close();

    }
}
