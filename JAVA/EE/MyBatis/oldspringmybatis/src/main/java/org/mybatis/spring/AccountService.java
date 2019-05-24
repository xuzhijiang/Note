package org.mybatis.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

// 编程式事务管理
//
//如果上述两种声明式事务的配置都不是你要想要，那么你可以采取编程式事务。你可以在业务bean中注入事务管理器，然后进行编程式事务的管理，如：
public class AccountService{
    @Autowired
    private DataSourceTransactionManager transactionManager;

//    @Autowired
//    private AccountMapper accountMapper;

    public void transfer(final String out,final String in,final Double money){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = transactionManager.getTransaction(def);
        try {
//            accountMapper.outMoney(out, money);
            int i=1/0;
//            accountMapper.inMoney(in, money);
            transactionManager.commit(status);
        }
        catch (Exception ex) {
            transactionManager.rollback(status);
        }
    }
}

// 另外，Spring还提供了一个TransactionTemplate，用于简化编程式事务代码的编写，配置方式如下：
/**
 * <bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
 *     <property name="transactionManager"ref=“transactionManager"/>
 * </bean>
 *
 * 使用方式如下：
 *
 *   public void transfer(final String out,final String in,final Double money) {
 *         //在事务模板中执行操作
 *         transactionTemplate.execute(new TransactionCallbackWithoutResult(){
 *         @Override
 *         protected void doInTransactionWithoutResult(TransactionStatustransactionstatus){
 *             accountMapper.outMoney(out, money);
 *             int i=1/0;
 *             accountMapper.inMoney(in, money);
 *          }
 *         });
 *     }
 *
 *     总的来说，编程式事务管理还是显得略微麻烦。
 */