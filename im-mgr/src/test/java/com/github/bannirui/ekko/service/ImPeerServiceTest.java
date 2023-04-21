package com.github.bannirui.ekko.service;

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
public class ImPeerServiceTest {

    @Autowired
    ImPeerService imPeerService;

    private static final String path1 = "10.10.132.185:9527";
    private static final String path2 = "10.10.132.186:9527";

    @Test
    public void test00() {
        this.imPeerService.add(path1);
    }

    @Test
    public void test01() {
        this.imPeerService.discard(path1);
    }

    @Test
    public void test02() {
        this.imPeerService.add(path1);
        this.imPeerService.add(path2);
        String[] host = new String[1];
        Integer[] port = new Integer[1];
        boolean b = this.imPeerService.lb(x -> host[0] = x, y -> port[0] = y);
        System.out.println("[TESTING] ip=" + host[0] + ", port=" + port[0]);
    }
}