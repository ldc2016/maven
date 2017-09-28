package com.vip.mybatis.learn;

import com.alibaba.fastjson.JSON;
import com.vip.common.entity.UserInfoModel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by dacheng.liu on 2017/9/26.
 */
public class MyBatisLearnTest {
    private static Logger logger = LoggerFactory.getLogger(MyBatisLearnTest.class);

    public static void main(String[] args) throws IOException {

        Reader reader = Resources.getResourceAsReader("myBatis-configuration.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sessionFactory.openSession();

//        UserInfoRepository userInfoRepository = sqlSession.getMapper(UserInfoRepository.class);
//        UserInfoModel userInfoModel = userInfoRepository.getUserInfoModelByUserNo("20161228165343000000000002002012");

        UserInfoModel userInfoModel = sqlSession.selectOne("com.vip.common.repository.UserInfoRepository.getUserInfoModelByUserNo","20161228165343000000000002002012");

        logger.info("********* 单独使用mybatis获取数据库数据 ******** ： {}",JSON.toJSONString(userInfoModel));
        sqlSession.close();
    }
}
