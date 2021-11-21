package com.example.dynamic.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dynamic.domain.entity.Article;
import com.example.dynamic.domain.mapper.IArticleDao;
import com.example.dynamic.domain.service.IArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<IArticleDao, Article> implements IArticleService {

    @Override
    public List<Article> select() {
        return baseMapper.selectList(null);
    }
}
