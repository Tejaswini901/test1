package com.inn.core.entity;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
public class BaseEntity {
    @Basic
    @Column(name = "modificationtime", insertable = true, updatable = true)
    private Date modifiedTime;

    /**
     * The created time.
     */
    @Basic
    @Column(name = "creationtime", insertable = true, updatable = false)
    private Date createdTime;

    public long getModifiedTime() {
        if (modifiedTime!=null){
            return modifiedTime.getTime();
        }
        return  0;

    }

//    public Date getModifiedTime() {
//
//        return  modifiedTime;
//
//    }

    private void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public long getCreatedTime() {
        if (createdTime!=null){
            return createdTime.getTime();
        }
        return  0;

    }
//    public Date getCreatedTime() {
//      return createdTime;
//
//    }

    private void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @PrePersist
    void onCreate() {
        Date date = new Date();
        this.setCreatedTime(date);
        this.setModifiedTime(date);
    }

    @PreUpdate
    void onUpdate() {
        Date date = new Date();
        this.setModifiedTime(date);
    }

}
