package com.kafka.consumer.partition;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeSet;

/**
 * @description: 简单的消费者案例  主题下指定分区消费
 * @author: shangqj
 * @date: 2023/12/21
 * @version: 1.0
 */
public class SimpleConsumerPartitions {
    public static void main(String[] args) {

        Properties properties = new Properties();
        //连接
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"119.23.70.8:9092");
        //反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //配置消费者 groupid
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test1");


        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String,String>(properties);

        //消费的分区
        ArrayList<TopicPartition> partitions = new ArrayList<>();
        //指定主题和分区
        TopicPartition test = new TopicPartition("test", 0);
        partitions.add(test);
        kafkaConsumer.assign(partitions);


        while (true){
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ZERO);
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord);
            }
        }

    }
}
