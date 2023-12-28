package com.kafka.consumer.group;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * @description: 生产者分区策略使用
 * @author: shangqj
 * @date: 2023/12/18
 * @version: 1.0
 */
public class SimpleProducerSyncPartitions {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //kafka配置
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"10.120.52.34:9092");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //普通发送消息
        //producer.send(new ProducerRecord<>("test", "hello"));

        //发送消息并获取应答的信息  同步发送与异步发送的区别在于 同步发送多了一个get方法
        //指定分区进行发送消息
        /*for (int i = 0; i < 50; i++) {
            producer.send(new ProducerRecord<>("test",0,"","hello kafka partition  " + i), (recordMetadata, e) -> {
                if (e == null) {
                    System.out.println(recordMetadata.topic() + "\t" + recordMetadata.offset() + "\t" + recordMetadata.partition());
                }
            }).get();
        }*/


        //不指定分区，指定key
        /*producer.send(new ProducerRecord<>("test","key1","hello kafka partition key"), (recordMetadata, e) -> {
            if (e == null) {
                System.out.println(recordMetadata.topic() + "\t" + recordMetadata.offset() + "\t" + recordMetadata.partition());
            }
        }).get();*/

        //不指定分区，指定key
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("test","hello kafka partition key"+i), (recordMetadata, e) -> {
                if (e == null) {
                    System.out.println(recordMetadata.topic() + "\t" + recordMetadata.offset() + "\t" + recordMetadata.partition());
                }
            }).get();
            //TimeUnit.MILLISECONDS.sleep(5);
        }

        producer.close();

    }
}
