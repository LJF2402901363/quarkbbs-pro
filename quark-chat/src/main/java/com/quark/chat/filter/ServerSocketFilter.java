package com.quark.chat.filter;

import com.quark.chat.handler.MessageHandler;
import com.quark.chat.handler.UserAuthHandler;
import com.quark.chat.serverexcutor.EventLoopExecutorImpl;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Classname:ServerSocketFilter
 *
 * @description:
 * @author: 陌意随影
 * @Date: 2021-05-31 00:28
 * @Version: 1.0
 **/
@Component
//@Scope("prototype")
public class ServerSocketFilter extends ChannelInitializer<SocketChannel> {
    @Autowired
    private UserAuthHandler authHandler;
    @Autowired
    private MessageHandler messageHandler;
    private DefaultEventLoopGroup defaultGroup;
    @PostConstruct
    public void  init(){
        this.defaultGroup = new DefaultEventLoopGroup(8, new EventLoopExecutorImpl(4,"DEFAULTGROUP"));
    }


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(
                        defaultGroup,
                        new HttpServerCodec(),//请求解码器
                        new HttpObjectAggregator(65536),//将多个消息转换成单一的消息对象
                        new ChunkedWriteHandler(),  //支持异步发送大的码流
                        new IdleStateHandler(60, 0, 0), //定时检测链路是否读空闲
                        authHandler,//认证Handler
                        messageHandler//消息Handler

                );
    }

    @PreDestroy
    public void destroy() {
        if (defaultGroup != null)
            defaultGroup.shutdownGracefully();
    }
}
