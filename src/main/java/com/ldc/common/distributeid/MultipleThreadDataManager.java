package com.ldc.common.distributeid;

import com.ldc.common.entity.UserInfoModel;

import java.util.List;

/**
 * Created by dacheng.liu on 2017/11/20.
 */
public interface MultipleThreadDataManager {

    public List<UserInfoModel> getTaskDataListByPidRange(String uid, long minPid, long maxPid, int step);
}
