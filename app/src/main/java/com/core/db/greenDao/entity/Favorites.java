package com.core.db.greenDao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2019/9/26 0026.
 */
@Entity
public class Favorites {
    @Id
    private String id;
    private  String  name;
    private  String  hint;
    private  String  type;
    private  String  content;
    private  String  desc;
    private  String  createorid;
    private  String  createorname;
    private  String  updateuserId;
    private  String  createtime;
    private  String  updatetime;
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUpdatetime() {
        return this.updatetime;
    }
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    public String getCreatetime() {
        return this.createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getUpdateuserId() {
        return this.updateuserId;
    }
    public void setUpdateuserId(String updateuserId) {
        this.updateuserId = updateuserId;
    }
    public String getCreateorname() {
        return this.createorname;
    }
    public void setCreateorname(String createorname) {
        this.createorname = createorname;
    }
    public String getCreateorid() {
        return this.createorid;
    }
    public void setCreateorid(String createorid) {
        this.createorid = createorid;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getHint() {
        return this.hint;
    }
    public void setHint(String hint) {
        this.hint = hint;
    }
    @Generated(hash = 1010743892)
    public Favorites(String id, String name, String hint, String type,
            String content, String desc, String createorid, String createorname,
            String updateuserId, String createtime, String updatetime) {
        this.id = id;
        this.name = name;
        this.hint = hint;
        this.type = type;
        this.content = content;
        this.desc = desc;
        this.createorid = createorid;
        this.createorname = createorname;
        this.updateuserId = updateuserId;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }
    @Generated(hash = 1752129379)
    public Favorites() {
    }

    
}
