package com.business.configbean;

import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.inter.IItemView;

/**
 * Created by Administrator on 2019/10/21 0021.
 */

public class DyItemBase {

   protected   DyItemBean itemBean=new DyItemBean();

    public void setOnItemListener(IItemView.onItemClick onItemClick) {
          itemBean.setOnItemListener(onItemClick);

    }

    public DyItemBean getBean() {
        return itemBean;
    }

}
