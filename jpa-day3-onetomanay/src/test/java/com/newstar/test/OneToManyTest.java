package com.newstar.test;

import com.newstar.dao.CustomerDao;
import com.newstar.dao.LinkManDao;
import com.newstar.domain.Customer;
import com.newstar.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTest {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    /**
     * 添加一个客户同时添加联系人
     * 配置客户到联系人的关系
     */
    @Test
    @Transactional//配置事务，使得所有操作在同一个事务下进行
    @Rollback(value = false)//jpa默认执行完毕自动回滚，false让它不自动回滚
    public void testAdd() {
        Customer customer = new Customer();
        customer.setCustName("百度");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李");
        //配置客户到联系人的关系
        customer.getLinkMans().add(linkMan);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /**
     * 保存客户同时保存联系人
     * 配置联系人到客户的关系
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd1() {
        Customer customer = new Customer();
        customer.setCustName("阿里");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小明");
        //配置联系人到客户的关系
        linkMan.setCustomer(customer);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /**
     * 保存客户同时保存联系人
     * 配置联系人到客户的关系
     * 配置客户到联系人的关系
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd2() {
        Customer customer = new Customer();
        customer.setCustName("腾讯");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小花");
        //双向配置客户和联系人的关系
        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /**
     * 级联添加
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd() {
        Customer customer = new Customer();
        customer.setCustName("百度");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李");
        //配置双向关系
        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);
        //添加客户，并级联添加联系人
        customerDao.save(customer);
    }

    /**
     * 级联删除
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeDelete() {
        //查询客户
        Customer customer = customerDao.findOne(2l);
        //删除客户，并级联删除该客户下所有联系人
        customerDao.delete(customer);
    }
}
