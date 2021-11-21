package com.example.dynamic.interfaces.facade;


import com.example.dynamic.domain.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author xiaoqi
 * @since 2021-11-18
 */
@RestController
@RequestMapping("/t")
public class TransactionController {

    @Autowired
    private ITransactionService iTransactionService;

    @RequestMapping("/test")
    public Object testTransaction() {
        return iTransactionService.test();
    }

}
