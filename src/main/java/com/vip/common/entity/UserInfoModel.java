package com.vip.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by dacheng.liu on 2017/8/9.
 */
@Data
public class UserInfoModel implements Serializable{
    private String userName;
    private String idCardNo;
    private String sex;
    private String userNo;
}
