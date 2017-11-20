package com.ldc.common.repository;

import com.ldc.common.entity.UserInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dacheng.liu on 2017/8/9.
 */
@Repository
public interface UserInfoRepository {
    UserInfoModel getUserInfoModelByUserNo(@Param("userNo") String userNo);

    List<UserInfoModel> getDataListByPidRange(String uid, long startPid, long endPid);
}
