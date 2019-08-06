package com.springboot.core.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

// REST控制器仅简单地返回一个json字符串。
@Data
@AllArgsConstructor()
@RequiredArgsConstructor(staticName = "create")
public class MyDataClass {
    private int value=100;
    private String info="hello";

}
