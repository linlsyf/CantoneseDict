package com.business.configbean;

import com.business.factory.jav.JavFactory;
import com.business.factory.jav.MessageBean;
import com.easy.recycleview.bean.DyItemBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/10/21 0021.
 */

public class ConfigBeanFactory {

    private static Map<String,IDyItemconfig> configBeanMap;

    private static ConfigBeanFactory config;
    public static  final String  TYPE_JAV="jav";
    public static  final String  TYPE_NOR="nor";

    public static ConfigBeanFactory getInstance() {
        if(config == null) {
            config = new ConfigBeanFactory();
            configBeanMap=new HashMap<>();
            init();
        }

        return config;
    }

    public static void init(){
        configBeanMap.put(TYPE_NOR,new DyitemNormelConfig());
        configBeanMap.put(TYPE_JAV,new JavCarDyItemConfig());
    }
     public DyItemBean getItemBean(String type,MessageBean msgBean){

         IDyItemconfig  iDyItemconfig=   configBeanMap.get(type);
            iDyItemconfig.init();
         iDyItemconfig.setTitle(msgBean.getTitle());
         iDyItemconfig.setOnItemListener(msgBean.onItemClick);

         return  iDyItemconfig.getBean();
     }


}
