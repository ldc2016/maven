package com.ldc.common.entity;

import java.util.Date;

/**
 * Created by dacheng.liu on 2017/11/20.
 */
public class TaskSequenceModel {
    private Long pid;
    private String idKeyName;
    private Long idValue;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;

    public TaskSequenceModel() {
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getIdKeyName() {
        return idKeyName;
    }

    public void setIdKeyName(String idKeyName) {
        this.idKeyName = idKeyName;
    }

    public Long getIdValue() {
        return idValue;
    }

    public void setIdValue(Long idValue) {
        this.idValue = idValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
