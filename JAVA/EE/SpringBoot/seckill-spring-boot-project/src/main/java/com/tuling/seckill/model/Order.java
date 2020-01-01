package com.tuling.seckill.model;

import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.math.BigDecimal;

public class Order implements Serializable {
    private Long id;
    private Long productId;
    // 订单的总金额
    private BigDecimal amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productId=" + productId +
                ", amount=" + amount +
                '}';
    }
}
