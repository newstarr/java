package com.newstar.test;

import com.newstar.dao.CustomerDao;
import com.newstar.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @RunWith(SpringJUnit4ClassRunner.class):声明运行环境为spring的运行环境
 * @ContextConfiguration(locations = "classpath:applicationContext.xml")：指定spring配置文件，初始化
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecTest {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testSpec() {
        /*匿名内部类*/
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                /*1、获取比较属性*/
                Path<Object> custName = root.get("custName");
                /*2、构造查询条件*/
                Predicate predicate = cb.equal(custName, "小杨1");
                return predicate;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }

    /**
     * 多条件查询
     * 查询小杨2且行业是教育的客户
     */
    @Test
    public void testSpec1() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //从root中获取需要的属性
                Path custName = root.get("custName");
                Path custIndustry = root.get("custIndustry");
                //构造查询
                Predicate p1 = cb.equal(custName, "小杨2");
                Predicate p2 = cb.equal(custIndustry, "教育");
                Predicate and = cb.and(p1, p2);
                return and;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }

    /**
     * 模糊查询
     * gt、lt、ge、le、like需要指定比较参数的类型
     * path.as(参数类型的字节码)
     */
    @Test
    public void testSpec2() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //获取属性
                Path<Object> custName = root.get("custName");
                //构造查询
                Predicate predicate = cb.like(custName.as(String.class), "小杨%");
                return predicate;
            }
        };
        /*模糊查询*/
//        List<Customer> list = customerDao.findAll(spec);
//        for (Customer customer : list) {
//            System.out.println(customer);
//        }
        /*模糊查询、倒序查询*/
        Sort sort = new Sort(Sort.Direction.DESC, "custId");
        List<Customer> list = customerDao.findAll(spec, sort);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 分页查询
     * 查询姓名小杨开头的客户，并分页
     */
    @Test
    public void testSpec3() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //获取比较的属性
                Path<Object> custName = root.get("custName");
                //构造查询
                Predicate like = cb.like(custName.as(String.class), "小杨%");
                return like;
            }
        };
        Pageable pageable = new PageRequest(0, 2);
        //spec：查询条件，pageable：分页参数
        Page<Customer> page = customerDao.findAll(spec, pageable);
        System.out.println(page.getTotalElements());//查询到的总数
        System.out.println(page.getTotalPages());//查询到总页数
        System.out.println(page.getContent());//查询到数据集
    }
}
