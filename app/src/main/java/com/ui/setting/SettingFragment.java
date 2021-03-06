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
import com.easy.recycleview.ContentItemView;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.baseview.config.HeadImageViewConfig;
import com.easy.recycleview.custom.baseview.config.HintTextViewConfig;
import com.easy.recycleview.inter.IDyItemBean;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.utils.lib.update.UpdateAPK;
import com.easysoft.widget.config.WidgetConfig;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.TopBarBuilder;

import com.iflytek.voicedemo.MainActivity;
import com.linlsyf.cantonese.R;
import com.ui.common.browser.CommonBrowserFrament;
import com.ui.common.custom.CustomFragment;
import com.ui.common.select.SelectFragment;
import com.ui.common.view.CommonTextItemView;
import com.ui.login.LoginActivity;
import com.ui.setting.about.AboutFragment;

import java.security.acl.Permission;
import java.util.List;

public class SettingFragment extends BaseFragment implements ISafeSettingView{
	DyLayout recycleView;
	  SettingPresenter presenter;
    NavigationBar toolbar;
    View mRootLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

    	View rootView=inflater.inflate(R.layout.fragment_common, null);

    	setRootView(rootView);
       return rootView;

    }
    @Override
    public void initFragment() {
//    	initUIView();
//        initData();


    }
      @Override
    public void initUIView() {
    	  recycleView= getViewById(R.id.dyLayout);
    	   toolbar=getViewById(R.id.toolbar);
          mRootLayout = getViewById(R.id.rootLayout);
      }
      @Override
    public void initData() {
          TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "设置", 0);

          toolbar.resetConfig();
          mRootLayout.setBackgroundColor(WidgetConfig.getInstance().getBgColor());
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
            if(presenter!=null) {
                presenter.updateUserInfo();
            }
        }
        else if(type.equals(BusinessBroadcastUtils.TYPE_CHANGE_THEME)){

            toolbar.resetConfig();
        }
        else if(type.equals(BusinessBroadcastUtils.TYPE_CHANGE_THEME_WB)){
            if(presenter!=null){
                presenter.changeTemeWB();

            }

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
//                UpdateAPK apk=new UpdateAPK( a);
//                apk.Beginning();
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
    public void loadDataStart() {
        initUIView();

        initData();
    }


    @Override
    public void updateItem(final DyItemBean imgBean) {

       recycleView.updateItem(imgBean);

    }

    @Override
    public void updateApk(final String url,final  String name) {

//
//        XXPermissions.with(activity)
//                // 申请安装包权限
//                .permission(Permission.REQUEST_INSTALL_PACKAGES)
//                // 申请悬浮窗权限
//                //.permission(Permission.SYSTEM_ALERT_WINDOW)
//                // 申请通知栏权限
//                //.permission(Permission.NOTIFICATION_SERVICE)
//                // 申请系统设置权限
//                //.permission(Permission.WRITE_SETTINGS)
//                // 申请单个权限
//                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
//                // 申请多个权限
////                .permission(Permission.Group.CALENDAR)
//                .request(new OnPermission() {
//
//                    @Override
//                    public void hasPermission(List<String> granted, boolean all) {
//                        if (all) {
//                           //showToast("获取存储和拍照权限成功");
//                            recycleView.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    UpdateAPK apk=new UpdateAPK(activity,url,name);
////
//                                    apk.Beginning();
//                                }
//                            });
//
//                        } else {
//                            showToast("获取权限成功，部分权限未正常授予");
//                        }
//                    }
//
//                    @Override
//                    public void noPermission(List<String> denied, boolean never) {
//                        if (never) {
//                            showToast("被永久拒绝授权，请手动授予存储和拍照权限");
//                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
//                            XXPermissions.startPermissionActivity(activity, denied);
//                        } else {
//                            showToast("获取存储和拍照权限失败");
//                        }
//                    }
//                });




    }

    @Override
    public void updateUIItem(final boolean isPlaying, final DyItemBean imgBean) {
          activity.runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  View  itemView= recycleView.getItemView(imgBean);
                  if(itemView!=null){
                      ContentItemView contentItemView=(ContentItemView) itemView;
                      contentItemView.mTitleTextView.setText(imgBean.getTitle());
//                      contentItemView.mHintTextView.setText(imgBean.getHint());
                      HintTextViewConfig.load(contentItemView,imgBean);
                      if (!isPlaying){
                          HeadImageViewConfig.load((DyItemBean) imgBean,contentItemView.mHeadImageView);
                      }

                  }
              }
          });



    }
    @Override
    public void showNews() {

    }

    @Override
    public void showTest() {

    }

    @Override
    public void clickAbout() {

                Bundle bundle=new Bundle();

                FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new AboutFragment(), bundle);

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
//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);

        Bundle  bundle=new Bundle();
        bundle.putString("url",url);

        FragmentHelper.showFrag(activity,R.id.container_framelayout,new CommonBrowserFrament(),bundle);
    }

    @Override
    public void selectTheme(List<DyItemBean> dataList) {
        SelectFragment inputFragment=new SelectFragment();
//        inputFragment.setIsColse();
        inputFragment.setOnUpdateSuccessListener(new SelectFragment.OnUpdateSuccessListener() {
            @Override
            public void updateSuccess( List<IDyItemBean> itemBeans) {
                presenter.updateSelectTheme(itemBeans);
//                persenter.updateMsg(itemBeans);
            }
        });

        Bundle bundle=new Bundle();
        bundle.putBoolean(SelectFragment.KEY_INTNET_CLOSE,false);
        inputFragment.initDataMap(dataList);

        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);
    }

    @Override
    public void test() {
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
    }
}

