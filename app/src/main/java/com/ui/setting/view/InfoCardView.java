package com.ui.setting.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.utils.ImageLoadUtils;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.custom.baseview.base.select.MutiTypeSelectUtils;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.linlsyf.area.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ldh on 2017/5/11.
 */

public class InfoCardView extends LinearLayout implements IItemView {
    Context mContext;
   
    /** 企业名 */
    @Bind(R.id.companyNameTv)
    TextView mCompanyNameTv;
    /** 用户头像 */
    @Bind(R.id.userImg)
    ImageView mUserImg;
    /** 用户名*/
    @Bind(R.id.nameTv)
    TextView mNameTv;
    /** 职位 */
    @Bind(R.id.postionTv)
    TextView mPostionTv;
    /** 邮件 */
    @Bind(R.id.emailTv)
    TextView mEmailTv;
    public InfoCardView(Context context) {
        super(context);
        initUI(context);
    }

    public InfoCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context) {
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.view_infocard, this, true);
        ButterKnife.bind(this);
//        setBackgroundColor(getResources().getColor(R.color.blue_theme));
//        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }

//    @Override
//    public void initSelectUtils(MutiTypeSelectUtils selectUtils) {
//
//    }

//    @Override
    public void initSelectUtils(MutiTypeSelectUtils mutiTypeSelectUtils) {

    }

//    @Override
    public void initData(DyItemBean map) {
//
//        final InfoCardBean cardBean=(InfoCardBean)map;
//        mCompanyNameTv.setText(cardBean.getCompanyName());
//        mNameTv.setText(cardBean.getUserName());
//        mPostionTv.setText(cardBean.getPostionName());
//        mEmailTv.setText(cardBean.getEmilName());
//

//        String name = cardBean.getHeadImgeSettings().getHeadImgUserName();
////        String headLoadType = ChatType.USER.getCode();
//
//        RelativeLayout.LayoutParams mHeadParams = (RelativeLayout.LayoutParams) mUserImg.getLayoutParams();
//        if (cardBean.getHeadImgeSettings().getHeadImgRadius() != 0) {
//            mHeadParams.width = cardBean.getHeadImgeSettings().getHeadImgRadius();
//            mHeadParams.height = cardBean.getHeadImgeSettings().getHeadImgRadius();
//            mUserImg.setLayoutParams(mHeadParams);
//        }

//        ImageLoadUtils.getInStance().load("http://192.168.155.1:8090/api/v1/file/down?type=2&name=48cead4a-7ba1-41bb-8cb1-5da76d144dd9",mUserImg);
//        if(StringUtils.isNotEmpty(BusinessBroadcastUtils.USER_VALUE_USER_ID)){
//
//        }else{
            ImageLoadUtils.getInStance().loadResourceId(R.drawable.ic_login_default_user, mUserImg);

//        }


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (cardBean.getOnItemListener()!=null){
//                    cardBean.getOnItemListener().onItemClick(ClickTypeEnum.ITEM,cardBean);
//                }
            }
        });
    }

    @Override
    public void initData(IDyItemBean IDyItemBean) {

    }
}

