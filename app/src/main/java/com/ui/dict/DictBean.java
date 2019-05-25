package com.ui.dict;

import com.business.bean.BaseBean;

/**
 * Created by Administrator on 2019/2/24 0024.
 */

public class DictBean extends BaseBean{
    private String yyName;
    private String yyPy;
    private String ptPy;
    private String content;

    public String getYyName() {
        return yyName;
    }

    public void setYyName(String yyName) {
        this.yyName = yyName;
    }

    public String getYyPy() {
        return yyPy;
    }

    public void setYyPy(String yyPy) {
        this.yyPy = yyPy;
    }

    public String getPtPy() {
        return ptPy;
    }

    public void setPtPy(String ptPy) {
        this.ptPy = ptPy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
