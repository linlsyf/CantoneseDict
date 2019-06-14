package com.ui.dict.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.recycleview.custom.baseview.base.select.MutiTypeSelectUtils;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.linlsyf.area.R;
import com.ui.dict.DictBusBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public class DictMainView extends LinearLayout implements IItemView {

    Context mContext;

    @Bind(R.id.imgStart)
    View imgStart;

    @Bind(R.id.tvTranslate)
    TextView tvTranslate;


    @Bind(R.id.tvBase)
    TextView tvBase;
    @Bind(R.id.translateLayout)
    View translateLayout;
    @Bind(R.id.startLayout)
    View startLayout;
    @Bind(R.id.rootLayout)
    View  mRootView;

    public DictMainView(Context context) {
        super(context);
        initUI(context);
    }

    public DictMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    private void initUI(Context context) {
        mContext=context;
          LayoutInflater.from(context).inflate(R.layout.view_dict_main, this, true);
        ButterKnife.bind(this);
    }

//    @Override
    public void initSelectUtils(MutiTypeSelectUtils selectUtils) {

    }

    @Override
    public void initData(IDyItemBean itemBean) {
       final DictBusBean data=(DictBusBean)itemBean;



        startLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getOnItemListener()!=null){
                    data.getOnItemListener().onItemClick(ClickTypeEnum.ITEM,data);
                }
            }
        });
        tvBase.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getOnItemListener()!=null){
                    data.getOnItemListener().onItemClick(ClickTypeEnum.ITEM_LONG,data);
                }
            }
        });
        translateLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getOnItemListener()!=null){
                    data.getOnItemListener().onItemClick(ClickTypeEnum.RIGHTBUTTION,data);
                }
            }
        });


    }

}
