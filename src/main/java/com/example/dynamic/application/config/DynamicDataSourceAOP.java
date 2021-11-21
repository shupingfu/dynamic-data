package com.example.dynamic.application.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
    @Pointcut("execution(public * com.example.dynamic.controller..*(..))")
    public void pointCut() {
    }

    @After("pointCut()")
    public void after() {
        DynamicDataSource.clearDataSource();
        log.info("清理线程绑定的数据源数据。");
    }

}
