package com.kafka.producer.transactional;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @description: 事务使用
 * @author: shangqj
 * @date: 2023/12/18
 * @version: 1.0
 */
public class ProducerTransactional {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //0、配置参数设置
        Properties properties = new Properties();
        //kafka 服务器配置
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "119.23.70.8:9092");
        //序列化配置
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //****设置事务id****
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transactional_id_test");

        //1、创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //初始化事务
        producer.initTransactions();
        //开启事务
        producer.beginTransaction();


        //2、发送消息
        try {
            int j = 10 / 0;
            for (int i = 0; i < 5; i++) {
                producer.send(new ProducerRecord<>("test", "hello kafka transactional " + i), (recordMetadata, e) -> {
                    if (e == null) {
                        System.out.println(recordMetadata.topic() + "\t" + recordMetadata.partition() + "\t" + recordMetadata.offset());
                    }
                }).get();
            }

            //提交事务
            producer.commitTransaction();
        } catch (Exception e) {

            //放弃事务
            producer.abortTransaction();
        } finally {

            //3、关闭资源
            producer.close();
        }
    }
}
