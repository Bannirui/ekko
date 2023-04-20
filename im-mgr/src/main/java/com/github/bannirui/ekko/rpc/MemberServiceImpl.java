package com.github.bannirui.ekko.rpc;

import com.github.bannirui.ekko.api.MemberService;
import com.github.bannirui.ekko.bean.ImServerNode;
import com.github.bannirui.ekko.constants.OpCode;
import com.github.bannirui.ekko.dal.model.User;
import com.github.bannirui.ekko.dal.service.UserService;
import com.github.bannirui.ekko.req.LoginReq;
import com.github.bannirui.ekko.req.LogoutReq;
import com.github.bannirui.ekko.req.RegisterReq;
import com.github.bannirui.ekko.resp.LoginResp;
import com.github.bannirui.ekko.resp.RegisterResp;
import com.github.bannirui.ekko.service.ImPeerService;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 用户管理.
 *
 * @author dingrui
 * @since 2023/4/20
 */
@DubboService
public class MemberServiceImpl implements MemberService {

    private final UserService userService;
    private final ImPeerService imPeerService;

    public MemberServiceImpl(UserService userService, ImPeerService imPeerService) {
        this.userService = userService;
        this.imPeerService = imPeerService;
    }

    @Override
    public RegisterResp register(RegisterReq req) {
        String uname = null;
        if (Objects.isNull(req) || (StringUtils.isBlank(uname = req.getUname()))) {
            return null;
        }
        // TODO: 2023/4/20 全局唯一id方案
        long uid = System.currentTimeMillis();
        if (!this.userService.save(new User(uid, uname))) {
            return null;
        }
        return new RegisterResp(uid);
    }

    @Override
    public LoginResp login(LoginReq req) {
        if (Objects.isNull(req)) {
            return null;
        }
        long state = this.userService.login(req.getUid(), req.getUname());
        LoginResp resp = new LoginResp(state);
        if (state != OpCode.SUCC) {
            return resp;
        }
        String[] host = new String[1];
        Integer[] ip = new Integer[1];
        if (!this.imPeerService.lb(x -> host[0] = x, y -> ip[0] = y)) {
            return resp;
        }
        resp.setServer(new ImServerNode(host[0], ip[0]));
        return resp;
    }

    @Override
    public boolean logout(LogoutReq req) {
        if (Objects.isNull(req)) {
            return false;
        }
        long state = this.userService.logout(req.getUid());
        return state == OpCode.SUCC;
    }
}
