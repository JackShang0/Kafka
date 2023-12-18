package com.kafka.producer.ack;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @description: ack配置 重试次数配置
 * @author: shangqj
 * @date: 2023/12/18
 * @version: 1.0
 */
public class ProducerAck {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //0、配置参数设置
        Properties properties = new Properties();
        //kafka 服务器配置
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "119.23.70.8:9092");
        //序列化配置
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //acks  默认是all/-1
        properties.put(ProducerConfig.ACKS_CONFIG,"1");

        //重试次数，默认是int的最大值  2147483647
        properties.put(ProducerConfig.RETRIES_CONFIG,5);


        //1、创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //2、发送消息
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("test", "hello kafka param ack " + i), (recordMetadata, e) -> {
                if (e==null){
                    System.out.println(recordMetadata.topic() + "\t" + recordMetadata.partition() + "\t" + recordMetadata.offset());
                }
            }).get();
        }

        //3、关闭资源
        producer.close();
    }
}
