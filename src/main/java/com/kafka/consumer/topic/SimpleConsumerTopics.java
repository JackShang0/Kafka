package com.kafka.consumer.topic;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @description: 简单的消费者案例  指定主题消费
 * @author: shangqj
 * @date: 2023/12/21
 * @version: 1.0
 */
public class SimpleConsumerTopics {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"119.23.70.8:9092");

        //配置消费者 groupid
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test");


        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String,String>(properties);

        //消费的主题数组
        ArrayList<String> topics = new ArrayList<>();
        topics.add("test");
        kafkaConsumer.subscribe(topics);


        while (true){
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            System.out.println("再次开始消费数据");
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println("consumerRecord = " + consumerRecord);
            }

        }

        //伪代码 csdn 获取
        /*while(true){
            //拉取消息
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            records.partitions().forEach(partition ->{
                //从redis获取partition的偏移量
                String redisKafkaOffset = redisTemplate.opsForHash().get(partition.topic(), "" + partition.partition()).toString();
                long redisOffset = StringUtils.isEmpty(redisKafkaOffset)?-1:Long.valueOf(redisKafkaOffset);
                List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                partitionRecords.forEach(record ->{
                    //redis记录的偏移量>=kafka实际的偏移量，表示已经消费过了，则丢弃。
                    if(redisOffset >= record.offset()){
                        return;
                    }
                    //业务端只需要实现这个处理业务的方法就可以了，不用再处理幂等性问题
                    doMessage(record.topic(),record.value());
                });
            });
            //处理完成后立即保存Redis偏移量
            long saveRedisOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
            redisTemplate.opsForHash().put(partition.topic(),"" + partition.partition(),saveRedisOffset);
            //异步提交。消费业务多时，异步提交有可能造成消息重复消费，通过Redis中的Offset，就可以过滤掉这一部分重复的消息。。
            consumer.commitAsync();
        }*/

    }
}
