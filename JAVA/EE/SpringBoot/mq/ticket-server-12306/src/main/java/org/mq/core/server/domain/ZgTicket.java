package org.mq.core.server.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZgTicket {
    private Integer ticketId;
    private Double ticketMoney;
    private Integer ticketStatus;
    private Date ticketTime;
    private Integer version;
}
