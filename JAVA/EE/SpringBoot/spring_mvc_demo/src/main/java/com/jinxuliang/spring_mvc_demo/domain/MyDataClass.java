package com.jinxuliang.spring_mvc_demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor()
@RequiredArgsConstructor(staticName = "create")
public class MyDataClass {
    private int value=100;
    private String info="hello";

}
