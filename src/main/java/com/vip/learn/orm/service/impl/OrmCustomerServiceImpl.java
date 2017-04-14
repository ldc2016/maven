package com.vip.learn.orm.service.impl;

import com.vip.learn.model.Customer;
import com.vip.learn.orm.service.OrmCustomerService;

import java.util.List;
import java.util.Map;

/**
 * Created by dacheng.liu on 2017/4/13.
 */
public class OrmCustomerServiceImpl implements OrmCustomerService {
    @Override
    public List<Customer> getCustomerList(String keyWord) {
        return null;
    }

    @Override
    public Customer getCustomer(long id) {
        return null;
    }

    @Override
    public boolean createCustomer(Map<String, Object> filedMap) {
        return false;
    }

    @Override
    public boolean updateCustomer(long id, Map<String, Object> filedMap) {
        return false;
    }

    @Override
    public boolean deleteCustomer(long id) {
        return false;
    }
}
