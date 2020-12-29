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
import com.easy.recycleview.outinter.RecycleConfig;
import com.linlsyf.cantonese.R;
import com.ui.dict.DictBusBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public class DictHomeView extends LinearLayout implements IItemView {

    Context mContext;

    @Bind(R.id.tvYestoday)
    TextView tvYestoday;

    @Bind(R.id.tvToday)
    TextView tvToday;
    @Bind(R.id.unlearn)
    TextView unlearn;

    @Bind(R.id.today)
    TextView today;
    @Bind(R.id.todayLayout)
    View todayLayout;
    @Bind(R.id.yesdayLayout)
    View yesdayLayout;

    @Bind(R.id.tvCount)
    TextView  tvCount;
    @Bind(R.id.tvLearn)
    TextView  tvLearn;
    @Bind(R.id.msgLayout)
    View  countLayout;

    public DictHomeView(Context context) {
        super(context);
        initUI(context);
    }

    public DictHomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    private void initUI(Context context) {
        mContext=context;
          LayoutInflater.from(context).inflate(R.layout.view_dict_type, this, true);
        ButterKnife.bind(this);
    }

//    @Override
    public void initSelectUtils(MutiTypeSelectUtils selectUtils) {

    }

    @Override
    public void initData(IDyItemBean itemBean) {
       final DictBusBean data=(DictBusBean)itemBean;
        unlearn.setText(data.getUnread()+"");
        today.setText(data.getToday()+"");


        tvCount.setText(data.getTotalMsg());
        tvLearn.setText(data.getTotalLearnMsg());
        countLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getOnItemListener()!=null){
                    data.getOnItemListener().onItemClick(ClickTypeEnum.ITEM_LONG,data);
                }
            }
        });


        todayLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getOnItemListener()!=null){
                    data.getOnItemListener().onItemClick(ClickTypeEnum.RIGHTBUTTION,data);
                }
            }
        });
        yesdayLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getOnItemListener()!=null){
                    data.getOnItemListener().onItemClick(ClickTypeEnum.ITEM,data);
                }
            }
        });

        if (RecycleConfig.getInstance().getThemeConfig().getBgColorResId()!=0){
            unlearn.setTextColor(RecycleConfig.getInstance().getThemeConfig().getTitleColorResId());
            today.setTextColor(RecycleConfig.getInstance().getThemeConfig().getTitleColorResId());
            tvToday.setTextColor(RecycleConfig.getInstance().getThemeConfig().getTitleColorResId());
            tvYestoday.setTextColor(RecycleConfig.getInstance().getThemeConfig().getTitleColorResId());
        }

    }

}
