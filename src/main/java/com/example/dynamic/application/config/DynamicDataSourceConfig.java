package com.example.dynamic.application.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

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
