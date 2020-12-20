package com.business.configbean;

import com.core.base.GlobalConstants;
import com.easy.recycleview.bean.CentLayoutConfig;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.inter.IItemView;

/**
 * Created by Administrator on 2019/10/21 0021.
 */

public class JavCarDyItemConfig extends  DyItemBase implements IDyItemconfig {


    @Override
    public void setTitle(String title) {
       itemBean.getCentLayoutConfig()
                .setName(title);
    }

    @Override
    public void init() {
    itemBean=new DyItemBean();
        itemBean.setSpanSize(3);
        itemBean.setCentLayoutConfig(new CentLayoutConfig());
    }


}
