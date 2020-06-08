package com.newstar.test;

import com.newstar.dao.CustomerDao;
import com.newstar.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    /*查询一个客户*/
    @Test
    public void testFindOne() {
        Customer customer = customerDao.findOne(1l);
        System.out.println(customer);
    }

    /*保存客户*/
    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer.setCustAddress("南京");
        customer.setCustIndustry("服务业");
        customer.setCustLevel("服务员");
        customer.setCustName("小杨");
        customer.setCustPhone("11111111111");
        customer.setCustSource("网络");
        customerDao.save(customer);
    }

    /*更新客户*/
    @Test
    public void testUpdate() {
        Customer customer = customerDao.findOne(2l);
        customer.setCustPhone("22222");
        customerDao.save(customer);
    }

    /*删除客户*/
    @Test
    public void testDelete() {
        customerDao.delete(2l);
    }

    /*查询所有客户*/
    @Test
    public void testFindAll() {
        List<Customer> list = customerDao.findAll();
        for (Customer customer: list) {
            System.out.println(customer);
        }
    }

    /*统计查询*/
    @Test
    public void testCount() {
        long count = customerDao.count();
        System.out.println("总共有" + count + "条记录");
    }

    /**
     * 判断id为5的客户是否存在
     * 1、查询id为5的客户
     *      如果为空不存在，否则存在
     * 2、判断id为5个客户数量
     *      如果等于0不存在，如果大于0存在
     */
    @Test
    public void testExists() {
        boolean exists = customerDao.exists(5l);
        System.out.println(exists);
    }

    /**
     * 查询一个
     * findOne 底层是em.find 立即加载
     * getOne 底层是em.getReference 延迟加载
     * 添加@Transactional保证在同一个事务下，不会报错
     */
    @Test
    @Transactional
    public void testGetOne() {
        Customer customer = customerDao.getOne(5l);
        System.out.println(customer);
    }
}
