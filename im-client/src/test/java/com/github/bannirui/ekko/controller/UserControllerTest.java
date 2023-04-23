package com.github.bannirui.ekko.controller;

import com.github.bannirui.ekko.bean.resp.LoginResponse;
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

    /**
     * 注册两个用户.
     * <ul>
     *     <li>uid=1682238419291 name=1</li>
     *     <li>uid=1682241699880 name=2</li>
     * </ul>
     */
    @Test
    public void test_register() {
        ResponseEntity<Long> ret = this.userController.register("2");
        System.out.println(ret);
    }

    @Test
    public void test_login() {
        ResponseEntity<LoginResponse> ret = this.userController.login(1682238419291L, "1");
        System.out.println(ret);
    }

    @Test
    public void test_logout() {
        ResponseEntity<Boolean> ret = this.userController.logout(1682238419291L);
        System.out.println(ret);
    }
}