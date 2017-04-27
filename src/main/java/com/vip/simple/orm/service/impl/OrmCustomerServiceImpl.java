package com.vip.simple.orm.service.impl;

import com.vip.simple.model.Customer;
import com.vip.simple.orm.service.OrmCustomerService;
import com.vip.simple.utils.DataBaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by dacheng.liu on 2017/4/13.
 */
public class OrmCustomerServiceImpl implements OrmCustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmCustomerServiceImpl.class);

    @Override
    public List<Customer> getCustomerList(String keyWord) {
        String querySql = "SELECT * from customer";
        List<Customer> customerList = DataBaseHelper.queryEntityList(Customer.class,querySql);
        return customerList;
    }

    @Override
    public Customer getCustomer(long id) {
        String querySql = "SELECT * from customer where id = " + id;
        Customer customer = DataBaseHelper.queryEntity(Customer.class,querySql);
        return customer;
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
