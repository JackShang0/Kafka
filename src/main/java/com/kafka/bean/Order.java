package com.kafka.bean;

import lombok.Data;

/**
 * @description
 * @author: shangqj
 * @date: 2023/3/13
 * @version: 1.0
 */
@Data
public class Order {
    private Long id ;
    private int price;

    public Order(Long id, int price) {
        this.id = id;
        this.price = price;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
