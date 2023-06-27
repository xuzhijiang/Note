package org.mq.core.server.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.mq.core.server.dao.AccountMapper;
import org.mq.core.server.dao.MessageMapper;
import org.mq.core.server.domain.Account;
import org.mq.core.server.domain.Message;
import org.mq.core.server.config.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Random;

@Service
@Slf4j
public class AlipayService {

    /**
     * TransactionTemplate的编程式事务管理是使用模板方法设计模式 对原始事务管理方式的封装,
     * TransactionTemplate主要依赖于execute(TransactionCallback<T> action)方法执行事务管理.
     */
    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    RabbitSender rabbitSender;

    public static String SUCCESS = "OK";

    /**
     * 1. 修改我们的支付宝账户余额信息
     * 2. 插入我们的本地消息表
     * 3. 往mq中插入消息,供余额宝业务消费
     *
     * 1和2可以写在一个本地事务中,所以用到代码级别的细粒度,不可以使用@Transactional注解.
     * 所以这里使用编程式事务.
     *
     * @param amount
     * @param userId
     */
    // 修改支付宝账户余额的接口 -1000 userId
    public void updateAmount(final int amount,final String userId) {
        String messageID = transactionTemplate.execute(new TransactionCallback<String>() {
            @Override
            public String doInTransaction(TransactionStatus transactionStatus) {
                // 操作支付宝的本地account帐单表
                // 1. 修改我们的支付宝余额信息
                Account account = new Account();
                account.setUserId(userId);
                account.setAmount(amount);
                int count = accountMapper.updateAccount(account);
                // 支付宝本地修改成功
                if (count == 1) {
                    // 构建一个本地消息对账表
                    Message message = new Message();
                    // 账务修改成功后往消息表插入一条未确认的消息记录
                    String messageId = "SAM" + System.currentTimeMillis() + new Random().nextInt(Integer.MAX_VALUE); // 消息流水号
                    message.setMessage_id(messageId);
                    message.setUser_id(userId);
                    message.setAmount(amount);
                    int result = messageMapper.addMessage(message);
                    if (result == 1) {
                        return messageId;
                    } else {
                        log.info("更新本地消息表失败!!!!!");
                        return null;
                    }
                }
                return null;
            }
        });

        if (messageID != null && messageID.trim().length() > 1) {
            // 如果本地消息表操作成功,往mq里面插入消息
            // 构建一个待确认的消息给mq
            Message message = new Message();
            message.setAmount(amount);
            message.setUser_id(userId);
            message.setMessage_id(messageID);
            message.setState("unconfirm");
            // 调用消息模板发送
            // 发送消息到mq消息队列
            rabbitSender.sendMessage("exchange.message", "sam.message.routeKey", message);
        }
    }

    /**
     * 余额宝消费w完之后,支付宝更新本地消息表的状态
     * @param param
     */
    // 回调接口,修改本地消息表消息的状态 --- unconfirm
    public void updateMessage(String param) {
        JSONObject jsonObject = JSONObject.parseObject(param);
        String respCode = jsonObject.getString("respCode");
        String messageId = jsonObject.getString("messageId");
        if (SUCCESS.equals(respCode)) {
            Message message = new Message();
            message.setState("confirm");
            message.setMessage_id(messageId);
            messageMapper.updateMessage(message);
        }
    }
}
