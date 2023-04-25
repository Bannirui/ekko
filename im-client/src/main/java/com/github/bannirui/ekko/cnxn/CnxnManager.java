package com.github.bannirui.ekko.cnxn;

import com.github.bannirui.ekko.bean.constants.OpCode;
import com.github.bannirui.ekko.bean.constants.OpCode.Sender;
import com.github.bannirui.ekko.bean.pb.MessageProto;
import io.netty.channel.Channel;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 负责客户端与服务端基于netty的通信.
 *
 * @author dingrui
 * @since 2023/4/24
 */
@Component
public class CnxnManager {

    private static final Logger LOG = LoggerFactory.getLogger(CnxnManager.class);

    private final Map<Long, SenderWorker> senderMap = new ConcurrentHashMap<>(128);
    private final Map<Long, BlockingQueue<MessageProto.Message>> messageMap = new ConcurrentHashMap<>(128);

    public class SenderWorker extends Thread {

        private Long uid;
        private Channel ch;

        public SenderWorker(Long uid, Channel ch) {
            this.uid = uid;
            this.ch = ch;
        }

        @Override
        public void run() {
            for (; ; ) {
                BlockingQueue<MessageProto.Message> messages =
                    CnxnManager.this.messageMap.computeIfAbsent(this.uid, key -> new LinkedBlockingQueue<>());
                MessageProto.Message message = null;
                try {
                    message = messages.poll(3_000, TimeUnit.MILLISECONDS); // 超时阻塞
                } catch (Exception ignored) {
                    // ignored
                }
                if (Objects.nonNull(message)) {
                    LOG.info("[IM-CLIENT] 负责通信的channel轮询在用户的消息上 客户端要发送的消息是={}", message);
                    this.ch.writeAndFlush(message);
                }
            }
        }

        @Override
        public String toString() {
            return "SenderWorker{"
                + "uid=" + uid
                + ", ch=" + ch
                + '}';
        }
    }

    /**
     * 保存客户端的channel.
     *
     * @param uid 客户端uid
     * @param ch  netty初始化channel
     */
    public void cacheCh(Long uid, Channel ch) {
        SenderWorker sw = new SenderWorker(uid, ch);
        this.senderMap.put(uid, sw);
        sw.start();
    }

    /**
     * 移除内存缓存的channel.
     *
     * @param uid 用户uid
     */
    public void removeCh(Long uid) {
        if (Objects.isNull(uid)) {
            return;
        }
        this.senderMap.remove(uid);
    }

    public void reConnect(){

    }

    /**
     * 开放给客户端调用的发送消息.
     *
     * @param sender  发送方uid
     * @param message 要发送的消息
     * @return {@link com.github.bannirui.ekko.bean.constants.OpCode} 标识消息是否发送出去 不是同步发送
     */
    public long send(Long sender, MessageProto.Message message) {
        SenderWorker sw = this.senderMap.get(sender);
        LOG.info("[IM-CLIENT::CNXN] 缓存在客户端的SenderWorker={}", sw);
        if (Objects.isNull(sw)) {
            return Sender.LOGIN_FAIL;
        }
        BlockingQueue<MessageProto.Message> messages = this.messageMap.computeIfAbsent(sender, key -> new LinkedBlockingQueue<>());
        LOG.info("[IM-CLIENT::CNXN] 消息队列大小={}", messages.size());
        boolean succ = true;
        try {
            succ = messages.offer(message, 3_000, TimeUnit.MILLISECONDS);
            LOG.info("[IM-CLIENT::CNXN] 客户端要发送的数据已经缓存在了本地 消息={} 此时消息队列大小={}", message, messages.size());
        } catch (InterruptedException ignored) {
            // ignored
            succ = false;
        }
        return succ ? OpCode.SUCC : OpCode.FAIL;
    }
}
