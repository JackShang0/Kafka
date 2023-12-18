package com.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @description
 * @author: shangqj
 * @date: 2023/3/13
 * @version: 1.0
 */
public class MySimpleProducer {

    //private final static String TOPIC_NAME = "my-replicated-topic";
    private final static String TOPIC_NAME_TEST = "test";



    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //1、设置参数
        Properties properties = new Properties();
        //kafka集群配置
        //此处需要修改kafka的配置ip为公网ip     内网地址 172.30.40.136  公网地址 119.23.70.8
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"119.23.70.8:9092");
        //把发送的key从在字符串序列化为字节数组
        //properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        //上下等价
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        //把发送的value从字符串序列化为字节数组
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        //2、创建生产者，传入参数
        //发送消息的客户端
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //3、创建消息
        //未指定发送分区，具体发送的分区计算公式：hash(key)%partitionNum (key的哈希值 取余 分区数)
        //key 决定分区，value 是具体发送的值
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME_TEST,
                "kafkaKey", "hello,kafka");

        //4、发送消息  得到消息发送的元数据
        //异步回调方式发送消息
        RecordMetadata recordMetadata = producer.send(producerRecord).get();
        System.out.println("异步发送消息结果： "+"topic-"+recordMetadata.topic() + " partition-"+
                recordMetadata.partition()+" offset-"+recordMetadata.offset());
        /*producer.send(producerRecord, (recordMetadata, e) -> {
            if (e!=null){
                System.out.println("message error");
            }
            if (recordMetadata!=null){
                System.out.println("异步发送消息结果： "+"topic-"+recordMetadata.topic() + " partition-"+
                        recordMetadata.partition()+" offset-"+recordMetadata.offset());
            }
        });*/

        producer.close();

    }






    @PostMapping("/producer")
    public String producer()  {

        String result = "Success";
        String [] a = new String[10];
        try {
            main(a);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            result = "Error";
        }
        return result;
    }

    @PostMapping("/producer")
    public String producer2()  {

        String result = "Success";
        try {
            //1、设置参数
            Properties properties = new Properties();
            //kafka集群配置
            //此处需要修改kafka的配置ip为公网ip     内网地址 172.30.40.136  公网地址 119.23.70.8
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"172.30.40.136:9092");
            //把发送的key从在字符串序列化为字节数组
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
            //把发送的value从字符串序列化为字节数组
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


            //2、创建生产者，传入参数
            //发送消息的客户端
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

            //3、创建消息
            //未指定发送分区，具体发送的分区计算公式：hash(key)%partitionNum (key的哈希值 取余 分区数)
            //key 决定分区，value 是具体发送的值
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME_TEST,
                    "kafkaKey", "hello,kafka");

            //4、发送消息  得到消息发送的元数据
            //异步回调方式发送消息
            RecordMetadata recordMetadata = producer.send(producerRecord).get();
            System.out.println("异步发送消息结果： "+"topic-"+recordMetadata.topic() + " partition-"+
                    recordMetadata.partition()+" offset-"+recordMetadata.offset());

            producer.close();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            result = "Error";
        }
        return result;
    }
}
