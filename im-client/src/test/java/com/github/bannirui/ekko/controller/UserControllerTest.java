package com.github.bannirui.ekko.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * unit test.
 *
 * @author dingrui
 * @since 2023/4/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    public void test00() {
        ResponseEntity<Long> ret = this.userController.register("1");
        System.out.println(ret);
    }
}