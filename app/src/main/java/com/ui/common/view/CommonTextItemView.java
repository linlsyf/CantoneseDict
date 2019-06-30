package com.ui.common.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.ViewGroup;

import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;

/**
 * Created by Administrator on 2019/5/1 0001.
 */

public class CommonTextItemView extends AppCompatTextView implements IItemView {
    public CommonTextItemView(Context context) {
        super(context);
    }

    private void initUI(Context context) {
         setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
         setGravity(Gravity.CENTER);
    }
    @Override
    public void initData(IDyItemBean IDyItemBean) {

         setText(IDyItemBean.getTitle());
        DyItemBean itemBean=(DyItemBean)IDyItemBean;
         if (itemBean.getTitleSettings().getTextSize()!=0){
             setTextSize(itemBean.getTitleSettings().getTextSize());
         }

//         float textFloat=16;
//          setTextSize();

    }
}
