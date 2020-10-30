package com.bean;

import com.business.bean.BaseBean;


public class AppMsg extends BaseBean {

    private  String version_code;
    private  String apk_name;

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getApk_name() {
        return apk_name;
    }

    public void setApk_name(String apk_name) {
        this.apk_name = apk_name;
    }
}
