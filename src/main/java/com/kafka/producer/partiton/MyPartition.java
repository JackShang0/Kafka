package com.kafka.producer.partiton;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @description: 自定义分区器 以实现我们更复杂的业务场景
 * @author: shangqj
 * @date: 2023/12/18
 * @version: 1.0
 */
public class MyPartition implements Partitioner {

    /**
     * 核心方法
     * @param s
     * @param o
     * @param bytes
     * @param o1
     * @param bytes1
     * @param cluster
     * @return
     */
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        Integer partition;
        if (o1.toString().contains("kafka")){
            partition = 0;
        }else {
            partition = 1;
        }
        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
