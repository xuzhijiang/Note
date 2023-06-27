package org.mq.core.meituan.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.mq.core.meituan.domain.Message;
import org.mq.core.meituan.sender.RabbitSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

// 余额宝
@Component
@Slf4j
public class ReceiveListener {

    private static String SUCCESS = "OK";

    @Autowired
    BalanceService balanceService;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    RabbitSender rabbitSender;

    /**
     * 1和2应该是本地事务.
     *
     * @param jsonStr
     */
    @RabbitListener(queues = "sam.message")
//    @Transactional
    public void processMsg(String jsonStr) {
        Message message = JSONObject.parseObject(jsonStr, Message.class);
        log.info("=======>>>余额宝开始消费mq里面的消息,消息内容为: " + jsonStr);

        // 编程式事务
        Boolean isSucess = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                // 1. 修改本地余额宝的庄户余额
                // 2. 修改本地余额成功的消息 => 插入到本地消息对账表
                String message_id = message.getMessage_id();
                int count = balanceService.queryMessageCountByMessageId(message_id);
                // count == 0: 代表余额宝没有处理过该流水号的记录
                if (count == 0) {
                    balanceService.updateAmount(message.getAmount(), message.getUser_id());
                    balanceService.addMessage(message.getUser_id(), message_id, message.getAmount());
                    return true;
                } else {
                    log.info("本条消息流水号: " + message_id + "已消费, 余额宝转账异常终止!!");
                    return false;
                }
            }
        });

        // 3. 修改本地余额成功的消息 => mq应答队列 => 支付宝确认
        if (isSucess) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("messageId", message.getMessage_id());
            jsonObject.put("respCode", SUCCESS);
            log.info("==========>发送转账成功应答消息给支付宝============>"+jsonObject.toJSONString());
            rabbitSender.sentMessage("exchange.message.response", "sam.message.routeKey.response", jsonObject.toJSONString());
        }
    }
}
