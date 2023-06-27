package org.mq.core.server.controller;

import org.mq.core.server.business.TicketBusiness;
import org.mq.core.server.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

    @Autowired
    TicketBusiness ticketBusiness;

    @GetMapping("/getTicket/{id}")
    public String getTicket(@PathVariable("id") Long id) {
        ticketBusiness.process("OK");
        return "ok";
    }
}
