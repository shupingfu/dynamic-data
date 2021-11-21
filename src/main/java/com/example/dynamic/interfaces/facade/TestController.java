package com.example.dynamic.interfaces.facade;

import com.example.dynamic.infrastructure.config.DynamicDataSource;
import com.example.dynamic.domain.mapper.IArticleDao;
import com.example.dynamic.domain.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private IArticleDao iArticleDao;
    @Autowired
    private IArticleService iArticleService;

    @GetMapping("/get")
    public Object getSchema( String schemaName) {
        DynamicDataSource.setDataSource(schemaName);
        return iArticleDao.selectList(null);
    }

}
