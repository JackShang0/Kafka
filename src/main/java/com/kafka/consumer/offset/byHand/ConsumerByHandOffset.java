package com.kafka.consumer.offset.byHand;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @description: 默认自动提交offset，提交时间间隔为 5 秒  -->   调整为手动提交offset
 * @author: shangqj
 * @date: 2023/12/22
 * @version: 1.0
 */
public class ConsumerByHandOffset {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"119.23.70.8:9092");

        //配置消费者 groupid
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test");

        //自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        //调整自动提交时间间隔为1秒钟
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,1000);

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String,String>(properties);

        //消费的主题数组
        ArrayList<String> topics = new ArrayList<>();
        topics.add("test");
        kafkaConsumer.subscribe(topics);


        while (true){
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            //System.out.println("再次开始消费数据");
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println( consumerRecord);
            }

            //手动同步提交数据
            kafkaConsumer.commitSync();

            //手动异步提交数据  生产环境中手动提交异步提交使用较多
            kafkaConsumer.commitAsync();

        }

    }
}
