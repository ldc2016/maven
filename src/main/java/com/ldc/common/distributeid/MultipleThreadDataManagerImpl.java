package com.ldc.common.distributeid;

import com.ldc.common.entity.TaskSequenceModel;
import com.ldc.common.entity.UserInfoModel;
import com.ldc.common.repository.TaskSequenceDao;
import com.ldc.common.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dacheng.liu on 2017/11/20.
 */
public class MultipleThreadDataManagerImpl implements MultipleThreadDataManager {
    private Logger LOG = LoggerFactory.getLogger(MultipleThreadDataManagerImpl.class);

    @Resource
    TaskSequenceDao taskSequenceDao;

    @Resource
    UserInfoRepository userInfoRepository;

    /**
     * 采用CAS定理 + 乐观锁机制实现多线程的数据源分pid区间，
     * 一个任务多个线程跑时可以采用这种方式使得每个线程获取的数据不一样，提高任务的执行效率
     * lock-free操作
     * @param uid
     * @param minPid
     * @param maxPid
     * @param step
     * @return
     */
    @Override
    public List<UserInfoModel> getTaskDataListByPidRange(String uid, long minPid, long maxPid, int step) {

        String uniqueKey = "TASK_CODE" + "_" + "yyyyMMdd";
        long value = minPid-1;

        TaskSequenceModel initTaskSequenceModel = new TaskSequenceModel();
        initTaskSequenceModel.setIdKeyName(uniqueKey);
        TaskSequenceModel taskSequenceModelFromDb = taskSequenceDao.getByTaskSequenceModel(initTaskSequenceModel);
        if (taskSequenceModelFromDb == null) {
            initTaskSequenceModel.setIdValue(value);
            int insertResult = taskSequenceDao.insertIntoSeq(initTaskSequenceModel);
            if (insertResult == -1) {
                initTaskSequenceModel = taskSequenceDao.updateSeqValueByKey(initTaskSequenceModel, step);
            }
        } else {
            initTaskSequenceModel = taskSequenceDao.updateSeqValueByKey(taskSequenceModelFromDb, step);
        }

        long currentIdValue = initTaskSequenceModel.getIdValue();

        if(currentIdValue > maxPid){
            return null;
        }
        LOG.error("MultipleThreadDataManagerImpl : getTaskDataListByPidRange =====>：" + (currentIdValue+1) + " ~ " + (currentIdValue+step));
        return userInfoRepository.getDataListByPidRange(uid, currentIdValue + 1, currentIdValue + step);
    }
}
