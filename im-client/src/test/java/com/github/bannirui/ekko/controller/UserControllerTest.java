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

    private static final long uid = 1682384581891L;
    private static final String uname = "1";

    @Test
    public void test_register() {
        ResponseEntity<Long> ret = this.userController.register(uname);
        System.out.println(ret.getBody());
    }

    @Test
    public void test_login() {
        ResponseEntity<LoginResponse> ret = this.userController.login(uid, uname);
        System.out.println(ret.getBody());
    }

    @Test
    public void test_logout() {
        ResponseEntity<Boolean> ret = this.userController.logout(uid);
        System.out.println(ret.getBody());
    }

    @Test
    public void test_logoff() {
        ResponseEntity<Long> ret = this.userController.logoff(uid);
        System.out.println(ret.getBody());
    }
}