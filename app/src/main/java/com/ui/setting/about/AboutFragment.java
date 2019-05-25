package com.ui.setting.about;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.BaseFragment;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.DyItemBean;
import com.easysoft.utils.lib.system.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.area.R;
import com.ui.common.view.CommonTextItemView;
import com.ui.setting.ISafeSettingView;


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
                return new CommonTextItemView(context);
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
    public void showUpdate() {

    }

    @Override
    public void logOut() {

    }

    @Override
    public void updateItem(DyItemBean imgBean) {

    }

    @Override
    public void showNews() {

    }

    @Override
    public void showTest() {

    }

    @Override
    public void clickAbout() {

    }
    @Override
    public void openUrl(String url) {
//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);

    }
    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {

    }
}
