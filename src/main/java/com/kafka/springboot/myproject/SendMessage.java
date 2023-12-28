package com.kafka.springboot.myproject;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * @description:  项目中发送消息
 * @author: shangqj
 * @date: 2023/12/28
 * @version: 1.0
 */
@Slf4j
public class SendMessage {

    @Value("${spring.profiles.active}")
    public static String active;

    public static SimpleDateFormat simpleDateFormatKey = new SimpleDateFormat("yyyyMMddHHmmss");



    public static void main(String[] args) {
        sendMessageToKafka("message");
    }

    public static void sendMessageToKafka(String message) {

        //KafkaProducer<String, String> test = KafkaProducerUtil.kafkaProducerTest("test");
        KafkaProducer<String, String> producer = KafkaProducerUtil.kafkaProducerTest(active);

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("Topic_oss_ugw_5min", 0,
                simpleDateFormatKey.format(new Date()), message);

        RecordMetadata recordMetadata = null;
        try {
            recordMetadata = producer.send(producerRecord).get();
            log.info("异步发送消息结果   topic->{},partition->{},offset->{}",
                    recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }finally {
            producer.close();
        }

    }

}
