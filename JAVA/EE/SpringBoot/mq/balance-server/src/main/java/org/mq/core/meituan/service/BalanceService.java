package org.mq.core.meituan.service;

import org.mq.core.meituan.mapper.BalanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {
    @Autowired
    BalanceMapper balanceMapper;

    public int queryMessageCountByMessageId(String message_id) {
        return balanceMapper.selectMessageCountByMessageId(message_id);
    }

    public void updateAmount(Integer amount, String user_id) {
        balanceMapper.updateAmount(amount,user_id);
    }

    public void addMessage(String user_id, String message_id, Integer amount) {
        balanceMapper.addMessage(user_id, message_id, amount);
    }
}
