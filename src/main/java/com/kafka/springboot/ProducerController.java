package com.kafka.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * @description: kafka生产者  简单案例
 * @author: shangqj
 * @date: 2023/12/27
 * @version: 1.0
 */
@RestController
@Slf4j
public class ProducerController {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping ("/producer")
    public String sendMessage(@RequestBody String mes){
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send("test", mes);
        SendResult<String, String> result = null;
        try {
            result = send.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        log.info("  "+result.getProducerRecord()+"\t"+result.getRecordMetadata());

        return "Success";
    }


}
