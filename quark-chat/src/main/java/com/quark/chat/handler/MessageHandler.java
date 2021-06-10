package com.quark.chat.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.quark.chat.entity.ChatUser;
import com.quark.chat.protocol.QuarkChatProtocol;
import com.quark.chat.protocol.QuarkClientProtocol;
import com.quark.chat.service.ChannelManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @date: 2021/6/9 0009 20:17
 * @description: 消息处理器
 * @author  moyisuiying
 */
@ChannelHandler.Sharable
@Component
public  class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    @Autowired
    private ChannelManager channelManager;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        ChatUser chatUser = channelManager.getChatUser(ctx.channel());
        if (chatUser!=null&&chatUser.isAuth()){
            //包装数据
            QuarkClientProtocol clientProto = JSON.parseObject(frame.text(), QuarkClientProtocol.class);
            //广播消息
            channelManager.broadMessage(QuarkChatProtocol.buildMessageCode(chatUser.getUser(),clientProto.getMsg()));
        }
    }

    /**
     * Channel取消注册
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        channelManager.removeChannel(ctx.channel());
        channelManager.broadMessage(QuarkChatProtocol.buildSysUserInfo(channelManager.getUsers()));
        super.channelUnregistered(ctx);
    }

    /**
     * Netty I/O异常事件
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("connection error and close the channel:{}",cause);
        channelManager.removeChannel(ctx.channel());
        channelManager.broadMessage(QuarkChatProtocol.buildSysUserInfo(channelManager.getUsers()));
    }
}
