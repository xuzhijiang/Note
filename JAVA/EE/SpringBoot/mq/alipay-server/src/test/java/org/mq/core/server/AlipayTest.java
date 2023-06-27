package org.mq.core.server;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mq.core.server.dao.AccountMapper;
import org.mq.core.server.dao.MessageMapper;
import org.mq.core.server.domain.Account;
import org.mq.core.server.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class AlipayTest {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    MessageMapper messageMapper;

    @Test
    public void test01() {
        Account account = new Account();
        account.setUserId("user_id_00001");
        account.setAmount(10000);
        accountMapper.addAccount(account);

        Account account2 = new Account();
        account2.setUserId("user_id_00001");
        account2.setAmount(1000);
        accountMapper.updateAccount(account2);
    }

    @Test
    public void test02() {
        Message message = new Message();
        String messageId = "SAM" + System.currentTimeMillis() + new Random().nextInt(Integer.MAX_VALUE); // 消息流水号
        message.setUser_id("user_id_00001");
        message.setMessage_id(messageId);
        message.setAmount(1000);
        messageMapper.addMessage(message);

        List<Message> unconfirm = messageMapper.queryMessageByState("unconfirm");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>" + unconfirm.toString());

        Message message2 = new Message();
        message2.setUser_id("user_id_00001");
        message2.setMessage_id(messageId);
        message2.setState("confirm");
        messageMapper.updateMessage(message2);

        unconfirm = messageMapper.queryMessageByState("unconfirm");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>" + unconfirm.toString());
    }
}
