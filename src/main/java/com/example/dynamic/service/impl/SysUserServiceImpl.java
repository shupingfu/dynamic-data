package com.example.dynamic.service.impl;

import com.example.dynamic.entity.SysUser;
import com.example.dynamic.mapper.ISysUserDao;
import com.example.dynamic.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author xiaoqi
 * @since 2021-11-18
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<ISysUserDao, SysUser> implements ISysUserService {

}
