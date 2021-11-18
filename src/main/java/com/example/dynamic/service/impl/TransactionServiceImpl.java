package com.example.dynamic.service.impl;

import com.example.dynamic.config.DynamicDataSource;
import com.example.dynamic.entity.Article;
import com.example.dynamic.entity.SysUser;
import com.example.dynamic.mapper.IArticleDao;
import com.example.dynamic.mapper.ISysUserDao;
import com.example.dynamic.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private IArticleDao iArticleDao;
    @Autowired
    private ISysUserDao iSysUserDao;


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
}
