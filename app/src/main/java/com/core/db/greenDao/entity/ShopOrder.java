package com.core.db.greenDao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShopOrder {
    @Id
    private String id;
    private String name;
    private String imagUrl;
    private String creator;
    private String colorName;
    private String colorNum;
    private String content;
    private double price;
    private double total ;
    private double num ;
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
    public double getNum() {
        return this.num;
    }
    public void setNum(double num) {
        this.num = num;
    }
    public double getTotal() {
        return this.total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getColorNum() {
        return this.colorNum;
    }
    public void setColorNum(String colorNum) {
        this.colorNum = colorNum;
    }
    public String getColorName() {
        return this.colorName;
    }
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
    public String getCreator() {
        return this.creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getImagUrl() {
        return this.imagUrl;
    }
    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }
    @Generated(hash = 863075107)
    public ShopOrder(String id, String name, String imagUrl, String creator,
            String colorName, String colorNum, String content, double price,
            double total, double num) {
        this.id = id;
        this.name = name;
        this.imagUrl = imagUrl;
        this.creator = creator;
        this.colorName = colorName;
        this.colorNum = colorNum;
        this.content = content;
        this.price = price;
        this.total = total;
        this.num = num;
    }
    @Generated(hash = 158702859)
    public ShopOrder() {
    }
}
