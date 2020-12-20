package com.business.configbean;

import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.inter.IItemView;

/**
 * Created by Administrator on 2019/10/21 0021.
 */

public interface IDyItemconfig {
//      get(String type);
//     void  init(DyItemBean itemBean);
   void  setTitle(String title);

    void  init();
    void setOnItemListener(IItemView.onItemClick onItemClick);

    DyItemBean getBean();



}
