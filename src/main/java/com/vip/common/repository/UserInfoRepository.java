package com.vip.common.repository;

import com.vip.common.entity.UserInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by dacheng.liu on 2017/8/9.
 */
@Repository
public interface UserInfoRepository {
    UserInfoModel getUserInfoModelByUserNo(@Param("userNo") String userNo);
}
