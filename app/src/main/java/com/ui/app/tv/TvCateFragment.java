package com.ui.app.tv;


import android.content.Context;
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
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.cantonese.R;
import com.ui.app.bt.BtListFragment;
import com.ui.app.tv.cate.TVCustomFragment;
import com.ui.app.tv.cate.TVFragment;
import com.ui.app.view.AppItemView;
import com.ui.video.IVideoHomeView;

import java.util.List;

public class TvCateFragment extends BaseFragment implements IVideoHomeView {
    TvCatePresenter persenter;
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
//       initUIView();
//       initListener();
    }
    @Override
    public void initUIView() {
        persenter=new TvCatePresenter(this);
        recycleView = getViewById(R.id.dyLayout);
        persenter.init();
        toolbar=getViewById(R.id.toolbar);
//        editLayout=getViewById(R.id.editLayout);
        addTv=getViewById(R.id.add);
//        selectAllTv=getViewById(R.id.selectAll);

        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "电视类型", 0);
       recycleView.initCustomViewCallBack(new DyLayout.CustomViewCallBack() {
           @Override
           public View getCustomView(Context context, int type) {
               View  itemView = null;
               if (type==3){
                   itemView=new AppItemView(context);
               }
               return itemView;
           }
       });
    }
    @Override
    public void initListener() {
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.RIGHT_FIRST) {
                  boolean isShow=  persenter.setCanEdit();
                   if (isShow){
//                       editLayout.setVisibility(View.VISIBLE);
                       addTv.setVisibility(View.VISIBLE);
                       selectAllTv.setVisibility(View.VISIBLE);
                   }else{
//                       editLayout.setVisibility(View.GONE);
                   }


                }

            }
        });
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<SelectBean>  selectBeanList= recycleView.getSectionAdapterHelper().getSelect(TvCatePresenter.KEY_SETTING);
//                persenter.setHide(selectBeanList);

            }
        });
        selectAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isShow=  persenter.setCanEdit();
                if (isShow){
//                    editLayout.setVisibility(View.VISIBLE);
                    addTv.setVisibility(View.VISIBLE);
                    selectAllTv.setVisibility(View.VISIBLE);
                }else{
//                    editLayout.setVisibility(View.GONE);
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
           persenter.init();
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
    public void showItem(final VideoBussinessItem imgBean) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Bundle  bundle=new Bundle();
                if (imgBean.getId().equals(TvCatePresenter.ID_FILMS)){
                    bundle.putString("name","film");

                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TVFragment(), bundle);

                }

                else if(imgBean.getId().equals(TvCatePresenter.ID_TV_FILM)){

                    bundle.putString("name","video");

                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TVFragment(), bundle);

                }
                else if(imgBean.getId().equals(TvCatePresenter.ID_TV)){
                    bundle.putString("name","tv");
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TVFragment(), bundle);

                }
                else if(imgBean.getId().equals(TvCatePresenter.ID_CUSTOM)){
                    bundle.putString("name","custom");
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new
                            TVCustomFragment(), bundle);

                }
                else if(imgBean.getId().equals(TvCatePresenter.ID_BTDOWNLOAD)){
                    bundle.putString("name","btdownloaed");
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new
                            BtListFragment(), bundle);

                }
                else if(imgBean.getId().equals(TvCatePresenter.ID_translate)){
                    bundle.putString("name","translate");
//                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new
//                            DictHomeFragment(), bundle);

                }
                else{
                    bundle.putString("name","other");
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TVFragment(), bundle);

                }


            }
        });
    }

    @Override
    public void openVideo(VideoBussinessItem itemBean) {

    }

    @Override
    public void showCustomView(List<DyItemBean> dataList) {

    }

    @Override
    public void loadDataStart() {

    }


//    @Override
//    public void addLayoutHelper(LayoutHelper helper,boolean isRefresh) {
//        recycleView.addLayoutHelper(helper,isRefresh);
//    }
}
