package com.github.bannirui.ekko.dal.service;


import com.github.bannirui.ekko.dal.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * unit.
 *
 * @author dingrui
 * @since 2023/4/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void test00() {
        User one = this.userService.lambdaQuery().eq(User::getId, 1L).one();
        Assert.assertNull(one);
    }

    @Test
    public void test01() {
        boolean suc = this.userService.save(new User(1L, "1"));
        User one = this.userService.lambdaQuery().eq(User::getUid, 1L).one();
        Assert.assertNotNull(one);
        Assert.assertEquals(1L, one.getUid());
    }
}