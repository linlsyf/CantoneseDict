package com.core.db.greenDao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2019/4/7 0007.
 */
@Entity

public class DictType {
    @Id
    private String id;
    private String name;
    private String typecode;

    @Generated(hash = 405892029)
    public DictType(String id, String name, String typecode) {
        this.id = id;
        this.name = name;
        this.typecode = typecode;
    }

    @Generated(hash = 152119867)
    public DictType() {
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

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }
}
