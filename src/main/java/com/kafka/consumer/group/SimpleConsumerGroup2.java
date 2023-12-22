package com.kafka.consumer.group;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @description: 简单的消费者案例  消费组消费
 * @author: shangqj
 * @date: 2023/12/21
 * @version: 1.0
 */
public class SimpleConsumerGroup2 {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"119.23.70.8:9092");

        //配置消费者 groupid
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test");


        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String,String>(properties);

        //消费的主题数组
        ArrayList<String> topics = new ArrayList<>();
        topics.add("test");
        kafkaConsumer.subscribe(topics);


        while (true){
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            //System.out.println("再次开始消费数据");
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord);
            }

        }

    }
}
