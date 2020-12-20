package com.ui.other.tuling.news;

import android.os.Bundle;

import com.core.base.BaseFragment;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.Section;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.area.R;
import com.ui.other.tuling.entity.NewsEntity;
import com.ui.other.tuling.news.newdetail.NewDetailFragment;

/**
 * Created by sunfusheng on 2015/2/5.
 */
public class NewsFragment extends BaseFragment implements INewsView {


    private NavigationBar toolbar;

    DyLayout recycleView;

    NewsPresenter persenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_common;
    }

    @Override
    public void initFragment() {

      initUIView();
        initData();
        initView();
    }

    @Override
    public void initUIView() {

        recycleView = getViewById(R.id.dyLayout);
        toolbar=getViewById(R.id.toolbar);
        TopBarBuilder.buildCenterTextTitle(toolbar, activity, "新闻", 0);

    }

    public void initData() {
        persenter=new NewsPresenter(this);
        persenter.init();


    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void onDestroy() {
//        service.
        super.onDestroy();
    }

    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {

    }

    private void initView() {
//        initXlistView();


    }




    @Override
    public void initUI(final Section section) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                recycleView.updateSection(section);
            }
        });
    }

    @Override
    public void showItem(final NewsEntity itemBean) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Bundle bundleDetail=new Bundle();
                bundleDetail.putString("url",  itemBean.getDetailurl());
                FragmentHelper.showFrag(activity, R.id.container_framelayout, new NewDetailFragment(), bundleDetail);
            }
        });
    }

    @Override
    public void loadDataStart() {

    }
}
