package com.quark.chat.server;

import com.quark.chat.filter.ServerSocketChannelInitializer;
import com.quark.chat.serverexcutor.EventLoopExecutorImpl;
import com.quark.chat.service.ChannelManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author : ChinaLHR
 * @Date : Create in 15:06 2017/10/22
 * @Email : 13435500980@163.com
 */
@Component
public class QuarkChatServer implements Server {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workGroup;
    private ChannelFuture future;
    private ServerBootstrap bootstrap;
    private ScheduledExecutorService executorService;
    @Value("${PORT}")
    private int port;



    @Autowired
    private ChannelManager manager;
    @Autowired
    private ServerSocketChannelInitializer serverSocketChannelInitializer;
    @PostConstruct
    @Override
    public void init() {
        logger.info("server init");
        int cpus = Runtime.getRuntime().availableProcessors();



        bossGroup = new NioEventLoopGroup(cpus, new EventLoopExecutorImpl(cpus,"BOSSGROUP"));

        workGroup = new NioEventLoopGroup(cpus * 10, new EventLoopExecutorImpl(cpus,"WORKGROUP"));

        bootstrap = new ServerBootstrap();
        executorService = Executors.newScheduledThreadPool(2);
    }

    @Override
    public void start() {
        logger.info("server start");
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)//TCP测链路检测
                .option(ChannelOption.TCP_NODELAY, true)//禁止使用Nagle算法
                .option(ChannelOption.SO_BACKLOG, 1024)//初始化服务端可连接队列大小
                .localAddress(new InetSocketAddress(port))
                .childHandler(serverSocketChannelInitializer);

        try{
            future = bootstrap.bind().sync();
            InetSocketAddress addr = (InetSocketAddress) future.channel().localAddress();
            logger.info("QuarkChat start success ,host is :"+addr.getHostName()+",port is:"+addr.getPort());

            /**
             * 定时扫描Channel 关闭失效的Channel
             */
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    logger.info("scheduleAtFixedRate to close channel");
                    manager.scanNotActiveChannel();
                }
            },3,60, TimeUnit.SECONDS);//initialDelay：延迟三秒执行，period：任务执行的间隔周期

            /**
             * 定时向客户端发送Ping进行心跳检测
             */
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    logger.info("scheduleAtFixedRate to ping");
                    manager.broadPing();
                }
            },3,50,TimeUnit.SECONDS);
        }catch (InterruptedException e){
            logger.error("Quark Chat fail ",e);
            Thread.currentThread().interrupt();
        }
    }

    @PreDestroy
    @Override
    public void shutdown() {

        if (executorService != null)
            executorService.shutdown();
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

}
