package com.ldc.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by dacheng.liu on 2017/11/20.
 */
@Data
public class TaskSequenceModel {
    private Long pid;
    private String idKeyName;
    private Long idValue;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;
}
