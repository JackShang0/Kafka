package com.kafka.consumer.offset.limitOffset;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
/**
 * @description: 指定时间进行消费
 * @author: shangqj
 * @date: 2023/12/25
 * @version: 1.0
 */
public class ConsumerLimitOffset {
    public static void main(String[] args) {
        //
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"119.23.70.8:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"limitOffset");

        //创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        //订阅主题
        ArrayList<String> objects = new ArrayList<>();
        objects.add("test");
        consumer.subscribe(objects);

        //指定offset消费
        Set<TopicPartition> partitions = consumer.assignment();

        //获取到的分区可能为空：我们消费组的初始化过程，先通过 coordinator 注册我们消费组信息，消费组会制定消费策略，然后再同步给我们的每个消费者
        //如此以来，消费策略同步到消费者的时候需要一些时间交互，我们单线程去获取分区信息可能会获取不到
        while (partitions.size() == 0){
            consumer.poll(Duration.ZERO);
            partitions = consumer.assignment();
        }

        for (TopicPartition partition : partitions) {
            //指定offset进行消费  传入分区和offset
            consumer.seek(partition,100);
        }

        //注意：一条消息被消费者组消费过后，就不会再被消费了，如果需要再次消费这条消息，需要调整消费者组id
        while (true){
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ZERO);

            for (ConsumerRecord<String, String> record : poll) {
                System.out.println("record = " + record);
            }
        }

    }
}
