package com.vip.learn.orm.service;

import com.vip.learn.model.Customer;

import java.util.List;
import java.util.Map;
import java.util.Observer;

/**
 * Created by dacheng.liu on 2017/4/13.
 */
public interface OrmCustomerService {

    List<Customer> getCustomerList(String keyWord);
    Customer getCustomer(long id);
    boolean createCustomer(Map<String,Object> filedMap);
    boolean updateCustomer(long id, Map<String,Object> filedMap);
    boolean deleteCustomer(long id);
}
