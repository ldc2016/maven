package com.vip.common.service;

import com.vip.common.entity.UserInfoModel;

/**
 * Created by dacheng.liu on 2017/8/9.
 */
public interface UserInfoService {
    UserInfoModel getUserInfoModelByUserNo(String userNo);
}
