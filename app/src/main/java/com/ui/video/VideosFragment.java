package com.ui.video;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.business.BusinessBroadcastUtils;
import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.cantonese.R;
import com.utils.OpenFileUtils;

import java.util.List;

/**
 * 展示商品
 */

public class VideosFragment extends BaseFragment implements IVideoHomeView {
    VideoHomePresenter persenter;
    DyLayout recycleView;
    private NavigationBar toolbar;
//    LinearLayout editLayout;
    TextView  addTv;
    TextView  selectAllTv;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
    	View rootView=inflater.inflate(R.layout.fragment_common, null);
    	setRootView(rootView);
       return rootView;

    }
    @Override
    public void initFragment() {
       initUIView();
       initListener();
    }
    @Override
    public void initUIView() {
        persenter=new VideoHomePresenter(this);
        recycleView = getViewById(R.id.dyLayout);
        persenter.init(getActivity());
        toolbar=getViewById(R.id.toolbar);
//        editLayout=getViewById(R.id.editLayout);
        addTv=getViewById(R.id.add);
//        selectAllTv=getViewById(R.id.selectAll);
//        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "本地视频", 0);
        TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.RIGHT_FIRST, "选择", 0);
//        addTv.setText(R.string.hide);

    }
    @Override
    public void initListener() {
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.RIGHT_FIRST) {
                  boolean isShow=  persenter.setCanEdit();
                   if (isShow){
                       //editLayout.setVisibility(View.VISIBLE);
                       addTv.setVisibility(View.VISIBLE);
                       selectAllTv.setVisibility(View.VISIBLE);
                   }else{
                       //editLayout.setVisibility(View.GONE);
                   }


                }

            }
        });
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<SelectBean>  selectBeanList= recycleView.getSectionAdapterHelper().getSelect(VideoHomePresenter.KEY_SETTING);
//                persenter.setHide(selectBeanList);

            }
        });
        selectAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isShow=  persenter.setCanEdit();
                if (isShow){
                    //editLayout.setVisibility(View.VISIBLE);
                    addTv.setVisibility(View.VISIBLE);
                    selectAllTv.setVisibility(View.VISIBLE);
                }else{
                   // editLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {

         if(type.equals(BusinessBroadcastUtils.TYPE_REFRESH_VIDEO)){
           persenter.init(getActivity());
        }
//        if(type.equals(BusinessBroadcastUtils.TYPE_LOGIN_FAILS)){
//
//            noticeTv.setVisibility(View.VISIBLE);
//        }
//        else if(type.equals(BusinessBroadcastUtils.TYPE_GOODS_ADD_SUCESS)){
//            persenter.list();
//        }
	}

    @Override
    public void initUI(final Section nextSection) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                recycleView.updateSection(nextSection);
            }
        });

    }

    @Override
    public void showItem(VideoBussinessItem itemBean) {

    }

    @Override
    public void openVideo(final VideoBussinessItem itemBean) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                OpenFileUtils.openVideoBySelf(getActivity(),itemBean);
            }
        });
    }

    @Override
    public void showCustomView(List<DyItemBean> dataList) {

    }


    @Override
    public void loadDataStart() {

    }
}
