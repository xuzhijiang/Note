package com.tuling.seckill.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String pic;
}
