package com.ui.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.custom.baseview.base.select.MutiTypeSelectUtils;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.linlsyf.cantonese.R;



/**
 * Created by ldh on 2017/5/11.
 */

public class AppItemView extends LinearLayout implements IItemView {
    Context mContext;
    /** 用户头像 */
    ImageView mUserImg;
    /** 用户名*/
    TextView mNameTv;
    public AppItemView(Context context) {
        super(context);
        initUI(context);
    }

    public AppItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context) {
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.view_app_item, this, true);
    }

    public void initSelectUtils(MutiTypeSelectUtils selectUtils) {

    }


    public void initData(DyItemBean itemBean) {

//        mNameTv.setText(itemBean.getTitle());
//        mUserImg.setImageResource(itemBean.getHeadImgeSettings().getHeadImgDrawableId());
//        mUserImg.setImageResource(R.drawable.app_icon);
//        if (!itemBean.getId().equals(TvCatePresenter.ID_EMPTY)){
//              ImageLoadUtils.getInStance().loadResourceId(itemBean.getHeadImgeSettings().getHeadImgDrawableId(),mUserImg);
//
//        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (itemBean.getOnItemListener()!=null){
//                    itemBean.getOnItemListener().onItemClick(ClickTypeEnum.ITEM,itemBean);
//                }
            }
        });
    }

    @Override
    public void initData(IDyItemBean IDyItemBean) {

    }
}

