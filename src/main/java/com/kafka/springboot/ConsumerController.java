package com.kafka.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @description: 消费者 简单案例
 * @author: shangqj
 * @date: 2023/12/28
 * @version: 1.0
 */
@Slf4j
@Configuration
public class ConsumerController {

    @KafkaListener(topics = "test")
    public void consumerTopic(String message) {
        System.out.println(message);
    }
}
