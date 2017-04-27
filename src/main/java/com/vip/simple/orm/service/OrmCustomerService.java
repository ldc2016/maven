package com.vip.simple.orm.service;

import com.vip.simple.model.Customer;

import java.util.List;
import java.util.Map;

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
