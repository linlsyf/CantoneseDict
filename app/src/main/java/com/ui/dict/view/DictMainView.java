package com.ui.dict.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linlsyf.cantonese.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public class DictMainView extends RelativeLayout  {

    Context mContext;

    @Bind(R.id.imgStart)
    View imgStart;

    @Bind(R.id.tvTranslate)
    TextView tvTranslate;
    @Bind(R.id.startPerLayout)
    public View startPerLayout;


    @Bind(R.id.tvBase)
     public TextView tvBase;
    @Bind(R.id.translateLayout)
    public  View translateLayout;
    @Bind(R.id.startLayout)
    public View startLayout;
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
//    public void initData(IDyItemBean itemBean) {
//       final DictBusBean data=(DictBusBean)itemBean;
//
//
//
//        startLayout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (data.getOnItemListener()!=null){
//                    data.getOnItemListener().onItemClick(ClickTypeEnum.ITEM,data);
//                }
//            }
//        });
//        tvBase.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (data.getOnItemListener()!=null){
//                    data.getOnItemListener().onItemClick(ClickTypeEnum.ITEM_LONG,data);
//                }
//            }
//        });
//        translateLayout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (data.getOnItemListener()!=null){
//                    data.getOnItemListener().onItemClick(ClickTypeEnum.RIGHTBUTTION,data);
//                }
//            }
//        });
//        startPerLayout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (data.getOnItemListener()!=null){
//                    data.getOnItemListener().onItemClick(ClickTypeEnum.CUSTOM,data);
//                }
//            }
//        });
//
//
//    }

}
