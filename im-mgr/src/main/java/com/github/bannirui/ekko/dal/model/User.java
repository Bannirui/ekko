package com.github.bannirui.ekko.dal.model;

/**
 * 用户.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class User {

    private Long id;
    private long uid;
    private String nickname;

    public User() {
    }

    public User(long uid, String nickname) {
        this.uid = uid;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
