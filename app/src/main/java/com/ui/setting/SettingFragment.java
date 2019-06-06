package com.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.business.BusinessBroadcastUtils;
import com.core.base.BaseFragment;
import com.core.update.UpdateAPK;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.DyItemBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easysoft.utils.lib.system.FragmentHelper;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.config.WidgetConfig;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.iflytek.voicedemo.MainActivity;
import com.linlsyf.area.R;
import com.ui.common.custom.CustomFragment;
import com.ui.common.select.SelectFragment;
import com.ui.login.LoginActivity;
import com.ui.setting.about.AboutFragment;
import com.webview.WebMainActivity;

import java.util.List;


//import cn.smssdk.SMSSDK;



public class SettingFragment extends BaseFragment implements ISafeSettingView{
	DyLayout recycleView;
	  SettingPresenter presenter;
    NavigationBar toolbar;
    View mRootLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

    	View rootView=inflater.inflate(R.layout.fragment_common, null);
//    	if(recycleView==null){
//    		recycleView=new MySettingContentView(getActivity());
//    	}
    	
    	setRootView(rootView);
       return rootView;

    }
    @Override
    public void initFragment() {
    	initUIView();
    	initData();
    }
      @Override
    public void initUIView() {
    	  recycleView= getViewById(R.id.dyLayout);
    	   toolbar=getViewById(R.id.toolbar);
          mRootLayout = getViewById(R.id.rootLayout);

		  TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "设置", 0);
//		  recycleView.initCustomViewCallBack(new DyLayout.CustomViewCallBack() {
//              @Override
//              public View getCustomView(Context context, int type) {
//
//                      View  itemView=null;
//                      if (type== 3){
//                          itemView=new InfoCardView(getContext());
//                          return itemView;
//                      }
//
//                      return itemView;
//                  }
//
//          });

          toolbar.resetConfig();
          mRootLayout.setBackgroundColor(WidgetConfig.getInstance().getBgColor());


          toolbar.resetConfig();
     
      }
      @Override
    public void initData() {
    presenter=new SettingPresenter(this);
    presenter.init();
    		
    }



    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
        if(type.equals(BusinessBroadcastUtils.TYPE_LOGIN_SUCESS)){
            presenter.updateUserInfo();
        }
        else if(type.equals(BusinessBroadcastUtils.TYPE_CHANGE_THEME)){

            toolbar.resetConfig();
        }
        else if(type.equals(BusinessBroadcastUtils.TYPE_CHANGE_THEME_WB)){

            presenter.changeTemeWB();
        }
	}
	@Override
	public void initUI(Section section) {
		recycleView.updateSection(section);
		
	}

    @Override
    public void showUpdate() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UpdateAPK apk=new UpdateAPK( getActivity());
                apk.Beginning();
            }
        });

    }

    @Override
    public void logOut() {
        Intent homeIntent=new Intent(getActivity(),LoginActivity.class);
        startActivity(homeIntent);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        //用完回调要注销掉，否则可能会出现内存泄露
//        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showToast(String text) {
    ToastUtils.show(getActivity(),text);
    }

    @Override
    public void updateItem(final DyItemBean imgBean) {

    }
    @Override
    public void showNews() {

    }

    @Override
    public void showTest() {

    }

    @Override
    public void clickAbout() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Bundle bundle=new Bundle();

                FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new AboutFragment(), bundle);

            }
        });
    }

    @Override
    public void openCustomView(List<DyItemBean> dataList) {
        CustomFragment inputFragment=new CustomFragment();

        Bundle bundle=new Bundle();

        inputFragment.initDataMap(dataList);

        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);
    }

    @Override
    public void openUrl(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);


    }

    @Override
    public void selectTheme(List<DyItemBean> dataList) {
        SelectFragment inputFragment=new SelectFragment();

        inputFragment.setOnUpdateSuccessListener(new SelectFragment.OnUpdateSuccessListener() {
            @Override
            public void updateSuccess( List<IDyItemBean> itemBeans) {
                presenter.updateSelectTheme(itemBeans);
//                persenter.updateMsg(itemBeans);
            }
        });

        Bundle bundle=new Bundle();

        inputFragment.initDataMap(dataList);

        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);
    }

    @Override
    public void test() {
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
    }
}

