package com.ui.app.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.business.BusinessBroadcastUtils;
import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.search.SearchHeadView;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.cantonese.R;
import com.ui.app.tv.cate.TVFragment;
import com.ui.other.tuling.news.NewsFragment;
import com.ui.video.IVideoHomeView;
import com.ui.video.VideoHideListFragment;
import com.ui.video.VideosFragment;

import java.util.List;

/**
 * 搜索资源文件
 */

public class SearchFragment extends BaseFragment implements IVideoHomeView {
    SearchPresenter persenter;
    DyLayout recycleView;
    private NavigationBar toolbar;
    SearchHeadView searchHeadView;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_tab_goods, null);
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
        persenter=new SearchPresenter(this);
        recycleView = getViewById(R.id.dyLayout);
        toolbar=getViewById(R.id.toolbar);
        searchHeadView=getViewById(R.id.searchView);

        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), getString(R.string.search), 0);

        searchHeadView.getBackLayout().setVisibility(View.GONE);

        searchHeadView.getSearchEditText().setFocusable(true);

        searchHeadView.getSearchEditText().setFocusableInTouchMode(true);

        searchHeadView.getSearchEditText().requestFocus();

    }
    @Override
    public void initListener() {
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.LEFT_FIRST) {
                  FragmentHelper.popBackFragment(activity);

                }

            }
        });

        searchHeadView.setOnTextChangerListener(new SearchHeadView.onTextChangerListener() {
            @Override
            public void onTextChanger(String text) {
                if(StringUtils.isEmpty(text)){
                    ToastUtils.show(activity,"请输入关键字");
                }else{
//                    persenter.search( text);
                }
            }

            @Override
            public void onVoiceClick() {

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

	}

    @Override
    public void initUI(final Section nextSection) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                KeyboardUtils.closeKeybord(activity);
                recycleView.updateSection(nextSection);
            }
        });

    }

    @Override
    public void showItem(final VideoBussinessItem imgBean) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (imgBean.getId().equals(SearchPresenter.ID_NEWS)){
//                    Bundle bundle=new Bundle();
//                    bundle.putSerializable("messageEntity", entity);
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new NewsFragment(), null);

                }

                else if(imgBean.getId().equals(SearchPresenter.ID_BTDOWNLOAD)){
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new VideosFragment(), null);

                }
               else if(imgBean.getId().equals(SearchPresenter.ID_TV)){
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TVFragment(), null);

                }
               else if(imgBean.getId().equals(SearchPresenter.ID_SEARCH)){
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TVFragment(), null);

                }
                else{
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new VideoHideListFragment(), null);

                }

            }
        });
    }

    @Override
    public void loadDataStart() {

    }

    @Override
    public void showToast(String text) {
        super.showToast(text);
    }

    @Override
    public void openVideo(VideoBussinessItem itemBean) {

    }

    @Override
    public void showCustomView(List<DyItemBean> dataList) {

    }


//    @Override
//    public void addLayoutHelper(LayoutHelper helper,boolean isRefresh) {
//        recycleView.addLayoutHelper(helper,isRefresh);
//    }
}
