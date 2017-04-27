package com.vip.simple.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlInlineBinaryData;

/**
 * Created by dacheng.liu on 2017/4/13.
 */
@Data
public class Customer {

    private long id;
    private String name;
    private String contact;
    private String telephone;
    private String email;
    private String remark;

}
