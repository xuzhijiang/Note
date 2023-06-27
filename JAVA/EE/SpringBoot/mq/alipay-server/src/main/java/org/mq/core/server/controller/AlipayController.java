package org.mq.core.server.controller;

import org.mq.core.server.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlipayController {

    @Autowired
    AlipayService alipayService;

    @RequestMapping("/transfer")
    public String transferAmount(String userId, int amount) {
        try {
            alipayService.updateAmount(amount, userId);
        } catch (Exception e) {
            return "fail";
        }
        return "ok";
    }
}
