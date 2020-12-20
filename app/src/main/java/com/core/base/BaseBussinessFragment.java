package com.core.base;

import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.Section;

/**
 * Created by Administrator on 2019/12/1 0001.
 */

public abstract class BaseBussinessFragment extends  BaseFragment {

    public static BaseBussinessFragment instance ;


     public void updateSection(final DyLayout recycleView, final Section section){
         activity.runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 recycleView.updateSection(section);
             }
         });
     }
}
