package com.ldc.common.repository;

import com.ldc.common.entity.TaskSequenceModel;
import org.springframework.stereotype.Repository;

/**
 * Created by dacheng.liu on 2017/11/20.
 */
@Repository
public class TaskSequenceDao {

    /**
     * CREATE TABLE `task_sequence` (
         `pid` bigint(20) NOT NULL AUTO_INCREMENT,
         `key_name` varchar(255) NOT NULL DEFAULT '',
         `id_value` int(11) NOT NULL DEFAULT '0',
         `is_deleted` bigint(20) NOT NULL DEFAULT '0',
         `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
         `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
          PRIMARY KEY (`pid`),
          UNIQUE KEY `seq_name` (`seq_name`)
       ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='任务序列表';
     *
     *
     */

    /**
     * select * from task_sequence where 0=0
     * @param initTaskSequenceModel
     * @return
     */
    public TaskSequenceModel getByTaskSequenceModel(TaskSequenceModel initTaskSequenceModel){
        return initTaskSequenceModel;
    }

    public int insertIntoSeq(TaskSequenceModel initTaskSequenceModel){
        try {
            return insertSelective(initTaskSequenceModel);
        } catch (Exception e) {
            return -1;
        }
    }

    private int insertSelective(TaskSequenceModel initTaskSequenceModel) {
        return 1;
    }

    /**
     * CAS原理更新DB数据
     * @param initTaskSequenceModel
     * @param step
     * @return
     */
    public TaskSequenceModel updateSeqValueByKey(TaskSequenceModel initTaskSequenceModel, int step){
        int result = 0;
        for (;;) {
            initTaskSequenceModel.setIdValue(initTaskSequenceModel.getIdValue() + step);
            result = updateValueByKey(initTaskSequenceModel);
            if (result > 0) {
                break;
            }
        }
        return initTaskSequenceModel;
    }


    /**
     * 乐观锁
     * update task_sequence set id_value = #{idValue},update_time=now()
     *        where key_name = #{idKeyName} and seq_value &lt; #{seqValue}
     * @param initTaskSequenceModel
     * @return
     */
    private int updateValueByKey(TaskSequenceModel initTaskSequenceModel) {
        return 0;
    }
}
