package com.ui.catalog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.business.BusinessBroadcastUtils;
import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.core.db.greenDao.entity.Dict;
import com.easy.recycleview.ContentItemView;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.baseview.config.HeadImageViewConfig;
import com.easy.recycleview.custom.baseview.config.HintTextViewConfig;
import com.easysoft.widget.config.WidgetConfig;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.cantonese.R;
import com.ui.common.browser.CommonBrowserFrament;
import com.ui.common.custom.CustomFragment;
import com.ui.common.tempview.TempViewFragment;
import com.ui.dict.DictTypeEnum;
import com.ui.dict.search.SearchDictFragment;
import com.ui.dict.search.sentenceyy.SearchSentenceFragment;
import com.ui.dict.yueping.DictYuePinyFragment;

import java.util.List;

import static com.ui.dict.search.SearchDictFragment.DICT_NAME;


public class CatalogFragment extends BaseFragment implements ICatalogView {
    private static final int REQUEST_CALL_PERMISSION =100001 ;
    DyLayout recycleView;
	  CatalogPresenter presenter;
    NavigationBar toolbar;
    View mRootLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
//    	View rootView=inflater.inflate(R.layout.catelog_fragment, null);
    	View rootView=inflater.inflate(R.layout.fragment_common, null);
    	setRootView(rootView);
       return rootView;

    }
    @Override
    public void initFragment() {
//    	initUIView();
//    	initData();
    }
      @Override
    public void initUIView() {
    	  recycleView= getViewById(R.id.dyLayout);
    	   toolbar=getViewById(R.id.toolbar);
          mRootLayout = getViewById(R.id.rootLayout);
          ImageView imageView=getViewById(R.id.headImgeView);
          imageView.setVisibility(View.VISIBLE);
//          recycleView.initCustomViewCallBack();
      }
      @Override
    public void initData() {
          TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "设置", 0);
          TopBarBuilder.buildOnlyText(toolbar,getActivity(), NavigationBar.Location.RIGHT_FIRST,getString(R.string.refresh),0);
          toolbar.resetConfig();
          mRootLayout.setBackgroundColor(WidgetConfig.getInstance().getBgColor());
      presenter=new CatalogPresenter(this);
      presenter.init();
//
//          if(checkReadPermission(Manifest.permission.READ_EXTERNAL_STORAGE,REQUEST_READ_STOR_PERMISSION)){
//
//          }
    }

    @Override
    public void initListener() {
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {

                if (location == NavigationBar.Location.RIGHT_FIRST) {
                    presenter.init();
                }
            }
        });

    }
    public boolean checkReadPermission(String string_permission,int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(activity, string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(activity, new String[]{string_permission}, request_code);
        }
        return flag;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                    showToast("请允许存储权限后再试");
                } else {//成功
                    // call("tel:"+"10086");
//                    Toast.makeText(this,"请求成功可以使用",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    public void showType(final VideoBussinessItem item, final int type) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle=new Bundle();
                bundle.putInt(SearchDictFragment.DICT_TYPE,type);
                if (type== DictTypeEnum.SEARCH.value()){
                    if (null!=item){
                        if (null!=item.getBindObject()){
                            Dict dict=(Dict) item.getBindObject();
                            bundle.putString(DICT_NAME,dict.getTranName());
                        }
                    }
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new SearchSentenceFragment(), bundle);

                }else if(type==DictTypeEnum.LJ.value()){
                    if (null!=item){
                        if (null!=item.getBindObject()){
                            Dict dict=(Dict) item.getBindObject();
                            bundle.putString(DICT_NAME,dict.getName());
                        }
                    }
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new SearchSentenceFragment(), bundle);
                }else{

                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new SearchDictFragment(), bundle);
                }
            }
        });
    }

    @Override
    public void openUrl(String url) {


        Bundle  bundle=new Bundle();
        bundle.putString("url",url);

        FragmentHelper.showFrag(activity,R.id.container_framelayout,new CommonBrowserFrament(),bundle);
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
                    HintTextViewConfig.load(contentItemView,imgBean);
                    if (!isPlaying){
                        HeadImageViewConfig.load((DyItemBean) imgBean,contentItemView.mHeadImageView);
                    }
                }
            }
        });
    }

    @Override
    public void openTempView(List<DyItemBean> dataList) {
        CustomFragment inputFragment=new CustomFragment();
//        TempViewFragment inputFragment=new TempViewFragment();
        Bundle bundle=new Bundle();
        inputFragment.initDataMap(dataList);
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);
    }

    private String getMIMEType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    @Override
    public void showAllLearn() {
        Bundle bundle=new Bundle();
        bundle.putInt(SearchDictFragment.DICT_TYPE,DictTypeEnum.MARK.value());
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new SearchDictFragment(), bundle);
    }
    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
        if (type.equals(BusinessBroadcastUtils.TYPE_CHANGE_THEME)) {
            toolbar.resetConfig();
        }
    }
	@Override
	public void initUI(final  Section section) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recycleView.updateSection(section);

            }
        });

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
    public void test() {
        Bundle bundle=new Bundle();
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new CatalogFragment(), bundle);
    }
    @Override
    public void toYuePing() {
        Bundle bundle=new Bundle();
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new DictYuePinyFragment(), bundle);

    }
    @Override
    public void openCustomView(List<DyItemBean> dataList) {
        CustomFragment inputFragment=new CustomFragment();
        Bundle bundle=new Bundle();
        inputFragment.initDataMap(dataList);
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);
    }

}

