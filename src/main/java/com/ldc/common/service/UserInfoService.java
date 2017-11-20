package com.ldc.common.service;

import com.ldc.common.entity.UserInfoModel;

/**
 * Created by dacheng.liu on 2017/8/9.
 */
public interface UserInfoService {
    UserInfoModel getUserInfoModelByUserNo(String userNo);
}
