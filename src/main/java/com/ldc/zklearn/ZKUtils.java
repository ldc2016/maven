package com.ldc.zklearn;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dacheng.liu on 2017/10/10.
 */
public class ZKUtils {

    private static Logger logger = LoggerFactory.getLogger(ZKUtils.class);

    public static final String ZK_SERVER_ADDRESS = "10.199.204.246:2181,10.199.204.246:2182,10.199.204.246:2182";

    public static ZkClient getZKConnection(String zkServerAddr, int connectionTimeOut){

        ZkClient zkClient = new ZkClient(ZK_SERVER_ADDRESS,5000,5000,new CustomSerializer());
        if(zkClient != null){
            logger.info("ZKUtils.getZKConnection, ********** 成功连接到zk! ***********");
        }

        return zkClient;
    }


    /**
     * 创建节点
     * @param path
     * @param data
     * @param createMode
     * @return
     */
    public static String createNode(ZkClient zkClient ,String path, Object data, CreateMode createMode){

        if(zkClient == null || StringUtils.isBlank(path)){
            logger.info("ZKUtils.createNode, required param zkClient or path is Null!");
            return null;
        }

        String resultPath = zkClient.create(path,data,CreateMode.PERSISTENT);
        if(StringUtils.isNotBlank(resultPath)){
            logger.info("ZKUtils.createNode, ****** 节点：{} 被成功创建了！******** ",resultPath);
            return resultPath;
        }
        return null;
    }

    /**
     * 删除单一节点
     * @param zkClient
     * @param path
     * @return
     */
    public static Boolean deleteNode(ZkClient zkClient ,String path){

        if(zkClient == null || StringUtils.isBlank(path)){
            logger.info("ZKUtils.deleteNode, required param zkClient or path is Null!");
            return null;
        }

        Boolean deleteResult = zkClient.delete(path);

        logger.info("ZKUtils.deleteNode, 节点：{} 删除结果：{}！",path,deleteResult);

        return deleteResult;

    }

    /**
     * 删除包含子节点的节点
     * @param zkClient
     * @param path
     * @return
     */
    public static Boolean deleteRecursiveNode(ZkClient zkClient ,String path){

        if(zkClient == null || StringUtils.isBlank(path)){
            logger.info("ZKUtils.deleteNode, required param zkClient or path is Null!");
            return null;
        }

        Boolean deleteResult = zkClient.deleteRecursive(path);

        logger.info("ZKUtils.deleteNode, 节点：{} 删除结果：{}！",path,deleteResult);

        return deleteResult;

    }

    /**
     * 获取节点的值
     * @param zkClient
     * @param path
     * @return
     */
    public static Object getDateFromNode(ZkClient zkClient ,String path){

        if(zkClient == null || StringUtils.isBlank(path)){
            logger.info("ZKUtils.getDateFromNode, required param zkClient or path is Null!");
            return null;
        }
        Stat stat = new Stat();
        Object obj = zkClient.readData(path,stat);

        logger.info("ZKUtils.getDateFromNode, 节点：{} 的值为：{}！",path, JSON.toJSONString(obj));

        return obj;
    }

    /**
     * 判断节点是否存在
     * @param zkClient
     * @param path
     * @return
     */
    public static Object isExistsNode(ZkClient zkClient ,String path){

        if(zkClient == null || StringUtils.isBlank(path)){
            logger.info("ZKUtils.isExistsNode, required param zkClient or path is Null!");
            return null;
        }
        Boolean result = zkClient.exists(path);
        logger.info("ZKUtils.isExistsNode, 节点：{} 是否存在：{}！",path, result);
        return result;
    }

    /**
     * 更新节点的值
     * @param zkClient
     * @param path
     * @return
     */
    public static void updateNodeValue(ZkClient zkClient ,String path, Object obj){
        if(zkClient == null || StringUtils.isBlank(path)){
            logger.info("ZKUtils.isExistsNode, required param zkClient or path is Null!");
            return ;
        }
        if(zkClient.exists(path)){
            logger.info("ZKUtils.isExistsNode, 节点：{} 存在,现将其值修改为：{}！",path,JSON.toJSONString(obj));
            zkClient.writeData(path,obj);
        }
    }

    /**
     * 监听指定节点的变化
     * @param zkClient
     * @param path
     */
    public static void subscribeChildNodeChanges(ZkClient zkClient ,String path,IZkChildListener zkChildListener){
        if(zkClient == null || StringUtils.isBlank(path)){
            logger.info("ZKUtils.subscribeChildNodeChanges, required param zkClient or path is Null!");
            return ;
        }

        zkClient.subscribeChildChanges(path,zkChildListener);
    }

    /**
     * 监听指定节点的变化
     * @param zkClient
     * @param path
     */
    public static void subscribeNodeDataChanges(ZkClient zkClient ,String path,IZkDataListener zkDataListener){
        if(zkClient == null || StringUtils.isBlank(path)){
            logger.info("ZKUtils.subscribeNodeDataChanges, required param zkClient or path is Null!");
            return ;
        }

        zkClient.subscribeDataChanges(path,zkDataListener);
    }

}
