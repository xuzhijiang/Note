package org.mq.core.server.service;

import org.mq.core.server.dao.TicketMapper;
import org.mq.core.server.domain.ZgTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    public ZgTicket getTicketById(String id) {
        return ticketMapper.queryTicketById(id);
    }

    public int updateZgTicketVersion(ZgTicket zgTicket) {
        return ticketMapper.updateZgTicketVersion(zgTicket);
    }

    public List<ZgTicket> queryAllTicket() {
        return ticketMapper.queryAllTicket();
    }

    public int updateZgTicketState(ZgTicket zgTicket) {
        return ticketMapper.updateZgTicketState(zgTicket);
    }
}
