package com.ldc.zklearn;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dacheng.liu on 2017/10/10.
 */
public class ZKDataListener implements IZkDataListener {

    private Logger logger = LoggerFactory.getLogger(ZKDataListener.class);

    private ZkClient zkClient;

    public ZKDataListener(ZkClient zkClient){
        this.zkClient = zkClient;
    }

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        logger.info("ZKDataListener.handleDataChange, 当前节点是：{},节点的值是：{}", dataPath, JSON.toJSONString(data));
        System.setProperty(dataPath.replace("/",""),data.toString());
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        logger.info("ZKDataListener.handleDataChange, 当前节点是：{},节点被删除", dataPath);
    }
}
