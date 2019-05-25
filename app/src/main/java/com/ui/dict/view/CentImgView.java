package com.ui.dict.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.easy.recycleview.custom.bean.AddressHeadImgeSettings;
import com.easy.recycleview.custom.bean.DyItemBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easy.recycleview.outinter.RecycleConfig;
import com.linlsyf.area.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public class CentImgView extends LinearLayout implements IItemView {

    Context mContext;

    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.rootLayout)
    View  mRootView;
    public CentImgView(Context context) {
        super(context);
        initUI(context);
    }

    public CentImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    private void initUI(Context context) {
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.view_center_img, this, true);
        ButterKnife.bind(this);
    }


    @Override
    public void initData( IDyItemBean dataBean) {
       final  DyItemBean   itemBean=(DyItemBean)dataBean;
        AddressHeadImgeSettings settings=itemBean.getHeadImgeSettings();
         img.setImageResource(  settings.getHeadImgDrawableId());

            if (settings.getHeadImgRadius()!=0){
                RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(settings.getHeadImgRadius(),settings.getHeadImgRadius());
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                img.setLayoutParams(params);
            }

        img.setImageResource(settings.getHeadImgDrawableId());

            if (RecycleConfig.getInstance().getThemeConfig().getBgColorResId()!=0){
                mRootView.setBackgroundColor(RecycleConfig.getInstance().getThemeConfig().getBgColorResId());
//                setBackgroundColor(RecycleConfig.getInstance().getThemeConfig().getBgColorResId());
            }

       setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemBean.getOnItemListener()!=null){
                    itemBean.getOnItemListener().onItemClick(ClickTypeEnum.ITEM,itemBean);
                }
            }
        });



    }

}
