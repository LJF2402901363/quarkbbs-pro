package com.quark.chat.serverexcutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Classname:EventLoopExecutorImpl
 *
 * @description:
 * @author: 陌意随影
 * @Date: 2021-06-01 00:51
 * @Version: 1.0
 **/
public class EventLoopExecutorImpl implements Executor {
    private ExecutorService executorService;
    private int nThreads;
    private String prefixName;
    public EventLoopExecutorImpl(int nThreads,String prefixName){
        this.nThreads = nThreads;
        this.prefixName = prefixName;
        this.executorService = Executors.newFixedThreadPool(nThreads, new ThreadFactoryImpl(prefixName));
    }
    @Override
    public void execute(Runnable command) {
    executorService.submit(command);
    }


}
