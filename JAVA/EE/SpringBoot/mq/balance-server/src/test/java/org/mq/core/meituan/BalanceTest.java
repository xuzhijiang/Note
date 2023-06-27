package org.mq.core.meituan;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mq.core.meituan.domain.Message;
import org.mq.core.meituan.mapper.BalanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BalanceTest {

    @Autowired
    BalanceMapper balanceMapper;

    @Test
    public void test02() {
        Message message = new Message();
        String messageId = "SAM" + System.currentTimeMillis() + new Random().nextInt(Integer.MAX_VALUE); // 消息流水号
        balanceMapper.addMessage("user_id_00001", messageId, 10000);

        int i = balanceMapper.selectMessageCountByMessageId(messageId);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>" + i);

        balanceMapper.updateAmount(500, "user_id_00001");
    }
}
