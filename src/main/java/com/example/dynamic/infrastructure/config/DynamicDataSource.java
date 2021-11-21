package com.example.dynamic.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

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
