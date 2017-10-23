package com.lanou.test;

import com.lanou.domain.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.HibernateUtil;

/**
 * Created by dllo on 17/10/19.
 */
public class SessionCacheTest {
    /*
    以及缓存测试类
     */

    private Session session;
    private Transaction transaction;
    @Before
    public void init(){
        session = HibernateUtil.getSession();
        transaction = session.beginTransaction();
    }
    @After
    public void destroy(){
        transaction.commit();
        HibernateUtil.closeSession();
    }

    /**
     * 一级缓存查询**/
    @Test
    public void find(){
        long start = System.currentTimeMillis();
        Item item = session.get(Item.class,1);
       // System.out.println(item);
        Item item1 = session.get(Item.class,2);
       // System.out.println(item1);
        Item item2 = session.get(Item.class,1);
        Item item3 = session.get(Item.class,2);
       // System.out.println(item2);
       // System.out.println(item3);
        long end = System.currentTimeMillis();
        System.out.println("运行时间:" +(end-start));
    }

    /**
     * 修改数据
     * 1.当缓存中的数据发生变化时,会在下一次事物提交将修改的数据同步到数据库中**/
    @Test
    public  void update(){
        //从数据库查询
        Item item = session.get(Item.class,1);
        //从从一级缓存中获取
        Item item1 = session.get(Item.class,1);

        item1.setName("烤好的牛肉");
    }

    /**
     * 数据库中的数据库同步到缓存中**/
    @Test
    public void update2(){
        Item item = session.get(Item.class,1);
        System.out.println(item);
       // transaction.commit();
        //先睡十秒
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // transaction = session.beginTransaction();
        session.clear();
        System.out.println("睡醒之后的" + item);

        Item item1 = session.get(Item.class,1);
        System.out.println("重新获取" + item1);
    }
}
