package com.vip.simple.test;

import com.vip.simple.model.Customer;
import com.vip.simple.orm.service.OrmCustomerService;
import com.vip.simple.orm.service.impl.OrmCustomerServiceImpl;
import com.vip.simple.utils.DataBaseHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dacheng.liu on 2017/4/14.
 */
public class CustomerServiceTest {
    private final OrmCustomerService ormCustomerService;

    public CustomerServiceTest(){
        ormCustomerService = new OrmCustomerServiceImpl();
    }

    @Before
    public void init(){
        // TODO: 2017/4/14 初始化数据库连接
       DataBaseHelper.initDB();
    }

    @Test
    public void getCustomerListTest() throws Exception{
        List<Customer> customerList = ormCustomerService.getCustomerList("");
        Assert.assertEquals(2,customerList.size());
    }

    @Test
    public void getCustomerTest() throws Exception{
        Customer customer = ormCustomerService.getCustomer(1L);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerTest() throws Exception{
        Map<String , Object> paramMap = new HashMap<>();
        paramMap.put("name", "test0001");
        paramMap.put("contact", "tom");
        paramMap.put("telephone","13478900004");
        paramMap.put("email","13478900004@163.com");
        boolean result = ormCustomerService.createCustomer(paramMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomerTest() throws Exception{
        long id = 1L;
        Map<String , Object> paramMap = new HashMap<>();
        paramMap.put("contact", "vector");
        boolean result = ormCustomerService.updateCustomer(id,paramMap);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomerTest() throws Exception{
        long id = 1L;
        boolean result = ormCustomerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }

}
