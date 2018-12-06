package com.piaoniu.service;

import com.piaoniu.dao.DynamicRunSqlDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.function.Consumer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: daniel
 * Date: 2018-12-06
 * Time: 下午10:24
 */
@Component
@Slf4j
public class DBLocker {

    @Resource(name = "transactionManager")
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private DynamicRunSqlDao dynamicRunSqlDao;

    /**
     * 利用数据库select ...for update (nowait mysql低版本不支持本参数) 悲观锁
     * 实现单库情况下的分布式锁。
     * 优点:相比更新状态位，解锁简单、不侵入业务外数据。
     * 缺点:被锁逻辑必须是在一个事务内。
     */
    public void withLock(int id, String tableName, Consumer<Integer> consumer, int waitSecs) {
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            //事务传播级别 待商榷
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = transactionManager.getTransaction(def);
            try {
                String sql = "select 1 from " + tableName + " where id= " + id + " for update ";
                if (waitSecs >= 0) {
                    sql += "wait " + waitSecs;
                } else {
                    sql += " nowait";
                }
                int i = dynamicRunSqlDao.runDynamic(sql);
                log.info("successfully locked:" + i);
                consumer.accept(id);
                transactionManager.commit(status);
            } catch (Exception e) {
                log.info("lock failed:{}", e.getClass().getName());
                transactionManager.rollback(status);
            }
        } catch (Exception e) {
            log.info("ensure no exception break program ");
        }
    }

}
