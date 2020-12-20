package com.ui.other.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.easy.recycleview.DyLayout;


/**
 * 个人设置内容布局
 */

public class OtherContentView extends DyLayout {

    public OtherContentView(Context context) {
        super(context);
    }

    public OtherContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View addItemView(int viewType) {
        View  itemView;
//        if (viewType== IItemView.ViewTypeEnum.INFO_CARD_VIEW.value()){
//            itemView=new InfoCardView(getContext());
//            return itemView;
//        }

        return super.addItemView(viewType);
    }
}
