package com.newstar.test;

import com.newstar.dao.RoleDao;
import com.newstar.dao.UserDao;
import com.newstar.domain.Role;
import com.newstar.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestManyToMany {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    //保存一个用户和一个角色
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd() {
        User user = new User();
        user.setUserName("小李");
        Role role = new Role();
        role.setRoleName("java程序员");
        //建立关系
        user.getRoles().add(role);
        role.getUsers().add(user);
        userDao.save(user);
        roleDao.save(role);
    }

    //级联添加
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd() {
        User user = new User();
        user.setUserName("小明");
        Role role = new Role();
        role.setRoleName("客户经理");
        //建立关系
        user.getRoles().add(role);
        role.getUsers().add(user);
        userDao.save(user);
    }

    //级联删除
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeDelete() {
        User user = userDao.findOne(1l);
        userDao.delete(user);
    }
}
