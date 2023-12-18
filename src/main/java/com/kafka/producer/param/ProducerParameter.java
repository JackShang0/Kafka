package com.kafka.producer.param;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @description: kafka生产者吞吐量 常用配置参数：缓冲区、消息发送间隔时间、一次发送消息大小，压缩
 * @author: shangqj
 * @date: 2023/12/18
 * @version: 1.0
 */
public class ProducerParameter {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //0、配置参数设置
        Properties properties = new Properties();
        //kafka 服务器配置
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "119.23.70.8:9092");
        //序列化配置
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //缓冲区大小配置  默认32m
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432);

        //一次性发送消息的大小  默认16k 16384
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG,16384);

        //linger.ms  发送消息的间隔时间  默认是0
        properties.put(ProducerConfig.LINGER_MS_CONFIG,10);

        //压缩  可配置的压缩方式有：gzip、snappy、lz4、zstd  常用的snappy
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");


        //1、创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //2、发送消息
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("test", "hello kafka param  " + i), (recordMetadata, e) -> {
                if (e==null){
                    System.out.println(recordMetadata.topic() + "\t" + recordMetadata.partition() + "\t" + recordMetadata.offset());
                }
            }).get();
        }


        //3、关闭资源
        producer.close();
    }
}
