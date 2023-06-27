package org.mq.core.server.service;

import com.alibaba.fastjson.JSONObject;
import org.mq.core.server.domain.ZgTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

// 持续出票(抢票)接口
@Service
public class GrapService {

    @Autowired
    TicketService ticketService;

    public static String RETURN_CODE = "respCode";

    // 100个客户端去执行
    @Transactional
    public String grapTicket() {
        // 去12306的数据库查询是否有余票
        // 这里查询的version=0的票,version=0说明这个票还没有被购买
        List<ZgTicket> tickets = ticketService.queryAllTicket();
        JSONObject retJo = new JSONObject();
        if (tickets.size() == 0) {
            // 1. 余票不足
            retJo.put(RETURN_CODE, "1");
            return retJo.toJSONString();
        }

        // 随机从余票中获取一个 下标索引
        int ticketIndex = new Random().nextInt(tickets.size());
        // 随机拿到一张还没有被购买的票,这个ZgTicket中的version目前依然是0,
        ZgTicket zgTicket = tickets.get(ticketIndex);

        // 通过数据库的乐观锁 更新ZgTicket的Version, 有且仅有一个client可以更新成功
        int count = ticketService.updateZgTicketVersion(zgTicket);
        // count: 1,表示更新 成功
        if (count == 1) {
            retJo.put(RETURN_CODE, "2"); // 出票成功: 2
            retJo.put("ticket", JSONObject.toJSONString(zgTicket));
            // 12306将这张票更新为被占有
            ticketService.updateZgTicketState(zgTicket);
        } else {
            // 99失败来到这里
            // 3: 抢票失败,余额不明确
            retJo.put(RETURN_CODE, "3");
        }

        return retJo.toJSONString();
    }

}
