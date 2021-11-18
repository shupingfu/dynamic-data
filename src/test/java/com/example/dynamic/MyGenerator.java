package com.example.dynamic;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * mybatis-plus 代码生成器
 * 文档地址：https://baomidou.com/guide/generator-new.html
 *
 */
public class MyGenerator {

    public static final String url = "jdbc:mysql://127.0.0.1:3306/schema1?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String userName = "root";
    public static final String password = "123456";

    public static void main(String[] args) {
        FastAutoGenerator.create(url, userName, password)
                .globalConfig(builder -> {
                    builder.author("xiaoqi") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/zhangtian/Documents/idea_workspace/dynamic-data/src/main/java"); // 指定输出目录
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
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/zhangtian/Documents/idea_workspace/dynamic-data/src/main/resources"))
                            .build();
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_user"); // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板，需要自己导入
                .execute();
    }

}