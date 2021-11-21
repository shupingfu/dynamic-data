package com.example.dynamic;

import java.util.concurrent.*;

public class ExecutorTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //runAsync方法不支持返回值。
        //supplyAsync可以支持返回值。

        // 创建线程池
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2,
                5,
                15,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                new ThreadPoolExecutor.CallerRunsPolicy());

        // 并发异步执行
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "执行任务");
            return "completableFuture1";
        }, poolExecutor);

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "执行任务");
            return "completableFuture1";
        }, poolExecutor);

        // 接受异步执行的结果
        // CompletableFuture.allOf(..) 等待runAsync完成
        // CompletableFuture.anyOf(..) supplyAsync 任意一个完成时都返回。
        System.out.println(completableFuture1.get());
        System.out.println(completableFuture2.get());
        // 关闭线程池
        poolExecutor.shutdown();
    }

}
