package com.kafka.consumer.offset.limitTime;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.*;

/**
 * @description: 指定时间消费
 * @author: shangqj
 * @date: 2023/12/25
 * @version: 1.0
 */
public class ConsumerLimitTime {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "119.23.70.8:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "limitOffset");


        //创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);


        //订阅主题
        ArrayList<String> objects = new ArrayList<>();
        objects.add("test");
        consumer.subscribe(objects);

        //指定offset消费
        Set<TopicPartition> partitions = consumer.assignment();

        //创建主题分区、时间类型的一个hashmap，指定我们的分区和需要开始消费的时间
        HashMap<TopicPartition, Long> hashMap = new HashMap<>();
        for (TopicPartition partition : partitions) {
            hashMap.put(partition, System.currentTimeMillis() - 24 * 3600 * 1000);
        }

        //根据时间 获得我们时间对应的消息的 offset
        Map<TopicPartition, OffsetAndTimestamp> offsetAndTimestampMap = consumer.offsetsForTimes(hashMap);


        for (TopicPartition partition : partitions) {
            //获取时间对应的 offset
            OffsetAndTimestamp offsetAndTimestamp = offsetAndTimestampMap.get(partition);
            //根据时间转换  获取offset消费消息
            consumer.seek(partition, offsetAndTimestamp.offset());
        }


        while (true) {
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ZERO);

            for (ConsumerRecord<String, String> record : poll) {
                System.out.println("record = " + record);
            }
        }
    }
}
