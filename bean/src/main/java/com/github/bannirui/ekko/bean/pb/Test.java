package com.github.bannirui.ekko.bean.pb;

import com.github.bannirui.ekko.bean.pb.TestProto.Person;
import com.github.bannirui.ekko.bean.pb.TestProto.Person.Builder;

/**
 * @author dingrui
 * @since 2023/4/19
 */
public class Test {

    public static void main(String[] args) {
        Builder pojo = Person.newBuilder()
            .setId("1")
            .setSex(1);
    }
}
