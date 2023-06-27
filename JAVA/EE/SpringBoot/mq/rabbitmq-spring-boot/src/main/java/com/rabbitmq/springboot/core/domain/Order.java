package com.rabbitmq.springboot.core.domain;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private String orderSn; //订单号
    private Long skuId; //购买的商品id
    private Integer num; //购买的个数
    private Integer memberId; //购买者的会员id
}
