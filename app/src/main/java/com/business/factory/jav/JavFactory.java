package com.business.factory.jav;

import com.business.configbean.ConfigBeanFactory;
import com.easy.recycleview.bean.DyItemBean;

import java.util.Map;

/**
 * Created by Administrator on 2019/10/21 0021.
 */

public class JavFactory {

    private static JavFactory config;

    public static JavFactory getInstance() {
        if(config == null) {
            config = new JavFactory();
        }

        return config;
    }



    public static DyItemBean getBean(String type,MessageBean msgBean){

        DyItemBean itemBean=ConfigBeanFactory.getInstance() .getItemBean(type,msgBean);


        return  itemBean;

    }

}
