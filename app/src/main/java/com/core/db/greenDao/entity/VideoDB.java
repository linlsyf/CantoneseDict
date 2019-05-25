package com.core.db.greenDao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class VideoDB {
    @Id
    private String id;
    private String name;
    private String data;
    private String oldFilePath;
    private String thumbPath;

    private long duration;
    private long size;
    private String durationString;






    @Generated(hash = 1751516696)
    public VideoDB(String id, String name, String data, String oldFilePath,
            String thumbPath, long duration, long size, String durationString) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.oldFilePath = oldFilePath;
        this.thumbPath = thumbPath;
        this.duration = duration;
        this.size = size;
        this.durationString = durationString;
    }

    @Generated(hash = 992505193)
    public VideoDB() {
    }






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldFilePath() {
        return oldFilePath;
    }

    public void setOldFilePath(String oldFilePath) {
        this.oldFilePath = oldFilePath;
    }
}
