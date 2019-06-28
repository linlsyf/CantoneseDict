package com.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lindanghong on 2019/6/27.
 */

public class ThreadPoolExecutorFactory {
    static ThreadPoolExecutorFactory factory;
    static ExecutorService mExecutorService;
     public static ThreadPoolExecutorFactory  getInstance(){
         if (null==factory){
             factory=new ThreadPoolExecutorFactory();
         }

         return factory ;
     }

    public static ExecutorService getCachedThreadPool() {

         if (null==mExecutorService){
             mExecutorService=new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                     new SynchronousQueue<Runnable>());
         }
         return mExecutorService;
    }

}
