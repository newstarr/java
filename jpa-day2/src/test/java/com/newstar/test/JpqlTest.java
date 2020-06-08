package com.newstar.test;

import com.newstar.dao.CustomerDao;
import com.newstar.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JpqlTest {
    @Autowired
    private CustomerDao customerDao;


    /**
     * jpql根据客户名称查询客户
     */
    @Test
    public void testFindJpql() {
        Customer customer = customerDao.findJpql("小明");
        System.out.println(customer);
    }

    /**
     * jpql根据客户名称和id查询客户
     */
    @Test
    public void testFindNameAndIdJpql() {
//        Customer customer = customerDao.getCustomerByNameAndId("小杨", 6l);
        Customer customer = customerDao.getCustomerByNameAndId(6l, "小杨");
        System.out.println(customer);
    }

    /**
     * @Transactional:添加事务支持
     * @Rollback(value = false)：指定执行完后事务不会滚，默认回滚
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testUpdateCustomer() {
        customerDao.updateCustomer(5l, "小黑");
    }

    /**
     * 使用sql语句查询所有
     */
    @Test
    public void testFindSql() {
        List<Object[]> list = customerDao.findSql();
        for (Object[] obj: list) {
            System.out.println(Arrays.toString(obj));
        }
    }

    /**
     * 使用sql模糊查询
     */
    @Test
    public void testFindLikeSql() {
        List<Object[]> list = customerDao.findLikeSql("小杨%");
        for (Object[] obj: list) {
            System.out.println(Arrays.toString(obj));
        }
    }

    /**
     * 测试方法名命名规则
     */
    @Test
    public void testFindByCustomerName() {
        Customer customer = customerDao.findByCustName("小明");
        System.out.println(customer);
    }

    @Test
    public void findByCustNameLike() {
        List<Customer> list = customerDao.findByCustNameLike("小杨%");
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    @Test
    public void findByCustNameLikeAndCustIndustry() {
        Customer customer = customerDao.findByCustNameLikeAndCustIndustry("小杨%", "制造业");
        System.out.println(customer);
    }
}
