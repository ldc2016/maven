package com.ldc.zklearn;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * Created by dacheng.liu on 2017/10/10.
 */
public class zkClientTest {

    public static void main(String[] args) throws InterruptedException {

        // 1.建立zk连接
        ZkClient zkClient = ZKUtils.getZKConnection(ZKUtils.ZK_SERVER_ADDRESS,5000);


        String path = "/testZk";

        // 将指定节点下的值
        List<String> childNodes = zkClient.getChildren(path);
        for (String childNode :childNodes) {
            String nodeData = zkClient.readData(path + "/" + childNode);
            System.setProperty(childNode,nodeData);
        }

        // 6.监听节点的变化
        ZKUtils.subscribeChildNodeChanges(zkClient,path,new ZKChildListener(zkClient));

        // 7.监听节点值的变化
        ZKUtils.subscribeNodeDataChanges(zkClient,path,new ZKDataListener(zkClient));


        // 删除节点
//        ZKUtils.deleteRecursiveNode(zkClient,path);

        // 2.创建节点：/testZk
//        ZKUtils.createNode(zkClient,"/testZk/test00",1, CreateMode.PERSISTENT);

        // 3.获取节点 /testZk的值
        ZKUtils.getDateFromNode(zkClient,path);

        // 4.更新节点的值
//        UserInfoModel userInfoModel = new UserInfoModel();
//        userInfoModel.setSex("1");
//        userInfoModel.setUserName("test1");
        ZKUtils.updateNodeValue(zkClient,path,2);

        // 5.获取节点 /testZk的值
        ZKUtils.getDateFromNode(zkClient,path);

//        userInfoModel.setSex("0");
//        userInfoModel.setUserName("test22222");
//        ZKUtils.updateNodeValue(zkClient,path,userInfoModel);

//        ZKUtils.createNode(zkClient,"/testZk/test02",1, CreateMode.PERSISTENT);
        ZKUtils.subscribeNodeDataChanges(zkClient,"/testZk/test02",new ZKDataListener(zkClient));

        System.out.println("***** System,Properties ****** :  " + JSON.toJSONString(System.getProperty("test02")));
        Thread.sleep(Integer.MAX_VALUE);
    }
}
