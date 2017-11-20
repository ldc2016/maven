package com.ldc.zklearn;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by dacheng.liu on 2017/10/10.
 */
public class ZKChildListener implements IZkChildListener {

    private  Logger logger = LoggerFactory.getLogger(ZKChildListener.class);

    private ZkClient zkClient;

    public ZKChildListener(ZkClient zkClient){
        this.zkClient = zkClient;
    }

    /**
     * Called when the children of the given path changed.
     *
     * @param parentPath    The parent path
     * @param currentChilds The children or null if the root node (parent path) was deleted.
     * @throws Exception
     */
    @Override
    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
        logger.info("ZKChildListener.handleChildChange, 当前父节点是：{},子节点变更为：{}", parentPath, JSON.toJSONString(currentChilds));
        // fixme: 可触发其他操作
        if(CollectionUtils.isNotEmpty(currentChilds)){
            for (String childNode : currentChilds) {
                String nodeData = zkClient.readData(parentPath + "/" + childNode);
                System.setProperty(childNode,nodeData);
                logger.info("ZKChildListener.handleChildChange, *** 子节点：{} 对应的值为：{}",childNode,nodeData);
            }
        }
    }
}
