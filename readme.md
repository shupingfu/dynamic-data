## 动态数据源111



### 使用 `druid` 配合 `AbstractRoutingDataSource` 实现动态数据源

1. 依赖

   ```xml
           <!-- https://mvnrepository.com/artifact/com.alibaba/druid-spring-boot-starter -->
           <dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>druid-spring-boot-starter</artifactId>
               <version>1.2.8</version>
           </dependency>
   				<dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-aop</artifactId>
           </dependency>
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <scope>runtime</scope>
           </dependency>
           <!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter -->
           <dependency>
               <groupId>com.baomidou</groupId>
               <artifactId>mybatis-plus-boot-starter</artifactId>
               <version>3.4.3.4</version>
           </dependency>
   ```

2. 配置类:`DynamicDataSource` , `DynamicDataSourceConfig` , `DynamicDataSourceAOP` 。

   ```java
   /**
    * 动态数据源
    */
   @Slf4j
   public class DynamicDataSource extends AbstractRoutingDataSource {
   
       private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();
   
       public static final String DB_SCHEMA1 = "SCHEMA1";
       public static final String DB_SCHEMA2 = "SCHEMA2";
   
       @Override
       protected Object determineCurrentLookupKey() {
           log.info("切换数据源:{}", getDataSource());
           return getDataSource();
       }
   
       public static String getDataSource() {
           return CONTEXT_HOLDER.get();
       }
   
       public static void setDataSource(String datasource) {
           CONTEXT_HOLDER.set(datasource);
       }
   
       public static void clearDataSource() {
           CONTEXT_HOLDER.remove();
       }
   
   }
   ```

   ```java
   /**
    * 动态数据源配置
    */
   @Slf4j
   @Configuration
   public class DynamicDataSourceConfig {
   
       @Bean
       @ConfigurationProperties(prefix = "spring.datasource.schema1")
       public DataSource schema1() {
           return DruidDataSourceBuilder.create().build();
       }
   
       @Bean
       @ConfigurationProperties(prefix = "spring.datasource.schema2")
       public DataSource schema2() {
           return DruidDataSourceBuilder.create().build();
       }
   
       @Bean
       @Primary
       public DynamicDataSource dynamicDataSource(DataSource schema1, DataSource schema2) {
           ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>(2);
           map.put(DynamicDataSource.DB_SCHEMA1, schema1);
           map.put(DynamicDataSource.DB_SCHEMA2, schema2);
   
           DynamicDataSource dynamicDataSource = new DynamicDataSource();
           dynamicDataSource.setDefaultTargetDataSource(schema1);
           dynamicDataSource.setTargetDataSources(map);
           dynamicDataSource.afterPropertiesSet();
           return dynamicDataSource;
       }
   
   }
   ```

   ```java
   /**
    * 切面管理线程绑定的数据源信息
    */
   @Slf4j
   @Component
   @Aspect
   @Order(1)
   public class DynamicDataSourceAOP {
   
       //execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)
       //execution(修饰符匹配式? 返回类型匹配式 类名匹配式? 方法名匹配式(参数匹配式) 异常匹配式?)  
       // 带问号可以为空不配置。execution 后面描述的是精确到的方法的信息
       // * 单词通配符 .. 一个或多个内容
       @Pointcut("execution(public * com.example.dynamic.controller..*(..))")
       public void pointCut() {
       }
   
       @After("pointCut()")
       public void after() {
           DynamicDataSource.clearDataSource();
           log.info("清理线程绑定的数据源数据。");
       }
   
   }
   ```

3. 使用：

   用 `DynamicDataSource.setDataSource(DynamicDataSource.SCHEMA2)` 来切换不同的数据源。加入事务时，切换不生效。



### 动态数据源的事务管理

```java
 @Override
    @Transactional // 同一个数据源才有事务，不同数据源会导致切换数据库失败
    public boolean test() {
        DynamicDataSource.setDataSource(DynamicDataSource.DB_SCHEMA2);
        // insert article
        int result1 = iArticleDao.insert(Article.builder().date(new Date()).author("xiaoqi").title("tt").build());

        // insert sysUser
//        DynamicDataSource.setDataSource(DynamicDataSource.DB_SCHEMA2);
        int result2 = iSysUserDao.insert(SysUser.builder().username("xiaoqi").password("123456").build());

        // error
//        int i = 6 / 0;
        return true;
    }
```

* 使用事务注解，schema1插入article，schema1插入sysUser，可回滚。
* 使用事务注解，schema1插入article，schema2插入sysUser，切库失败，sysUser插入到schema1，事务也是schema1的。
* 使用事务注解，schema2插入article，schema2插入sysUser，可回滚。
* 不使用事务注解，可随意切库。





###  `mybaits-plus-generator` 使用

文档地址：https://baomidou.com/guide/generator-new.html

#### 依赖

```xml
        <!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-generator -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.5.1</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.quhaodian/freemaker -->
        <dependency>
            <groupId>com.quhaodian</groupId>
            <artifactId>freemaker</artifactId>
            <version>1.8.1</version>
        </dependency>
```



#### 代码实现

```java
    public static void main(String[] args) {
        FastAutoGenerator.create(url, userName, password)
                .globalConfig(builder -> {
                    builder.author("xiaoqi") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("项目绝对路径/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.dynamic")
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
//                            .mapperXml("mapper.xml")
//                            .controller("controller")
//                            .other("other")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "项目绝对路径/src/main/resources/mapper"))
                            .build();
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_user"); // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板，需要自己导入
                .execute();
    }
```



