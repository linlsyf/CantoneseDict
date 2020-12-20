package com.business.factory.jav;

import com.easy.recycleview.bean.DyItemBean;

import java.util.HashMap;

/**
 * Created by Administrator on 2019/10/21 0021.
 */

public class CofigMap extends HashMap<String,DyItemBean> {

    @Override
    public DyItemBean get(Object key) {
          if (!containsKey(key)){
              DyItemBean itemBean=new DyItemBean();
              put(key.toString(),itemBean);
              return   itemBean;
          }


        return super.get(key);
    }
}
