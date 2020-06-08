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

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ObjectQueryTest {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    /**
     * 对象导航查询测试
     */
    @Test
    @Transactional//解决：no Session问题
    public void testQuery1() {
        //getOne是延迟加载，findOne是立即加载
        Customer customer = customerDao.getOne(1l);
        Set<LinkMan> linkMans = customer.getLinkMans();
        for (LinkMan linkMan : linkMans) {
            System.out.println(linkMan);
        }
    }

    /**
     * 对象导航延迟加载一的一方测试
     * 立即加载：查客户时左外链接查询了联系人
     * 延迟加载：查询客户是没查询联系人，查询联系人时才查询联系人
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testQuery2() {
        Customer customer = customerDao.findOne(1l);
        Set<LinkMan> linkMans = customer.getLinkMans();
        for (LinkMan linkMan : linkMans) {
            System.out.println(linkMan);
        }
    }

    /**
     * 对象导航延迟加载多的一方测试
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testQuery3() {
        LinkMan linkMan = linkManDao.findOne(1l);
        Customer customer = linkMan.getCustomer();
        System.out.println(customer);
    }
}
