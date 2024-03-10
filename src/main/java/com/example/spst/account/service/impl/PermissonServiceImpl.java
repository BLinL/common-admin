package com.example.spst.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spst.account.dao.PermissionMapper;
import com.example.spst.account.po.PermissionPO;
import com.example.spst.account.service.PermissonService;
import org.springframework.stereotype.Service;

@Service
public class PermissonServiceImpl extends ServiceImpl<PermissionMapper, PermissionPO> implements PermissonService {
}
