package com.quark.chat.serverexcutor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class  ThreadFactoryImpl implements ThreadFactory {
            private AtomicInteger index = new AtomicInteger(0);
            private String prefixName;
            public ThreadFactoryImpl(String prefixName){
                this.prefixName = prefixName;
            }
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, prefixName+ index.incrementAndGet());
            }
 }