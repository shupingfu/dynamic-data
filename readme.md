## DDD架构简要解析

![image-20211121091800115](https://github.com/shupingfu/dynamic-data/blob/architecture-ddd/readme.assets/image-20211121091800115.png?raw=true)

![image-20211121091853047](https://github.com/shupingfu/dynamic-data/blob/architecture-ddd/readme.assets/image-20211121091853047.png?raw=true)



```
├── application // 应用层
│   ├── publish // 应用之间的发布/订阅
│   └── subscribe
├── domain // 领域层
│   ├── entity
│   ├── mapper
│   ├── repository 
│   └── service
│       └── impl
├── infrastructure // 基础设施层
│   ├── cache 
│   ├── config // 系统配置
│   ├── constants
│   └── util
└── interfaces // 用户界面/展示层
    ├── assembler // 对象转换或者其他
    ├── dto
    ├── facade // 前端借口
    └── rpc // 模块之间调用
```



 + User Interface : 2021.1.3版idea不允许创建带有`interface` 的包，遂改为`interfaces`。

 + 基于Application代码的抽象，适用于微服务项目。而普通分布式项目，或者单体架构项目，还是使用基于业务的分包更为一目了然。

 + `高内聚低耦合` 。选择代码内聚，业务可能会耦合；选择业务内聚，代码可能会耦合，二者只得其一。DDD架构在微服务本身已经有了基于业务解耦的基础之上，再对本身的代码进行解耦，是比较合适的。

 + [相关书](https://github.com/backstudy/bookrack/blob/master/DDD(%E9%A2%86%E5%9F%9F%E9%A9%B1%E5%8A%A8%E8%AE%BE%E8%AE%A1)-%E7%B2%BE%E7%AE%80%E7%89%88.pdf)

   

## 线程池，多线程异步执行

```java
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
```

+ 整合`spring boot`， 可以将线程池配置`@Bean` 注解载入IOC容器，用到时取出。