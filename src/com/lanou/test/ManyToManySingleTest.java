package com.lanou.test;

import com.lanou.domain.Category;
import com.lanou.domain.Item;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.HibernateUtil;

import java.util.Iterator;
import java.util.List;

/**
 * Created by dllo on 17/10/19.
 */
public class ManyToManySingleTest {
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
     */
    @Test
    public void save(){
        Category category = new Category("零食");
        Item item = new Item("薯片");
        Item item1 = new Item("果冻");

        //关联关系
        category.getItemSet().add(item);
        category.getItemSet().add(item1);
        //保存对象
        session.save(category);

    }
    /*
    删除数据
    1.删除关联的集合中的某个对象时,需要先解除关联关系,在session.delete
     */
    @Test
    public void delete(){
        //已经存在的分类对象
        Category category = session.get(Category.class,1);
        //1.先删除该分类下的某个item
        Iterator<Item> iterator  = category.getItemSet().iterator();

        Item fistitem = iterator.next();//取第一个

        category.getItemSet().remove(fistitem);//解除关联
        //解除关联后删除item对象
        session.delete(fistitem);

    }
    /*
    删除分类
    2.删除设置了set关联关系的对象时,如果cascade级联没有包含delete级联,
    则只删除本对象,以及中间表中本对象的id数据;不会删除级联的对方对象
     */
    @Test
    public void deleteCategory(){
        Category category = session.get(Category.class,1);
        session.delete(category);//删除分类
    }
    /*
    查找
     */
@Test
    public void find(){
    Category category = new Category("串串");
    Item item = new Item("鱼豆腐");
    category.getItemSet().add(item);
    session.save(category);

    //按名字查找
    Query query = session.createQuery("from Category where name=:na");
    //设置参数
    query.setParameter("na","串串");
    //返回结果
    List<Category> categories = query.list();
    //遍历集合
    for (Category ca : categories) {
        System.out.println("基础信息:" + ca);
        //得到当前的的分类下的item集合
        Iterator<Item> iterator = ca.getItemSet().iterator();
        //遍历item集合
        while (iterator.hasNext()){
            System.out.println("item:" + iterator.next());
        }
    }

}
}
