package com.ui.setting.about;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.BaseFragment;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.baseview.item.ContentItemView;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.area.R;
import com.ui.common.browser.CommonBrowserFrament;
import com.ui.common.view.CommonTextItemView;


public class AboutFragment extends BaseFragment implements IAboutView {
    AboutPresenter presenter;
    DyLayout recycleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_common, null);
        setRootView(rootView);
        return rootView;

    }
    @Override
    public void initFragment() {
        // initData();
        initUIView();
        initListener();
    }
    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void initUIView() {
        recycleView=getViewById(R.id.dyLayout);
        NavigationBar   toolbar=getViewById(R.id.toolbar);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), getString(R.string.about_title), 0);
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.LEFT_FIRST) {
                    FragmentHelper.popBackFragment(getActivity());
                }

            }
        });

        recycleView.initCustomViewCallBack(new DyLayout.CustomViewCallBack() {
            @Override
            public View getCustomView(Context context, int i) {
                View itemView=null;
                 if (i==4){
                     itemView=new CommonTextItemView(context);
                 }else{
                     itemView=new ContentItemView(context);
                 }
                return itemView;
            }
        });
        presenter=new AboutPresenter(this);
        presenter.init();

    }

    @Override
    public void initUI(Section section) {
        recycleView.updateSection(section);



    }


    @Override
    public void openUrl(String url) {


         Bundle  bundle=new Bundle();
        bundle.putString("url",url);

        FragmentHelper.showFrag(activity,R.id.container_framelayout,new CommonBrowserFrament(),bundle);

    }

    @Override
    public void openUrlByBroswer(String url) {
                Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {

    }
}
