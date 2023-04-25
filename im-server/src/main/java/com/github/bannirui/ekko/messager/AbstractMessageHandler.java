package com.github.bannirui.ekko.messager;

import com.github.bannirui.ekko.bean.constants.OpCode;
import com.github.bannirui.ekko.bean.pb.MessageProto;
import com.github.bannirui.ekko.bean.pb.MessageProto.Message;
import com.github.bannirui.ekko.common.annotations.HandlerEntry;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息处理器.
 *
 * @author dingrui
 * @since 2023/4/25
 */
public abstract class AbstractMessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractMessageHandler.class);

    /**
     * 策略实现入口.
     *
     * @param args 参数
     * @return 操作状态码
     * @see com.github.bannirui.ekko.bean.constants.OpCode
     */
    @HandlerEntry
    public final long process(MessageHandlerArgs args) {
        Message message = null;
        if (Objects.isNull(args) || Objects.isNull(message = args.getMessage())) {
            return OpCode.PARAM_INVALID;
        }
        Object attach = args.getAttach();
        LOG.info("[IM-SERVER] 收到客户端的信息 message={} attach={}}", message, attach);
        return this.soProcess(message, attach);
    }

    protected abstract long soProcess(MessageProto.Message message, Object attach);
}
