package com.github.bannirui.ekko.dal.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * unit test.
 *
 * @author dingrui
 * @since 2023/4/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void test00() {
        long code = this.userService.login(1L, "1");
        System.out.println();
    }

    @Test
    public void test01() {
        long code = this.userService.logout(1L);
        System.out.println();
    }
}