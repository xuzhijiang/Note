package org.mq.core.server.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private String message_id;
    private String user_id;
    private Integer amount;
    private String state;
}
