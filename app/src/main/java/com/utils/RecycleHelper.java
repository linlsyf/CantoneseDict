package com.utils;

import com.easy.recycleview.custom.bean.DyItemBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/6/7 0007.
 */

public class RecycleHelper {


     public static  List<IDyItemBean> wrappingList( List<IDyItemBean>  sectionList){

         int i=0;
         List<IDyItemBean>  newSectionList=new ArrayList<>();

         for (IDyItemBean itemBean:sectionList ) {
               if (i!=0){
                   DyItemBean itemSpliteBean=new DyItemBean();
                   itemSpliteBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
                   newSectionList.add(itemSpliteBean);
               }
             newSectionList.add(itemBean);
             i=i+1;
         }

         return newSectionList;
     }


}
