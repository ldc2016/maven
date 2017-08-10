package com.vip.common.service.iml;

import com.vip.common.entity.UserInfoModel;
import com.vip.common.repository.UserInfoRepository;
import com.vip.common.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dacheng.liu on 2017/8/9.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfoModel getUserInfoModelByUserNo(String userNo) {
//        return null;
        return userInfoRepository.getUserInfoModelByUserNo(userNo);
    }
}
