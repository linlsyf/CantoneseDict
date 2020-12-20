package com.business.configbean;

/**
 * Created by Administrator on 2019/10/21 0021.
 */

public class DyitemNormelConfig extends  DyItemBase implements IDyItemconfig {
    @Override
    public void setTitle(String title) {
          itemBean.setTitle(title);
    }

    @Override
    public void init() {

    }
}
