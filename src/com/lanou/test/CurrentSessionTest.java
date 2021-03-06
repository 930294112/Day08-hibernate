package com.lanou.test;

import com.lanou.domain.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by dllo on 17/10/19.
 */
public class CurrentSessionTest {
    private SessionFactory sessionFactory;
    @Before
    public void init(){
        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Test
     public void find(){
        Session session = sessionFactory.getCurrentSession();
        System.out.println("第一次获取的" + session.getClass().hashCode());
        //如果使用getCurrentSession的方式获得基于当前线程的session对象
        //则数据库的增删改查操作必须放在事物中进行
        Transaction transaction = session.beginTransaction();
        Item item = session.get(Item.class,1);
        System.out.println(item);
        transaction.commit();//事物提交
        //事物提交后的session状态为关闭
        System.out.println("事物提交后的session状态:" + session.isOpen());


    }

}
