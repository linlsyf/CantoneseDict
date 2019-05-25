package com.core.db.greenDao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by anye0 on 2016/7/24.
 */
@Entity
public class Dict {
    @Id
    private String id;
    private String name;
    private String createTime;
    private String modifyTime;
    private int type;
    private int status;
    private int count;//查询点击次数
    private int simple;//列句

    private String tranName="";
    private String tranPy="";
    private String py;
    private String content;


    @Generated(hash = 663597978)
    public Dict(String id, String name, String createTime, String modifyTime,
            int type, int status, int count, int simple, String tranName,
            String tranPy, String py, String content) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.type = type;
        this.status = status;
        this.count = count;
        this.simple = simple;
        this.tranName = tranName;
        this.tranPy = tranPy;
        this.py = py;
        this.content = content;
    }
    @Generated(hash = 1138334630)
    public Dict() {
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getPy() {
        return this.py;
    }
    public void setPy(String py) {
        this.py = py;
    }
    public String getTranPy() {
        return this.tranPy;
    }
    public void setTranPy(String tranPy) {
        this.tranPy = tranPy;
    }
    public String getTranName() {
        return this.tranName;
    }
    public void setTranName(String tranName) {
        this.tranName = tranName;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getSimple() {
        return this.simple;
    }
    public void setSimple(int simple) {
        this.simple = simple;
    }
    public int getCount() {
        return this.count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getModifyTime() {
        return this.modifyTime;
    }
    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
   
}
