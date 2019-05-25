package com.core.db.greenDao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DownLoadBean  {
    @Id
    private String id;
    private String url;
    private String name;
    private long downSize;
    public long fileSize;
    public int taskStatus;
    long taskId;
    public int errorCode;

    @Generated(hash = 718647843)
    public DownLoadBean(String id, String url, String name, long downSize,
            long fileSize, int taskStatus, long taskId, int errorCode) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.downSize = downSize;
        this.fileSize = fileSize;
        this.taskStatus = taskStatus;
        this.taskId = taskId;
        this.errorCode = errorCode;
    }

    @Generated(hash = 600345743)
    public DownLoadBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDownSize() {
        return downSize;
    }

    public void setDownSize(long downSize) {
        this.downSize = downSize;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}
