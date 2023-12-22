package com.kafka.consumer.partition.strategy.stick;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @description: 消费者分区策略： stick
 * @author: shangqj
 * @date: 2023/12/21
 * @version: 1.0
 */
public class ConsumerPartitions_Stick {
    public static void main(String[] args) {

        Properties properties = new Properties();
        //连接
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"119.23.70.8:9092");
        //反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //配置消费者 groupid
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"stick");

        //指定分区策略 roundRobin
        properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,"org.apache.kafka.clients.consumer.StickyAssignor");


        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String,String>(properties);

        //消费的主题
        ArrayList<String> topics = new ArrayList<>();
        topics.add("test");
        kafkaConsumer.subscribe(topics);


        while (true){
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ZERO);
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord);
            }
        }

    }
}
