package com.ui.setting;


import com.easy.recycleview.custom.bean.DyItemBean;

/**
 * Created by ldh on 2017/5/11.
 * 个人信息卡片
 */

public class InfoCardBean extends DyItemBean {
    /**企业名字*/
    private String companyName;
    /**用户名字*/
    private String userName;
    /**用户id*/
    private String userId;
    /**职位*/
    private String postionName;
    /**邮箱*/
    private String emilName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostionName() {
        return postionName;
    }

    public void setPostionName(String postionName) {
        this.postionName = postionName;
    }

    public String getEmilName() {
        return emilName;
    }

    public void setEmilName(String emilName) {
        this.emilName = emilName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
