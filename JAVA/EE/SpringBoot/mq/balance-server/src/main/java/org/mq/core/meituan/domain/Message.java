package org.mq.core.meituan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private String message_id;
    private String user_id;
    private Integer amount;
    private String state;
}
