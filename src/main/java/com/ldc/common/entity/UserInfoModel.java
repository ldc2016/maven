package com.ldc.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dacheng.liu on 2017/8/9.
 */
@Data
public class UserInfoModel implements Serializable{
    private String userName;
    private String idCardNo;
    private String sex;
    private String userNo;

    private List<Integer> testList;

    @Override
    public String toString() {
        return "UserInfoModel{" +
                "userNo='" + userNo + '\'' +
                '}';
    }
}
