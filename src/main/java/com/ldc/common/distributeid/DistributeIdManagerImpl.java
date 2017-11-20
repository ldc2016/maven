package com.ldc.common.distributeid;

import com.ldc.common.repository.DistributeIdDao;
import com.ldc.common.utils.DateFormatTools;
import com.ldc.common.utils.DistributeIdTools;
import com.ldc.common.utils.StringTools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
/**
 * Created by dacheng.liu on 2017/11/20.
 */
public class DistributeIdManagerImpl extends AbstractDistributedIdManager implements DistributeIdManager {

    private Logger logger = LoggerFactory.getLogger(DistributeIdManagerImpl.class);
    private static final Integer SCAN_STEP = 1000;

    @Autowired
    private DistributeIdDao distributeIdDao;

    /**
     * 重新初始化给定idKeyName的值，子类自己实现
     *
     * @param idKeyName
     */
    @Override
    public Long reinitializeIdValue(String idKeyName) {
        logger.info("idValue reinitializeIdValue update......");
        try{
            Long idValue = distributeIdDao.getValueBySequenceName(idKeyName);
            Integer num= distributeIdDao.updateValueByNameAndCurrentValue(idValue + SCAN_STEP, idKeyName, idValue);
            if(num!=1){
                throw new RuntimeException("idValue reInit happen error,please retry again! idKeyName :" + idKeyName);
            }
            return idValue;

        }catch(Exception e){
            logger.error("==>DistributeIdManagerImpl.reinitializeIdValue error,idKeyName: {}, exception info:{}",idKeyName,e);
            throw new RuntimeException("==>DistributeIdManagerImpl.reinitializeIdValue error,idKeyName:"+idKeyName, e);
        }
    }

    /**
     * 获取流水号
     * 必须新起一个事务，否则采用悲观锁方式生成唯一id有问题
     * @param idKeyName
     * @param uid
     */
    @Override
    @Transactional(value="transactionManager" , propagation= Propagation.REQUIRES_NEW)
    public String getUniqueDistributeIdByIdKeyName(String idKeyName, String uid) throws Exception {
        if(uid.length()<2){
            uid = StringTools.alignRight(uid,2,"0");
        }
        String uidFlag = StringUtils.substring(uid, uid.length() - 2, uid.length());

        Long idValue = getUniqueDistributeId(idKeyName);

        String dateFlag= DateFormatTools.convertDateToString(new Date(),"yyyyMMddHHmmss");

        String sequenceStr= StringTools.alignRight(String.valueOf(idValue),10,"0");

        String uniqueId = DistributeIdTools.buildDistributeUniqueId(dateFlag,"001","0",uidFlag,sequenceStr);

        return uniqueId;
    }

}
