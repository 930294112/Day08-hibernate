package com.lanou.test;

import com.lanou.domain.Category;
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
public class ManyToManyBothsTest {
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
    /*
    保存数据
    1.双向n对n保存数据以inverse为主动方的对象进行级联保存
     */
    @Test
    public void save(){
        Category category = new Category("烤肉");
        Category category1 = new Category("火锅");
        Category category2 = new Category("冰淇淋");

        Item item = new Item("精品肥牛");
        Item item1 = new Item("黑椒牛肉");

        //关联关系,以分类为主
        category.getItemSet().add(item);
        category.getItemSet().add(item1);

//        item1.getCategorySet().add(category1);
//        item1.getCategorySet().add(category2);

        category1.getItemSet().add(item1);
        category2.getItemSet().add(item1);

        //保存数据
        session.save(category);
        session.save(category1);
        session.save(category2);
    }
    /*
    删除delete
    2.在进行删除还有引用,即在中间表中还包含该对象的索引是,只能删除inverse=false的对象
        删除不了inverse=true对象,inverse为true时放弃了中间表的主动权
     */
    @Test
    public void delete(){
        //先删主动方:分类
//        Category category = session.get(Category.class,1);
//        session.delete(category);

        //删除被动方:有关联关系的item对象
        //删不了,是被动方,主动权在主动方
        Item item = session.get(Item.class,2);
        session.delete(item);
    }
}
